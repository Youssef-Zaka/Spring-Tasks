package com.sumerge.jdbc.Mappers;


import com.sumerge.jdbc.Model.Author;
import com.sumerge.jdbc.Model.Course;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRowMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        Author author = new Author();
        author.setId(rs.getLong("author_id"));
        author.setName(rs.getString("author_name"));
        author.setEmail(rs.getString("author_email"));
        author.setBirthdate(rs.getDate("author_birthdate").toLocalDate());

        Course course = new Course();
        course.setId(rs.getLong("id"));
        course.setName(rs.getString("name"));
        course.setDescription(rs.getString("description"));
        course.setCredit(rs.getInt("credit"));
        course.setAuthor(author);
        return course;
    }
}