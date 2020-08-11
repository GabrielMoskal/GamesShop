package gabriel.games.repository;

import gabriel.games.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT username, password FROM users WHERE username=?";

        return queryForUser(sql, username.toLowerCase());
    }

    private Optional<User> queryForUser(String sql, String username) {
        Optional<User> result;
        try {
            User user = jdbcTemplate.queryForObject(sql, this::mapRowToUser, username);
            result = Optional.ofNullable(user);
        } catch (DataAccessException e) {
            result = Optional.empty();
        }
        return result;
    }

    private User mapRowToUser(ResultSet resultSet, int rowNumber) throws SQLException {
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        List<GrantedAuthority> grantedAuthorities = queryForAuthorities(username);

        return new User(
                username,
                password,
                grantedAuthorities
        );
    }

    private List<GrantedAuthority> queryForAuthorities(String username) {
        String sql = "SELECT authority FROM authorities WHERE username=?";
        List<String> authorities = jdbcTemplate.queryForList(sql, String.class, username);

        return authorities.stream()
                .map((SimpleGrantedAuthority::new))
                .collect(Collectors.toList());
    }

    @Override
    public User save(User user) {
        String username = user.getUsername().toLowerCase();

        saveToUsers(username, user.getPassword());
        saveToAuthorities(username, user.getAuthorities());
        return user;
    }

    private void saveToUsers(String username, String password) {
        String sqlUsers = "INSERT INTO users(username, password) VALUES(?, ?)";
        jdbcTemplate.update(
                sqlUsers,
                username,
                password
        );
    }

    private void saveToAuthorities(String username, Collection<? extends GrantedAuthority> authorities) {
        String sqlAuthorities = "INSERT INTO authorities(username, authority) VALUES(?, ?)";

        for (GrantedAuthority authority : authorities) {
            jdbcTemplate.update(
                    sqlAuthorities,
                    username,
                    authority.getAuthority()
            );
        }
    }
}
