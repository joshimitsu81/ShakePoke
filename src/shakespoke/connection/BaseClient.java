/**
* shakespoke.connection.BaseClient
* Utility class to lookup an item from a given endpoint of the PokeAPI online resource.
* Returns the JSON output from PokeAPI.
*/

package shakespoke.connection;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.net.URL;

public class BaseClient {
   
   /**
   * resourceLookup
   * Take the endpoint label, and the item label, return the JSON result.
   * @param endpoint is any endpoint running on PokeAPI (usually an object type)
   * @param label is any object of the type
   */   
   public static String resourceLookup (String endpoint, String label) {
      String baseURL = "https://pokeapi.co/api/v2/";
      String remoteaddr = baseURL + endpoint + label;
      String json_result = "";      
      
      try {     
         URL url= new URL(remoteaddr);
         URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            
         var client = HttpClient.newHttpClient();
         var httpRequest = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();
            
         var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
         json_result = response.body();
         
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      return json_result;  
   }
   
}