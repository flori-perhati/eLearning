package com.student.elearning.mapper;

import com.student.elearning.model.Exam;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamMapper implements RowMapper<Exam> {
    @Override
    public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
        Exam exam = new Exam();
        exam.setId(resultSet.getInt("id"));
        exam.setCourseId(resultSet.getInt("course_id"));
        exam.setPedagogueId(resultSet.getInt("pedagogue_id"));
        exam.setTitle(resultSet.getString("title"));
        return exam;
    }
}
