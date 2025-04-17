package com.sumerge.SpringPractice.Service;


import com.sumerge.SpringPractice.Exception.ResourceNotFoundException;
import com.sumerge.SpringPractice.Model.RatingDto;
import com.sumerge.SpringPractice.Entity.Course;
import com.sumerge.SpringPractice.Entity.Rating;
import com.sumerge.SpringPractice.Mappers.RatingMapper;
import com.sumerge.SpringPractice.Repository.CourseRepository;
import com.sumerge.SpringPractice.Repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final CourseRepository courseRepository;
    private final RatingMapper ratingMapper;

    public RatingService(RatingRepository ratingRepository, CourseRepository courseRepository, RatingMapper ratingMapper) {
        this.ratingRepository = ratingRepository;
        this.courseRepository = courseRepository;
        this.ratingMapper = ratingMapper;
    }

    public RatingDto createRating(RatingDto dto) {
        Rating rating = ratingMapper.toEntity(dto);
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        rating.setCourse(course);
        return ratingMapper.toDto(ratingRepository.save(rating));
    }

    public List<RatingDto> getAllRatings() {
        return ratingRepository.findAll().stream()
                .map(ratingMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteRating(Long id) {
        ratingRepository.deleteById(id);
    }

    public RatingDto updateRating(Long id, RatingDto dto) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found"));
        rating.setNumber(dto.getNumber());
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        rating.setCourse(course);
        return ratingMapper.toDto(ratingRepository.save(rating));
    }
    
    public RatingDto getRatingById(Long id) {
        return ratingRepository.findById(id)
                .map(ratingMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found"));
    }
}