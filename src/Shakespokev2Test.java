/**
* Shakespokev2Test
* Basic test client for the Shakespokev2 program.
* Ensure the input file "test_poke.json" is available.
* Input file must contain a list of tests, each populated with name, version, language.
*/

// package shakespoke_pk;

import java.util.concurrent.*;
import java.util.ArrayList;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;

public class Shakespokev2Test {
   public static void main (String[] args) {
   
     long time = 1;
     TimeUnit tu = TimeUnit.SECONDS;
      
      try {

      Gson gson = new Gson();
      FileReader fr = new FileReader("test_poke.json");
      FileWriter fw = new FileWriter("test_output.txt");
      TestEntriesObj obj = new Gson().fromJson(fr, TestEntriesObj.class);
      ArrayList<TestObj> tests = obj.tests;
      
      for (TestObj testentry : tests)
      {
         // initialise and run the target program
         Shakespokev3 target = new Shakespokev3(testentry.name, testentry.version, testentry.language);
         target.run();
         tu.sleep(time);
         
         // write results to file instead of console
         fw.write("Sent: " + testentry.name + ", " + testentry.version + ", " + testentry.language + "\n");
         fw.write("Processed: " + target.pokename + ", " + target.pokever + ", " + target.pokelang + "\n");
         fw.write(target.shakeresult + "\n\n");
      }

      fw.close();
      fr.close();
      

     } catch (Exception ex) {
         ex.printStackTrace();
     }
   
   }
}

class TestEntriesObj {
   ArrayList<TestObj> tests;
}

class TestObj {
   String name;
   String version;
   String language;
}