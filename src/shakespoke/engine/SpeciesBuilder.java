/** 
* shakespoke.engine.SpeciesBuilder
* Search on the pokemon-species endpoint for the species,
* extract some basic fields from the JSON output.
*/

package shakespoke.engine;

import shakespoke.connection.BaseClient;
import shakespoke.domain.*;

import java.util.ArrayList;

import com.google.gson.Gson;

public class SpeciesBuilder {
   private String speciesname = "";
   private String langname = "";
   private String vername = "";
   private JSONSpecies species;
   private ArrayList<JSONFlavorEntry> flavors_list;
   private String endpoint = "pokemon-species/";
   private String flavor_string = "";

   /**
   * SpeciesBuilder 
   * Constructor class, instatiates as an object following the design format,
   * allows later expansions.
   * @param search_species is the species name matching an entry in PokeAPI
   * @param search_lang is the language name matching an entry in PokeAPI
   * @param search_ver is the version name matching an entry in PokeAPI
   */
   public SpeciesBuilder(String search_species, String search_lang, String search_ver) {
      
      String json_input = "";
      speciesname = search_species;
      langname = search_lang;
      vername = search_ver;
      
      try {
         json_input = BaseClient.resourceLookup(endpoint, search_species);
      
      } catch (Exception e) {
         e.printStackTrace();
      }
   
      try {
         species = new Gson().fromJson(json_input, JSONSpecies.class);
         speciesname = species.getName();
         flavors_list = species.getFlavorTexts();
         for (JSONFlavorEntry flavorentry : flavors_list) {
            if (flavorentry.getLanguage().getName().equals(langname) && flavorentry.getVersion().getName().equals(vername))
               flavor_string = flavorentry.getFlavorText();
         }
      
      } catch (Exception e) {
         e.printStackTrace();
      }
      
   }
   
   public String getName() {
      return this.speciesname;  
   }
   
   public ArrayList<JSONFlavorEntry> getFlavors() {
      return this.flavors_list;
   }
   
   public String getFlavorString () {
      return this.flavor_string;
   }

}