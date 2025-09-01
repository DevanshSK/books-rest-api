package com.devanshsk.books.repositories;

import com.devanshsk.books.TestDataUtil;
import com.devanshsk.books.domain.entities.AuthorEntity;
import com.devanshsk.books.domain.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {
    private BookRepository underTest;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository underTest){
        this.underTest = underTest;
//        this.authorDao = authorDao;
    }

    @Test
    // Ensure that the Book has been created, and we can retrieve it using findOne
    public void testThatBookCanBeCreatedAndRecalled(){
        // Arrange: create Author and Book
        AuthorEntity author = TestDataUtil.createTestAuthor();
        BookEntity book = TestDataUtil.createTestBook(author);

        // Act 1: Save the book
        BookEntity savedBook = underTest.save(book);

        // Assert 1: Verify save worked
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getIsbn()).isEqualTo(book.getIsbn()); // ISBN is stable
        assertThat(savedBook.getTitle()).isEqualTo(book.getTitle());
        assertThat(savedBook.getAuthor().getName()).isEqualTo(author.getName());
        assertThat(savedBook.getAuthor().getId()).isNotNull(); // Hibernate assigned ID

        // Act 2: Fetch from DB
        Optional<BookEntity> result = underTest.findById(book.getIsbn());

        // Assert 2: Verify fetch worked
        assertThat(result).isPresent();
        BookEntity fetchedBook = result.get();

        assertThat(fetchedBook.getIsbn()).isEqualTo(book.getIsbn());
        assertThat(fetchedBook.getTitle()).isEqualTo(book.getTitle());
        assertThat(fetchedBook.getAuthor().getId()).isEqualTo(savedBook.getAuthor().getId());
        assertThat(fetchedBook.getAuthor().getName()).isEqualTo(author.getName());

        // Optional: check object equality (after field-level assertions)
        assertThat(fetchedBook).isEqualTo(savedBook);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled(){
        // Arrange: Prepare some books with Authors
        AuthorEntity authorA = TestDataUtil.createCustomAuthor( "Sung Jin Woo", 21);
        BookEntity bookA = TestDataUtil.createCustomBook("978-1-2345-6789-0", "The Shadow in the Attic", authorA);

        AuthorEntity authorB = TestDataUtil.createCustomAuthor( "Thomas Cronin", 44);
        BookEntity bookB = TestDataUtil.createCustomBook("978-1-2345-6789-1", "Beyond the Horizon", authorB);

        AuthorEntity authorC = TestDataUtil.createCustomAuthor( "Jesse A Casey", 24);
        BookEntity bookC = TestDataUtil.createCustomBook("978-1-2345-6789-2", "The Last Ember", authorC);

        // Act: Create and fetch these books in DB
        bookA = underTest.save(bookA);
        bookB = underTest.save(bookB);
        bookC = underTest.save(bookC);

        Iterable<BookEntity> result = underTest.findAll();

        // Assert: Verify the books
        assertThat(result)
                .hasSize(3)
                .containsExactly(bookA, bookB, bookC);
    }

    @Test
    public void testThatBookCanBeUpdated(){
        // Arrange: Create a book with an author.
        AuthorEntity author = TestDataUtil.createTestAuthor();
        BookEntity book = TestDataUtil.createTestBook(author);
        book = underTest.save(book);

        // // Act: Update the Book contents
        book.setTitle("Harry Potter and the Philosopher Stone");
        underTest.save(book);

        // Assert: Fetch the updated book and verify
        Optional<BookEntity> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatBookCanBeDeleted(){
        // Create a Book
        AuthorEntity author = TestDataUtil.createTestAuthor();
        BookEntity book = TestDataUtil.createTestBook(author);
        book = underTest.save(book);

        // Delete the Book
        underTest.deleteById(book.getIsbn());

        // Check for book and verify
        Optional<BookEntity> result = underTest.findById(book.getIsbn());
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatBookCanBeFetchedByTitle(){
        // Arrange: Prepare some books with Authors
        AuthorEntity authorA = TestDataUtil.createCustomAuthor( "Sung Jin Woo", 21);
        BookEntity bookA = TestDataUtil.createCustomBook("978-1-2345-6789-0", "The Shadow in the Attic", authorA);
        BookEntity bookB = TestDataUtil.createCustomBook("978-1-2345-6789-1", "Beyond the Horizon", authorA);
        BookEntity bookX = TestDataUtil.createCustomBook("978-1-2345-6789-9", "Beyond the Horizon", authorA);

        AuthorEntity authorC = TestDataUtil.createCustomAuthor( "Jesse A Casey", 24);
        BookEntity bookC = TestDataUtil.createCustomBook("978-1-2345-6789-2", "The Last Ember", authorC);

        // Act: Create and fetch these books in DB
        bookA = underTest.save(bookA);
        bookB = underTest.save(bookB);
        bookX = underTest.save(bookX);
        bookC = underTest.save(bookC);

        // Assert: Fetch and verify the books by title
        Iterable<BookEntity> result = underTest.findBooksByTitle("Beyond the Horizon");
        assertThat(result).hasSize(2).containsExactly(bookB, bookX);
    }

    @Test
    public void testThatBookCanBeFetchedByAuthor(){
        // Arrange: Prepare some books with Authors
        AuthorEntity authorA = TestDataUtil.createCustomAuthor( "Sung Jin Woo", 21);

        BookEntity bookA = TestDataUtil.createCustomBook("978-1-2345-6789-0", "The Shadow in the Attic", authorA);
        bookA = underTest.save(bookA);
        authorA = bookA.getAuthor();    // Ensure we use the persisted author

        BookEntity bookB = TestDataUtil.createCustomBook("978-1-2345-6789-1", "Beyond the Horizon", authorA);
        bookB = underTest.save(bookB);

        BookEntity bookX = TestDataUtil.createCustomBook("978-1-2345-6789-9", "Beyond the Horizon 2", authorA);
        bookX = underTest.save(bookX);

        AuthorEntity authorB = TestDataUtil.createCustomAuthor( "Jesse A Casey", 24);
        BookEntity bookC = TestDataUtil.createCustomBook("978-1-2345-6789-2", "The Last Ember", authorB);
        bookC = underTest.save(bookC);


        // Act: Fetch some books by author.
        Iterable<BookEntity> result = underTest.findBooksByAuthor(bookA.getAuthor());


        // Assert: Verify books by author
        assertThat(result).hasSize(3).containsExactly(bookA, bookB, bookX);
    }
}
