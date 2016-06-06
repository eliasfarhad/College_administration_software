import java.util.Random;
import java.util.Scanner;


public class Validation {


   /**
    * Reads the ID# of a student
    * @return   returns the parsed Integer value of the user input
    */
   public int readID() {
      System.out.println("\nPlease enter the Student ID#: ");
      Scanner scan = new Scanner(System.in);
      String studentID = scan.nextLine();
      
      if (isValidID(studentID))
         return Integer.parseInt(studentID);
      else {
         System.exit(0); 
         return 0;
      }
   }
   
   /**
    * Validate every ID# entered by the user
    * @param studentID   receives the user input from another method
    * @return            returns true/false based on the validity of the student ID#
    * 
    * Time Complexity = big-O(n)
    */
   public boolean isValidID(String studentID) {
      if (studentID.length() != 6) {
         System.out.println("\nInvalid entry. A Student ID must have 6 digits.");
         return false;
      }
      
      for (int i=0; i<studentID.length(); i++) {
         if (! Character.isDigit(studentID.charAt(i))) {
            System.out.println("\nStudent ID must have only digits.");
            return false;
         }
      }    
      return true;
   }
   
   /**
    * Reads any user input of type String
    * @return   returns the user input after checking for its validity
    */
   public String readString() {
      Scanner scan = new Scanner(System.in);
      String LName = scan.nextLine();
//      scan.close();
      
      if(isValidString(LName))
         return LName;
      else {
         System.exit(0);
         return null;
      }
   }
   
   /**
    * Checks the validity of a required string
    * @param LName   receives the parameter to be checked
    * @return   return true/false after checking for user input validity
    * 
    * Time Complexity = big-O(n)
    */
   public boolean isValidString(String LName) {
      for (int i=0; i<LName.length(); i++) {
         if(! Character.isLetterOrDigit(LName.charAt(i))) {
            System.out.println("Invalid Entry. Only letters and numbers permitted.");
            return false;
         }
      } 
      return true;
   }
   
   /**
    * Assign a random, valid ID# to a new student
    * @return   return the newly created ID#
    */
   public int validID() {
      
      int min = 111112;
      int max = 999999;
      
      Random rand = new Random();
      int id = rand.nextInt(max - min) + min;             // This gives a random integer between min (inclusive) and max (exclusive)
      
      String signal = Student.search(id, "", "", 0);
      
      if (signal.compareTo("No student with this ID/Name exists!") == 0) {
         return id;
      }
      else {
         id = validID();                                  // after the recursive call assign the returned value(id) to the variable id
         return id;
      }
   }
   
   
}


