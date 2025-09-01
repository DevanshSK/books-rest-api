package com.devanshsk.books.services.impl;

import com.devanshsk.books.domain.entities.BookEntity;
import com.devanshsk.books.repositories.BookRepository;
import com.devanshsk.books.services.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(String isbn, BookEntity book) {
        // Set the ISBN to the book
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }
}
