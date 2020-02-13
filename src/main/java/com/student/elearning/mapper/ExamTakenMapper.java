package com.student.elearning.mapper;

import com.student.elearning.entity.ExamTaken;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamTakenMapper implements RowMapper<ExamTaken> {
    @Override
    public ExamTaken mapRow(ResultSet resultSet, int i) throws SQLException {
        ExamTaken examTaken = new ExamTaken();
        examTaken.setId(resultSet.getInt("id"));
        examTaken.setExamId(resultSet.getInt("exam_id"));
        examTaken.setStudentId(resultSet.getInt("student_id"));
        examTaken.setPedagogueId(resultSet.getInt("pedagogue_id"));
        examTaken.setHoldingDate(resultSet.getString("holding_date"));
        examTaken.setResult(resultSet.getString("result"));
        return examTaken;
    }
}