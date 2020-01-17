package com.student.elearning.dao;

import com.student.elearning.mapper.StudentMapper;
import com.student.elearning.model.Faculty;
import com.student.elearning.model.Student;
import com.student.elearning.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
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
public class StudentDao extends JdbcDaoSupport {

    JdbcTemplate template;

    @Autowired
    public StudentDao(DataSource dataSource) {
        this.setDataSource(dataSource);
        template = this.getJdbcTemplate();
    }

    public boolean insert(Student student) {
        String sql = "INSERT INTO student (user_id, faculty_id, first_name, last_name, register_date) values (?, ?, ?, ?, ?)";
        Object[] params = new Object[] {student.getUserId(), student.getFacultyId(), student.getFirstName(), student.getLastName(), student.getRegisterDate()};
        return template.update(sql, params) == 1;
    }

    public boolean delete(long studentId) {
        String sql = "DELETE FROM student WHERE id = ?";
        Object[] params = new Object[] {studentId};
        return template.update(sql, params) == 1;
    }

    public boolean update(Student student) {
        String sql = "UPDATE student SET first_name = ?, last_name = ? WHERE id = ?";
        Object[] params = new Object[] {student.getFirstName(), student.getLastName(), student.getId()};
        return template.update(sql, params) == 1;
    }

    public Student getStudentById(int id) {
        String sql = "SELECT * FROM student WHERE id = ?";
        Object[] params = new Object[] {id};
        return template.queryForObject(sql, params, new StudentMapper());
    }

    public Student studentByUserId(long userId) {
        String sql = "SELECT s.id, s.user_id, s.faculty_id, s.first_name, s.last_name, s.register_date, " +
                "u.username, u.password, u.user_status, f.description " +
                "FROM student s " +
                "LEFT JOIN users u ON u.id = s.user_id " +
                "LEFT JOIN faculty f ON f.id = s.faculty_id WHERE s.user_id = ?";
        Object[] params = new Object[] {userId};
        return template.queryForObject(sql, params, new RowMapper<Student>() {
            @Override
            public Student mapRow(ResultSet resultSet, int i) throws SQLException {
                Student student = new Student();
                Faculty faculty = new Faculty();
                User user = new User();

                student.setId(resultSet.getInt("id"));
                student.setUserId(resultSet.getInt("user_id"));
                student.setFacultyId(resultSet.getInt("faculty_id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setRegisterDate(resultSet.getString("register_date"));

                user.setId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setUserStatus(resultSet.getString("user_status"));

                faculty.setId(resultSet.getInt("faculty_id"));
                faculty.setDescription(resultSet.getString("description"));

                student.setFaculty(faculty);
                student.setUser(user);

                return student;
            }
        });
    }

    public Student studentDetailsToEdit(int id) {
        String sql = "SELECT s.id, s.user_id, s.faculty_id, s.first_name, s.last_name, s.register_date, " +
                "u.username, u.password, u.user_status, f.description " +
                "FROM student s " +
                "LEFT JOIN users u ON u.id = s.user_id " +
                "LEFT JOIN faculty f ON f.id = s.faculty_id WHERE s.id = ?";
        Object[] params = new Object[] {id};
        return template.queryForObject(sql, params, new RowMapper<Student>() {
            @Override
            public Student mapRow(ResultSet resultSet, int i) throws SQLException {
                Student student = new Student();
                Faculty faculty = new Faculty();
                User user = new User();

                student.setId(resultSet.getInt("id"));
                student.setUserId(resultSet.getInt("user_id"));
                student.setFacultyId(resultSet.getInt("faculty_id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setRegisterDate(resultSet.getString("register_date"));

                user.setId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setUserStatus(resultSet.getString("user_status"));

                faculty.setId(resultSet.getInt("faculty_id"));
                faculty.setDescription(resultSet.getString("description"));

                student.setFaculty(faculty);
                student.setUser(user);

                return student;
            }
        });
    }

    public List<Student> getStudents() {
        String sql = "SELECT s.id, s.user_id, s.faculty_id, s.first_name, s.last_name, s.register_date, " +
                "u.username, u.password, u.user_status, f.description " +
                "FROM student s " +
                "LEFT JOIN users u ON u.id = s.user_id " +
                "LEFT JOIN faculty f ON f.id = s.faculty_id";

        return template.query(sql, new ResultSetExtractor<List<Student>>() {
            public List<Student> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<Student> students = new ArrayList<Student>();
                while(resultSet.next()) {
                    Student student = new Student();
                    Faculty faculty = new Faculty();
                    User user = new User();

                    student.setId(resultSet.getInt("id"));
                    student.setUserId(resultSet.getInt("user_id"));
                    student.setFacultyId(resultSet.getInt("faculty_id"));
                    student.setFirstName(resultSet.getString("first_name"));
                    student.setLastName(resultSet.getString("last_name"));
                    student.setRegisterDate(resultSet.getString("register_date"));

                    user.setId(resultSet.getInt("user_id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setUserStatus(resultSet.getString("user_status"));

                    faculty.setId(resultSet.getInt("faculty_id"));
                    faculty.setDescription(resultSet.getString("description"));

                    student.setFaculty(faculty);
                    student.setUser(user);

                    students.add(student);
                }
                return students;
            }
        });
    }

    public List<Student> studentsByCourse(long courseId) {
        String sql = "SELECT * FROM student_course WHERE course_id = ?";
        Object[] params = new Object[] {courseId};
        return template.query(sql, params, new ResultSetExtractor<List<Student>>() {
            public List<Student> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<Student> students = new ArrayList<Student>();
                while(resultSet.next()) {
                    Student student = new Student();
                    Faculty faculty = new Faculty();
                    User user = new User();

                    student.setId(resultSet.getInt("id"));
                    student.setUserId(resultSet.getInt("user_id"));
                    student.setFacultyId(resultSet.getInt("faculty_id"));
                    student.setFirstName(resultSet.getString("first_name"));
                    student.setLastName(resultSet.getString("last_name"));
                    student.setRegisterDate(resultSet.getString("register_date"));

                    user.setId(resultSet.getInt("user_id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setUserStatus(resultSet.getString("user_status"));

                    faculty.setId(resultSet.getInt("faculty_id"));
                    faculty.setDescription(resultSet.getString("description"));

                    student.setFaculty(faculty);
                    student.setUser(user);

                    students.add(student);
                }
                return students;
            }
        });
    }
}
