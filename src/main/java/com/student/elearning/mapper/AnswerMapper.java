package com.student.elearning.mapper;

import com.student.elearning.model.Answer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnswerMapper implements RowMapper<Answer> {
    @Override
    public Answer mapRow(ResultSet resultSet, int i) throws SQLException {
        Answer answer = new Answer();
        answer.setId(resultSet.getLong("id"));
        answer.setQuestionId(resultSet.getLong("question_id"));
        answer.setValue(resultSet.getString("value"));
        answer.setCorrect(resultSet.getInt("is_correct") == 1);
        return null;
    }
}
