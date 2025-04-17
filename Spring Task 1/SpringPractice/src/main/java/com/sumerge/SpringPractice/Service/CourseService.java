package com.sumerge.SpringPractice.Service;


import com.sumerge.SpringPractice.Exception.ResourceNotFoundException;
import com.sumerge.SpringPractice.Model.CourseDto;
import com.sumerge.SpringPractice.Entity.Author;
import com.sumerge.SpringPractice.Entity.Course;
import com.sumerge.SpringPractice.Mappers.CourseMapper;
import com.sumerge.SpringPractice.Repository.AuthorRepository;
import com.sumerge.SpringPractice.Repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final AuthorRepository authorRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, AuthorRepository authorRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.authorRepository = authorRepository;
        this.courseMapper = courseMapper;
    }

    public CourseDto createCourse(CourseDto dto) {
        Course course = courseMapper.toEntity(dto);
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        course.setAuthor(author);
        return courseMapper.toDto(courseRepository.save(course));
    }

    public Page<CourseDto> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable)
                .map(courseMapper::toDto);
    }

    public CourseDto getCourseById(Long id) {
        return courseRepository.findById(id)
                .map(courseMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public CourseDto updateCourse(Long id, CourseDto dto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        course.setName(dto.getName());
        course.setDescription(dto.getDescription());
        course.setCredit(dto.getCredit());
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        course.setAuthor(author);
        return courseMapper.toDto(courseRepository.save(course));
    }
}