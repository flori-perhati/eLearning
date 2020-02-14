package com.student.elearning.dao;

import com.student.elearning.entity.Course;
import com.student.elearning.entity.Exam;
import com.student.elearning.mapper.ExamMapper;
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
public class ExamDao extends JdbcDaoSupport {

    private JdbcTemplate template;

    @Autowired
    public ExamDao(DataSource dataSource) {
        this.setDataSource(dataSource);
        template = this.getJdbcTemplate();
    }

    public boolean insert(Exam exam) {
        String sql = "INSERT INTO exam (course_id, pedagogue_id, header, description) values (?, ?, ?, ?)";
        Object[] params = new Object[] {exam.getCourseId(), exam.getPedagogueId(), exam.getHeader(), exam.getDescription()};
        return template.update(sql, params) == 1;
    }

    public boolean update(Exam exam) {
        String sql = "UPDATE exam SET title = ? WHERE id = ?";
        Object[] params = new Object[] {exam.getHeader(), exam.getId()};
        return template.update(sql, params) == 1;
    }

    public boolean delete(long answerId) {
        String sql = "DELETE FROM exam WHERE id = ?";
        Object[] params = new Object[] {answerId};
        return template.update(sql, params) == 1;
    }

    public List<Exam> examByPedagogue(long pedagogueId) {
        String sql = "SELECT e.id, e.course_id, e.pedagogue_id, e.header, e.description, c.description AS course_description " +
                "FROM exam e " +
                "LEFT JOIN course c ON c.id = e.course_id " +
                "WHERE e.pedagogue_id = ? ";
        Object[] params = new Object[] {pedagogueId};
        return template.query(sql, params, resultSet -> {
            List<Exam> exams = new ArrayList<>();
            while (resultSet.next()) {
                Course course = new Course();
                course.setDescription(resultSet.getString("course_description"));

                Exam exam = new Exam();
                exam.setId(resultSet.getInt("id"));
                exam.setCourseId(resultSet.getInt("course_id"));
                exam.setPedagogueId(resultSet.getInt("pedagogue_id"));
                exam.setHeader(resultSet.getString("header"));
                exam.setDescription(resultSet.getString("description"));
                exam.setCourse(course);

                exams.add(exam);
            }
            return exams;
        });
    }

    public List<Exam> examByCourse(long courseId) {
        String sql = "SELECT * FROM exam WHERE course_id = ?";
        Object[] params = new Object[] {courseId};
        return template.query(sql, params, new ExamMapper());
    }

    public Exam examById(long examId) {
        String sql = "SELECT * FROM exam WHERE id = ?";
        Object[] params = new Object[] {examId};
        List<Exam> exams = template.query(sql, params, new ExamMapper());
        return exams.isEmpty() ? null : exams.get(0);
    }

    public Exam lastExam() {
        String sql = "SELECT TOP 1 * FROM exam ORDER BY id DESC";
        List<Exam> exams = template.query(sql, new ExamMapper());
        return exams.isEmpty() ? null : exams.get(0);
    }
}
