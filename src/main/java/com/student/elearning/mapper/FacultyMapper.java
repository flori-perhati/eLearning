package com.student.elearning.mapper;

import com.student.elearning.entity.Faculty;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyMapper implements RowMapper<Faculty> {
    @Override
    public Faculty mapRow(ResultSet resultSet, int i) throws SQLException {
        Faculty faculty = new Faculty();
        faculty.setId(resultSet.getLong("id"));
        faculty.setDescription(resultSet.getString("description"));
        return faculty;
    }
}
