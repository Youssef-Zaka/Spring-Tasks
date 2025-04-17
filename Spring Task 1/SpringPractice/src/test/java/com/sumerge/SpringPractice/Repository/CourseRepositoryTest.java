package com.sumerge.SpringPractice.Repository;

import com.sumerge.SpringPractice.Entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;





@DataJpaTest
 class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Test
     void testSaveCourse() {
        Course course = new Course();
        
        course = courseRepository.save(course);
        assertNotNull(course.getId(), "Course ID should not be null after saving");
    }

    @Test
     void testFindCourseById() {
        Course course = new Course();

        // Save course
        course = courseRepository.save(course);
        Long courseId = course.getId();
        
        // Retrieve course using its ID
        Optional<Course> retrievedCourse = courseRepository.findById(courseId);
        assertTrue(retrievedCourse.isPresent(), "Expected course to be present in the repository");
    }

    @Test
     void testDeleteCourse() {
        Course course = new Course();

        // Save and then delete the course
        course = courseRepository.save(course);
        Long courseId = course.getId();
        courseRepository.delete(course);

        // Verify that the course is no longer in the repository
        Optional<Course> deletedCourse = courseRepository.findById(courseId);
        assertFalse(deletedCourse.isPresent(), "Expected course to be deleted from the repository");
    }
}