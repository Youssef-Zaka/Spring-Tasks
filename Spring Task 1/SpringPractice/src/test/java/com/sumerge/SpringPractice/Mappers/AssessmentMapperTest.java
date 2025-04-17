package com.sumerge.SpringPractice.Mappers;

import com.sumerge.SpringPractice.Entity.Assessment;
import com.sumerge.SpringPractice.Entity.Course;
import com.sumerge.SpringPractice.Model.AssessmentDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mapstruct.factory.Mappers;



public class AssessmentMapperTest {

    private final AssessmentMapper mapper = Mappers.getMapper(AssessmentMapper.class);

    @Test
    public void testToDto() {
        // Create an Assessment instance with a nested Course having an id.
        Assessment assessment = new Assessment();
        Course course = new Course();
        course.setId(1L);
        assessment.setCourse(course);

        // Map to AssessmentDto and verify the mapping
        AssessmentDto dto = mapper.toDto(assessment);
        assertNotNull(dto);
        assertEquals(1L, dto.getCourseId());
    }

    @Test
    public void testToEntity() {
        // Create an AssessmentDto instance with a courseId.
        AssessmentDto dto = new AssessmentDto();
        dto.setCourseId(2L);

        // Map to Assessment and verify the mapping
        Assessment assessment = mapper.toEntity(dto);
        assertNotNull(assessment);
        assertNotNull(assessment.getCourse());
        assertEquals(2L, assessment.getCourse().getId());
    }
}