package com.example.quizzen.book;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
/**
 * A parser that implements Android's built in tokenizer for JSON.
 * @author Judy Xie u7397058
 */
public class Parser {

    // Given an InputStream of JSON, return a book.
    public static List<Book> readJsonStream(InputStream in) throws IOException {
        try (JsonReader reader = new JsonReader(new java.io.InputStreamReader(in, StandardCharsets.UTF_8))) {
            return readBookArray(reader);
        }
    }

    // Read an array of books.
    public static List<Book> readBookArray(JsonReader reader) throws IOException {
        List<Book> books = new ArrayList<>();

        reader.beginArray();

        while (reader.hasNext())
            books.add(readBook(reader));

        reader.endArray();
        return books;
    }

    // Read a book.
    public static Book readBook(JsonReader reader) throws IOException {
        String title = null;
        String author = null;
        boolean favorite = false;
        String text = null;
        int id = 0;

        reader.beginObject();

        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "title":
                    title = reader.nextString();
                    break;
                case "author":
                    author = reader.nextString();
                    break;
                case "id":
                    id = reader.nextInt();
                    break;
                case "favorite":
                    favorite = reader.nextBoolean();
                    break;
                case "text":
                    text = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Book(title, author, id, favorite, text);
    }






}
