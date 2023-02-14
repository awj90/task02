package myapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        
        try {
            
            // 1. Instantiate File object (actually a directory)
            Path path = Paths.get(args[0]);
            File directory = path.toFile();

            // 2. Set up some empty data structures for use later
            List<String> uniqueWords = new ArrayList<>();
            List<Word> words = new ArrayList<>();
            
            // 3. Iterate through each File object in the directory
            for (File file : directory.listFiles()) {
                String currentWord = "";
                String nextWord = "";
                String fileContent = preprocess(file);
                String[] strArray = fileContent.split("\\s+");

                // 4. Update Word object's next word count. i stops at array length minus 1 as the last word of a file will not have a next word.
                for (int i = 0; i < strArray.length - 1; i++) {
                    currentWord = strArray[i].trim();
                    nextWord = strArray[i+1].trim();
                    if (!uniqueWords.contains(currentWord)) {
                        uniqueWords.add(currentWord);
                        words.add(new Word(currentWord));
                    }
                    words.get(uniqueWords.indexOf(currentWord)).updateNextWordCount(nextWord);
                }
            }

            // 5. Print probabilities
            for (Word word : words) {
                word.printNextWordProbabilities();
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please provide file directory in args:");
            System.out.println("usage: java ... myapp.Main <directory_name>");
            System.exit(0);
        } 
    }

    // helper method to preprocess a File object into a String free of any punctuations and case differences
    public static String preprocess(File file) throws IOException {

        StringBuilder result = new StringBuilder();
        InputStream is = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line; 
        while ((line = br.readLine()) != null) { // continue reading the existing file line by line till the end
            result.append(line);
            result.append("\n");
        }
        br.close();
        isr.close();
        is.close();
        return result.toString() // convert StringBuilder object back to String
                .replaceAll("\\p{Punct}","") // remove punctuations
                .toLowerCase() // to lower case
                .replaceAll("\u2019", "") // strip off other unrecognized characters
                .replaceAll("\u201C", "") // strip off other unrecognized characters
                .replaceAll("\u2026", ""); // strip off other unrecognized characters
    }
}