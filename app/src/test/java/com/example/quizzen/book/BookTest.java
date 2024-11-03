package com.example.quizzen.book;

import org.junit.Test;
import static org.junit.Assert.*;

public class BookTest {

    @Test
    public void testGetTitle() {
        Book book = new Book("Title", "Author", 1, false, "Text");
        assertEquals("Title", book.getTitle());
    }

    @Test
    public void testSetTitle() {
        Book book = new Book("Title", "Author", 1, false, "Text");
        book.setTitle("New Title");
        assertEquals("New Title", book.getTitle());
    }

    @Test
    public void testGetAuthor() {
        Book book = new Book("Title", "Author", 1, false, "Text");
        assertEquals("Author", book.getAuthor());
    }

    @Test
    public void testSetAuthor() {
        Book book = new Book("Title", "Author", 1, false, "Text");
        book.setAuthor("New Author");
        assertEquals("New Author", book.getAuthor());
    }

    @Test
    public void testGetBookID() {
        Book book = new Book("Title", "Author", 1, false, "Text");
        assertEquals(1, book.getBookID());
    }

    @Test
    public void testSetBookID() {
        Book book = new Book("Title", "Author", 1, false, "Text");
        book.setBookID(2);
        assertEquals(2, book.getBookID());
    }

    @Test
    public void testIsFavorite() {
        Book book = new Book("Title", "Author", 1, true, "Text");
        assertTrue(book.isFavorite());
    }

    @Test
    public void testSetFavorite() {
        Book book = new Book("Title", "Author", 1, false, "Text");
        book.setFavorite(true);
        assertTrue(book.isFavorite());
    }

    @Test
    public void testGetText() {
        Book book = new Book("Title", "Author", 1, false, "Text");
        assertEquals("Text", book.getText());
    }

    @Test
    public void testSetText() {
        Book book = new Book("Title", "Author", 1, false, "Text");
        book.setText("New Text");
        assertEquals("New Text", book.getText());
    }

}
