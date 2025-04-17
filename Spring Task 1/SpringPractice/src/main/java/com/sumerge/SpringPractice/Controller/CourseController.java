package com.sumerge.SpringPractice.Controller;


import com.sumerge.SpringPractice.Model.CourseDto;
import com.sumerge.SpringPractice.Service.CourseService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;


    @GetMapping
    public ResponseEntity<Page<CourseDto>> getAllCourses(
            @Parameter(in = ParameterIn.QUERY, description = "Page number (0-based index)") @RequestParam(defaultValue = "0") int page,
            @Parameter(in = ParameterIn.QUERY, description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(in = ParameterIn.QUERY, description = "Sort field, e.g., credit or name") @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
        return ResponseEntity.ok(courseService.getAllCourses(pageable));
    }

    Sort.Order[] parseSort(String[] sort) {
        return Arrays.stream(sort)
                .map(s -> {
                    String[] parts = s.split(",");
                    return parts.length == 2
                            ? new Sort.Order(Sort.Direction.fromString(parts[1]), parts[0])
                            : new Sort.Order(Sort.Direction.ASC, parts[0]);
                })
                .toArray(Sort.Order[]::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @SecurityRequirement(name = "basicAuth")
    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto dto) {
        return ResponseEntity.ok(courseService.createCourse(dto));
    }

    @SecurityRequirement(name = "basicAuth")
    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Long id, @RequestBody CourseDto dto) {
        return ResponseEntity.ok(courseService.updateCourse(id, dto));
    }
    @SecurityRequirement(name = "basicAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}