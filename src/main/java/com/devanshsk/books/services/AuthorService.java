package com.devanshsk.books.services;

import com.devanshsk.books.domain.entities.AuthorEntity;

import java.util.List;

public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity author);

    List<AuthorEntity> findAll();
}
