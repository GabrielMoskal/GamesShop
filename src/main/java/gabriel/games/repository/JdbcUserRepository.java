package gabriel.games.repository;

import gabriel.games.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

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

        return queryForUser(sql, username);
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
        return new User(
                resultSet.getString("username"),
                resultSet.getString("password")
        );
    }
}
