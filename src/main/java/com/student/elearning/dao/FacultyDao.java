package com.student.elearning.dao;

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
public class FacultyDao extends JdbcDaoSupport {

    JdbcTemplate template;

    @Autowired
    public FacultyDao(DataSource dataSource) {
        this.setDataSource(dataSource);
        template = this.getJdbcTemplate();
    }

    public List<String> getFaculties() {
        String sql = "SELECT * FROM faculty";

        return template.query(sql, new ResultSetExtractor<List<String>>() {
            public List<String> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<String> faculties = new ArrayList<>();
                while(resultSet.next()) {
                    faculties.add(resultSet.getInt("id") + ". " + resultSet.getString("description"));
                }
                return faculties;
            }
        });
    }
}
