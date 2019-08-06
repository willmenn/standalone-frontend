package com.example.standalone.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    List<Map<String, Object>> fetchAllByApp(String appToken) {
        return jdbcTemplate.queryForList("SELECT USERNAME, ROLE FROM USER_DETAILS WHERE APP = ?",
                new Object[]{appToken});
    }

    boolean insert(String username, String password, String role, String app) {
        jdbcTemplate.execute("INSERT INTO USER_DETAILS " +
                "(username,password,role, app)" +
                "VALUES(?,MD5(?),?, ?)", (PreparedStatementCallback<Boolean>) ps -> {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role.toUpperCase());
            ps.setString(4, app);

            return ps.execute();

        });

        return true;
    }

    public String findByUsername(String username, String password, String app) {
        try {
            return jdbcTemplate
                    .queryForObject("SELECT ROLE FROM USER_DETAILS " +
                                    "WHERE USERNAME = ? " +
                                    "AND PASSWORD = MD5(?)" +
                                    "AND APP = ?",
                            new Object[]{username, password, app}, String.class
                    );
        } catch (Exception e) {
            return null;
        }
    }
}
