package com.example.standalone.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> fetchAll() {
        return jdbcTemplate.queryForList("SELECT USERNAME, MD5(PASSWORD) FROM USER_DETAILS");
    }

    public boolean insert(String username, String password, String role) {

        jdbcTemplate.execute("INSERT INTO USER_DETAILS " +
                "(username,password,role)" +
                "VALUES(?,MD5('?'),?)", (PreparedStatementCallback<Boolean>) ps -> {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);

            return ps.execute();

        });
        return true;
    }

    public boolean findByUsername(String username) {
        String usernameFromDb = jdbcTemplate
                .queryForObject("SELECT USERNAME FROM USER_DETAILS WHERE USERNAME = ?", new Object[]{username},String.class
                );

        return usernameFromDb.equals(username);
    }
}
