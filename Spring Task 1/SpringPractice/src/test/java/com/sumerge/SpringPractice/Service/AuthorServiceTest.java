package com.sumerge.SpringPractice.Service;

import com.sumerge.SpringPractice.Entity.Author;
import com.sumerge.SpringPractice.Exception.ResourceNotFoundException;
import com.sumerge.SpringPractice.Mappers.AuthorMapper;
import com.sumerge.SpringPractice.Model.AuthorDto;
import com.sumerge.SpringPractice.Repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;





@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorService authorService;

    @Test
    void testCreateAuthor() {
        AuthorDto dto = new AuthorDto();
        dto.setName("John Doe");
        dto.setEmail("john@example.com");
        dto.setBirthdate(LocalDate.of(1980, 1, 1));

        Author author = new Author();
        author.setName("John Doe");
        author.setEmail("john@example.com");
        author.setBirthdate(LocalDate.of(1980, 1, 1));

        when(authorMapper.toEntity(dto)).thenReturn(author);
        when(authorRepository.save(author)).thenReturn(author);
        when(authorMapper.toDto(author)).thenReturn(dto);

        AuthorDto result = authorService.createAuthor(dto);
        assertEquals(dto, result);
    }

    @Test
    void testGetAllAuthors() {
        Author author = new Author();
        AuthorDto dto = new AuthorDto();
        dto.setName("Jane Doe");

        when(authorRepository.findAll()).thenReturn(Arrays.asList(author));
        when(authorMapper.toDto(author)).thenReturn(dto);

        List<AuthorDto> result = authorService.getAllAuthors();
        assertEquals(1, result.size());
        assertEquals("Jane Doe", result.get(0).getName());
    }

    @Test
    void testGetAuthorByIdFound() {
        Long id = 1L;
        Author author = new Author();
        AuthorDto dto = new AuthorDto();
        dto.setName("Jane Doe");

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        when(authorMapper.toDto(author)).thenReturn(dto);

        AuthorDto result = authorService.getAuthorById(id);
        assertEquals("Jane Doe", result.getName());
    }

    @Test
    void testGetAuthorByIdNotFound() {
        Long id = 1L;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> authorService.getAuthorById(id));
        assertEquals("Author not found", exception.getMessage());
    }

    @Test
    void testDeleteAuthor() {
        Long id = 1L;
        authorService.deleteAuthor(id);
        verify(authorRepository).deleteById(id);
    }

    @Test
    void testUpdateAuthorFound() {
        Long id = 1L;
        Author existingAuthor = new Author();

        AuthorDto updateDto = new AuthorDto();
        updateDto.setName("Updated Name");
        updateDto.setEmail("updated@example.com");
        updateDto.setBirthdate(LocalDate.of(1990, 2, 2));

        when(authorRepository.findById(id)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.save(existingAuthor)).thenReturn(existingAuthor);
        when(authorMapper.toDto(existingAuthor)).thenReturn(updateDto);

        AuthorDto result = authorService.updateAuthor(id, updateDto);
        assertEquals("Updated Name", result.getName());
        assertEquals("updated@example.com", result.getEmail());
        assertEquals(LocalDate.of(1990, 2, 2), result.getBirthdate());
    }

    @Test
    void testUpdateAuthorNotFound() {
        Long id = 1L;
        AuthorDto updateDto = new AuthorDto();

        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> authorService.updateAuthor(id, updateDto));
        assertEquals("Author not found", exception.getMessage());
    }

    @Test
    void testGetAuthorByEmailFound() {
        String email = "test@example.com";
        Author author = new Author();
        AuthorDto dto = new AuthorDto();
        dto.setEmail(email);

        when(authorRepository.findByEmail(email)).thenReturn(Optional.of(author));
        when(authorMapper.toDto(author)).thenReturn(dto);

        AuthorDto result = authorService.getAuthorByEmail(email);
        assertEquals(email, result.getEmail());
    }

    @Test
    void testGetAuthorByEmailNotFound() {
        String email = "notfound@example.com";
        when(authorRepository.findByEmail(email)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> authorService.getAuthorByEmail(email));
        assertEquals("Author not found", exception.getMessage());
    }
}