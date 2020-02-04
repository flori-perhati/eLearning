package com.student.elearning.mapper;

import com.student.elearning.entity.Course;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper implements RowMapper<Course> {
    @Override
    public Course mapRow(ResultSet resultSet, int i) throws SQLException {
        Course course = new Course();
        course.setId(resultSet.getLong("id"));
        course.setPedagogueId(resultSet.getLong("pedagogue_id"));
        course.setDescription(resultSet.getString("description"));
        return null;
    }
}
