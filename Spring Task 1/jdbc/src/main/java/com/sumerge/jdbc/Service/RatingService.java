package com.sumerge.jdbc.Service;


import com.sumerge.jdbc.Mappers.RatingRowMapper;
import com.sumerge.jdbc.Model.Rating;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final NamedParameterJdbcTemplate jdbc;

    public RatingService(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void addRating(Rating rating) {
        String sql = "INSERT INTO rating (number, course_id) VALUES (:number, :course_id)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("number", rating.getNumber())
                .addValue("course_id", rating.getCourseId());
        jdbc.update(sql, params);
    }

    public Rating getRatingById(Long id) {
        String sql = "SELECT * FROM rating WHERE id = :id";
        return jdbc.queryForObject(sql, new MapSqlParameterSource("id", id), new RatingRowMapper());
    }

    public List<Rating> getRatingsByCourseId(Long courseId) {
        String sql = "SELECT * FROM rating WHERE course_id = :course_id";
        return jdbc.query(sql, new MapSqlParameterSource("course_id", courseId), new RatingRowMapper());
    }

    public void deleteRating(Long id) {
        String sql = "DELETE FROM rating WHERE id = :id";
        jdbc.update(sql, new MapSqlParameterSource("id", id));
    }

    public void deleteRatingByCourseId(Long courseId) {
        String sql = "DELETE FROM rating WHERE course_id = :course_id";
        jdbc.update(sql, new MapSqlParameterSource("course_id", courseId));
    }
}