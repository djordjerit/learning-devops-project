package com.example.bookapi.controller;

import com.example.bookapi.model.Book;
import com.example.bookapi.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * BookControllerTest - Unit tests for BookController REST endpoints
 */
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        // Clear books before each test to ensure consistent state
        // In a real application, you might use a test database or reset method
    }

    /**
     * Test GET /api/books/hello endpoint
     */
    @Test
    public void testHelloEndpoint() throws Exception {
        mockMvc.perform(get("/api/books/hello")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", equalTo("Hello from BookAPI!")))
                .andExpect(jsonPath("$.status", equalTo("API is running")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    /**
     * Test GET /api/books - Get all books
     */
    @Test
    public void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", isA(List.class)))
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[0].title", notNullValue()));
    }

    /**
     * Test GET /api/books/{id} - Get specific book
     */
    @Test
    public void testGetBookById() throws Exception {
        mockMvc.perform(get("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.title", notNullValue()));
    }

    /**
     * Test GET /api/books/{id} - Book not found (404)
     */
    @Test
    public void testGetBookByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/books/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", equalTo("Book not found")))
                .andExpect(jsonPath("$.id", equalTo(999)));
    }

    /**
     * Test POST /api/books - Create new book
     */
    @Test
    public void testCreateBook() throws Exception {
        Book newBook = new Book(null, "New Test Book", "Test Author", 2024, true);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", equalTo("New Test Book")))
                .andExpect(jsonPath("$.author", equalTo("Test Author")));
    }

    /**
     * Test POST /api/books - Create with minimal data
     */
    @Test
    public void testCreateBookMinimalData() throws Exception {
        String json = "{\"title\": \"Minimal Book\", \"author\": \"Unknown\"}";

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", equalTo("Minimal Book")))
                .andExpect(jsonPath("$.available", equalTo(true)));
    }

    /**
     * Test PUT /api/books/{id} - Update book
     */
    @Test
    public void testUpdateBook() throws Exception {
        Book updateData = new Book();
        updateData.setTitle("Updated Title");
        updateData.setAuthor("Updated Author");

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.title", equalTo("Updated Title")))
                .andExpect(jsonPath("$.author", equalTo("Updated Author")));
    }

    /**
     * Test PUT /api/books/{id} - Update non-existing book
     */
    @Test
    public void testUpdateBookNotFound() throws Exception {
        Book updateData = new Book();
        updateData.setTitle("Updated Title");

        mockMvc.perform(put("/api/books/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", equalTo("Book not found")));
    }

    /**
     * Test DELETE /api/books/{id} - Delete book
     */
    @Test
    public void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", equalTo("Book deleted successfully")))
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    /**
     * Test DELETE /api/books/{id} - Delete non-existing book
     */
    @Test
    public void testDeleteBookNotFound() throws Exception {
        mockMvc.perform(delete("/api/books/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", equalTo("Book not found")));
    }

    /**
     * Test GET /api/books/stats/summary - Statistics endpoint
     */
    @Test
    public void testGetStatistics() throws Exception {
        mockMvc.perform(get("/api/books/stats/summary")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalBooks", notNullValue()))
                .andExpect(jsonPath("$.availableBooks", notNullValue()))
                .andExpect(jsonPath("$.unavailableBooks", notNullValue()));
    }

    /**
     * Test response content type
     */
    @Test
    public void testResponseContentType() throws Exception {
        mockMvc.perform(get("/api/books/hello"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
