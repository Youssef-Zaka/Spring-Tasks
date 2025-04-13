package com.sumerge.jdbc.Mappers;

import com.sumerge.jdbc.Model.Rating;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingRowMapper implements RowMapper<Rating> {
    @Override
    public Rating mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Rating(
                rs.getLong("id"),
                rs.getInt("number"),
                rs.getLong("course_id")
        );
    }
}