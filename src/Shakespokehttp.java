/**
* Shakespokehttp
* This program opens a basic HTTP listener on the given port
* It takes a GET request of the form http://<host_name>:<port_number>/pokemon/<pokemon_name>
* It then returns the description of the Pokemon to the http client
* in JSON format, with Shakespearean translation.
* 
* @author Joshim Ahmed
* @version 3.0
* @released 2021-04-25
*/

// package shakespoke_pk;

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
   
    String language = "en";

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(Integer.parseInt(args[0])), 0);
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
             
             String urlparam = requestParamValue.split("/")[2];
             String pokemon_name = urlparam.split("\\?")[0];
             String lang_name = urlparam.split("=")[1];
             String shakeResponse = "Not Found";
             String jsonResponse = "Not Found";

            try {
            Shakespokev3 target = new Shakespokev3(pokemon_name, "ruby", lang_name);
            target.run();
            shakeResponse = target.shakeresult;
            if (!lang_name.equals("shake")) {
               shakeResponse = shakeResponse.replaceAll("[\\n\\t\\f]", " ");
            }
   
            
                // create a map
                Map<String, Object> map = new HashMap<>();
                map.put("name", pokemon_name);
                map.put("description", shakeResponse);
            
                // convert map to JSON string
                if (lang_name.equals("shake")) {
                   Gson gson = new GsonBuilder()
                      .setPrettyPrinting()
                      .disableHtmlEscaping()
                      .create();
                  jsonResponse = gson.toJson(map);
                }
                
                else {
                   Gson gson = new GsonBuilder()
                      .setPrettyPrinting()
                      .create();
                  jsonResponse = gson.toJson(map);
                }

            
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
