package com.student.elearning.mapper;

import com.student.elearning.entity.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getLong("id"));
        student.setUserId(resultSet.getLong("user_id"));
        student.setFacultyId(resultSet.getLong("faculty_id"));
        student.setFirstName(resultSet.getString("first_name"));
        student.setLastName(resultSet.getString("last_name"));
        student.setGender(resultSet.getString("gender"));
        student.setBirthdate(resultSet.getDate("birthdate"));
        student.setRegistrationDate(resultSet.getDate("registration_date"));
        student.setStatus(resultSet.getInt("status") == 1);
        return student;
    }
}
