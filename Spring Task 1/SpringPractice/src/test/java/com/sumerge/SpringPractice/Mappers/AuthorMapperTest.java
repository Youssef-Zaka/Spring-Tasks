package com.sumerge.SpringPractice.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.sumerge.SpringPractice.Entity.Author;
import com.sumerge.SpringPractice.Model.AuthorDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;




@SpringBootTest
public class AuthorMapperTest {

    @Autowired
    private AuthorMapper authorMapper;

    @Test
    public void testToDto() {
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
    public void testToEntity() {
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
}