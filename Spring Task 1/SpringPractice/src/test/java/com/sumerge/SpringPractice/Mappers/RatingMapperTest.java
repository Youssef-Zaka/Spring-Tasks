package com.sumerge.SpringPractice.Mappers;

import com.sumerge.SpringPractice.Entity.Course;
import com.sumerge.SpringPractice.Entity.Rating;
import com.sumerge.SpringPractice.Model.RatingDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;




 class RatingMapperTest {

    private final RatingMapper mapper = Mappers.getMapper(RatingMapper.class);

    @Test
     void testToDto() {
        // create a Rating entity with a Course having an id
        Rating rating = new Rating();
        Course course = new Course();
        course.setId(5L);
        rating.setCourse(course);

        RatingDto dto = mapper.toDto(rating);

        // assert that course.id is mapped to courseId in dto
        assertEquals(5L, dto.getCourseId(), "The courseId should be mapped from course.id");
    }

    @Test
     void testToEntity() {
        // create a RatingDto with a courseId
        RatingDto dto = new RatingDto();
        dto.setCourseId(10L);

        Rating rating = mapper.toEntity(dto);

        // assert that courseId in dto is mapped to course.id in entity
        assertNotNull(rating.getCourse(), "Rating.course should not be null");
        assertEquals(10L, rating.getCourse().getId(), "The course.id should be mapped from courseId");
    }

    @Test
     void testToDto_NullRating() {
        // Test mapping when the input Rating is null
        RatingDto dto = mapper.toDto(null);
        assertNull(dto);
    }

    @Test
     void testToDto_NullCourseInRating() {
        // Test mapping when the Rating has a null Course
        Rating rating = new Rating();
        rating.setCourse(null);
        rating.setId(1L);
        rating.setNumber(5);

        RatingDto dto = mapper.toDto(rating);
        assertNotNull(dto);
        assertNull(dto.getCourseId());
        assertEquals(1L, dto.getId());
        assertEquals(5, dto.getNumber());
    }

    @Test
     void testToEntity_NullDto() {
        // Test mapping when the input RatingDto is null
        Rating rating = mapper.toEntity(null);
        assertNull(rating);
    }

    @Test
     void testToEntity_NullCourseIdInDto() {
        // Test mapping when the RatingDto has a null courseId
        RatingDto dto = new RatingDto();
        dto.setCourseId(null);
        dto.setId(2L);
        dto.setNumber(4);

        Rating rating = mapper.toEntity(dto);
        assertNotNull(rating);
        assertNotNull(rating.getCourse());
        assertNull(rating.getCourse().getId());
        assertEquals(2L, rating.getId());
        assertEquals(4, rating.getNumber());
    }

}