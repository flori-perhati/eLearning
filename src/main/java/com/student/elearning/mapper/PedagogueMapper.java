package com.student.elearning.mapper;

import com.student.elearning.entity.Pedagogue;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PedagogueMapper implements RowMapper<Pedagogue> {
    @Override
    public Pedagogue mapRow(ResultSet resultSet, int i) throws SQLException {
        Pedagogue pedagogue = new Pedagogue();
        pedagogue.setId(resultSet.getLong("id"));
        pedagogue.setUserId(resultSet.getLong("user_id"));
        pedagogue.setFacultyId(resultSet.getLong("faculty_id"));
        pedagogue.setFirstName(resultSet.getString("first_name"));
        pedagogue.setLastName(resultSet.getString("last_name"));
        pedagogue.setGender(resultSet.getString("gender"));
        pedagogue.setBirthdate(resultSet.getDate("birthdate"));
        pedagogue.setRegistrationDate(resultSet.getDate("registration_date"));
        pedagogue.setStatus(resultSet.getInt("status") == 1);
        return pedagogue;
    }
}
