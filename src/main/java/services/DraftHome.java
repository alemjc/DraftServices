package services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class DraftHome {

    // Home page that will describe the different routes to client users.
    @GET
    @Produces("Text/plain")
    public String home(){
        return "Welcome to the draft page. Two links are included to see players:\n" +
                "1. {hostname}:{port}/draft/sport/{sportname} will get you all the players for a particular sport\n"+
                "2. {hostname}:{port}/draft/player/{playerId} will give you information about a particular player with {playerId}";
    }
}
