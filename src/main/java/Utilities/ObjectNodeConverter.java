package Utilities;

import Entities.Player;
import Entities.Position;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javax.servlet.ServletContext;
import java.util.HashMap;

public class ObjectNodeConverter {

    public static ObjectNode buildObjectNode(Player player, ServletContext context){

        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        HashMap<String, Position> positionHashMap = (HashMap<String, Position>) context.getAttribute("positionsMap");

        objectNode.put("id", player.getId());
        objectNode.put("first_name", player.getFirstName());
        objectNode.put("last_name", player.getLastName());
        objectNode.put("position", player.getPosition());
        objectNode.put("age", player.getAge());


        if(player.getFirstName().length() > 0 && player.getLastName().length() > 0){
            switch(player.getSport()){
                case "baseball":
                    objectNode.put("name_brief", player.getFirstName().charAt(0)+". "+player.getLastName().charAt(0)+".");
                    break;

                case "basketball":
                    objectNode.put("name_brief", player.getFirstName()+" "+player.getLastName().charAt(0)+".");
                    break;

                case "football":
                    objectNode.put("name_brief", player.getFirstName()+". "+player.getLastName());
            }
        }

        Position positionInfo = positionHashMap.get(player.getPosition());
        float positionAgeAverage = positionInfo.getAgesSum()/positionInfo.getNumberOfPlayers();
        objectNode.put("average_position_age_diff", player.getAge() - positionAgeAverage);

        return objectNode;
    }
}
