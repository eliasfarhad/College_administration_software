import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class BST {

   Map<String, String> treeMap = new TreeMap<String, String>();
   Map<String, String> linkedHashMap = new LinkedHashMap<String, String>();
   
   /**
    * Saves all the students data in a tree in a sorted order.
    * 
    * Time Complexity = big-O(n)
    */
   public void studentMap() {

      File file = new File("StudentData.txt");

      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
         String line = br.readLine();

         while (line != null) {
            StringTokenizer tokenizer = new StringTokenizer(line, ",");
            String token = tokenizer.nextToken();
            String temp = token;
            token = tokenizer.nextToken();
            String Name = token;
            token = tokenizer.nextToken();
            Name += token;
            Name += temp;
            
            treeMap.put(Name, line);
            linkedHashMap.put(Name, line);
            
            line = br.readLine();
         }

   /*      for (String key: treeMap.keySet()) {
            String info = treeMap.get(key);
            System.out.println(info);
         }*/
         
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      File file2 = new File("StudentDataSorted.txt");
      
      try(BufferedWriter bw = new BufferedWriter(new FileWriter(file2))) {
         
         for (String key: treeMap.keySet()) {
            String info = treeMap.get(key);
            bw.write(info + "\n");
         }
         
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   
   /**
    * Displays all the students' data currently in the system in an unsorted order.
    * 
    * Time Complexity = big-O(n)
    */
   public void displayList() {
      System.out.println();
      for (String key: linkedHashMap.keySet()) {
         String info = linkedHashMap.get(key);
         System.out.println(info);
      }
   }
   
   /**
    * Displays all the students' data in a sorted order according their Last Names.
    * 
    * Time Complexity = Big-O(n)
    */
   public void displaySortedList() {
      System.out.println();
      for (String key: treeMap.keySet()) {
         String info = treeMap.get(key);
         System.out.println(info);
      }
   }

}
