package com.student.elearning.dao;

import com.student.elearning.mapper.ExamMapper;
import com.student.elearning.model.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class ExamDao extends JdbcDaoSupport {

    private JdbcTemplate template;

    @Autowired
    public ExamDao(DataSource dataSource) {
        this.setDataSource(dataSource);
        template = this.getJdbcTemplate();
    }

    public boolean insert(Exam exam) {
        String sql = "INSERT INTO exam (course_id, pedagogue_id, title) values (?, ?, ?)";
        Object[] params = new Object[] {exam.getCourseId(), exam.getPedagogueId(), exam.getTitle()};
        return template.update(sql, params) == 1;
    }

    public boolean update(Exam exam) {
        String sql = "UPDATE exam SET title = ? WHERE id = ?";
        Object[] params = new Object[] {exam.getTitle(), exam.getId()};
        return template.update(sql, params) == 1;
    }

    public boolean delete(long answerId) {
        String sql = "DELETE FROM exam WHERE id = ?";
        Object[] params = new Object[] {answerId};
        return template.update(sql, params) == 1;
    }

    public List<Exam> examByPedagogue(long pedagogueId) {
        String sql = "SELECT * FROM exam WHERE id = ?";
        Object[] params = new Object[] {pedagogueId};
        return template.query(sql, params, new ExamMapper());
    }
}
