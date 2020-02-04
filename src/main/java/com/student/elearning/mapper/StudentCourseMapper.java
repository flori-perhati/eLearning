package com.student.elearning.mapper;

import com.student.elearning.entity.StudentCourse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentCourseMapper implements RowMapper<StudentCourse> {
    @Override
    public StudentCourse mapRow(ResultSet resultSet, int i) throws SQLException {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setId(resultSet.getLong("id"));
        studentCourse.setCourseId(resultSet.getLong("course_id"));
        studentCourse.setStudentId(resultSet.getLong("student_id"));
        return null;
    }
}