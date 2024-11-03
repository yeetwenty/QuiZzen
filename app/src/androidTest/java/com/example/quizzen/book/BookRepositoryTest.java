package com.example.quizzen.book;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryTest {

    @Test
    public void testGetAllBooks() throws IOException {
        // Create a mock JSON string representing a list of books
        String json = "[{\"title\":\"Title 1\",\"author\":\"Author 1\",\"id\":1,\"favorite\":true,\"text\":\"Text 1\"},{\"title\":\"Title 2\",\"author\":\"Author 2\",\"id\":2,\"favorite\":false,\"text\":\"Text 2\"}]";

        // Convert the JSON string to an InputStream
        InputStream inputStream = new ByteArrayInputStream(json.getBytes());

        // Call the readJsonStream() method from the Parser class to parse the JSON and get the list of books
        List<Book> mockBooks = Parser.readJsonStream(inputStream);

        // Create an instance of BookRepository with the mock list of books
        BookRepository bookRepository = new BookRepository(mockBooks);

        // Call the getAllBooks() method
        List<Book> result = bookRepository.getAllBooks();

        // Assert that the result matches the mock list of books
        Assert.assertEquals(mockBooks, result);
    }

    @Test
    public void testGetBookCount() {
        // Create a mock list of books
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(new Book("Title 1", "Author 1", 1, true, "Text 1"));
        mockBooks.add(new Book("Title 2", "Author 2", 2, false, "Text 2"));

        // Create an instance of BookRepository with the mock list of books
        BookRepository bookRepository = new BookRepository(mockBooks);

        // Call the getBookCount() method
        int result = bookRepository.getBookCount();

        // Assert that the result matches the size of the mock list of books
        Assert.assertEquals(mockBooks.size(), result);
    }
}