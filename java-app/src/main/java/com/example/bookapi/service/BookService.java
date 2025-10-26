package com.example.bookapi.service;

import com.example.bookapi.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * BookService - Contains business logic for book management
 * Uses in-memory storage for Phase 1
 */
@Service
public class BookService {
    private static final List<Book> books = new ArrayList<>();
    private static final AtomicLong bookIdCounter = new AtomicLong(0);

    // Initialize with sample data
    static {
        books.add(new Book(1L, "Java Programming", "John Doe", 2022, true));
        books.add(new Book(2L, "Spring Boot Guide", "Jane Smith", 2023, true));
        books.add(new Book(3L, "Microservices Architecture", "Mike Johnson", 2021, false));
        bookIdCounter.set(3);
    }

    /**
     * Get all books
     */
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    /**
     * Get book by ID
     */
    public Book getBookById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Add a new book
     */
    public Book addBook(Book book) {
        // Generate new ID if not set
        if (book.getId() == null) {
            book.setId(bookIdCounter.incrementAndGet());
        }
        
        // Set defaults
        if (book.getYear() == null) {
            book.setYear(java.time.Year.now().getValue());
        }
        if (book.getAvailable() == null) {
            book.setAvailable(true);
        }
        
        books.add(book);
        return book;
    }

    /**
     * Update an existing book
     */
    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);
        if (book != null) {
            if (bookDetails.getTitle() != null) {
                book.setTitle(bookDetails.getTitle());
            }
            if (bookDetails.getAuthor() != null) {
                book.setAuthor(bookDetails.getAuthor());
            }
            if (bookDetails.getYear() != null) {
                book.setYear(bookDetails.getYear());
            }
            if (bookDetails.getAvailable() != null) {
                book.setAvailable(bookDetails.getAvailable());
            }
        }
        return book;
    }

    /**
     * Delete a book by ID
     */
    public boolean deleteBook(Long id) {
        return books.removeIf(book -> book.getId().equals(id));
    }

    /**
     * Count total books
     */
    public int getTotalBookCount() {
        return books.size();
    }

    /**
     * Get available books count
     */
    public int getAvailableBooksCount() {
        return (int) books.stream()
                .filter(Book::getAvailable)
                .count();
    }
}
