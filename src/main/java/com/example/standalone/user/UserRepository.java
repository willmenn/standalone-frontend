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

    List<Map<String, Object>> fetchAll() {
        return jdbcTemplate.queryForList("SELECT USERNAME, ROLE FROM USER_DETAILS");
    }

    boolean insert(String username, String password, String role) {
        jdbcTemplate.execute("INSERT INTO USER_DETAILS " +
                "(username,password,role)" +
                "VALUES(?,MD5(?),?)", (PreparedStatementCallback<Boolean>) ps -> {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);

            return ps.execute();

        });

        return true;
    }

    public String findByUsername(String username, String password) {
        return jdbcTemplate
                .queryForObject("SELECT ROLE FROM USER_DETAILS " +
                                "WHERE USERNAME = ?," +
                                "AND PASSWORD = MD5(?)",
                        new Object[]{username, password}, String.class
                );
    }
}
