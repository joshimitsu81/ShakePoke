/** 
* shakespoke.engine.TranslationBuilder
* Take a plain text phrase in modern English,
* lookup a translation using the ShakeClient connection,
* return the translated text.
*/

package shakespoke.engine;

import shakespoke.connection.ShakeClient;
import shakespoke.domain.*;

import java.util.ArrayList;

import com.google.gson.Gson;

public class TranslationBuilder {
   private String translated = "Not Found";
   private JSONTranslation tr;
   private JSONTranslationContent jtc;

   private String flavor_string = "";

   /** 
   * TranslationBuilder
   * Constructor method, instantiates as an object following the design format,
   * allows later expansions.
   * @param plain_text is the input string in modern English.
   */
   public TranslationBuilder(String plain_text) {
      
      String json_input = "";
      
      try {
         json_input = ShakeClient.resourceLookup(plain_text);
      
      } catch (Exception e) {
         e.printStackTrace();
      }
   
      try {
         tr = new Gson().fromJson(json_input, JSONTranslation.class);
         jtc = tr.getContents();
         translated = jtc.getTranslated();
      
      } catch (Exception e) {
         e.printStackTrace();
      }
      
   }
   
   public String getTranslated() {
      return this.translated;  
   }
   
}