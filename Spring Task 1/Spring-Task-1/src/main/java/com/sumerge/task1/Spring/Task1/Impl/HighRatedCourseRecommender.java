package com.sumerge.task1.Spring.Task1.Impl;

import com.sumerge.task1.Spring.Task1.Interface.CourseRecommender;
import com.sumerge.task1.Spring.Task1.Model.Course;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

//@Primary
//@Component("highRatedCourseRecommender")
public class HighRatedCourseRecommender implements CourseRecommender {
    @Override
    public List<Course> recommendCourses() {
        // Logic to recommend high-rated courses
        return List.of(
                new Course("High Rated Course 1", "Description 1", "Instructor 1"),
                new Course("High Rated Course 2", "Description 2", "Instructor 2")
        );
    }
}
