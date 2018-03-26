package Utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class DataRetriever <T>{

    private static final String USER_AGENT = "Mozilla/5.0";

    public List<T> retrieveData(String url,TypeReference<List<T>> typeReference) throws IOException{

        URL url1 = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) url1.openConnection();

        connection.setRequestMethod("GET");

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", USER_AGENT);

        Reader reader = new InputStreamReader(connection.getInputStream());

        try{

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode tree = objectMapper.readTree(reader);

            JsonNode playersTree = tree.get("body").get("players");

            System.out.println("reading players list: "+tree.asText());

            return objectMapper.readValue(playersTree.toString(), typeReference);
        }
        finally {
            reader.close();
        }

    }
}
