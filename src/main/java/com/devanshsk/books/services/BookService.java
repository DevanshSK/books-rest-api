package com.devanshsk.books.services;

import com.devanshsk.books.domain.entities.BookEntity;

public interface BookService {
    BookEntity createBook(String isbn, BookEntity book);
}
