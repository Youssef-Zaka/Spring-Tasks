package com.sumerge.SpringPractice.Service;

import com.sumerge.SpringPractice.Entity.Course;
import com.sumerge.SpringPractice.Entity.Rating;
import com.sumerge.SpringPractice.Exception.ResourceNotFoundException;
import com.sumerge.SpringPractice.Mappers.RatingMapper;
import com.sumerge.SpringPractice.Model.RatingDto;
import com.sumerge.SpringPractice.Repository.CourseRepository;
import com.sumerge.SpringPractice.Repository.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;





@ExtendWith(MockitoExtension.class)
 class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private RatingMapper ratingMapper;

    @InjectMocks
    private RatingService ratingService;

    private RatingDto ratingDto;
    private Rating rating;
    private Course course;

    @BeforeEach
     void setup() {
        ratingDto = new RatingDto();
        ratingDto.setCourseId(1L);
        ratingDto.setNumber(5);

        rating = new Rating();
        rating.setNumber(5);

        course = new Course();
        course.setId(1L);
    }

    @Test
     void testCreateRating_Success() {
        // Arrange
        when(ratingMapper.toEntity(ratingDto)).thenReturn(rating);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(ratingRepository.save(rating)).thenReturn(rating);
        when(ratingMapper.toDto(rating)).thenReturn(ratingDto);

        // Act
        RatingDto result = ratingService.createRating(ratingDto);

        // Assert
        assertNotNull(result);
        verify(courseRepository, times(1)).findById(1L);
        verify(ratingRepository, times(1)).save(rating);
        verify(ratingMapper, times(1)).toDto(rating);
    }

    @Test
     void testCreateRating_CourseNotFound() {
        // Arrange
        when(ratingMapper.toEntity(ratingDto)).thenReturn(rating);
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                ratingService.createRating(ratingDto));
        assertEquals("Course not found", thrown.getMessage());
    }

    @Test
     void testGetAllRatings_EmptyList() {
        // Arrange
        when(ratingRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<RatingDto> result = ratingService.getAllRatings();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
     void testGetAllRatings_NonEmptyList() {
        // Arrange
        Rating anotherRating = new Rating();
        RatingDto anotherRatingDto = new RatingDto();
        when(ratingRepository.findAll()).thenReturn(Arrays.asList(rating, anotherRating));
        when(ratingMapper.toDto(rating)).thenReturn(ratingDto);
        when(ratingMapper.toDto(anotherRating)).thenReturn(anotherRatingDto);

        // Act
        List<RatingDto> result = ratingService.getAllRatings();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
     void testDeleteRating() {
        // Act
        ratingService.deleteRating(1L);

        // Assert
        verify(ratingRepository, times(1)).deleteById(1L);
    }

    @Test
     void testUpdateRating_Success() {
        // Arrange
        ratingDto.setNumber(10);
        Rating existingRating = new Rating();
        existingRating.setNumber(5);
        when(ratingRepository.findById(1L)).thenReturn(Optional.of(existingRating));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(ratingRepository.save(existingRating)).thenReturn(existingRating);
        when(ratingMapper.toDto(existingRating)).thenReturn(ratingDto);

        // Act
        RatingDto result = ratingService.updateRating(1L, ratingDto);

        // Assert
        assertNotNull(result);
        assertEquals(10, result.getNumber());
        verify(ratingRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).findById(1L);
        verify(ratingRepository, times(1)).save(existingRating);
    }

    @Test
     void testUpdateRating_RatingNotFound() {
        // Arrange
        when(ratingRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                ratingService.updateRating(1L, ratingDto));
        assertEquals("Rating not found", thrown.getMessage());
    }

    @Test
     void testUpdateRating_CourseNotFound() {
        // Arrange
        Rating existingRating = new Rating();
        existingRating.setNumber(5);
        when(ratingRepository.findById(1L)).thenReturn(Optional.of(existingRating));
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                ratingService.updateRating(1L, ratingDto));
        assertEquals("Course not found", thrown.getMessage());
    }

    @Test
     void testGetRatingById_Success() {
        // Arrange
        when(ratingRepository.findById(1L)).thenReturn(Optional.of(rating));
        when(ratingMapper.toDto(rating)).thenReturn(ratingDto);

        // Act
        RatingDto result = ratingService.getRatingById(1L);

        // Assert
        assertNotNull(result);
        verify(ratingRepository, times(1)).findById(1L);
    }

    @Test
     void testGetRatingById_NotFound() {
        // Arrange
        when(ratingRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                ratingService.getRatingById(1L));
        assertEquals("Rating not found", thrown.getMessage());
    }
}