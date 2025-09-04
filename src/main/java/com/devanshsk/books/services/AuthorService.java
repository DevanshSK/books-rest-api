package com.devanshsk.books.services;

import com.devanshsk.books.domain.entities.AuthorEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorEntity save(AuthorEntity author);
    List<AuthorEntity> findAll();
    Slice<AuthorEntity> findAll(Pageable pageable);
    Optional<AuthorEntity> findOne(Long id);

    boolean isExists(Long id);
    AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity);
    void delete(Long id);
}
