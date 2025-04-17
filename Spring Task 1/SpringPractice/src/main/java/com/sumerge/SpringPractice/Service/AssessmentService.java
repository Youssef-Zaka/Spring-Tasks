package com.sumerge.SpringPractice.Service;

import com.sumerge.SpringPractice.Entity.Assessment;
import com.sumerge.SpringPractice.Entity.Course;
import com.sumerge.SpringPractice.Exception.ResourceNotFoundException;
import com.sumerge.SpringPractice.Mappers.AssessmentMapper;
import com.sumerge.SpringPractice.Model.AssessmentDto;
import com.sumerge.SpringPractice.Repository.AssessmentRepository;
import com.sumerge.SpringPractice.Repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssessmentService {
    private final AssessmentRepository assessmentRepository;
    private final CourseRepository courseRepository;
    private final AssessmentMapper assessmentMapper;

    public AssessmentService(AssessmentRepository assessmentRepository, CourseRepository courseRepository, AssessmentMapper assessmentMapper) {
        this.assessmentRepository = assessmentRepository;
        this.courseRepository = courseRepository;
        this.assessmentMapper = assessmentMapper;
    }

    public AssessmentDto createAssessment(AssessmentDto dto) {
        Assessment assessment = assessmentMapper.toEntity(dto);
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        assessment.setCourse(course);
        return assessmentMapper.toDto(assessmentRepository.save(assessment));
    }

    public List<AssessmentDto> getAllAssessments() {
        return assessmentRepository.findAll().stream()
                .map(assessmentMapper::toDto)
                .toList();
    }

    public void deleteAssessment(Long id) {
        assessmentRepository.deleteById(id);
    }

    public AssessmentDto updateAssessment(Long id, AssessmentDto dto) {
        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assessment not found"));
        assessment.setContent(dto.getContent());
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        assessment.setCourse(course);
        return assessmentMapper.toDto(assessmentRepository.save(assessment));
    }

    public AssessmentDto getAssessmentById(Long id) {
        return assessmentRepository.findById(id)
                .map(assessmentMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Assessment not found"));
    }
}
