package com.sumerge.SpringPractice.Mappers;

import com.sumerge.SpringPractice.Entity.Author;
import com.sumerge.SpringPractice.Entity.Course;
import com.sumerge.SpringPractice.Model.CourseDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;




 class CourseMapperTest {

    // Use Mapstruct's factory to get an instance of the mapper.
    private final CourseMapper mapper = Mappers.getMapper(CourseMapper.class);

    @Test
     void testToDtoMapping() {
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
     void testToEntityMapping() {
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

    @Test
     void testToDto_NullCourse() {
        // Test mapping when the input Course is null
        CourseDto dto = mapper.toDto(null);
        assertNull(dto);
    }

    @Test
     void testToDto_NullAuthorInCourse() {
        // Test mapping when the Course has a null Author
        Course course = Course.builder()
                .id(1L)
                .name("Sample Course")
                .credit(3)
                .description("Sample Description")
                .author(null)
                .build();

        CourseDto dto = mapper.toDto(course);
        assertNotNull(dto);
        assertNull(dto.getAuthorId());
        assertEquals(1L, dto.getId());
        assertEquals("Sample Course", dto.getName());
        assertEquals(3, dto.getCredit());
        assertEquals("Sample Description", dto.getDescription());
    }

    @Test
     void testToEntity_NullDto() {
        // Test mapping when the input CourseDto is null
        Course course = mapper.toEntity(null);
        assertNull(course);
    }

    @Test
     void testToEntity_NullAuthorIdInDto() {
        // Test mapping when the CourseDto has a null authorId
        CourseDto dto = CourseDto.builder()
                .id(1L)
                .name("Sample Course")
                .credit(3)
                .description("Sample Description")
                .authorId(null)
                .build();

        Course course = mapper.toEntity(dto);
        assertNotNull(course);
        assertNotNull(course.getAuthor());
        assertNull(course.getAuthor().getId());
        assertEquals(1L, course.getId());
        assertEquals("Sample Course", course.getName());
        assertEquals(3, course.getCredit());
        assertEquals("Sample Description", course.getDescription());
    }

}