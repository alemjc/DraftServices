package listeners;

import Entities.Player;
import Entities.Position;
import Utilities.DataRetriever;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mongodb.*;
import org.mongojack.JacksonDBCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class ContextListenerImpl implements ServletContextListener {

    private final String SPORTS_URL = "http://api.cbssports.com/fantasy/players/list?version=3.0&SPORT=%s&response_format=JSON";
    private final String sports[] = {"football", "baseball", "basketball"};

    private void saveToCollection(DBCollection collection, List<Player> players, String sport, Map<String, Position> positionHashMap){

        final JacksonDBCollection<Player, String> wrappedCollection = JacksonDBCollection.wrap(collection, Player.class, String.class);

        players
                .stream()
                .filter(player -> !player.getFirstName().isEmpty() && !player.getLastName().isEmpty())
                .forEach(player -> {
            player.setSport(sport);

            if(!positionHashMap.containsKey(player.getPosition())){
                positionHashMap.put(player.getPosition(), new Position(player.getPosition(),player.getAge(), 1));
            }
            else{
                Position position = positionHashMap.get(player.getPosition());
                position.setAgesSum(position.getAgesSum()+player.getAge());
                position.setNumberOfPlayers(position.getNumberOfPlayers()+1);
            }
            wrappedCollection.insert(player);
        });

    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Logger logger = LoggerFactory.getLogger(ContextListenerImpl.class.getName());
        Map<String, List<Player>> sportToPlayersMap = new HashMap<>();
        Map<String, Position> positionMap = new HashMap<>();

        try{

            InputStream inputStream = this.getClass().getResourceAsStream("/DB.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            MongoClient mongoClient = new MongoClient(new MongoClientURI(String.format("mongodb://%s:%s", properties.getProperty("hostname"), properties.getProperty("port"))));

            DB database = mongoClient.getDB("DraftDB");
            DBCollection playerCollection = database.getCollection("players");

            BasicDBObject basicDBObject = new BasicDBObject();


            DataRetriever<Player> dataRetriever = new DataRetriever<>();


            for (String sport: sports){
                List<Player> players = dataRetriever.retrieveData(String.format(SPORTS_URL, sport), new TypeReference<List<Player>>(){});

                if (players != null){
                    System.out.println("players size: "+players.size());
                    System.out.println("got players: "+players.get(0).getFirstName());
                    sportToPlayersMap.put(sport, players);
                }
            }

            System.out.println("got map size is: "+sportToPlayersMap.size());
            if(sportToPlayersMap.size() == 3){
                // Removed all documents from players collection.
                playerCollection.remove(basicDBObject);

                for (String sport: sportToPlayersMap.keySet()){
                    saveToCollection(playerCollection, sportToPlayersMap.get(sport), sport, positionMap);
                }
            }

            servletContextEvent.getServletContext().setAttribute("positionsMap", positionMap);



        }
        catch (IOException e){
            logger.error(e.getMessage());
        }

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
