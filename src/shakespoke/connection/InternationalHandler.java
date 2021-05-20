/** 
* shakespoke.connection.InternationalHandler
* Create an HTTP listener, call the PokemonBuilder constructor,
* extract the required values from the Pokemon objects,
* encode and return the HTTP response in JSON
*/

package shakespoke.connection;

import shakespoke.engine.PokemonBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import java.util.Map;
import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class InternationalHandler implements HttpHandler {    
   
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
      String request_string = requestParamValue.split("/")[2];
      // separate the params from the path
      String[] requestarr = request_string.split("\\?");
      String pokemon_name = requestarr[0];
      // default language and version
      String pokemon_lang = "en";
      String pokemon_ver = "ruby";
      String poke_raw = "Description Not Found";
      String poke_clean = "Cleanup Not Found"; 
      String json_out = "Encoding Error";
      
      // modify the language and version params in case they were provided.
      if (requestarr.length > 1 ) {
         String[] paramarr = requestarr[1].split("\\&");
         for (int i = 0; i < paramarr.length; i++) {
            String[] keyvals = paramarr[i].split("\\=");
               if (keyvals[0].equals("lang")) pokemon_lang = keyvals[1];
               if (keyvals[0].equals("ver")) pokemon_ver = keyvals[1];
         }
      } 

      try {
         // first get the modern English description
         PokemonBuilder pb = new PokemonBuilder(pokemon_name, pokemon_lang, pokemon_ver);
         poke_raw = pb.getDescription(); 
         // clean out some whitespace which disrupts the JSON conversion.
         poke_clean = poke_raw.replaceAll("[\\n\\t\\f]", " ");
         
          Map<String, Object> map = new HashMap<>();
          map.put("name", pokemon_lang);
          map.put("language", pokemon_lang);
          map.put("version", pokemon_ver);
          map.put("plaindescr", poke_clean);
         
         Gson gson = new GsonBuilder()
             .setPrettyPrinting()
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
