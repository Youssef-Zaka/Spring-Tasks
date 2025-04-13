package com.sumerge.task2.Spring.Task2.Impl;



import com.sumerge.common.Interface.CourseRecommender;
import com.sumerge.common.Model.Course;

import java.util.List;

//@Component("cheapestCourseRecommender")
public class CheapestCourseRecommender implements CourseRecommender {
    @Override
    public List<Course> recommendCourses() {
        // Logic to recommend cheapest courses
        return List.of(
                new Course("External Cheapest Course 1", "Description 1", "Instructor 1"),
                new Course("External Cheapest Course 2", "Description 2", "Instructor 2")
        );
    }
}
