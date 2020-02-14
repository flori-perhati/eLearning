package com.student.elearning.dao;

import com.student.elearning.entity.ExamQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ExamQuestionDao extends JdbcDaoSupport {

    private JdbcTemplate template;

    @Autowired
    public ExamQuestionDao(DataSource dataSource) {
        this.setDataSource(dataSource);
        template = this.getJdbcTemplate();
    }

    public boolean insert(ExamQuestion examQuestion) {
        String sql = "INSERT INTO exam_question (exam_id, question_id, points) values (?, ?, ?)";
        Object[] params = new Object[] {examQuestion.getExamId(),  examQuestion.getQuestionId(), examQuestion.getQuestionPoints()};
        return template.update(sql, params) == 1;
    }

    public List<ExamQuestion> examQuestionByExamId(long examId, AnswerDao answerDao) {
        String sql = "SELECT eq.*, q.question_type, q.value FROM exam_question eq " +
                "LEFT JOIN question q ON q.id = eq.question_id " +
                "WHERE exam_id = ?";
        Object[] params = new Object[] {examId};
        return template.query(sql, params, resultSet -> {
            List<ExamQuestion> examQuestions = new ArrayList<>();
            while (resultSet.next()) {
                ExamQuestion examQuestion = new ExamQuestion();
                examQuestion.setId(resultSet.getInt("id"));
                examQuestion.setExamId(resultSet.getInt("exam_id"));
                examQuestion.setQuestionId(resultSet.getInt("question_id"));
                examQuestion.setQuestionPoints(resultSet.getDouble("points"));
                examQuestion.setQuestionType(resultSet.getString("question_type"));
                examQuestion.setQuestionDescription(resultSet.getString("value"));

                examQuestion.setAnswers(answerDao.answersByQuestionId(examQuestion.getQuestionId()));

                examQuestions.add(examQuestion);
            }
            return examQuestions;
        });
    }
}
