/**
* shakespoke.connection.RestService
* Startup the HTTP server, open the two endpoints.
*/

package shakespoke.connection;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class RestService {

   private String shakespearean_context = "/shakespearean";
   private String international_context = "/international";
   ///int port = 5050;

   /**
   * RestService constructor for the HTTP server
   * @param port is any available port on the host.
   */
   public RestService(int port) throws Exception {
    
      HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
      server.createContext(shakespearean_context, new ShakespeareanHandler());
      server.createContext(international_context, new InternationalHandler());
      server.setExecutor(null); 
      server.start();   
   }
   
}
