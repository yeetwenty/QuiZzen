package com.example.quizzen.book;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Book repository class.
 * @author Judy Xie u7397058
 * @version 1.0
 */
public class BookRepository {

    List<Book> books;
    int bookCount;

    public BookRepository(List<Book> books) {
        this.books = books;
        this.bookCount = getBookCount();
    }

    public List<Book> getAllBooks() throws IOException {
        File[] files = new File("app/main/assets").listFiles();
        for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
            InputStream in = Files.newInputStream(files[i].toPath());
            books.add(Parser.readJsonStream(in).get(i));
        }
        return books;
    }

    public int getBookCount() {
        return books.size();
    }
}
