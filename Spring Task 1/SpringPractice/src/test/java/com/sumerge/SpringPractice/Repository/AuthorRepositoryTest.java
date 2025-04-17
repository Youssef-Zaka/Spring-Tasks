package com.sumerge.SpringPractice.Repository;

import com.sumerge.SpringPractice.Entity.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;



@DataJpaTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testFindByEmail_ReturnsAuthor() {
        // Given: Create and save an Author instance
        Author author = new Author();
        author.setName("Jane Doe");
        author.setEmail("jane.doe@example.com");
        LocalDate birthdate = LocalDate.of(1990, 1, 1);
        author.setBirthdate(birthdate);
        authorRepository.save(author);

        // When: Find the Author by email
        Optional<Author> foundAuthor = authorRepository.findByEmail("jane.doe@example.com");

        // Then: Verify the result is present and matches expected email
        assertThat(foundAuthor).isPresent();
        assertThat(foundAuthor.get().getEmail()).isEqualTo("jane.doe@example.com");
        assertThat(foundAuthor.get().getName()).isEqualTo("Jane Doe");
        assertThat(foundAuthor.get().getBirthdate()).isEqualTo(birthdate);
    }

    @Test
    public void testFindByEmail_ReturnsEmpty() {
        // When: Searching for an email that does not exist in the repository
        Optional<Author> foundAuthor = authorRepository.findByEmail("nonexistent@example.com");

        // Then: The result should be empty
        assertThat(foundAuthor).isEmpty();
    }
}