package com.devanshsk.books.controllers;

import com.devanshsk.books.domain.entities.BookEntity;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
public class BookController {
    @GetMapping(path = "/books")
    public BookEntity retrieveBook(){
        return BookEntity.builder()
                .isbn("978-0-13-478627-5")
                .title("The Enigma of Eternity")
//                .author("Aria Montgomery")
                .yearPublished("2005")
                .build();
    }

    @PostMapping(path = "/books")
    public BookEntity createBook(@RequestBody final BookEntity book){
        log.info("Got Book: " + book.toString());
        return book;
    }
}
