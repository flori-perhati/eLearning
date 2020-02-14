package com.student.elearning.dao;

import com.student.elearning.entity.Faculty;
import com.student.elearning.entity.Student;
import com.student.elearning.entity.User;
import com.student.elearning.mapper.StudentMapper;
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
public class StudentDao extends JdbcDaoSupport {

    JdbcTemplate template;

    @Autowired
    public StudentDao(DataSource dataSource) {
        this.setDataSource(dataSource);
        template = this.getJdbcTemplate();
    }

    public boolean insert(Student student) {
        String sql = "INSERT INTO student (user_id, faculty_id, first_name, last_name, gender, birthdate, registration_date, status) values (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = new Object[] {student.getUserId(), student.getFacultyId(), student.getFirstName(), student.getLastName(), student.getGender(), student.getBirthdate(), student.getRegistrationDate(), student.getStatus()};
        return template.update(sql, params) == 1;
    }

    public boolean delete(long studentId) {
        String sql = "DELETE FROM student WHERE id = ?";
        Object[] params = new Object[] {studentId};
        return template.update(sql, params) == 1;
    }

    public boolean update(Student student) {
        String sql = "UPDATE student SET first_name = ?, last_name = ?, gender = ?, birthdate = ? WHERE id = ?";
        Object[] params = new Object[] {student.getFirstName(), student.getLastName(), student.getGender(), student.getBirthdate(), student.getId()};
        return template.update(sql, params) == 1;
    }

    public boolean updateStatus(long status, long id) {
        String sql = "UPDATE student SET status = ? WHERE id = ?";
        Object[] params = new Object[] {status, id};
        return template.update(sql, params) == 1;
    }

    public Student getStudentById(int id) {
        String sql = "SELECT * FROM student WHERE id = ?";
        Object[] params = new Object[] {id};
        List<Student> students = template.query(sql, params, new StudentMapper());
        return students.isEmpty() ? null : students.get(0);
    }

    public Student studentByUserId(long userId) {
        String sql = "SELECT s.id, s.user_id, s.faculty_id, s.first_name, s.last_name, s.gender, s.birthdate, s.registration_date, s.status, " +
                "u.username, u.password, u.user_status, f.description " +
                "FROM student s " +
                "LEFT JOIN users u ON u.id = s.user_id " +
                "LEFT JOIN faculty f ON f.id = s.faculty_id WHERE s.user_id = ?";
        Object[] params = new Object[] {userId};
        return template.queryForObject(sql, params, (resultSet, i) -> {
            Student student = new Student();
            Faculty faculty = new Faculty();
            User user = new User();

            student.setId(resultSet.getInt("id"));
            student.setUserId(resultSet.getInt("user_id"));
            student.setFacultyId(resultSet.getInt("faculty_id"));
            student.setFirstName(resultSet.getString("first_name"));
            student.setLastName(resultSet.getString("last_name"));
            student.setGender(resultSet.getString("gender"));
            student.setBirthdate(resultSet.getDate("birthdate"));
            student.setRegistrationDate(resultSet.getDate("registration_date"));
            student.setStatus(resultSet.getInt("status") == 1);

            user.setId(resultSet.getInt("user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setUserStatus(resultSet.getString("user_status"));

            faculty.setId(resultSet.getInt("faculty_id"));
            faculty.setDescription(resultSet.getString("description"));

            student.setFaculty(faculty);
            student.setUser(user);

            return student;
        });
    }

    public List<Student> getStudents() {
        String sql = "SELECT s.id, s.user_id, s.faculty_id, s.first_name, s.last_name, s.registration_date, " +
                "u.username, u.password, u.user_status, f.description " +
                "FROM student s " +
                "LEFT JOIN users u ON u.id = s.user_id " +
                "LEFT JOIN faculty f ON f.id = s.faculty_id " +
                "WHERE s.status = 1";

        return template.query(sql, resultSet -> {
            List<Student> students = new ArrayList<>();
            while(resultSet.next()) {
                Student student = new Student();
                Faculty faculty = new Faculty();
                User user = new User();

                student.setId(resultSet.getInt("id"));
                student.setUserId(resultSet.getInt("user_id"));
                student.setFacultyId(resultSet.getInt("faculty_id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setRegistrationDate(resultSet.getDate("registration_date"));

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
        });
    }

    public List<Student> getStudentsForCourse(long facultyId, long courseId, StudentCourseDao studentCourseDao) {
        String sql = "SELECT * FROM student WHERE faculty_id = ? AND status = 1";
        Object[] params = new Object[] {facultyId};
        return template.query(sql, params, resultSet -> {
            List<Student> students = new ArrayList<>();
            while(resultSet.next()) {
                Student student = new Student();

                student.setId(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));

                if (!studentCourseDao.studentCourseExist(courseId, student.getId()))
                    students.add(student.setCourseId(courseId));
            }
            return students;
        });
    }

    public List<Student> studentsByCourse(long courseId) {
        String sql = "SELECT * FROM student_course WHERE course_id = ?";
        Object[] params = new Object[] {courseId};
        return template.query(sql, params, resultSet -> {
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
                student.setRegistrationDate(resultSet.getDate("registration_date"));

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
        });
    }

    public Student getLastStudent(){
        String sql = "SELECT TOP 1 s.id, s.user_id, s.faculty_id, s.first_name, s.last_name, s.gender, s.birthdate, s.registration_date, " +
                "u.username, u.password, u.user_status, f.description " +
                "FROM student s " +
                "LEFT JOIN users u ON u.id = s.user_id " +
                "LEFT JOIN faculty f ON f.id = s.faculty_id " +
                "ORDER BY s.id DESC";

        return template.query(sql, resultSet -> {
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
                student.setGender(resultSet.getString("gender"));
                student.setBirthdate(resultSet.getDate("birthdate"));
                student.setRegistrationDate(resultSet.getDate("registration_date"));

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
        }).get(0);
    }
}
