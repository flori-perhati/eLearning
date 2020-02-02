package com.student.elearning.dao;

import com.student.elearning.entity.Student;
import com.student.elearning.mapper.PedagogueMapper;
import com.student.elearning.entity.Faculty;
import com.student.elearning.entity.Pedagogue;
import com.student.elearning.entity.User;
import com.student.elearning.mapper.StudentMapper;
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
public class PedagogueDao extends JdbcDaoSupport {

    JdbcTemplate template;

    @Autowired
    public PedagogueDao(DataSource dataSource) {
        this.setDataSource(dataSource);
        template = this.getJdbcTemplate();
    }

    public boolean insert(Pedagogue pedagogue) {
        String sql = "INSERT INTO pedagogue (user_id, faculty_id, first_name, last_name, gender, birthdate, registration_date, status) values (?, ?, ?, ?, ?, ?, ?, ?)";
        return template.update(sql, pedagogue.getUserId(), pedagogue.getFacultyId(), pedagogue.getFirstName(), pedagogue.getLastName(), pedagogue.getGender(), pedagogue.getBirthdate(), pedagogue.getRegistrationDate(), pedagogue.getStatus()) == 1;
    }

    public boolean delete(long pedagogueId) {
        String sql = "DELETE FROM pedagogue WHERE id = ?";
        return template.update(sql, pedagogueId) == 1;
    }

    public boolean update(Pedagogue pedagogue) {
        String sql = "UPDATE pedagogue set first_name = ?, last_name = ?, gender = ?, birthdate = ?  WHERE id = ?";
        return template.update(sql, pedagogue.getFirstName(), pedagogue.getLastName(), pedagogue.getGender(), pedagogue.getBirthdate(), pedagogue.getId()) == 1;
    }

    public boolean updateStatus(long status, long id) {
        String sql = "UPDATE pedagogue SET status = ? WHERE id = ?";
        Object[] params = new Object[] {status, id};
        return template.update(sql, params) == 1;
    }

    public Pedagogue getPedagogueById(int id) {
        String sql = "SELECT * FROM pedagogue WHERE id = ?";
        Object[] params = new Object[] {id};
        List<Pedagogue> pedagogues = template.query(sql, params, new PedagogueMapper());
        return pedagogues.isEmpty() ? null : pedagogues.get(0);
    }

    public Pedagogue pedagogueByUserId(long userId){
        String sql = "SELECT p.id, p.user_id, p.faculty_id, p.first_name, p.last_name, p.gender, p.birthdate, p.registration_date, p.status, " +
                "u.username, u.password, u.user_status, f.description " +
                "FROM pedagogue p " +
                "LEFT JOIN users u ON u.id = p.user_id " +
                "LEFT JOIN faculty f ON f.id = p.faculty_id WHERE p.user_id = ?";
        Object[] params = new Object[] {userId};
        return template.queryForObject(sql, params, new RowMapper<Pedagogue>() {
            @Override
            public Pedagogue mapRow(ResultSet resultSet, int i) throws SQLException {
                Pedagogue pedagogue = new Pedagogue();
                Faculty faculty = new Faculty();
                User user = new User();

                pedagogue.setId(resultSet.getInt("id"));
                pedagogue.setUserId(resultSet.getInt("user_id"));
                pedagogue.setFacultyId(resultSet.getInt("faculty_id"));
                pedagogue.setFirstName(resultSet.getString("first_name"));
                pedagogue.setLastName(resultSet.getString("last_name"));
                pedagogue.setGender(resultSet.getString("gender"));
                pedagogue.setBirthdate(resultSet.getDate("birthdate"));
                pedagogue.setRegistrationDate(resultSet.getDate("registration_date"));
                pedagogue.setStatus(resultSet.getInt("status") == 1);

                user.setId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setUserStatus(resultSet.getString("user_status"));

                faculty.setId(resultSet.getInt("faculty_id"));
                faculty.setDescription(resultSet.getString("description"));

                pedagogue.setFaculty(faculty);
                pedagogue.setUser(user);

                return pedagogue;
            }
        });
    }

    /*public Pedagogue pedagogueDetailsToEdit(int id){
        String sql = "SELECT p.id, p.user_id, p.faculty_id, p.first_name, p.last_name, p.register_date, " +
                "u.username, u.password, u.user_status, f.description " +
                "FROM pedagogue p " +
                "LEFT JOIN users u ON u.id = p.user_id " +
                "LEFT JOIN faculty f ON f.id = p.faculty_id WHERE p.id = ?";
        Object[] params = new Object[] {id};
        return template.queryForObject(sql, params, new RowMapper<Pedagogue>() {
            @Override
            public Pedagogue mapRow(ResultSet resultSet, int i) throws SQLException {
                Pedagogue pedagogue = new Pedagogue();
                Faculty faculty = new Faculty();
                User user = new User();

                pedagogue.setId(resultSet.getInt("id"));
                pedagogue.setUserId(resultSet.getInt("user_id"));
                pedagogue.setFacultyId(resultSet.getInt("faculty_id"));
                pedagogue.setFirstName(resultSet.getString("first_name"));
                pedagogue.setLastName(resultSet.getString("last_name"));
                pedagogue.setRegisterDate(resultSet.getString("register_date"));

                user.setId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setUserStatus(resultSet.getString("user_status"));

                faculty.setId(resultSet.getInt("faculty_id"));
                faculty.setDescription(resultSet.getString("description"));

                pedagogue.setFaculty(faculty);
                pedagogue.setUser(user);

                return pedagogue;
            }
        });
    }*/

    public List<Pedagogue> getPedagogues(){
        String sql = "SELECT p.id, p.user_id, p.faculty_id, p.first_name, p.last_name, p.gender, p.birthdate, p.registration_date, " +
                "u.username, u.password, u.user_status, f.description " +
                "FROM pedagogue p " +
                "LEFT JOIN users u ON u.id = p.user_id " +
                "LEFT JOIN faculty f ON f.id = p.faculty_id " +
                "WHERE p.status = 1";

        return template.query(sql, new ResultSetExtractor<List<Pedagogue>>() {
            public List<Pedagogue> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<Pedagogue> pedagogues = new ArrayList<Pedagogue>();
                while(resultSet.next()) {
                    Pedagogue pedagogue = new Pedagogue();
                    Faculty faculty = new Faculty();
                    User user = new User();

                    pedagogue.setId(resultSet.getInt("id"));
                    pedagogue.setUserId(resultSet.getInt("user_id"));
                    pedagogue.setFacultyId(resultSet.getInt("faculty_id"));
                    pedagogue.setFirstName(resultSet.getString("first_name"));
                    pedagogue.setLastName(resultSet.getString("last_name"));
                    pedagogue.setGender(resultSet.getString("gender"));
                    pedagogue.setBirthdate(resultSet.getDate("birthdate"));
                    pedagogue.setRegistrationDate(resultSet.getDate("registration_date"));

                    user.setId(resultSet.getInt("user_id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setUserStatus(resultSet.getString("user_status"));

                    faculty.setId(resultSet.getInt("faculty_id"));
                    faculty.setDescription(resultSet.getString("description"));

                    pedagogue.setFaculty(faculty);
                    pedagogue.setUser(user);

                    pedagogues.add(pedagogue);
                }
                return pedagogues;
            }
        });
    }

    public Pedagogue getLastPedagogue(){
        String sql = "SELECT TOP 1 p.id, p.user_id, p.faculty_id, p.first_name, p.last_name, p.registration_date, " +
                "u.username, u.password, u.user_status, f.description " +
                "FROM pedagogue p " +
                "LEFT JOIN users u ON u.id = p.user_id " +
                "LEFT JOIN faculty f ON f.id = p.faculty_id " +
                "ORDER BY p.id DESC";

        return template.query(sql, new ResultSetExtractor<List<Pedagogue>>() {
            public List<Pedagogue> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<Pedagogue> pedagogues = new ArrayList<Pedagogue>();
                while(resultSet.next()) {
                    Pedagogue pedagogue = new Pedagogue();
                    Faculty faculty = new Faculty();
                    User user = new User();

                    pedagogue.setId(resultSet.getInt("id"));
                    pedagogue.setUserId(resultSet.getInt("user_id"));
                    pedagogue.setFacultyId(resultSet.getInt("faculty_id"));
                    pedagogue.setFirstName(resultSet.getString("first_name"));
                    pedagogue.setLastName(resultSet.getString("last_name"));
                    pedagogue.setRegistrationDate(resultSet.getDate("registration_date"));

                    user.setId(resultSet.getInt("user_id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setUserStatus(resultSet.getString("user_status"));

                    faculty.setId(resultSet.getInt("faculty_id"));
                    faculty.setDescription(resultSet.getString("description"));

                    pedagogue.setFaculty(faculty);
                    pedagogue.setUser(user);

                    pedagogues.add(pedagogue);
                }
                return pedagogues;
            }
        }).get(0);
    }
}
