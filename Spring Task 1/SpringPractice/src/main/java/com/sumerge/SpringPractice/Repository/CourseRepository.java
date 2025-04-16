package com.sumerge.SpringPractice.Repository;

import com.sumerge.SpringPractice.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}