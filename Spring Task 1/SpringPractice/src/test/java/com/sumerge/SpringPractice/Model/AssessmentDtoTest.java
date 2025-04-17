package com.sumerge.SpringPractice.Model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




class AssessmentDtoTest {

    @Test
    void testNoArgsConstructor() {
        AssessmentDto assessmentDto = new AssessmentDto();
        assertNull(assessmentDto.getId());
        assertNull(assessmentDto.getContent());
        assertNull(assessmentDto.getCourseId());
    }

    @Test
    void testAllArgsConstructor() {
        AssessmentDto assessmentDto = new AssessmentDto(1L, "Test Content", 101L);
        assertEquals(1L, assessmentDto.getId());
        assertEquals("Test Content", assessmentDto.getContent());
        assertEquals(101L, assessmentDto.getCourseId());
    }

    @Test
    void testBuilder() {
        AssessmentDto assessmentDto = AssessmentDto.builder()
                .id(2L)
                .content("Builder Content")
                .courseId(202L)
                .build();
        assertEquals(2L, assessmentDto.getId());
        assertEquals("Builder Content", assessmentDto.getContent());
        assertEquals(202L, assessmentDto.getCourseId());
    }

    @Test
    void testSettersAndGetters() {
        AssessmentDto assessmentDto = new AssessmentDto();
        assessmentDto.setId(3L);
        assessmentDto.setContent("Setter Content");
        assessmentDto.setCourseId(303L);

        assertEquals(3L, assessmentDto.getId());
        assertEquals("Setter Content", assessmentDto.getContent());
        assertEquals(303L, assessmentDto.getCourseId());
    }

    @Test
    void testEqualsAndHashCode() {
        AssessmentDto assessmentDto1 = new AssessmentDto(4L, "Content A", 404L);
        AssessmentDto assessmentDto2 = new AssessmentDto(4L, "Content A", 404L);
        AssessmentDto assessmentDto3 = new AssessmentDto(5L, "Content B", 505L);

        assertEquals(assessmentDto1, assessmentDto2);
        assertNotEquals(assessmentDto1, assessmentDto3);
        assertEquals(assessmentDto1.hashCode(), assessmentDto2.hashCode());
        assertNotEquals(assessmentDto1.hashCode(), assessmentDto3.hashCode());
    }

    @Test
    void testToString() {
        AssessmentDto assessmentDto = new AssessmentDto(6L, "ToString Content", 606L);
        String expected = "AssessmentDto(id=6, content=ToString Content, courseId=606)";
        assertEquals(expected, assessmentDto.toString());
    }
}