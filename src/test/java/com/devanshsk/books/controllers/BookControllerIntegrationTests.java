package com.devanshsk.books.controllers;

import com.devanshsk.books.TestDataUtil;
import com.devanshsk.books.domain.dto.BookDto;
import com.devanshsk.books.domain.entities.AuthorEntity;
import com.devanshsk.books.domain.entities.BookEntity;
import com.devanshsk.books.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private BookService bookService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.bookService = bookService;
    }

    @Test
    public void testThatCreateBookReturnsHttpStatus201Created() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthor();
        BookEntity testBook = TestDataUtil.createTestBook(author);
        String bookJson = objectMapper.writeValueAsString(testBook);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/978-1-2345-6789-0")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookReturnsCreatedBook() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthor();
        BookEntity testBook = TestDataUtil.createTestBook(author);
        String bookJson = objectMapper.writeValueAsString(testBook);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/978-1-2345-6789-0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("978-1-2345-6789-0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("The Shadow in the Attic")
        );
    }

    @Test
    public void testThatListBooksReturnsHttpStatus200Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListBooksReturnsBook() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthor();
        BookEntity testBook = TestDataUtil.createTestBook(author);
        bookService.createBook(testBook.getIsbn(), testBook);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value("978-1-2345-6789-0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value("The Shadow in the Attic")
        );
    }

}
