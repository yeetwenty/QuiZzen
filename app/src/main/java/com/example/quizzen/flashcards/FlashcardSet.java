package com.example.quizzen.flashcards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Flashcard set class.
 * @author Judy Xie u7397058
 */
public class FlashcardSet implements Serializable {

    String title;
    String setID;
    String userID;
    boolean favorite;
    List<List<String>> cards = new ArrayList<>();

    public FlashcardSet() {
        // required empty constructor
    }

    public FlashcardSet(String title, String uid, String id, boolean favorite, List<List<String>> cards) {
        this.title = title;
        this.userID = uid;
        this.setID = id;
        this.favorite = favorite;
        this.cards = cards;
    }

    public String getTitle() {
        return title;
    }

    public String getSetID() {
        return setID;
    }

    public String getUserID() {
        return userID;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public List<List<String>> getCards() {
        return cards;
    }

}
