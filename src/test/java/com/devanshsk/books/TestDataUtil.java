package com.devanshsk.books;

import com.devanshsk.books.domain.entities.AuthorEntity;
import com.devanshsk.books.domain.entities.BookEntity;

public final class TestDataUtil {
    private TestDataUtil(){}

    public static AuthorEntity createTestAuthor() {
        return AuthorEntity.builder()
                .name("John Doe")
                .age(80)
                .build();
    }

    public static AuthorEntity createCustomAuthor(String name, int age) {
        return AuthorEntity.builder()
                .name(name)
                .age(age)
                .build();
    }



    public static BookEntity createTestBook(final AuthorEntity author) {
        return BookEntity.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .author(author)
                .build();
    }

    public static BookEntity createCustomBook(String isbn, String title, final AuthorEntity author) {
        return BookEntity.builder()
                .isbn(isbn)
                .title(title)
                .author(author)
                .build();
    }
}

