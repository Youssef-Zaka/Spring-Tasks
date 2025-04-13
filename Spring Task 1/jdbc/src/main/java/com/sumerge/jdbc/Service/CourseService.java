package com.sumerge.jdbc.Service;

import com.sumerge.jdbc.Mappers.CourseRowMapper;
import com.sumerge.jdbc.Model.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private final JdbcTemplate jdbcTemplate;

    public CourseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addCourse(Course course) {
        String sql = "INSERT INTO course (name, description, credit, author_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, course.getName(), course.getDescription(), course.getCredit(), course.getAuthor().getId());
    }

    public void updateCourse(Course course) {
        String sql = "UPDATE course SET name = ?, description = ?, credit = ?, author_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, course.getName(), course.getDescription(), course.getCredit(), course.getAuthor().getId(), course.getId());
    }

    public Course getCourse(Long id) {
        String sql = """
            SELECT 
                c.id, c.name, c.description, c.credit, c.author_id,
                a.id AS author_id, a.name AS author_name, a.email AS author_email, a.birthdate AS author_birthdate
            FROM course c
            JOIN author a ON c.author_id = a.id
            WHERE c.id = ?
        """;
        return jdbcTemplate.queryForObject(sql, new CourseRowMapper(), id);
    }

    public Course getCourseByName(String name) {
        String sql = """
            SELECT\s
                c.id, c.name, c.description, c.credit, c.author_id,
                a.id AS author_id, a.name AS author_name, a.email AS author_email, a.birthdate AS author_birthdate
            FROM course c
            JOIN author a ON c.author_id = a.id
            WHERE c.name = ?
       \s""";
        return jdbcTemplate.queryForObject(sql, new CourseRowMapper(), name);
    }

    public void deleteCourse(Long id) {
        String sql = "DELETE FROM course WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
