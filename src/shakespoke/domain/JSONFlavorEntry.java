/**
* shakespoke.domain.JSONFlavorEntry
* Attribute class to hold the object output from GSON library.
*/

package shakespoke.domain;

public class JSONFlavorEntry {
   private String flavor_text;
   private JSONLanguage language;
   private JSONPokeVersion version;

   public String getFlavorText () {
      return this.flavor_text;
   }
   
   public JSONLanguage getLanguage () {
      return this.language;
   }
   
   public JSONPokeVersion getVersion () {
      return this.version;
   }
}