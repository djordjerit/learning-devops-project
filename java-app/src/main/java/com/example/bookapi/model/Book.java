package com.example.bookapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Book Model - Represents a book in our system
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Long id;
    private String title;
    private String author;
    private Integer year;
    private Boolean available;

    /**
     * Constructor with only required fields
     */
    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = java.time.Year.now().getValue();
        this.available = true;
    }
}
