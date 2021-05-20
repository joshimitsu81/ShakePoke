/** 
* shakespoke.connection.ShakespeareanHandler
* Create an HTTP listener, call the xBuilder constructors,
* extract the required values from the Pokemon/Translation objects,
* encode and return the HTTP response in JSON
*/

package shakespoke.connection;

import shakespoke.engine.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import java.util.Map;
import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ShakespeareanHandler implements HttpHandler {    
   
   @Override    
   public void handle(HttpExchange httpExchange) throws IOException {
   
   String requestParamValue=null; 
   if("GET".equals(httpExchange.getRequestMethod())) { 
      requestParamValue = handleGetRequest(httpExchange);
      }else if("POST".equals(httpExchange)) { 
        requestParamValue = handlePostRequest(httpExchange);        
      }  
   
   handleResponse(httpExchange,requestParamValue); 
   }
  
   private String handlePostRequest(HttpExchange httpExchange) {
      return httpExchange
         .getRequestURI()
         .toString();
   } 
   
   private String handleGetRequest(HttpExchange httpExchange) {
      return httpExchange
         .getRequestURI()
         .toString();
   }
   
   private void handleResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {
      OutputStream outputStream = httpExchange.getResponseBody();
      
      // separate the path and params from the endpoint URL
      String pokemon_name = requestParamValue.split("/")[2];  
      String pokemon_lang = "en";
      String pokemon_ver = "ruby";
      String poke_raw = "Description Not Found";
      String poke_clean = "Cleanup Not Found"; 
      String trdescr = "Translation Not Found";
      String json_out = "Encoding Error";

      try {
         // first get the modern English description
         PokemonBuilder pb = new PokemonBuilder(pokemon_name, pokemon_lang, pokemon_ver);
         poke_raw = pb.getDescription(); 
         // clean out some whitespace which disrupts the translation.
         poke_clean = poke_raw.replaceAll("[\\n\\t\\f]", " ");
         // get the translation
         TranslationBuilder trb = new TranslationBuilder(poke_clean);
         trdescr = trb.getTranslated();
         
          Map<String, Object> map = new HashMap<>();
          map.put("name", pokemon_name);
          map.put("plaindescr", poke_clean);
          map.put("translateddescr", trdescr);
      
          Gson gson = new GsonBuilder()
             .setPrettyPrinting()
             .disableHtmlEscaping()
             .create();
         json_out = gson.toJson(map);       
  
         httpExchange.sendResponseHeaders(200, json_out.length());

         outputStream.write(json_out.getBytes());
         outputStream.flush();
         outputStream.close();

      } catch (Exception ex) {
          ex.printStackTrace();
      }
   
   }
}
