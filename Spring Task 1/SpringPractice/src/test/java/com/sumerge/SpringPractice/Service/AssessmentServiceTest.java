package com.sumerge.SpringPractice.Service;

import com.sumerge.SpringPractice.Entity.Assessment;
import com.sumerge.SpringPractice.Entity.Course;
import com.sumerge.SpringPractice.Exception.ResourceNotFoundException;
import com.sumerge.SpringPractice.Model.AssessmentDto;
import com.sumerge.SpringPractice.Mappers.AssessmentMapper;
import com.sumerge.SpringPractice.Repository.AssessmentRepository;
import com.sumerge.SpringPractice.Repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;





@ExtendWith(MockitoExtension.class)
public class AssessmentServiceTest {

    @Mock
    private AssessmentRepository assessmentRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private AssessmentMapper assessmentMapper;

    @InjectMocks
    private AssessmentService assessmentService;

    private AssessmentDto assessmentDto;
    private Assessment assessment;
    private Course course;

    @BeforeEach
    public void setUp() {
        assessmentDto = new AssessmentDto();
        assessmentDto.setCourseId(1L);
        assessmentDto.setContent("Test Content");

        assessment = new Assessment();
        course = new Course();

        course.setId(1L);
        assessment.setId(100L);
    }

    @Test
    public void testCreateAssessment_Success() {
        when(assessmentMapper.toEntity(assessmentDto)).thenReturn(assessment);
        when(courseRepository.findById(assessmentDto.getCourseId())).thenReturn(Optional.of(course));
        when(assessmentRepository.save(assessment)).thenReturn(assessment);
        when(assessmentMapper.toDto(assessment)).thenReturn(assessmentDto);

        AssessmentDto result = assessmentService.createAssessment(assessmentDto);

        // Verify that course is set
        assertNotNull(result);
        verify(courseRepository).findById(assessmentDto.getCourseId());
        verify(assessmentRepository).save(assessment);
        verify(assessmentMapper).toDto(assessment);

        // Optionally capture that the assessment has been set with course
        assertEquals(course, assessment.getCourse());
    }

    @Test
    public void testCreateAssessment_CourseNotFound() {
        when(assessmentMapper.toEntity(assessmentDto)).thenReturn(assessment);
        when(courseRepository.findById(assessmentDto.getCourseId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                assessmentService.createAssessment(assessmentDto));

        assertEquals("Course not found", exception.getMessage());
    }

    @Test
    public void testGetAllAssessments() {
        Assessment assessment2 = new Assessment();
        AssessmentDto dto2 = new AssessmentDto();

        when(assessmentRepository.findAll()).thenReturn(Arrays.asList(assessment, assessment2));
        when(assessmentMapper.toDto(assessment)).thenReturn(assessmentDto);
        when(assessmentMapper.toDto(assessment2)).thenReturn(dto2);

        List<AssessmentDto> result = assessmentService.getAllAssessments();
        assertEquals(2, result.size());
        verify(assessmentRepository).findAll();
    }

    @Test
    public void testDeleteAssessment() {
        Long id = 100L;
        assessmentService.deleteAssessment(id);
        verify(assessmentRepository).deleteById(id);
    }

    @Test
    public void testUpdateAssessment_Success() {
        AssessmentDto updatedDto = new AssessmentDto();
        updatedDto.setCourseId(1L);
        updatedDto.setContent("Updated Content");

        when(assessmentRepository.findById(assessment.getId())).thenReturn(Optional.of(assessment));
        when(courseRepository.findById(updatedDto.getCourseId())).thenReturn(Optional.of(course));
        when(assessmentRepository.save(assessment)).thenReturn(assessment);
        when(assessmentMapper.toDto(assessment)).thenReturn(updatedDto);

        AssessmentDto result = assessmentService.updateAssessment(assessment.getId(), updatedDto);
        assertNotNull(result);
        assertEquals("Updated Content", result.getContent());
        verify(assessmentRepository).findById(assessment.getId());
        verify(courseRepository).findById(updatedDto.getCourseId());
        verify(assessmentRepository).save(assessment);

        // Confirm that assessment content is updated and course assigned
        assertEquals("Updated Content", assessment.getContent());
        assertEquals(course, assessment.getCourse());
    }

    @Test
    public void testUpdateAssessment_AssessmentNotFound() {
        Long notFoundId = 999L;
        when(assessmentRepository.findById(notFoundId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                assessmentService.updateAssessment(notFoundId, assessmentDto));

        assertEquals("Assessment not found", exception.getMessage());
    }

    @Test
    public void testGetAssessmentById_Success() {
        when(assessmentRepository.findById(assessment.getId())).thenReturn(Optional.of(assessment));
        when(assessmentMapper.toDto(assessment)).thenReturn(assessmentDto);

        AssessmentDto result = assessmentService.getAssessmentById(assessment.getId());
        assertNotNull(result);
        verify(assessmentRepository).findById(assessment.getId());
        verify(assessmentMapper).toDto(assessment);
    }

    @Test
    public void testGetAssessmentById_NotFound() {
        Long notFoundId = 999L;
        when(assessmentRepository.findById(notFoundId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                assessmentService.getAssessmentById(notFoundId));
        assertEquals("Assessment not found", exception.getMessage());
    }

    @Test
    public void testUpdateAssessment_CourseNotFound() {
        AssessmentDto updatedDto = new AssessmentDto();
        updatedDto.setCourseId(1L);
        updatedDto.setContent("Updated Content");

        when(assessmentRepository.findById(assessment.getId())).thenReturn(Optional.of(assessment));
        when(courseRepository.findById(updatedDto.getCourseId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                assessmentService.updateAssessment(assessment.getId(), updatedDto));

        assertEquals("Course not found", exception.getMessage());
    }
}