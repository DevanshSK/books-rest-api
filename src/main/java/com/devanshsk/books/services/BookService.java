package com.devanshsk.books.services;

import com.devanshsk.books.domain.entities.BookEntity;

import java.util.List;

public interface BookService {
    BookEntity createBook(String isbn, BookEntity book);

    List<BookEntity> findAll();
}
