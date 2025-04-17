package com.sumerge.SpringPractice.Mappers;

import com.sumerge.SpringPractice.Entity.Author;
import com.sumerge.SpringPractice.Model.AuthorDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


 class AuthorMapperTest {

    @Autowired
    private final AuthorMapper authorMapper = Mappers.getMapper(AuthorMapper.class);

    @Test
     void testToDto() {
        // Arrange
        Author author = new Author();
        author.setId(1L);
        author.setName("John Doe");
        
        // Act
        AuthorDto dto = authorMapper.toDto(author);
        
        // Assert
        assertNotNull(dto);
        assertEquals(author.getId(), dto.getId());
        assertEquals(author.getName(), dto.getName());
    }

    @Test
     void testToEntity() {
        // Arrange
        AuthorDto dto = new AuthorDto();
        dto.setId(2L);
        dto.setName("Jane Doe");
        
        // Act
        Author author = authorMapper.toEntity(dto);
        
        // Assert
        assertNotNull(author);
        assertEquals(dto.getId(), author.getId());
        assertEquals(dto.getName(), author.getName());
    }

    @Test
     void testToDto_NullAuthor() {
        // Test mapping when the input Author is null
        AuthorDto dto = authorMapper.toDto(null);
        assertNull(dto);
    }


    @Test
     void testToEntity_NullAuthorDto() {
        // Test mapping when the input AuthorDto is null
        Author author = authorMapper.toEntity(null);
        assertNull(author);
    }
}