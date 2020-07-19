package gabriel.games.repository;

import gabriel.games.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository {
    Optional<UserDetails> findByUsername(String username);

    void save(User user);
}
