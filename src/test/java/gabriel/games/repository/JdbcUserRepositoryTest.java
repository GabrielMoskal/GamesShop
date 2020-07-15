package gabriel.games.repository;


import gabriel.games.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void findsExistingUserByUsername() {
        User expected = new User("user1", "password1");
        assertUsersAreTheSame(expected);
    }

    private void assertUsersAreTheSame(User expected) {
        User result = userRepository.findByUsername(expected.getUsername()).orElseThrow(IllegalStateException::new);
        assertTrue(encoder.matches(expected.getPassword(), result.getPassword()));
        assertEquals(expected, result);
    }

    @Test
    public void findsDifferentExistingUserByUsername() {
        User expected = new User("user2", "password2");
        assertUsersAreTheSame(expected);
    }

    @Test
    public void returnsOptionalEmptyIfNotFound() {
        User user = new User("doesntExist", "password");
        Optional<User> result = userRepository.findByUsername(user.getUsername());
        assertFalse("Optional<User> should be empty", result.isPresent());
    }
}
