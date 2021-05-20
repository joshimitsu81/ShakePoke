/**
* shakespoke.domain.JSONPokemon
* Attribute class to hold the object output from GSON library.
*/

package shakespoke.domain;

public class JSONPokemon {
   private String name;
   private int id;
   private JSONPokemonSpecies species;
   
   public String getName () {
      return this.name;
   }
   
   public int getId () {
      return this.id;
   }
   
   public JSONPokemonSpecies getSpecies () {
      return this.species;
   }
}
