package com.devanshsk.books.controllers;

import com.devanshsk.books.domain.dto.BookDto;
import com.devanshsk.books.domain.entities.BookEntity;
import com.devanshsk.books.mappers.Mapper;
import com.devanshsk.books.services.BookService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/books")
@Log
public class BookController {

    private Mapper<BookEntity, BookDto> bookMapper;
    private BookService bookService;

    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto){
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBook = bookService.createBook(isbn, bookEntity);
        BookDto savedDto = bookMapper.mapTo(savedBook);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

}
