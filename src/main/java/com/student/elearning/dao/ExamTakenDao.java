package com.student.elearning.dao;

import com.student.elearning.entity.ExamTaken;
import com.student.elearning.mapper.ExamTakenMapper;
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
public class ExamTakenDao extends JdbcDaoSupport {

    private JdbcTemplate template;

    @Autowired
    public ExamTakenDao(DataSource dataSource) {
        this.setDataSource(dataSource);
        template = this.getJdbcTemplate();
    }

    public boolean insert(ExamTaken examTaken) {
        String sql = "INSERT INTO exam_taken (exam_id, student_id, pedagogue_id, holding_date, result) values (?, ?, ?, ?, ?)";
        Object[] params = new Object[] {examTaken.getExamId(), examTaken.getStudentId(), examTaken.getPedagogueId(), examTaken.getHoldingDate(), examTaken.getResult()};
        return template.update(sql, params) == 1;
    }

    public ExamTaken getExamTakenByStudent(long examId, long studentId) {
        String sql = "SELECT * FROM exam_taken WHERE exam_id = ? AND student_id = ?";
        Object[] params = new Object[] {examId, studentId};
        List<ExamTaken> examTakenList = template.query(sql, params, new ExamTakenMapper());
        return examTakenList.isEmpty() ? null : examTakenList.get(0);
    }

    public List<ExamTaken> examsTakenByStudent(long studentId) {
        String sql = "SELECT c.description AS course_description, e.header, e.description, et.* FROM exam_taken et " +
                "LEFT JOIN exam e ON e.id = et.exam_id " +
                "LEFT JOIN course c ON c.id = e.course_id " +
                "WHERE student_id = ?";
        Object[] params = new Object[] {studentId};
        return template.query(sql, params, resultSet -> {
            List<ExamTaken> examTakenList = new ArrayList<>();
            while (resultSet.next()) {
                ExamTaken examTaken = new ExamTaken();
                examTaken.setCourse(resultSet.getString("course_description"));
                examTaken.setExamHeader(resultSet.getString("header"));
                examTaken.setExamDescription(resultSet.getString("description"));
                examTaken.setHoldingDate(resultSet.getString("holding_date"));
                examTaken.setResult(resultSet.getString("result"));
                examTakenList.add(examTaken);
            }
            return examTakenList;
        });
    }

    public ExamTaken lastExamTakenByStudent(long studentId) {
        String sql = "SELECT TOP 1 c.description AS course_description, e.header, e.description, et.* FROM exam_taken et " +
                "LEFT JOIN exam e ON e.id = et.exam_id " +
                "LEFT JOIN course c ON c.id = e.course_id " +
                "WHERE student_id = ? " +
                "ORDER BY et.id DESC";
        Object[] params = new Object[] {studentId};
        List<ExamTaken> examTakenList = template.query(sql, params, resultSet -> {
            List<ExamTaken> examList = new ArrayList<>();
            while (resultSet.next()) {
                ExamTaken examTaken = new ExamTaken();
                examTaken.setCourse(resultSet.getString("course_description"));
                examTaken.setExamHeader(resultSet.getString("header"));
                examTaken.setExamDescription(resultSet.getString("description"));
                examTaken.setHoldingDate(resultSet.getString("holding_date"));
                examTaken.setResult(resultSet.getString("result"));
                examList.add(examTaken);
            }
            return examList;
        });
        return examTakenList != null && !examTakenList.isEmpty() ? examTakenList.get(0) : null;
    }

    public List<ExamTaken> examResults(long examId) {
        String sql = "SELECT et.*, s.first_name, s.last_name FROM exam_taken et " +
                "LEFT JOIN student s ON s.id = et.student_id " +
                "WHERE et.exam_id = ?";
        Object[] params = new Object[] {examId};
        return template.query(sql, params, resultSet -> {
            List<ExamTaken> examList = new ArrayList<>();
            while (resultSet.next()) {
                ExamTaken examTaken = new ExamTaken();
                examTaken.setId(resultSet.getLong("id"));
                examTaken.setExamId(resultSet.getLong("exam_id"));
                examTaken.setStudentId(resultSet.getLong("student_id"));
                examTaken.setPedagogueId(resultSet.getLong("pedagogue_id"));
                examTaken.setHoldingDate(resultSet.getString("holding_date"));
                examTaken.setResult(resultSet.getString("result"));
                examTaken.setStudent(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                examList.add(examTaken);
            }
            return examList;
        });
    }
}
