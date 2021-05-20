/**
* Pokemon Shakespeare Translator
* Start up the RestService listener.
* @author Joshim Ahmed
*/

package shakespoke.engine;

import shakespoke.connection.RestService;

public class BaseEngine {

   /**
   * requires a port number in the runtime argument.
   */
   public static void main(String[] args) throws Exception {
      
      int port = Integer.parseInt(args[0]);
      RestService rs = new RestService(port);
   
   }
   
   public BaseEngine() {
      
   }
}