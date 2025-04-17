package com.sumerge.SpringPractice.Mappers;

import com.sumerge.SpringPractice.Entity.Author;
import com.sumerge.SpringPractice.Entity.Course;
import com.sumerge.SpringPractice.Model.CourseDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static org.junit.jupiter.api.Assertions.*;




public class CourseMapperTest {

    // Use Mapstruct's factory to get an instance of the mapper.
    private final CourseMapper mapper = Mappers.getMapper(CourseMapper.class);

    @Test
    public void testToDtoMapping() {
        // Arrange: create a dummy Course with an Author having an id.
        Author author = new Author();
        author.setId(100L);
        
        Course course = new Course();
        course.setAuthor(author);

        // Act: map Course to CourseDto.
        CourseDto dto = mapper.toDto(course);

        // Assert: check that the author id is correctly mapped.
        assertNotNull(dto, "CourseDto should not be null");
        assertEquals(author.getId(), dto.getAuthorId(), "The authorId should match the Author's id");
    }

    @Test
    public void testToEntityMapping() {
        // Arrange: create a CourseDto with an authorId.
        CourseDto dto = new CourseDto();
        dto.setAuthorId(200L);

        // Act: map CourseDto to Course.
        Course course = mapper.toEntity(dto);

        // Assert: check that the Author object was created and its id matches.
        assertNotNull(course, "Course should not be null");
        assertNotNull(course.getAuthor(), "Author should not be null");
        assertEquals(dto.getAuthorId(), course.getAuthor().getId(), "The Author's id should match the authorId");
    }
}