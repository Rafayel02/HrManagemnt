package com.example.HrManagement2;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private List<UserDetails> users = new ArrayList<>();

    private final JdbcTemplate jdbcTemplate;

    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return jdbcTemplate.queryForObject("select id, username, password from hrs where username = ?",
                new Object[]{username},
                ((resultSet, i) -> new HR(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password")
                ))
        );
    }


    public HR register(String username, String password) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        createTableIfNotExists(keyHolder);
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into hrs(username, password) values(?, ?)",
                    new String[]{"id"}
            );
            ps.setString(1, username);
            ps.setString(2, password);
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        return new HR(key.intValue(), username, password);
    }

    private void createTableIfNotExists(KeyHolder keyHolder) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS hrs( id serial not null primary key, username text not null, password text not null );"
            );
            return ps;
        }, keyHolder);
    }

}
