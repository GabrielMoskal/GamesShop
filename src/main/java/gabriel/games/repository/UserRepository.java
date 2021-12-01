package gabriel.games.repository;

import gabriel.games.model.auth.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);

    User save(User user);
}
