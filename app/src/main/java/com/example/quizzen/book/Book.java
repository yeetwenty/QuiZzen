package com.example.quizzen.book;

/**
 * Book class.
 * @author Judy Xie u7397058
 */
public class Book {

    private String title;
    private String author;
    private int bookID;
    private boolean favorite;
    private String text;

    // Constructor
    public Book(String title, String author, int bookID, boolean favorite, String text) {
        this.title = title;
        this.author = author;
        this.bookID = bookID;
        this.favorite = favorite;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
