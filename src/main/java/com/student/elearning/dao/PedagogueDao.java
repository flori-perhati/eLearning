package com.student.elearning.dao;

import com.student.elearning.mapper.PedagogueMapper;
import com.student.elearning.model.Faculty;
import com.student.elearning.model.Pedagogue;
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
public class PedagogueDao extends JdbcDaoSupport {

    JdbcTemplate template;

    @Autowired
    public PedagogueDao(DataSource dataSource) {
        this.setDataSource(dataSource);
        template = this.getJdbcTemplate();
    }

    public boolean insert(Pedagogue pedagogue) {
        String sql = "INSERT INTO pedagogue (user_id, faculty_id, first_name, last_name, register_date) values (?, ?, ?, ?, ?)";
        return template.update(sql, pedagogue.getUserId(), pedagogue.getFacultyId(), pedagogue.getFirstName(), pedagogue.getLastName(), pedagogue.getRegisterDate()) == 1;
    }

    public boolean delete(long pedagogueId) {
        String sql = "DELETE FROM pedagogue WHERE id = ?";
        return template.update(sql, pedagogueId) == 1;
    }

    public boolean update(Pedagogue pedagogue) {
        String sql = "UPDATE pedagogue set first_name = ?, last_name = ? WHERE id = ?";
        return template.update(sql, pedagogue.getFirstName(), pedagogue.getLastName(), pedagogue.getId()) == 1;
    }

    public Pedagogue getPedagogueById(int id) {
        String sql = "SELECT * FROM pedagogue WHERE id = ?";
        Object[] params = new Object[] {id};
        return template.queryForObject(sql, params, new PedagogueMapper());
    }

    public Pedagogue pedagogueByUserId(long userId){
        String sql = "SELECT p.id, p.user_id, p.faculty_id, p.first_name, p.last_name, p.register_date, " +
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
    }

    public Pedagogue pedagogueDetailsToEdit(int id){
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
    }

    public List<Pedagogue> getPedagogues(){
        String sql = "SELECT p.id, p.user_id, p.faculty_id, p.first_name, p.last_name, p.register_date, " +
                "u.username, u.password, u.user_status, f.description " +
                "FROM pedagogue p " +
                "LEFT JOIN users u ON u.id = p.user_id " +
                "LEFT JOIN faculty f ON f.id = p.faculty_id";

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
                    pedagogue.setRegisterDate(resultSet.getString("register_date"));

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
        String sql = "SELECT TOP 1 p.id, p.user_id, p.faculty_id, p.first_name, p.last_name, p.register_date, " +
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
                    pedagogue.setRegisterDate(resultSet.getString("register_date"));

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
