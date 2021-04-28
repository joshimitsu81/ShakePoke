import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import java.util.Map;
import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Shakespokehttp {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(5000), 0);
        server.createContext("/", new MyHttpHandler());
        server.setExecutor(null); 
        server.start();
    }

   static class MyHttpHandler implements HttpHandler {    
   
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
             return httpExchange.
                     getRequestURI()
                     .toString();
    } 
   
    private String handleGetRequest(HttpExchange httpExchange) {
             return httpExchange.
                     getRequestURI()
                     .toString();
    }
   
    private void handleResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {
             OutputStream outputStream = httpExchange.getResponseBody();
             
             String pokemon_name = requestParamValue.split("/")[2];
             String shakeResponse = "Not Found";
             String jsonResponse = "Not Found";

            try {
            Shakespokev3 target = new Shakespokev3(pokemon_name, "ruby", "shake");
            target.run();
            shakeResponse = target.shakeresult;
   
            
                // create a map
                Map<String, Object> map = new HashMap<>();
                map.put("name", pokemon_name);
                map.put("description", shakeResponse);
            
                // convert map to JSON string
                Gson gson = new GsonBuilder()
                      .setPrettyPrinting()
                      .disableHtmlEscaping()
                      .create();
                jsonResponse = gson.toJson(map);
            
               } catch (Exception ex) {
                   ex.printStackTrace();
               }
      
             httpExchange.sendResponseHeaders(200, jsonResponse.length());
   
             outputStream.write(jsonResponse.getBytes());
             outputStream.flush();
             outputStream.close();
   
         }
   }

}