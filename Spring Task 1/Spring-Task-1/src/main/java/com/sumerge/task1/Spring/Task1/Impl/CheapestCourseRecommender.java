package com.sumerge.task1.Spring.Task1.Impl;

import com.sumerge.task1.Spring.Task1.Interface.CourseRecommender;
import com.sumerge.task1.Spring.Task1.Model.Course;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component("cheapestCourseRecommender")
public class CheapestCourseRecommender implements CourseRecommender {
    @Override
    public List<Course> recommendCourses() {
        // Logic to recommend cheapest courses
        return List.of(
                new Course("Cheapest Course 1", "Description 1", "Instructor 1"),
                new Course("Cheapest Course 2", "Description 2", "Instructor 2")
        );
    }
}
