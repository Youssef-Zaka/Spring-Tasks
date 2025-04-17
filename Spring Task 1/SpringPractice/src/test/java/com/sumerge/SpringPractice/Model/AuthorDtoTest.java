package com.sumerge.SpringPractice.Model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;



class AuthorDtoTest {

    @Test
    void testNoArgsConstructor() {
        AuthorDto author = new AuthorDto();
        assertNotNull(author);
    }

    @Test
    void testAllArgsConstructor() {
        LocalDate birthdate = LocalDate.of(1990, 1, 1);
        AuthorDto author = new AuthorDto(1L, "John Doe", "john.doe@example.com", birthdate);

        assertEquals(1L, author.getId());
        assertEquals("John Doe", author.getName());
        assertEquals("john.doe@example.com", author.getEmail());
        assertEquals(birthdate, author.getBirthdate());
    }

    @Test
    void testBuilder() {
        LocalDate birthdate = LocalDate.of(1990, 1, 1);
        AuthorDto author = AuthorDto.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .birthdate(birthdate)
                .build();

        assertEquals(1L, author.getId());
        assertEquals("John Doe", author.getName());
        assertEquals("john.doe@example.com", author.getEmail());
        assertEquals(birthdate, author.getBirthdate());
    }

    @Test
    void testSettersAndGetters() {
        LocalDate birthdate = LocalDate.of(1990, 1, 1);
        AuthorDto author = new AuthorDto();
        author.setId(1L);
        author.setName("John Doe");
        author.setEmail("john.doe@example.com");
        author.setBirthdate(birthdate);

        assertEquals(1L, author.getId());
        assertEquals("John Doe", author.getName());
        assertEquals("john.doe@example.com", author.getEmail());
        assertEquals(birthdate, author.getBirthdate());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDate birthdate = LocalDate.of(1990, 1, 1);
        AuthorDto author1 = new AuthorDto(1L, "John Doe", "john.doe@example.com", birthdate);
        AuthorDto author2 = new AuthorDto(1L, "John Doe", "john.doe@example.com", birthdate);

        assertEquals(author1, author2);
        assertEquals(author1.hashCode(), author2.hashCode());
    }

    @Test
    void testToString() {
        LocalDate birthdate = LocalDate.of(1990, 1, 1);
        AuthorDto author = new AuthorDto(1L, "John Doe", "john.doe@example.com", birthdate);

        String expected = "AuthorDto(id=1, name=John Doe, email=john.doe@example.com, birthdate=1990-01-01)";
        assertEquals(expected, author.toString());
    }
}