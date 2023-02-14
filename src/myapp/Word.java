package myapp;

import java.util.HashMap;
import java.util.Map;

public class Word {
    
    private String name;
    private Map<String, Integer> nextWordCounts = new HashMap<>();
    private int countOfAllNextWords;

    // Constructor
    public Word(String name) {
        this.name = name;
    }

    // Getters
    public String getName() {
        return name;
    }

    public Map<String, Integer> getNextWordCounts() {
        return nextWordCounts;
    }

    public int getCountOfAllNextWords() {
        return countOfAllNextWords;
    }

    // If a next word is new, initiate its count to 1. If a next word already exists, increment its existing count by 1.
    public void updateNextWordCount(String nextWord) {
        if (!nextWordCounts.containsKey(nextWord)) {
            nextWordCounts.put(nextWord, 1);
        } else {
            int currentCountOfNextWord = nextWordCounts.get(nextWord);
            nextWordCounts.put(nextWord,currentCountOfNextWord + 1);
        }
        countOfAllNextWords++;
    }

    // Helper method to print the probabilities
    public void printNextWordProbabilities() {
        System.out.println(name);
        nextWordCounts.forEach(
            (key, value) -> {
                if (value/countOfAllNextWords == 1) {
                    System.out.printf("\t %s %d\n", key, value/countOfAllNextWords); // print without decimal point
                } else if (value/countOfAllNextWords < 1) {
                    System.out.printf("\t %s %.1f\n", key, ((float) (value))/countOfAllNextWords); // print with decimal point
                }
            }
        );
    }
}
