package com.example.quizzen.flashcards;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a tokenizer class that when a string of texts is given, turns it into an arraylist of
 * Tokens using white space delimiter
 * @author Jiazhe Lin
 * @version 1.0
 */
public class Tokenizer {
    public static List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        String[] words = input.trim().split("\\s+");
        for (String word : words) {
            tokens.add(word.toLowerCase());
        }
        return tokens;
    }
}

