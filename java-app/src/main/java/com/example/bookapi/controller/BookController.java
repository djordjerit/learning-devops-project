package com.example.bookapi.controller;

import com.example.bookapi.model.Book;
import com.example.bookapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BookController - REST API endpoints for Book management
 * Base path: /api/books
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * GET /api/books/hello
     * Simple hello endpoint to verify API is working
     */
    @GetMapping("/hello")
    public ResponseEntity<Map<String, Object>> hello() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello from BookAPI!");
        response.put("status", "API is running");
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/books
     * Get all books
     */
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    /**
     * GET /api/books/{id}
     * Get a specific book by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Book not found");
            errorResponse.put("id", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    /**
     * POST /api/books
     * Create a new book
     */
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book createdBook = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    /**
     * PUT /api/books/{id}
     * Update an existing book
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            Book updatedBook = bookService.updateBook(id, bookDetails);
            return ResponseEntity.ok(updatedBook);
        } else {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Book not found");
            errorResponse.put("id", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    /**
     * DELETE /api/books/{id}
     * Delete a book by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteBook(id);
        if (deleted) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Book deleted successfully");
            response.put("id", id);
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Book not found");
            errorResponse.put("id", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    /**
     * GET /api/books/stats/summary
     * Get statistics about books
     */
    @GetMapping("/stats/summary")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalBooks", bookService.getTotalBookCount());
        stats.put("availableBooks", bookService.getAvailableBooksCount());
        stats.put("unavailableBooks", bookService.getTotalBookCount() - bookService.getAvailableBooksCount());
        return ResponseEntity.ok(stats);
    }
}
