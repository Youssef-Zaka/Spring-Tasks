package com.sumerge.SpringPractice.Service;

import com.sumerge.SpringPractice.Entity.Author;
import com.sumerge.SpringPractice.Entity.Course;
import com.sumerge.SpringPractice.Exception.ResourceNotFoundException;
import com.sumerge.SpringPractice.Mappers.CourseMapper;
import com.sumerge.SpringPractice.Model.CourseDto;
import com.sumerge.SpringPractice.Repository.AuthorRepository;
import com.sumerge.SpringPractice.Repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;





@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private CourseMapper courseMapper;

    private CourseService courseService;

    @BeforeEach
    void setUp() {
        courseService = new CourseService(courseRepository, authorRepository, courseMapper);
    }

    @Test
    void testCreateCourse_success() {
        // Given
        CourseDto dto = new CourseDto();
        dto.setAuthorId(1L);
        dto.setName("Test Course");
        dto.setDescription("Description");
        dto.setCredit(3);

        Course course = new Course();
        Author author = new Author();
        author.setId(1L);

        when(courseMapper.toEntity(dto)).thenReturn(course);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(courseRepository.save(course)).thenReturn(course);
        when(courseMapper.toDto(course)).thenReturn(dto);

        // When
        CourseDto result = courseService.createCourse(dto);

        // Then
        verify(authorRepository).findById(1L);
        verify(courseRepository).save(course);
        verify(courseMapper).toEntity(dto);
        verify(courseMapper).toDto(course);
        assertEquals(dto, result);
    }

    @Test
    void testCreateCourse_authorNotFound() {
        // Given
        CourseDto dto = new CourseDto();
        dto.setAuthorId(1L);

        Course course = new Course();
        when(courseMapper.toEntity(dto)).thenReturn(course);
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> courseService.createCourse(dto));
        assertEquals("Author not found", ex.getMessage());
    }

    @Test
    void testGetAllCourses() {
        // Given
        Pageable pageable = Pageable.unpaged();
        Course course = new Course();
        CourseDto dto = new CourseDto();
        when(courseRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(course)));
        when(courseMapper.toDto(course)).thenReturn(dto);

        // When
        Page<CourseDto> pageResult = courseService.getAllCourses(pageable);

        // Then
        verify(courseRepository).findAll(pageable);
        assertEquals(1, pageResult.getTotalElements());
        assertEquals(dto, pageResult.getContent().get(0));
    }

    @Test
    void testGetCourseById_success() {
        // Given
        Long courseId = 1L;
        Course course = new Course();
        CourseDto dto = new CourseDto();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(courseMapper.toDto(course)).thenReturn(dto);

        // When
        CourseDto result = courseService.getCourseById(courseId);

        // Then
        verify(courseRepository).findById(courseId);
        verify(courseMapper).toDto(course);
        assertEquals(dto, result);
    }

    @Test
    void testGetCourseById_notFound() {
        // Given
        Long courseId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> courseService.getCourseById(courseId));
        assertEquals("Course not found", ex.getMessage());
    }

    @Test
    void testDeleteCourse() {
        // Given
        Long courseId = 1L;

        // When
        courseService.deleteCourse(courseId);

        // Then
        verify(courseRepository).deleteById(courseId);
    }

    @Test
    void testUpdateCourse_success() {
        // Given
        Long courseId = 1L;
        CourseDto dto = new CourseDto();
        dto.setAuthorId(2L);
        dto.setName("Updated Course");
        dto.setDescription("Updated Description");
        dto.setCredit(4);

        Course existingCourse = new Course();
        Author newAuthor = new Author();
        newAuthor.setId(2L);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(authorRepository.findById(2L)).thenReturn(Optional.of(newAuthor));

        // Capture the modifications done on the course
        ArgumentCaptor<Course> courseCaptor = ArgumentCaptor.forClass(Course.class);

        when(courseRepository.save(courseCaptor.capture())).thenReturn(existingCourse);
        when(courseMapper.toDto(existingCourse)).thenReturn(dto);

        // When
        CourseDto result = courseService.updateCourse(courseId, dto);

        // Then
        verify(courseRepository).findById(courseId);
        verify(authorRepository).findById(2L);
        verify(courseRepository).save(existingCourse);
        verify(courseMapper).toDto(existingCourse);

        // Validate that course fields have been updated.
        Course savedCourse = courseCaptor.getValue();
        assertEquals(dto.getName(), savedCourse.getName());
        assertEquals(dto.getDescription(), savedCourse.getDescription());
        assertEquals(dto.getCredit(), savedCourse.getCredit());
        assertEquals(newAuthor, savedCourse.getAuthor());
        assertEquals(dto, result);
    }

    @Test
    void testUpdateCourse_courseNotFound() {
        // Given
        Long courseId = 1L;
        CourseDto dto = new CourseDto();
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> courseService.updateCourse(courseId, dto));
        assertEquals("Course not found", ex.getMessage());
    }

    @Test
    void testUpdateCourse_authorNotFound() {
        // Given
        Long courseId = 1L;
        CourseDto dto = new CourseDto();
        dto.setAuthorId(2L);

        Course existingCourse = new Course();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(authorRepository.findById(2L)).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> courseService.updateCourse(courseId, dto));
        assertEquals("Author not found", ex.getMessage());
    }
}