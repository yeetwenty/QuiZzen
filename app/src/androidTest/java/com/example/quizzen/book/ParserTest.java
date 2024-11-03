package com.example.quizzen.book;

import static org.junit.Assert.*;

import org.junit.Test;

import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import static org.junit.Assert.assertEquals;

import android.util.JsonReader;

public class ParserTest {

    @Test
    public void testReadJsonStream() throws IOException {
        String json = "[{\"title\":\"Book 1\", \"author\":\"Author 1\", \"id\":1, \"favorite\":true, \"text\":\"Text 1\"}, " +
                "{\"title\":\"Book 2\", \"author\":\"Author 2\", \"id\":2, \"favorite\":false, \"text\":\"Text 2\"}]";
        InputStream in = new ByteArrayInputStream(json.getBytes());
        List<Book> books = Parser.readJsonStream(in);
        assertEquals(2, books.size());
        assertEquals("Book 1", books.get(0).getTitle());
        assertEquals("Author 1", books.get(0).getAuthor());
        assertEquals(1, books.get(0).getBookID());
        assertEquals(true, books.get(0).isFavorite());
        assertEquals("Text 1", books.get(0).getText());
        assertEquals("Book 2", books.get(1).getTitle());
        assertEquals("Author 2", books.get(1).getAuthor());
        assertEquals(2, books.get(1).getBookID());
        assertEquals(false, books.get(1).isFavorite());
        assertEquals("Text 2", books.get(1).getText());
    }

    @Test
    public void testReadBookArray() throws IOException {
        String json = "[{\"title\":\"Book 1\", \"author\":\"Author 1\", \"id\":1, \"favorite\":true, \"text\":\"Text 1\"}]";
        InputStream in = new ByteArrayInputStream(json.getBytes());
        JsonReader reader = new JsonReader(new java.io.InputStreamReader(in, StandardCharsets.UTF_8));
        List<Book> books = Parser.readBookArray(reader);
        assertEquals(1, books.size());
        assertEquals("Book 1", books.get(0).getTitle());
        assertEquals("Author 1", books.get(0).getAuthor());
        assertEquals(1, books.get(0).getBookID());
        assertEquals(true, books.get(0).isFavorite());
        assertEquals("Text 1", books.get(0).getText());
    }

    @Test
    public void testReadBook() throws IOException {
        String json = "{\"title\":\"Book 1\", \"author\":\"Author 1\", \"id\":1, \"favorite\":true, \"text\":\"Text 1\"}";
        InputStream in = new ByteArrayInputStream(json.getBytes());
        JsonReader reader = new JsonReader(new java.io.InputStreamReader(in, StandardCharsets.UTF_8));
        Book book = Parser.readBook(reader);
        assertEquals("Book 1", book.getTitle());
        assertEquals("Author 1", book.getAuthor());
        assertEquals(1, book.getBookID());
        assertEquals(true, book.isFavorite());
        assertEquals("Text 1", book.getText());
    }

}