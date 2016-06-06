/**
 * A program that reads Students' data from a .txt file and sorts and stores them in a separate .txt file. It also takes
 * care of any special cases such as identical Last Names and First Names. The user can search for a student, add a course
 * or delete a course from a student's schedule. If admission period is not over, user can also enroll or delete a 
 * student in the system. The user has or loses control over the system based on the Academic Fixed Deadlines.
 * 
 * Students Data are saved in a text file as a following format : 
 * CUNY ID, Last Name, First Name, Total Credits, Overall GPA, {2-D arrays of classes}
 * 
 * @author S M FARHAD
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;


public class Student {

   private static int ID;
   private static String lastName;
   private static String firstName;
   private static int totalCredits;
   private static float currentGPA;
   
   
   public static void main(String[] args) {
      
      initiate();
      
   }
   
   
   /** 
    * Initiate the complex process of making specific changes in the system. This method invokes every function based on the user requirements and
    * software design.
    * 
    * The time complexity is big-theta (c), where c = any constant
    */
   public static void initiate() {
      
      BST bst = new BST();
      Scanner scan = new Scanner(System.in);
      System.out.println("\nWelcome to CUNYFirst! \n\nYou are authorized to modify students data in the system.");
      System.out.print("\nIs the Admission period over?\nType 'Yes' or 'No' : ");
      String response = scan.nextLine();
      response = response.trim();
      String StudentInfo;
      int count;
      
      if (response.equalsIgnoreCase("No")) {
         System.out.println("\nEnter : 1 to View all Students' Data,\n        2 to Add a Student in the system,\n        3 to Delete a Student from the system,"
               + "\n        4 to Add a Course to a Student's schedule,\n        5 to Drop a Course from a Student's schedule,"
               + "\n        6 to Search for a Student.");
         
         System.out.print("\nUser Response : ");
         count = scan.nextInt();
         scan.nextLine();
         
         switch (count) {
         case 1:
            bst.studentMap();
            bst.displayList();
            break;
         case 2:
            addStudent();
            bst.studentMap();
            break;
         case 3:
            deleteStudent();
            bst.studentMap();
            break;
         case 4:
            addCourse();
            bst.studentMap();
            break;
         case 5:
            dropCourse();
            bst.studentMap();
            break;
         case 6:
            StudentInfo = userInput();
            display(StudentInfo);
            break;
         default:
            System.out.println("\n\n                         Invalid Response.");
            break;
         }
      }
      else if (response.equalsIgnoreCase("Yes")) {
         System.out.print("\nIs the Drop period over?\nType 'Yes' or 'No' : ");
         response = scan.nextLine();
         response = response.trim();
         
         if (response.equalsIgnoreCase("No")) {
            System.out.println("\nEnter : 1 to View all Students' Data in sorted order,"
                  + "\n        4 to Add a Course to a Student's schedule,\n        5 to Drop a Course from a Student's schedule,"
                  + "\n        6 to Search for a Student.");
            
            System.out.print("\nUser Response : ");
            count = scan.nextInt();
            scan.nextLine();
            
            switch (count) {
            case 1:
               bst.studentMap();
               bst.displaySortedList();
               break;
            case 4:
               addCourse();
               bst.studentMap();
               break;
            case 5:
               dropCourse();
               bst.studentMap();
               break;
            case 6:
               StudentInfo = userInput();
               display(StudentInfo);
               break;
            default:
               System.out.println("\n\n                         Invalid Response.");
               break;
            }
         }
         else if (response.equalsIgnoreCase("Yes")) {
            System.out.println("\nEnter : 1 to View all Students' Data in sorted order," + "\n        6 to Search for a Student."); 
            System.out.print("\nUser Response : ");
            count = scan.nextInt();
            scan.nextLine();
            
            switch (count) {
            case 1:
               bst.studentMap();
               bst.displaySortedList();
               break;
            case 6:
               StudentInfo = userInput();
               display(StudentInfo);
               break;
            default:
               System.out.println("\n\n                         Invalid Response.");
               break;
            }
         }
         else {
            System.out.println("\n\n                         Invalid Response.");
         }
      }
      else {
         System.out.println("\n\n                         Invalid Response.");
      }
   }
   
   /**
    * Takes user response from the keyboard and displays students' data based on that
    * @return   all the information about a specific student
    * 
    * Time Complexity = big-theta(c), where c = any constant
    */ 
   public static String userInput() {
      
      Validation validate = new Validation();
      String StudentInfo = null;
      int iteration = 0;
      int searchID = 0;
      String searchLName = null;
      String searchFName = null;
      
      System.out.println("\nEnter : 1 to search a student by ID#\n        2 to search a student by Last Name.");
      System.out.print("\nUser Response : ");
      
      Scanner scan = new Scanner(System.in);
      int input = scan.nextInt();
      scan.nextLine();
      
      if (input == 1) {
          searchID = validate.readID();
          StudentInfo = search(searchID, "", "", iteration);
          
          if (StudentInfo.equals("Multiple")) {
             System.out.println("\nMultiple students with same Student ID# exist.\nPlease enter the Name of the student.\n");
             System.out.print("Last  Name: ");
             searchLName = validate.readString();
             
             System.out.print("First Name: ");
             searchFName = validate.readString();
             iteration++;
             StudentInfo = search(searchID, searchLName, searchFName, iteration);
          }
      }
      else if(input == 2) {
          System.out.println("\nPlease enter the Student Last Name: ");
          searchLName = validate.readString();
          StudentInfo = search(searchID, searchLName, "", iteration);
          
          if (StudentInfo.equals("Multiple")) {
             System.out.println("\nMultiple students with same Last Name exist.");
             searchID = validate.readID();

             System.out.println("Please enter the Student First Name: ");
             searchFName = validate.readString();
             iteration++;
             StudentInfo = search(searchID, searchLName, searchFName, iteration);
          }
      }
      else {
         System.out.println("Invalid Input.");
         System.exit(0);                              // return null;
      } 
      
      return StudentInfo;
   }
   
   /**
    * Searches a student by ID# or by Last Names. Takes care of multiple students with the same names.
    * 
    * @param searchID   Looks for a student by his/her ID#
    * @param searchLName   Looks for a student by his/her Last Name
    * @param searchFName   Looks for a student by his/her First Name
    * @param iteration     Keeps track of how many times the method were called.
    * @return      returns students' data based on a successful search.
    * 
    * Time Complexity = big-O (n)
    */  
   public static String search(int searchID, String searchLName, String searchFName, int iteration) {
      File file = new File("StudentData.txt");
      
      try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
         int studentCount = 0;
         String line = reader.readLine();
         String infoLine = null;
         
         while (line != null) {
            StringTokenizer tokenizer = new StringTokenizer(line, ",");
            String token = tokenizer.nextToken();
            ID = Integer.parseInt(token);
            lastName = tokenizer.nextToken();
            firstName = tokenizer.nextToken();
            
            if ( ((searchID == ID) || (searchLName.compareToIgnoreCase(lastName) == 0)) && (iteration == 0) ) {
               infoLine = line;
               studentCount++;  
            }
            
            if ( (searchID == ID) && (iteration > 0) && (searchFName.compareToIgnoreCase(firstName) == 0) && (searchLName.compareToIgnoreCase(lastName) == 0) ) {
               infoLine = line;
               iteration++;
               studentCount++;
            }
            
            if ( (studentCount > 1) && (iteration == 0) ) {
               iteration++;
               return "Multiple";
            }
                     
            line = reader.readLine();
         }
         
         if((studentCount != 0) && (infoLine != null)) {
            return infoLine;
         }
         
         
         reader.close();
      } catch (FileNotFoundException e) {
      System.out.println("Couldn't open the file: " + file.toString());
      } catch (IOException e) {
      System.out.println("Couldn't close/Unable to read the file" + file.toString());
      }
      
      return "No student with this ID/Name exists!";
      
   } // search()
   
   /**
    * Deletes a student from the entire system.
    * 
    * Time Complexity = big-O(n);
    */
   public static void deleteStudent() {
      
      String StudentInfo = userInput();
      display(StudentInfo);
      System.out.println("\nIf this information matches the Exact Student to be Deleted, \ntype \"Yes\" "
            + "to execute the action otherwise type \"No\": ");
      Scanner scanner = new Scanner(System.in);
      String input = scanner.nextLine();
      
      if (input.compareToIgnoreCase("yes") == 0) {
         StringTokenizer tokenizer = new StringTokenizer(StudentInfo, ",");
         
         String StID = tokenizer.nextToken();
         String StLName = tokenizer.nextToken();
         String StFName = tokenizer.nextToken();
      
         File originFile = new File("StudentData.txt");
         File tempFile = new File("tempFile.txt");
         
         try {
            BufferedReader reader = new BufferedReader(new FileReader(originFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            
            String currentLine;
            
            while ( (currentLine = reader.readLine()) != null) {
               if ( (currentLine.contains(StID)) && (currentLine.contains(StLName)) && (currentLine.contains(StFName)) )
                  continue;
               
               writer.write(currentLine);
               writer.newLine();
            }
            
            writer.close();
            tempFile.renameTo(originFile);
            System.out.println("\nStudent has been deleted from the system.\n\nOperation Successful.");
                    
         } catch (FileNotFoundException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         }
         
      }
      else if (input.compareToIgnoreCase("no") == 0) { 
         System.out.println("Student was NOT deleted from the system.");
  //       System.exit(0);
      }
      else {
         System.out.println("Invalid Input. Operation abandoned.");
         System.exit(0);
      }
   }
   
   /**
    * Adds/Enrolls a student in the current semester
    * 
    * Time Complexity = big-O(n)
    */
   public static void addStudent() {
      // Be careful of using the static variables !

      StringBuilder builder = new StringBuilder();
      Scanner scan = new Scanner(System.in);
      
      Validation validate = new Validation();
      int ID = validate.validID();
      builder.append(Integer.toString(ID) + ",");
      System.out.println("\nA new Student ID has been assigned to the new student.");
      
      System.out.print("\nPlease enter Student's Last Name  : ");
      builder.append(scan.nextLine() + ",");
      
      System.out.print("Please enter Student's First Name : ");
      builder.append(scan.nextLine() + ",");
      
      System.out.print("\nEnter Student's total number of Credits accomplished (0 for freshman) : ");
      builder.append(scan.nextLine() + ",");
      
      System.out.print("Enter Student's Current GPA (0 for freshman)                          : ");
      builder.append(scan.nextLine() + ",");
      
      System.out.println("\nPlease enter the Course Names and Equivalent Credits for the courses \nthe new student wants to be enrolled in this semester : ");
      System.out.println("a grade of 0.00 will be assigned automatically - course currently being taken");
      System.out.println("\nWhen finished, type \"Stop\" at any time.");
      System.out.println("\nExample: Biology,4 \n");
      
      int count = 1;
      System.out.print("Course " + count + " : ");
      String input = scan.nextLine();
      StringTokenizer tokenizer = new StringTokenizer(input, ",");
      String token = tokenizer.nextToken();
      token = token.trim();
      builder.append("{");
      
      while (true) {
         if (token.equalsIgnoreCase("Stop"))
            break;
         else {
            token = token.trim();
            token = token.substring(0, 1).toUpperCase() + token.substring(1);
            builder.append("{" + token + ",");
            token = tokenizer.nextToken();
            token = token.trim();
            builder.append(token + ",");
            builder.append("0.00},");
         }
         
         count++;
         if (count != 1)
            System.out.print("Course " + count + " : ");
         input = scan.nextLine();
         tokenizer = new StringTokenizer(input, ",");
         token = tokenizer.nextToken();
         token = token.trim();
      }
      
      builder.deleteCharAt(builder.length() - 1);
      builder.append("}");
      
      File originFile = new File("StudentData.txt");
      File tempFile = new File("tempFile.txt");
      
      try {
         BufferedReader reader = new BufferedReader(new FileReader(originFile));
         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
         
         String currentLine;
         
         while ( (currentLine = reader.readLine()) != null) {
            
            writer.write(currentLine);
            writer.newLine();
         }
         
         writer.write(builder.toString());
         writer.close();
         tempFile.renameTo(originFile);
         System.out.println("\nNew Student has been added to the system.\n\nOperation Successful.");
         
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      
   }
   
   /**
    * Adds a course to a Student's schedule.
    * 
    * Time Complexity = big-O(n)
    */
   
   public static void addCourse() {
      
      StringBuilder builder = new StringBuilder();
      
      String StudentInfo = userInput();
      display(StudentInfo);
      
      System.out.println("\nIf this information matches the Exact Student, \ntype \"Yes\" "
            + "to add a Course, otherwise type \"No\": ");
      Scanner scan = new Scanner(System.in);
      String input = scan.nextLine();
      input = input.trim();
      
         if (input.equalsIgnoreCase("No")) {
            System.out.println("Operation abandoned. No Course was added to the Student's schedule.");
         }
         else if (input.equalsIgnoreCase("Yes")){
            builder.append(StudentInfo);
            builder.deleteCharAt(builder.length() - 1);
            
            System.out.println("\nPlease enter the Course Names and Equivalent Credits for the courses \nthe new student wants to be enrolled in this semester : ");
            System.out.println("a grade of 0.00 will be assigned automatically - course currently being taken");
            System.out.println("\nWhen finished, type \"Stop\" at any time.");
            System.out.println("\nExample: Biology,4 \n");
            
            int count = 1;
            System.out.print("Course " + count + " : ");
            input = scan.nextLine();
            
            StringTokenizer tokenizer = new StringTokenizer(input, ",");
            String token = tokenizer.nextToken();
            token = token.trim();
            
            builder.append(",");

            while (true) {
               if (token.equalsIgnoreCase("Stop"))
                  break;
               else {
                  token = token.trim();
                  token = token.substring(0, 1).toUpperCase() + token.substring(1);       // Capitalize the first letter of the course name
                  builder.append("{" + token + ",");
                  token = tokenizer.nextToken();
                  token = token.trim();
                  builder.append(token + ",");
                  builder.append("0.00},");
               }
               
               count++;
               if (count != 1)
                  System.out.print("Course " + count + " : ");
               input = scan.nextLine();
               tokenizer = new StringTokenizer(input, ",");
               token = tokenizer.nextToken();
               token = token.trim();
            }
            
            builder.deleteCharAt(builder.length() - 1);
            builder.append("}");
    //        System.out.print("\nType \"Yes\" to Add another course, or \"No\" to finish your task : ");
   //         input = scan.nextLine();
            File originFile = new File("StudentData.txt");
            File tempFile = new File("tempFile.txt");
            
            try {
               BufferedReader reader = new BufferedReader(new FileReader(originFile));
               BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
               
               String currentLine;
               
               while ( (currentLine = reader.readLine()) != null) {
                  
                  if (!currentLine.equals(StudentInfo)) {
                     writer.write(currentLine);
                     writer.newLine();
                  }
               }
               
               writer.write(builder.toString());
               writer.close();
               tempFile.renameTo(originFile);
               System.out.println("\nStudent's schedule has been updated with new course(s).\n\nOperation Successful.");
               
            } catch (FileNotFoundException e) {
               e.printStackTrace();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
         else {
            System.out.println("Invalid Input.");
         }
      
   }
   
   /**
    * Drops a course from a student's schedule
    * 
    * Time Complexity = big-O(n)
    */
   public static void dropCourse() {
      
      StringBuilder builder = new StringBuilder();
      
      String StudentInfo = userInput();
      display(StudentInfo);
      
      System.out.println("\nIf this information matches the Exact Student, \ntype \"Yes\" "
            + "to drop a Course, otherwise type \"No\": ");
      Scanner scan = new Scanner(System.in);
      String input = scan.nextLine();
      input = input.trim();
      
         if (input.equalsIgnoreCase("No")) {
            System.out.println("Operation abandoned. No Course was dropped from the Student's schedule.");
         }
         else if (input.equalsIgnoreCase("Yes")){
            
            StringTokenizer tokenizer  = new StringTokenizer(StudentInfo, ",");
            String token;
            for (int i=0; i<5; i++) {
               token = tokenizer.nextToken();
               builder.append(token + ",");
            }
                      
            System.out.println("\nPlease enter the Name of the Course to be dropped.");
            System.out.println("\nExample: Biology\n");
            
            System.out.print("Course Name: ");
            input = scan.nextLine();

            input = input.trim();
            input = input.substring(0, 1).toUpperCase() + input.substring(1);       // Capitalize the first letter of the course name
            
            while (tokenizer.hasMoreTokens()) {
               token = tokenizer.nextToken();
               
               if (token.contains(input)) {
                  if (token.startsWith("{{")) {
                     token = tokenizer.nextToken();
                     token = tokenizer.nextToken();
                     builder.append("{");
                  }
                  else {
                     token = tokenizer.nextToken();
                     token = tokenizer.nextToken();
                     if (token.endsWith("}}"))
                        builder.append("}");
                  }
               }
               else {
                  builder.append(token + ",");
                  
                  token = tokenizer.nextToken();
                  builder.append(token + ",");
                  
                  token = tokenizer.nextToken();
                  builder.append(token + ",");
               }
            } // while

            builder.deleteCharAt(builder.length() - 1);

            
            File originFile = new File("StudentData.txt");
            File tempFile = new File("tempFile.txt");
            
            try {
               BufferedReader reader = new BufferedReader(new FileReader(originFile));
               BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
               
               String currentLine;
               
               while ( (currentLine = reader.readLine()) != null) {
                  
                  if (!currentLine.equals(StudentInfo)) {
                     writer.write(currentLine);
                     writer.newLine();
                  }
               }
               
               writer.write(builder.toString());
               writer.close();
               tempFile.renameTo(originFile);
               
               if (!builder.toString().equalsIgnoreCase(StudentInfo))
                  System.out.println("\nStudent's schedule has been updated.\n\nOperation Successful.");
               else
                  System.out.println("\nNo such course was found in Student's schedule.\nOperation abandoned.");
               
            } catch (FileNotFoundException e) {
               e.printStackTrace();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
         else {
            System.out.println("Invalid Input.");
         }
      
   }
   
   /**
    * Displays all data about a specific student
    * 
    * @param line   receives the line that contains all the information about a student
    * Time Complexity = big-O(n)
    */
   public static void display(String line) {
      
      StringTokenizer tokenizer = new StringTokenizer(line, ",");
      String token;
      int count = 1;
      
      while (tokenizer.hasMoreTokens()) {
         token = tokenizer.nextToken();
         
         if(token.startsWith("{{")){
            token = token.substring(2);
            System.out.println("\nCourse               : " + token);
            
            token = tokenizer.nextToken();
            System.out.println("Equivalent Credit    : " + token);
         }
         else if (token.startsWith("{")) {
            token = token.substring(1);
            System.out.println("\nCourse               : " + token);
            
            token = tokenizer.nextToken();
            System.out.println("Equivalent Credit    : " + token);
         }
         else if (token.endsWith("}}")) {
            token = token.substring(0, token.length()-2);
            System.out.println("Grade received       : " + token + "\n");
         }
         else if (token.endsWith("}")) {
            token = token.substring(0, token.length()-1);
            System.out.println("Grade received       : " + token);
         }
         else if (token.compareTo("No student with this ID/Name exists!") != 0) {
            switch (count) {
            case 1:
               ID = Integer.parseInt(token);
               System.out.println("\nStudent ID           : " + ID);
               count++;
               break;
            case 2:
               lastName = token;
               System.out.println("Student Last  Name   : " + lastName);
               count++;
               break;
            case 3:
               firstName = token;
               System.out.println("Student First Name   : " + firstName);
               count++;
               break;
            case 4:
               totalCredits = Integer.parseInt(token);
               System.out.println("\nTotal Credits Taken  : " + totalCredits);
               count++;
               break;
            case 5:
               currentGPA = Float.parseFloat(token);
               System.out.println("Student Current GPA  : " + currentGPA);
               break;
            default:
               System.out.println("Something is Wrong.");
            }
         }
         else {
            System.out.println("\nNo Student with this combination of ID and Name exists!");
            System.exit(0);
         }
         
      } // while
   } // display()
   
   
}



