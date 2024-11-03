package com.example.quizzen.flashcards;

import static org.junit.Assert.*;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TokenizerTest {

    @Test
    public void testTokenize() {
        Tokenizer tokenizer = new Tokenizer();
        String text = "hello world! this is a sample text.";

        List<String> expectedTokens = new ArrayList<>();
        expectedTokens.add("hello");
        expectedTokens.add("world!");
        expectedTokens.add("this");
        expectedTokens.add("is");
        expectedTokens.add("a");
        expectedTokens.add("sample");
        expectedTokens.add("text.");

        List<String> actualTokens = tokenizer.tokenize(text);

        assertEquals(expectedTokens, actualTokens);
    }
}
