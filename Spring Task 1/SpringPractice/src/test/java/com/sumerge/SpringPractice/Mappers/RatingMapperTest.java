package com.sumerge.SpringPractice.Mappers;

import com.sumerge.SpringPractice.Entity.Course;
import com.sumerge.SpringPractice.Entity.Rating;
import com.sumerge.SpringPractice.Model.RatingDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static org.junit.jupiter.api.Assertions.*;




public class RatingMapperTest {

    private final RatingMapper mapper = Mappers.getMapper(RatingMapper.class);

    @Test
    public void testToDto() {
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
    public void testToEntity() {
        // create a RatingDto with a courseId
        RatingDto dto = new RatingDto();
        dto.setCourseId(10L);

        Rating rating = mapper.toEntity(dto);

        // assert that courseId in dto is mapped to course.id in entity
        assertNotNull(rating.getCourse(), "Rating.course should not be null");
        assertEquals(10L, rating.getCourse().getId(), "The course.id should be mapped from courseId");
    }
}