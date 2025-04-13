package com.sumerge.jdbc.Service;


import com.sumerge.jdbc.Mappers.AssessmentRowMapper;
import com.sumerge.jdbc.Model.Assessment;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentService {

    private final NamedParameterJdbcTemplate jdbc;

    public AssessmentService(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void addAssessment(Assessment assessment) {
        String sql = "INSERT INTO assessment (content, course_id) VALUES (:content, :course_id)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("content", assessment.getContent())
                .addValue("course_id", assessment.getCourseId());
        jdbc.update(sql, params);
    }

    public Assessment getAssessmentById(Long id) {
        String sql = "SELECT * FROM assessment WHERE id = :id";
        return jdbc.queryForObject(sql, new MapSqlParameterSource("id", id), new AssessmentRowMapper());
    }

    public List<Assessment> getAssessmentsByCourseId(Long courseId) {
        String sql = "SELECT * FROM assessment WHERE course_id = :course_id";
        return jdbc.query(sql, new MapSqlParameterSource("course_id", courseId), new AssessmentRowMapper());
    }

    public void deleteAssessment(Long id) {
        String sql = "DELETE FROM assessment WHERE id = :id";
        jdbc.update(sql, new MapSqlParameterSource("id", id));
    }

    public void deleteAssessmentByCourseId(Long courseId) {
        String sql = "DELETE FROM assessment WHERE course_id = :course_id";
        jdbc.update(sql, new MapSqlParameterSource("course_id", courseId));
    }
}

