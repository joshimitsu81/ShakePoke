/**
* shakespoke.domain.JSONSpecies
* Attribute class to hold the object output from GSON library.
*/

package shakespoke.domain;

import java.util.ArrayList;

public class JSONSpecies {
   private String name;
   private ArrayList<JSONFlavorEntry> flavor_text_entries;
   
   public String getName () {
      return this.name;
   }

   public ArrayList<JSONFlavorEntry> getFlavorTexts () {
      return this.flavor_text_entries;
   }
}
