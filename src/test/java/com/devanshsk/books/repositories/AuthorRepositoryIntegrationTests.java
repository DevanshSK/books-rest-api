package com.devanshsk.books.repositories;

import com.devanshsk.books.TestDataUtil;
import com.devanshsk.books.domain.entities.AuthorEntity;
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
public class AuthorRepositoryIntegrationTests {
    private AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository underTest){
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled(){
        // Arrange: Create a test author
        AuthorEntity author = TestDataUtil.createTestAuthor();

        // Act: Save and then retrieve the author
        underTest.save(author);
        Optional<AuthorEntity> result = underTest.findById(author.getId());

        // Assert: Verify author was saved and retrieved correctly
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled(){
        // Arrange: Create multiple authors
        AuthorEntity authorA = TestDataUtil.createCustomAuthor("Sung Jin Woo", 21);
        AuthorEntity authorB = TestDataUtil.createCustomAuthor("Thomas Cronin", 44);
        AuthorEntity authorC = TestDataUtil.createCustomAuthor("Jesse A Casey", 24);

        // Act 1: Save multiple authors
        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

        // Act 2: Fetch all authors from DB
        Iterable<AuthorEntity> result = underTest.findAll();

        // Assert: Verify the authors.
        assertThat(result)
                .hasSize(3)
                .containsExactly(authorA, authorB, authorC);
    }

    @Test
    public void testThatAuthorCanBeUpdated(){
        // Arrange: Create an Author
        AuthorEntity author = TestDataUtil.createTestAuthor();
        underTest.save(author);

        // Act: Update its details
        author.setAge(21);
        author.setName("Jack Sparrow");
        underTest.save(author);

        // Assert: Find and Verify that author update
        Optional<AuthorEntity> result = underTest.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatAuthorCanBeDeleted(){
        // Create an Author
        AuthorEntity author = TestDataUtil.createTestAuthor();
        underTest.save(author);

        // Delete the Author
        underTest.deleteById(author.getId());

        // Check for that author and verify
        Optional<AuthorEntity> result = underTest.findById(author.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatGetAuthorsWithAgeLessThan(){
        // Create some authors
        AuthorEntity author = TestDataUtil.createTestAuthor();
        AuthorEntity authorA = TestDataUtil.createCustomAuthor("Sung Jin Woo", 21);
        AuthorEntity authorB = TestDataUtil.createCustomAuthor("Thomas Cronin", 64);
        AuthorEntity authorC = TestDataUtil.createCustomAuthor("Jesse A Casey", 24);

        // Save these authors
        underTest.save(author);
        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

        // Fetch and verify these authors.
        Iterable<AuthorEntity> result = underTest.ageLessThan(50);
        assertThat(result).hasSize(2).containsExactly(authorA, authorC);
    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan(){
        // Arrange: Create some authors
        AuthorEntity author = TestDataUtil.createTestAuthor();
        AuthorEntity authorA = TestDataUtil.createCustomAuthor("Sung Jin Woo", 21);
        AuthorEntity authorB = TestDataUtil.createCustomAuthor("Thomas Cronin", 64);
        AuthorEntity authorC = TestDataUtil.createCustomAuthor("Jesse A Casey", 24);

        // Act: Save and fetch these authors
        underTest.save(author);
        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);
        Iterable<AuthorEntity> result = underTest.findAuthorsWithAgeGreaterThan(50);

        // Assert: verify these authors.
        assertThat(result).hasSize(2).containsExactly(author, authorB);
    }
}
