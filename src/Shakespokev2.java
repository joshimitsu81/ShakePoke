/**
* Shakespokev2
* This program takes a set of String parameters corresponding to
* a Pokemon name, a Pokemon game version, and a target language.
* It then returns the description of the Pokemon in either the target
* language, or translated to a Shakespearean approximation.
* 
* @author Joshim Ahmed
* @version 2.0
* @released 2021-04-25
*/

// package shakespoke_pk;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.net.URL;

import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;

public class Shakespokev2 {
   
   String pokename = "";
   String pokever = "ruby";
   String pokelang = "en";
   String shakeresult = "Not Found";

   /** 
   * Main method can be called directly passing the parameters:
   * @param args name, version, language.
   * Default version is "ruby".
   * Default language is "en".
   */
   public static void main(String[] args) {
      // defaults are Ruby version and English translation (via English)
      int argsn = args.length;
      if (argsn==0) {
         System.out.println("Please include a name to search.\n Example: Shakespokev2 charizard");
         return;
      }
        
     // override the parameters if optionals are passed      
     String namearg = args[0];
     String verarg = "ruby";
     String langarg = "en";
     if (argsn==2) {
      verarg=args[1];
     }
     else if (argsn==3) {
      verarg=args[1];
      langarg=args[2];
     } else if (argsn>3) {
      System.out.println("Too many arguments.\n Example: Shakespokev2 charizard ruby en");
      return;
     }
     
     // print results to console
     System.out.println(namearg + ", " + verarg + ", " + langarg + ":");
     System.out.println(pokeShake(namearg, verarg, langarg));
   }
   
   /** 
   * Allow the program to be initialised as an instance
   * @param testname The Pokemon name. Program will exit if null or empty.
   * @param testver The game version name. Default value is declared if missing.
   * @param testlang The game language. Default value is declared if missing.
   */
   public Shakespokev2(String testname, String testver, String testlang) {
      if (testname!=null && !testname.isEmpty()) {
       this.pokename = testname;
      }

      if (testver!=null && !testver.isEmpty()) {
       this.pokever = testver;
      }
      if (testlang!=null && !testlang.isEmpty()) {
       this.pokelang = testlang;
      }
   }
   
   /** 
   * Run the program as an instance.
   * Output is determined by the calling application.
   */
   public void run() {
      // System.out.println(pokeShake(pokename, pokever, pokelang));
      if (pokename!=null && !pokename.isEmpty()) {
         this.shakeresult = pokeShake(pokename, pokever, pokelang);
      }
   }
   
   // Base method to process the inputs, return the output
   private static String pokeShake(String name_in, String ver_in, String lang_in) {
      // System.out.println(name_in + ", " + ver_in + ", " + lang_in + "\n");
      String origstring = "Not Found";
      if (name_in==null || name_in.isBlank()) {
         return origstring;
      }
      String shakestring = "Not Found";
      String speciesurl = findPokeSpeciesUrl(name_in);
      if (speciesurl.equals("Not Found")) {
         return "Not Found";
      }
      if (lang_in.equals("shake")) {
         // first call Pokeapi for description, then call the translator
         origstring = findflavorString(speciesurl, ver_in, "en");
         shakestring = translate(origstring);
         return shakestring;
      }
      
      // if the language override was provided, only call Pokeapi
      origstring = findflavorString(speciesurl, ver_in, lang_in);     
      return origstring;
   } 
   
   // take a description (in English) and lookup the Shakespeare version
   private static String translate(String text_in) {
      // System.out.println(text_in + "\n");
      // clean out newline and tabs from text, or the input to translator is invalid
      String cleanrequest = text_in.replaceAll("[\\n\\t]", " "); 
      String translated = "Not Found";
      String shakespeare_url = "https://api.funtranslations.com/translate/shakespeare.json?text=";
      
      try {

         // standard Java libraries to build a GET request
         URL url= new URL(shakespeare_url.concat(cleanrequest));
         URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
         
         var client = HttpClient.newHttpClient();
         var httpRequest = HttpRequest.newBuilder()
         .uri(uri)
         .GET()
         .build();
      
         // expecting the JSON here
         var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
         // GSON library to convert to POJO
         TranslationObj obj = new Gson().fromJson(response.body(), TranslationObj.class);
         // find the key "translated"
         translated = obj.contents.translated;
         
         } catch (Exception e) {
            e.printStackTrace();
         }
        
      return translated;
      
   }
   
   private static String findflavorString(String url_in, String ver_in, String lang_in) {
      // System.out.println(url_in + ", " + ver_in + ", " + lang_in + "\n");     
      String flavor_string = "Not Found";

      try {
         // standard Java libraries to build a GET request
         URL url= new URL(url_in);
         URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
         
         var client = HttpClient.newHttpClient();
         var httpRequest = HttpRequest.newBuilder()
         .uri(uri)
         .GET()
         .build();

         // expecting the JSON here
         var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
         // GSON library to convert to POJO
         SpeciesObj obj = new Gson().fromJson(response.body(), SpeciesObj.class);         
         ArrayList<FlavorEntriesObj> foundflavors = obj.flavor_text_entries;
         // linear search through ArrayList to find the matching entry
         for (FlavorEntriesObj flavorentry : foundflavors)
         {
            if (flavorentry.language.name.equals(lang_in) && flavorentry.version.name.equals(ver_in))
               flavor_string = flavorentry.flavor_text;
         }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return flavor_string; 
   }
   
   
   private static String findPokeSpeciesUrl(String name_in) {
      String pokemon_url = "https://pokeapi.co/api/v2/pokemon/";
      String pokespeciesurl = "Not Found";

        try {
            // standard Java libraries to build a GET request
            URL url= new URL(pokemon_url.concat(name_in));
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            
            var client = HttpClient.newHttpClient();
            var httpRequest = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();
         
            // expecting the JSON here
            var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            // GSON library to convert to POJO
            PokemonObj obj = new Gson().fromJson(response.body(), PokemonObj.class);
            // pass the url straight back
            pokespeciesurl = obj.species.url;

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return pokespeciesurl;
   }
   
}

// object classes
class PokemonObj {
   String name;
   int id;
   PokeSpeciesObj species;
}

class PokeSpeciesObj {
   String name;
   String url;
}

class SpeciesObj {
   String name;
   ArrayList<FlavorEntriesObj> flavor_text_entries;
}

class FlavorEntriesObj {
   String flavor_text;
   LanguageObj language;
   VersionObj version;
}

class LanguageObj {
   String name;
}

class VersionObj {
   String name;
}

class TranslationObj {
   ContentsObj contents;
}

class ContentsObj {
   String translated;
}