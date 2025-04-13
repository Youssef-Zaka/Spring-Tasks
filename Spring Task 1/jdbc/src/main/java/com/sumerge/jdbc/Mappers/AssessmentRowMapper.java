package com.sumerge.jdbc.Mappers;

import com.sumerge.jdbc.Model.Assessment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AssessmentRowMapper implements RowMapper<Assessment> {
    @Override
    public Assessment mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Assessment(
                rs.getLong("id"),
                rs.getString("content"),
                rs.getLong("course_id")
        );
    }
}
