package com.example.quizzen.flashcards;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class FlashcardSetTest {

    @Test
    public void testGetTitle() {
        FlashcardSet flashcardSet = new FlashcardSet("Set 1", "user1", "set1", false, new ArrayList<>());
        assertEquals("Set 1", flashcardSet.getTitle());
    }

    @Test
    public void testGetSetID() {
        FlashcardSet flashcardSet = new FlashcardSet("Set 1", "user1", "set1", false, new ArrayList<>());
        assertEquals("set1", flashcardSet.getSetID());
    }

    @Test
    public void testGetUserID() {
        FlashcardSet flashcardSet = new FlashcardSet("Set 1", "user1", "set1", false, new ArrayList<>());
        assertEquals("user1", flashcardSet.getUserID());
    }

    @Test
    public void testIsFavorite() {
        FlashcardSet flashcardSet = new FlashcardSet("Set 1", "user1", "set1", true, new ArrayList<>());
        assertTrue(flashcardSet.isFavorite());
    }

    @Test
    public void testGetCards() {
        List<List<String>> cards = new ArrayList<>();
        cards.add(List.of("Question 1", "Answer 1"));
        cards.add(List.of("Question 2", "Answer 2"));

        FlashcardSet flashcardSet = new FlashcardSet("Set 1", "user1", "set1", false, cards);
        assertEquals(cards, flashcardSet.getCards());
    }
}
