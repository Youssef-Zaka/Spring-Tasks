package com.sumerge.SpringPractice.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;




class CourseDtoTest {

    @Test
    void testNoArgsConstructor() {
        CourseDto courseDto = new CourseDto();
        assertNotNull(courseDto);
    }

    @Test
    void testAllArgsConstructor() {
        CourseDto courseDto = new CourseDto(1L, "Java Basics", "Introduction to Java", 3, 101L);
        assertEquals(1L, courseDto.getId());
        assertEquals("Java Basics", courseDto.getName());
        assertEquals("Introduction to Java", courseDto.getDescription());
        assertEquals(3, courseDto.getCredit());
        assertEquals(101L, courseDto.getAuthorId());
    }

    @Test
    void testBuilder() {
        CourseDto courseDto = CourseDto.builder()
                .id(2L)
                .name("Spring Framework")
                .description("Comprehensive guide to Spring")
                .credit(4)
                .authorId(102L)
                .build();

        assertEquals(2L, courseDto.getId());
        assertEquals("Spring Framework", courseDto.getName());
        assertEquals("Comprehensive guide to Spring", courseDto.getDescription());
        assertEquals(4, courseDto.getCredit());
        assertEquals(102L, courseDto.getAuthorId());
    }

    @Test
    void testSettersAndGetters() {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(3L);
        courseDto.setName("Hibernate");
        courseDto.setDescription("ORM Framework");
        courseDto.setCredit(5);
        courseDto.setAuthorId(103L);

        assertEquals(3L, courseDto.getId());
        assertEquals("Hibernate", courseDto.getName());
        assertEquals("ORM Framework", courseDto.getDescription());
        assertEquals(5, courseDto.getCredit());
        assertEquals(103L, courseDto.getAuthorId());
    }

    @Test
    void testEqualsAndHashCode() {
        CourseDto courseDto1 = new CourseDto(1L, "Java Basics", "Introduction to Java", 3, 101L);
        CourseDto courseDto2 = new CourseDto(1L, "Java Basics", "Introduction to Java", 3, 101L);

        assertEquals(courseDto1, courseDto2);
        assertEquals(courseDto1.hashCode(), courseDto2.hashCode());
    }

    @Test
    void testToString() {
        CourseDto courseDto = new CourseDto(1L, "Java Basics", "Introduction to Java", 3, 101L);
        String expected = "CourseDto(id=1, name=Java Basics, description=Introduction to Java, credit=3, authorId=101)";
        assertEquals(expected, courseDto.toString());
    }
}