package gabriel.games.repository;

import gabriel.games.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
}
