package com.student.elearning.dao;

import com.student.elearning.entity.User;
import com.student.elearning.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

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

    public User validateUser(User user, StudentDao studentDao, PedagogueDao pedagogueDao) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ? COLLATE Latin1_General_CS_AS ";
        Object[] params = new Object[] {user.getUsername(), user.getPassword()};
        List<User> users = template.query(sql, params, new UserMapper());
        if (users.isEmpty())
            return null;
        else {
            User loggedUser = users.get(0);
            switch (loggedUser.getUserStatus()) {
                case "admin":
                    break;
                case "student":
                    if (!studentDao.studentByUserId(loggedUser.getId()).getStatus())
                        loggedUser = null;
                    break;
                case "pedagogue":
                    if (!pedagogueDao.pedagogueByUserId(loggedUser.getId()).getStatus())
                        loggedUser = null;
                    break;
            }
            return loggedUser;
        }
    }

    public User lastUser() {
        String sql = "SELECT TOP 1 * FROM users ORDER BY id DESC";
        List<User> users = template.query(sql, new UserMapper());
        return users.isEmpty() ? null : users.get(0);
    }
}
