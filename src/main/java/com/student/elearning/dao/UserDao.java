package com.student.elearning.dao;

import com.student.elearning.mapper.UserMapper;
import com.student.elearning.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
@Transactional
public class UserDao extends JdbcDaoSupport {

    JdbcTemplate template;

    @Autowired
    public UserDao(DataSource dataSource) {
        this.setDataSource(dataSource);
        template = this.getJdbcTemplate();
    }

    public boolean insert(User user) {
        String sql = "INSERT INTO users (username, password, user_status) values (?, ?, ?)";
        Object[] params = new Object[] {user.getUsername(), user.getPassword(), user.getUserStatus()};
        return template.update(sql, params) == 1;
    }

    public boolean update(User user) {
        String sql = "UPDATE users SET username = ?, password = ? WHERE id = ?";
        Object[] params = new Object[] {user.getUsername(), user.getPassword(), user.getId()};
        return template.update(sql, params) == 1;
    }

    public boolean delete(long userId) {
        String sql = "DELETE FROM users WHERE id = ?";
        Object[] params = new Object[] {userId};
        return template.update(sql, params) == 1;
    }

    public User validateUser(User user) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ? COLLATE Latin1_General_CS_AS";
        Object[] params = new Object[] {user.getUsername(), user.getPassword()};
        User loggedUser = template.queryForObject(sql, params, new UserMapper());
        return loggedUser;
    }

    public User lastUser() {
        String sql = "SELECT TOP 1 * FROM users ORDER BY id DESC";
        return template.queryForObject(sql, new UserMapper());
    }

    public User getUserById(long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        Object[] params = new Object[] {id};
        return template.queryForObject(sql, params, new UserMapper());
    }
}
