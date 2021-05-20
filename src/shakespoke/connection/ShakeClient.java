/** shakespoke.connection.ShakeClient
* Utility class to lookup the Shakespearean translation,
* from the Fun Translations online resource.
* Warning: Free public access version is rate limited to
* 5 calls per hour, 60 per day.
* Returns the JSON output from PokeAPI.
*/

package shakespoke.connection;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.net.URL;

public class ShakeClient {
      
   /**
   * resourceLookup
   * Take the endpoint label, and the item label, return the JSON result.
   * @param text_in is the modern English phrase to translate.
   */   
   public static String resourceLookup (String text_in) {
      String baseURL = "https://api.funtranslations.com/translate/shakespeare.json?text=";
      String remoteaddr = baseURL + text_in;
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