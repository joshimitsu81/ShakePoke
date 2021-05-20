package shakespoke.engine;

import shakespoke.connection.BaseClient;
import shakespoke.domain.*;

import com.google.gson.Gson;

public class PokemonBuilder {
   private String pokename = "";
   private JSONPokemonSpecies jpokespecies;
   private JSONSpecies jspecies;
   private SpeciesBuilder jspeciesbuild;
   private String speciesname = "";
   private String pokedescr = "";
   private String langname = "";
   private String vername = "";
   private String endpoint = "pokemon/";

   public PokemonBuilder(String search_poke, String search_lang, String search_ver) {
      
      String json_input = "";
      pokename = search_poke;
      langname = search_lang;
      vername = search_ver;
      
      try {
         json_input = BaseClient.resourceLookup(endpoint, search_poke);
      
      } catch (Exception e) {
         e.printStackTrace();
      }
   
      try {
         JSONPokemon jpoke = new Gson().fromJson(json_input, JSONPokemon.class);
         jpokespecies = jpoke.getSpecies();
         speciesname = jpokespecies.getName();
         jspeciesbuild = new SpeciesBuilder(speciesname, langname, vername);
         pokedescr = jspeciesbuild.getFlavorString();
      
      } catch (Exception e) {
         e.printStackTrace();
      }
      
   }
   
   public String getName() {
      return this.pokename;  
   }
   
   public JSONPokemonSpecies getPokeSpecies() {
      return this.jpokespecies;
   }
   
   public String getSpeciesName () {
      return this.speciesname;
   }
   
   public String getDescription () {
      return this.pokedescr;
   }

}