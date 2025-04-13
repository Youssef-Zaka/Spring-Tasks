package com.sumerge.jdbc;

import com.sumerge.jdbc.Model.Assessment;
import com.sumerge.jdbc.Model.Author;
import com.sumerge.jdbc.Model.Course;
import com.sumerge.jdbc.Model.Rating;
import com.sumerge.jdbc.Service.AssessmentService;
import com.sumerge.jdbc.Service.AuthorService;
import com.sumerge.jdbc.Service.CourseService;
import com.sumerge.jdbc.Service.RatingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class JdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(JdbcApplication.class, args);
	}

	@Bean
	CommandLineRunner run(CourseService courseService, AuthorService authorService,
						  RatingService ratingService, AssessmentService assessmentService) {
		return args -> {
			Author fetchedAuthor;
			Course course;

			// Fetch or create author
			try {
				fetchedAuthor = authorService.getAuthorByName("Zaka");
			} catch (Exception e) {
				System.out.println("Author not found, inserting a new one.");
				Author author = new Author(null, "Zaka", "zaka@example.com", LocalDate.of(1995, 1, 1));
				authorService.addAuthor(author);
				fetchedAuthor = authorService.getAuthorByName("Zaka");
				System.out.println("Author inserted!");
			}

			System.out.println("Fetched Author: " + fetchedAuthor.getName() + ", Email: " + fetchedAuthor.getEmail());

			// Fetch or create course
			try {
				course = courseService.getCourseByName("Spring Boot");
			} catch (Exception e) {
				System.out.println("Course not found, inserting a new one.");
				course = new Course(null, "Spring Boot", "Learn Spring Boot basics", 3, fetchedAuthor);
				courseService.addCourse(course);
				course = courseService.getCourseByName("Spring Boot");
				System.out.println("Course inserted!");
			}

			System.out.println("Fetched Course: " + course.getName() + ", Author: " + course.getAuthor().getName());

			// Add course rating
			Rating r = new Rating(null, 5, course.getId());
			ratingService.addRating(r);
			System.out.println("Rating added!");

			// Display ratings
			System.out.println("Ratings for course " + course.getName() + ":");
			ratingService.getRatingsByCourseId(course.getId()).forEach(rating -> {
				System.out.println("Rating ID: " + rating.getId() + ", Number: " + rating.getNumber());
			});

			// Add and display assessments
			Assessment a = new Assessment(null, "Course Assessment Content", course.getId());
			assessmentService.addAssessment(a);
			System.out.println("Assessment added!");

			System.out.println("Assessments for course " + course.getName() + ":");
			assessmentService.getAssessmentsByCourseId(course.getId()).forEach(assessment -> {
				System.out.println("Assessment ID: " + assessment.getId() + ", content: " + assessment.getContent());
			});

			// Delete data (comment if needed)
			ratingService.deleteRatingByCourseId(course.getId());
			System.out.println("Ratings deleted!");

			assessmentService.deleteAssessmentByCourseId(course.getId());
			System.out.println("Assessments deleted!");

			courseService.deleteCourse(course.getId());
			System.out.println("Course deleted!");

			authorService.deleteAuthor(fetchedAuthor.getId());
			System.out.println("Author deleted!");
		};
	}

}


