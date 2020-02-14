package com.student.elearning.dao;

import com.student.elearning.entity.Course;
import com.student.elearning.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class CourseDao extends JdbcDaoSupport {

    JdbcTemplate template;

    @Autowired
    public CourseDao(DataSource dataSource) {
        this.setDataSource(dataSource);
        template = this.getJdbcTemplate();
    }

    public boolean insert(Course course) {
        String sql = "INSERT INTO course (pedagogue_id, description) values (?, ?)";
        Object[] params = new Object[] {course.getPedagogueId(), course.getDescription()};
        return template.update(sql, params) == 1;
    }

    public boolean update(Course course) {
        String sql = "UPDATE course SET description = ? WHERE id = ?";
        Object[] params = new Object[] {course.getDescription(), course.getId()};
        return template.update(sql, params) == 1;
    }

    public boolean delete(long courseId) {
        String sql = "DELETE FROM course WHERE id = ?";
        Object[] params = new Object[] {courseId};
        return template.update(sql, params) == 1;
    }

    public List<String> getCourses() {
        String sql = "SELECT * FROM course";
        return template.query(sql, new ResultSetExtractor<List<String>>() {
            public List<String> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<String> courses = new ArrayList<>();
                while(resultSet.next()) {
                    courses.add(resultSet.getInt("id") + ". " + resultSet.getString("description"));
                }
                return courses;
            }
        });
    }

    public List<Course> getCoursesByPedagogueId(long pedagogueId) {
        String sql = "SELECT * FROM course WHERE pedagogue_id = ?";
        Object[] params = new Object[] {pedagogueId};
        return template.query(sql, params, new CourseMapper());
    }

    public Course getCourseById(long courseId) {
        String sql = "SELECT * FROM course WHERE id = ?";
        Object[] params = new Object[] {courseId};
        List<Course> courses = template.query(sql, params, new CourseMapper());
        return courses.isEmpty() ? null : courses.get(0);
    }

    public Course lastCourse() {
        String sql = "SELECT TOP 1 * FROM course ORDER BY id DESC";
        List<Course> courses = template.query(sql, new CourseMapper());
        return courses.isEmpty() ? null : courses.get(0);
    }
}
