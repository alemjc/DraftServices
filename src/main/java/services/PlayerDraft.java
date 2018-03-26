package services;

import Entities.Player;
import Utilities.ObjectNodeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
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
import java.util.Properties;

// The Java class will be hosted at the URI path "/PlayerDraft/{playerId}"
// It will be used to retrieve a user by his player id

@Path("draft/player/{playerId}")
public class PlayerDraft {
    // The Java method will process HTTP GET requests

    @Context
    private ServletContext context;

    @GET
    // The Java method will produce content identified by the MIME Media type "application/json"
    // This content is retrieved from a Json container DB.
    @Produces("application/json")
    public Response getPlayer(@PathParam("playerId") String playerId) {

        Logger logger = LoggerFactory.getLogger(ContextListenerImpl.class.getName());
        try{

            InputStream inputStream = this.getClass().getResourceAsStream("/DB.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            MongoClient mongoClient = new MongoClient(new MongoClientURI(String.format("mongodb://%s:%s", properties.getProperty("hostname"), properties.getProperty("port"))));
            ObjectMapper objectMapper = new ObjectMapper();


            DB database = mongoClient.getDB("DraftDB");
            DBCollection playerCollection = database.getCollection("players");
            JacksonDBCollection<Entities.Player, String> wrappedCollection = JacksonDBCollection.wrap(playerCollection, Entities.Player.class, String.class);

            DBCursor<Player> playerCursor = wrappedCollection.find(DBQuery.is("id", playerId));
            Entities.Player player = playerCursor.next();

            try{
                return Response.ok(objectMapper.writeValueAsString(ObjectNodeConverter.buildObjectNode(player, context))).build();
            }
            catch (JsonProcessingException e){
                logger.error(e.getMessage());
                return Response.serverError().build();
            }
            finally {
                playerCursor.close();
            }

        }
        catch (IOException e){
            logger.error(e.getMessage());
            return Response.serverError().build();
        }


    }

}
