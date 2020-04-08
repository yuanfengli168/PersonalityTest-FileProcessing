// Yuanfeng Li
// 03/03/2020
// TA: Rafia Khatri
// Assignment 7 Personality Test

// This assignment is a personality test which prompt for a .txt file and then
// read the content in it and return the result of what kind of personality do
// you have.
import java.util.*;
import java.io.*;

public class Personality {
   public static void main(String[] args) throws FileNotFoundException {
      Scanner console = new Scanner(System.in);
      intro();
      File file = new File(getFile("input file name? ", console));
      File outFile = new File(getFile("output file name? ", console));
      Scanner input = new Scanner(file);
      PrintStream output = new PrintStream(outFile);

      while (input.hasNextLine()) {
         String personName = input.nextLine();
         output.print(personName + ": ");
         String answerString = input.nextLine();
         String[] answerArr = new String[70];
         
         for (int index = 0; index < answerArr.length; index++) {
            if (index < answerString.length() - 1) {
               answerArr[index] = answerString.substring(index, index+1);
            } else {
               answerArr[index] = answerString.substring(index);
            }
         }
         
         int[] resultArr = getResultArr(answerArr);
         String personalityType = getPersonalityType(resultArr);         
         output.print(Arrays.toString(resultArr) + " = " + personalityType);
         output.println();   
      }
   } 
   
      
   // intro() method is going to introduce about the personality program 
   // it does not need any passing parameters 
   // it did not return any values
   public static void intro() {
      System.out.println("This program processes a file of answers to the");
      System.out.println("Keirsey Temperament Sorter. It converts the");
      System.out.println("various A and B answers for each person into");
      System.out.println("a sequence of B-percentages and then into a");
      System.out.println("four-letter personality type.");
      System.out.println(); 
   }
   
   // getFile method prompt for user about the filename 
   // parameters needed: String prompt - it indicates whether it is the input file
   //                                     or the output file
   //                    Scanner console - prompt for user's input
   // return: String filename - which is the filename that users typed
   public static String getFile(String prompt, Scanner console) {
      System.out.print(prompt);
      String fileName = console.nextLine();
      return fileName;
   } 
   
   // getResultArr() method is going to give the result for each dimension
   // the result is the percentage of B answers divided by total answers of
   // A and B, which do not inlude '-'
   // parameters needed: String[] answerArr - which contains 70 answers of the user
   // return: int[] - which is like [90, 15, 10, 10]
   public static int[] getResultArr(String[] answerArr) {
      int[] resultArr = new int[4];
      int resultFirstDimension = getFirstDimension(answerArr);
      resultArr[0] = resultFirstDimension; 
      int resultSecondDimension = getDimension(answerArr, 1);
      resultArr[1] = resultSecondDimension;
      int resultThirdDimension = getDimension(answerArr, 3);
      resultArr[2] = resultThirdDimension;
      int resultFourthDimension = getDimension(answerArr, 5);
      resultArr[3] = resultFourthDimension;
      return resultArr;
   }

   // getFirstDimension method get the percentage of B for the first dimension
   // paramers needed: String[] answerArr - which is the array form of somebody's answer
   // return: int result - which is the B percentage rounded to the whole number
   public static int getFirstDimension(String[] answerArr) {
      int numberOfA = 0;
      int numberOfB = 0;
      int numberOfBar = 0;
      int result = 0;
      for (int i = 0; i < answerArr.length; i += 7) {
         if (answerArr[i].equalsIgnoreCase("a")) {
            // how many As out there
            numberOfA++;
            // how many Bs out there
         } else if (answerArr[i].equalsIgnoreCase("b")) {
            numberOfB++;
         } else {
            numberOfBar++;
         }
      }
      return getBPercentage(numberOfA, numberOfB);
   }
   
   // getDimension method get the percentage of B for 2nd/3rd/4th Dimension in the program
   // parameters needed: String[] answerArr - which is the array form of somebody's answer
   // parameters needed: int startNumber - it is the number that count for the dimensions
   // return: int result - which is the B's answer's percentage rounded to the whole number
   // this mehtod could be used for many times. 
   public static int getDimension(String[] answerArr, int startNumber) {
      int numberOfA = 0;
      int numberOfB = 0;
      int numberOfBar = 0;
      int result = 0;
      for (int i = startNumber; i < answerArr.length; i += 7) {
         if (answerArr[i].equalsIgnoreCase("a")) {
            // how many As out there
            numberOfA++;
            // how many Bs out there
         } else if (answerArr[i].equalsIgnoreCase("b")) {
            numberOfB++;
         } else {
            numberOfBar++;
         }
      }
         
      for (int j = startNumber + 1; j < answerArr.length; j += 7) {

         if (answerArr[j].equalsIgnoreCase("a")) {
            // how many As out there
            numberOfA++;
            // how many Bs out there
         } else if (answerArr[j].equalsIgnoreCase("b")) {
            numberOfB++;
         } else {
            numberOfBar++;
         }
      }
      return getBPercentage(numberOfA, numberOfB);
   }
   
   // this method compute the percentage B out of A + B
   // parameters needed: int numberOfA - numbers of A for a certain Dimension
   //                    int numberOfB - numbers of B for a certain Dimension
   // return: int result - which is the number of percentage of B out of A + B
   public static int getBPercentage(int numberOfA, int numberOfB) {
      int result = (int) Math.round(100.0 * numberOfB/ (numberOfB + numberOfA));
      return result;
   }
   
   // this method get the personalityType for each user
   // parameters needed: int[] resultArr - which is the result of B percentage
   // for each dimension
   // return: nothing
   public static String getPersonalityType(int[] resultArr) {
      String firstOutCome = getDimensionChar(resultArr, 0, "I", "E");
      String secondOutCome = getDimensionChar(resultArr, 1, "N", "S");
      String thirdOutCome = getDimensionChar(resultArr, 2, "F", "T");
      String fourthOutCome = getDimensionChar(resultArr, 3, "P", "J");
      String resultOutCome = firstOutCome + secondOutCome + thirdOutCome + fourthOutCome;
      return resultOutCome;
   }
   
   // getDimensionChar method will give the exact personality type for each dimension
   // such as E: Extrovert, and I: Introvert
   // parameters needed: int[] resultArr - the array of result
   //                    int index - the index in resultArr which indicate the dimension
   //                    String answer1 - it is the personality type such as Extrovert
   //                    String answer2 - it is the personality type such as Introvert
   // return: String - which is the answer of personality type for each dimension, if A and
   // B's number are equal, it will return "X";
   public static String getDimensionChar(int[] resultArr, int index, String answer1, String answer2) {
      String outcome = "";
      if (resultArr[index] > 50) {
         outcome = answer1;
      } else if (resultArr[index] == 50) {
         outcome = "X";
      } else {
         outcome = answer2;
      }
      return outcome;
   }
}