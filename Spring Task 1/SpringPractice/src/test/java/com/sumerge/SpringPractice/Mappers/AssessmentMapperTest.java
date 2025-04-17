package com.sumerge.SpringPractice.Mappers;

import com.sumerge.SpringPractice.Entity.Assessment;
import com.sumerge.SpringPractice.Entity.Course;
import com.sumerge.SpringPractice.Model.AssessmentDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;



 class AssessmentMapperTest {

    private final AssessmentMapper mapper = Mappers.getMapper(AssessmentMapper.class);

    @Test
     void testToDto() {
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
     void testToEntity() {
        // Create an AssessmentDto instance with a courseId.
        AssessmentDto dto = new AssessmentDto();
        dto.setCourseId(2L);

        // Map to Assessment and verify the mapping
        Assessment assessment = mapper.toEntity(dto);
        assertNotNull(assessment);
        assertNotNull(assessment.getCourse());
        assertEquals(2L, assessment.getCourse().getId());
    }

    @Test
     void testToDto_NullAssessment() {
        // Test mapping when the input Assessment is null
        AssessmentDto dto = mapper.toDto(null);
        assertNull(dto);
    }

    @Test
     void testToDto_NullCourseInAssessment() {
        // Test mapping when the Assessment has a null Course
        Assessment assessment = new Assessment();
        assessment.setCourse(null);

        AssessmentDto dto = mapper.toDto(assessment);
        assertNotNull(dto);
        assertNull(dto.getCourseId());
    }

    @Test
     void testToEntity_NullDto() {
        // Test mapping when the input AssessmentDto is null
        Assessment assessment = mapper.toEntity(null);
        assertNull(assessment);
    }

    @Test
     void testToEntity_NullCourseIdInDto() {
        // Test mapping when the AssessmentDto has a null courseId
        AssessmentDto dto = new AssessmentDto();
        dto.setCourseId(null);

        Assessment assessment = mapper.toEntity(dto);
        assertNotNull(assessment);
        assertNotNull(assessment.getCourse());
        assertNull(assessment.getCourse().getId());
    }
}