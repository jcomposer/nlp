
/**
 * The spellcorrection class implements an application that 
 * offers a suggestion for a misspelled word.
 *
 * @author      Joseph Sanu 
 * @version     1.2                
 * @since       2014-09-10
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.Scanner;

    public class spellcorrection {
        final static String WORDFR = System.getProperty("user.dir")+"\\count_big.txt"; //file containing dictionary words.
        final static String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
        static ArrayList<String> candidates = new ArrayList<String>(); 
        public static final List<String> lines = readSmallTextFile(WORDFR);
        public static String mainCandidate = "";
        public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {    

        System.out.print("Enter a word: ");
        while(inDictionary()) {
        alteration(mainCandidate.toLowerCase());
        transposition(mainCandidate.toLowerCase());
        deletion(mainCandidate.toLowerCase());
        insertion(mainCandidate.toLowerCase());
        
        int candidateOccurance = 0;
        for (String w : candidates) {
            try {
                if(Integer.valueOf(lines.get(lines.indexOf(w)+1)) > candidateOccurance) {
                    mainCandidate = w;
                    candidateOccurance = Integer.valueOf(lines.get(lines.indexOf(w)+1));
                }}catch (Exception e) {}    
        }
        log(mainCandidate);
        candidates.clear();
        System.out.print("Enter a word: ");
        }
    }
    
      
    /**
    * Reads words from dictionary.
    *
    * Note: Splits each word in the dictionary and its frequency 
    * and stores them in an array list.
    *
    * @param fileName file from which words are read
    * @return         list of all dictionary words and the frequency
    */  
    public static List<String> readSmallTextFile(String fileName) {

        List<String> parsed = new ArrayList<String>();
        
        try {
        BufferedReader count = new BufferedReader(new FileReader(fileName));
        String readLine = count.readLine();
            while(readLine!=null) {
                parsed.addAll(Arrays.asList(readLine.split("\\s+")));
                readLine = count.readLine();            
            }
            count.close();
            return Collections.unmodifiableList(parsed);
        
        } catch (IOException e) {
            log("File not found.");
            return null;
        }
        
    }
    
    /**
    * Finds candidates through transposition.
    *
    * @param correct the misspelled word.
    */ 
    public static void transposition(String correct) {

        for(int j =1; j < correct.length(); j++) {
                
            StringBuilder word = new StringBuilder(correct);
            word.setCharAt(j,correct.charAt(j-1));
            word.setCharAt(j-1,correct.charAt(j));
            candidates.add(word.toString());
        }       
    }
    
    /**
    * Finds candidates through deletion.
    *
    * @param correct the misspelled word.
    */ 
    public static void deletion(String correct) {
    
        for(int j =0; j < correct.length(); j++) {
                
            StringBuilder word = new StringBuilder(correct);
            word = word.deleteCharAt(j);
            candidates.add(word.toString());
        }   
    }
    
    /**
    * Finds candidates through insertion.
    *
    * @param correct the misspelled word.
    */ 
    public static void insertion(String correct) {
    
        for(int j =0; j <= correct.length(); j++) {
            
            for(int k=0; k < 26; k++) {
                StringBuilder word = new StringBuilder(correct);
                word = word.insert(j,ALPHABET.charAt(k));
                candidates.add(word.toString());            
            }
        }
    }
    
    /**
    * Finds candidates through alteration.
    *
    * @param correct the misspelled word.
    */ 
    public static void alteration(String correct) {

        for(int j =0; j < correct.length(); j++) {
            
            StringBuilder word = new StringBuilder(correct);
            for(int k=0; k < 26; k++) {
                word.setCharAt(j,ALPHABET.charAt(k));
                candidates.add(word.toString());            
            }
        }   
    }
    
    /**
    * Checks if word is misspelled.
    *
    * @return true if word is misspelled.
    */ 
    public static boolean inDictionary(){
        while(input.hasNext()) {
            mainCandidate = input.nextLine();
            if(lines.contains(mainCandidate)) {
                log(mainCandidate);
                System.out.print("Enter a word: ");
            } else return true;
        }
        return false;
    }
    
    /**
    * Displays message to console.
    *
    * @param aMsg message to display.
    */ 
    private static void log(Object aMsg){
        System.out.println(String.valueOf(aMsg));
    }
  
}