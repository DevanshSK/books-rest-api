package com.devanshsk.books.repositories;


import com.devanshsk.books.domain.entities.AuthorEntity;
import com.devanshsk.books.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, String> {
    Iterable<BookEntity> findBooksByTitle(String title);
    Iterable<BookEntity> findBooksByAuthor(AuthorEntity author);
}
