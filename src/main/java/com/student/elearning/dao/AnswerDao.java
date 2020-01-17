package com.student.elearning.dao;

import com.student.elearning.mapper.AnswerMapper;
import com.student.elearning.model.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class AnswerDao extends JdbcDaoSupport {

    private JdbcTemplate template;

    @Autowired
    public AnswerDao(DataSource dataSource) {
        this.setDataSource(dataSource);
        template = this.getJdbcTemplate();
    }

    public boolean insert(Answer answer) {
        String sql = "INSERT INTO answer (question_id, value, is_correct) values (?, ?, ?)";
        Object[] params = new Object[] {answer.getQuestionId(), answer.getValue(), answer.isCorrect()};
        return template.update(sql, params) == 1;
    }

    public boolean update(Answer answer) {
        String sql = "UPDATE answer SET value = ?, is_correct WHERE id = ?";
        Object[] params = new Object[] {answer.getValue(), answer.isCorrect(), answer.getId()};
        return template.update(sql, params) == 1;
    }

    public boolean delete(long answerId) {
        String sql = "DELETE FROM answer WHERE id = ?";
        Object[] params = new Object[] {answerId};
        return template.update(sql, params) == 1;
    }

    public List<Answer> answersByQuestionId(long questionId) {
        String sql = "SELECT * FROM answer WHERE question_id = ?";
        Object[] params = new Object[] {questionId};
        return template.query(sql, params, new AnswerMapper());
    }
}
