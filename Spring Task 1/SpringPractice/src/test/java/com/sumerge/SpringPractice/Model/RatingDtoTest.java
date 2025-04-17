package com.sumerge.SpringPractice.Model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




class RatingDtoTest {

    @Test
    void testNoArgsConstructor() {
        RatingDto ratingDto = new RatingDto();
        assertNotNull(ratingDto);
        assertNull(ratingDto.getId());
        assertEquals(0, ratingDto.getNumber());
        assertNull(ratingDto.getCourseId());
    }

    @Test
    void testAllArgsConstructor() {
        RatingDto ratingDto = new RatingDto(1L, 5, 10L);
        assertNotNull(ratingDto);
        assertEquals(1L, ratingDto.getId());
        assertEquals(5, ratingDto.getNumber());
        assertEquals(10L, ratingDto.getCourseId());
    }

    @Test
    void testBuilder() {
        RatingDto ratingDto = RatingDto.builder()
                .id(2L)
                .number(4)
                .courseId(20L)
                .build();
        assertNotNull(ratingDto);
        assertEquals(2L, ratingDto.getId());
        assertEquals(4, ratingDto.getNumber());
        assertEquals(20L, ratingDto.getCourseId());
    }

    @Test
    void testSettersAndGetters() {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setId(3L);
        ratingDto.setNumber(3);
        ratingDto.setCourseId(30L);

        assertEquals(3L, ratingDto.getId());
        assertEquals(3, ratingDto.getNumber());
        assertEquals(30L, ratingDto.getCourseId());
    }

    @Test
    void testEqualsAndHashCode() {
        RatingDto ratingDto1 = new RatingDto(1L, 5, 10L);
        RatingDto ratingDto2 = new RatingDto(1L, 5, 10L);

        assertEquals(ratingDto1, ratingDto2);
        assertEquals(ratingDto1.hashCode(), ratingDto2.hashCode());
    }

    @Test
    void testToString() {
        RatingDto ratingDto = new RatingDto(1L, 5, 10L);
        String expected = "RatingDto(id=1, number=5, courseId=10)";
        assertEquals(expected, ratingDto.toString());
    }
}