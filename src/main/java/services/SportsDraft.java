package services;
import Entities.Player;
import Utilities.ObjectNodeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.*;
import listeners.ContextListenerImpl;
import org.mongojack.DBCursor;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

// The Java class will be hosted at the URI path "/draft/sport/{sportName}"
@Path("draft/sport/{sportName}")
public class SportsDraft {

    @Context
    private ServletContext context;

    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "application/json"
    // Which will be used to retrieve players by a sport's name from a mongo database
    @Produces("application/json")
    public Response getPlayers(@PathParam("sportName") String sportName) {

        Logger logger = LoggerFactory.getLogger(ContextListenerImpl.class.getName());
        try{
            InputStream inputStream = this.getClass().getResourceAsStream("/DB.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            MongoClient mongoClient = new MongoClient(new MongoClientURI(String.format("mongodb://%s:%s", properties.getProperty("hostname"), properties.getProperty("port"))));
            DB database = mongoClient.getDB("DraftDB");
            DBCollection playerCollection = database.getCollection("players");
            JacksonDBCollection<Player, String> wrappedCollection = JacksonDBCollection.wrap(playerCollection, Entities.Player.class, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            List<ObjectNode> objectNodes = new ArrayList<>();

            DBCursor<Player> dbCursor = wrappedCollection.find(DBQuery.is("sport", sportName));

            while (dbCursor.hasNext()){
                Player player = dbCursor.next();
                objectNodes.add(ObjectNodeConverter.buildObjectNode(player, context));
            }

            try{
                return Response.ok(objectMapper.writeValueAsString(objectNodes)).build();
            }
            catch (JsonProcessingException e){
                logger.error(e.getMessage());
                return Response.serverError().build();
            }
        }
        catch (IOException e){
            logger.error(e.getMessage());
            return Response.serverError().build();
        }

    }

}
