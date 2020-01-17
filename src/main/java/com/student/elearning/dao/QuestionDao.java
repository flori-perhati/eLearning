package com.student.elearning.dao;

import com.student.elearning.mapper.QuestionMapper;
import com.student.elearning.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class QuestionDao extends JdbcDaoSupport {

    private JdbcTemplate template;

    @Autowired
    public QuestionDao(DataSource dataSource) {
        this.setDataSource(dataSource);
        template = this.getJdbcTemplate();
    }

    public boolean insert(Question question) {
        String sql = "INSERT INTO question (pedagogue_id, question_type, description) values (?, ?, ?)";
        Object[] params = new Object[] {question.getPedagogueId(), question.getQuestionType(), question.getDescription()};
        return template.update(sql, params) == 1;
    }

    public boolean update(Question question) {
        String sql = "UPDATE question SET question_type = ?, description = ? WHERE id = ?";
        Object[] params = new Object[] {question.getQuestionType(), question.getDescription(), question.getId()};
        return template.update(sql, params) == 1;
    }

    public boolean delete(long questionId) {
        String sql = "DELETE FROM question WHERE id = ?";
        Object[] params = new Object[] {questionId};
        return template.update(sql, params) == 1;
    }

    public Question lastQuestion() {
        String sql = "SELECT TOP 1 * FROM question ORDER BY id DESC";
        return template.queryForObject(sql, new QuestionMapper());
    }

    public List<Question> questionsByPedagogue(long pedagogueId) {
        String sql = "SELECT * FROM question WHERE pedagogue_id = ?";
        Object[] params = new Object[] {pedagogueId};
        return template.query(sql, params, new QuestionMapper());
    }

    public List<Question> questionsById(long id) {
        String sql = "SELECT * FROM question WHERE id = ?";
        Object[] params = new Object[] {id};
        return template.query(sql, params, new QuestionMapper());
    }

    public List<Question> examQuestions(long id) {
        final AnswerDao answerDao = new AnswerDao(template.getDataSource());
        String sql = "SELECT * FROM question WHERE id = ?";
        Object[] params = new Object[] {id};
        return template.query(sql, params, new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet resultSet, int i) throws SQLException {
                Question question = new Question();
                question.setId(resultSet.getLong("id"));
                question.setPedagogueId(resultSet.getLong("pedagogue_id"));
                question.setDescription(resultSet.getString("description"));
                question.setQuestionType(resultSet.getString("question_type"));
                question.setAnswers(answerDao.answersByQuestionId(question.getId()));
                return question;
            }
        });
    }
}
