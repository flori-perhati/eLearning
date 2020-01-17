package com.student.elearning.mapper;

import com.student.elearning.model.Question;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionMapper implements RowMapper<Question> {
    @Override
    public Question mapRow(ResultSet resultSet, int i) throws SQLException {
        Question question = new Question();
        question.setId(resultSet.getLong("id"));
        question.setPedagogueId(resultSet.getLong("pedagogue_id"));
        question.setDescription(resultSet.getString("description"));
        question.setQuestionType(resultSet.getString("question_type"));
        return question;
    }
}
