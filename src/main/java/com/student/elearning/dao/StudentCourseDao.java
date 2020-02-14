package com.student.elearning.dao;

import com.student.elearning.entity.Course;
import com.student.elearning.entity.StudentCourse;
import com.student.elearning.mapper.StudentCourseMapper;
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
public class StudentCourseDao extends JdbcDaoSupport {

    private JdbcTemplate template;

    @Autowired
    public StudentCourseDao(DataSource dataSource) {
        this.setDataSource(dataSource);
        template = this.getJdbcTemplate();
    }

    public boolean insert(StudentCourse studentCourse) {
        String sql = "INSERT INTO student_course (course_id, student_id) values (?, ?)";
        Object[] params = new Object[] {studentCourse.getCourseId(),  studentCourse.getStudentId()};
        return template.update(sql, params) == 1;
    }

    public List<Course> getCoursesByStudentId(long studentId) {
        String sql = "SELECT c.*, p.first_name, p.last_name FROM student_course sc " +
                "LEFT JOIN course c ON c.id = sc.course_id " +
                "LEFT JOIN pedagogue p ON p.id = c.pedagogue_id " +
                "WHERE sc.student_id = ? AND  p.status = 1";
        Object[] params = new Object[] {studentId};
        return template.query(sql, params, resultSet -> {
            List<Course> courses = new ArrayList<>();
            while(resultSet.next()) {
                Course course = new Course();
                course.setId(resultSet.getInt("id"));
                course.setPedagogueId(resultSet.getInt("pedagogue_id"));
                course.setDescription(resultSet.getString("description"));
                course.setPedagogueFullName(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                courses.add(course);
            }
            return courses;
        });
    }

    public boolean studentCourseExist(long courseId, long studentId) {
        List<StudentCourse> studentCourseList;
        String sql = "SELECT * FROM student_course WHERE course_id = ? AND student_id = ?";
        Object[] params = new Object[] {courseId,  studentId};
        studentCourseList = template.query(sql, params, new StudentCourseMapper());
        return !studentCourseList.isEmpty();
    }
}
