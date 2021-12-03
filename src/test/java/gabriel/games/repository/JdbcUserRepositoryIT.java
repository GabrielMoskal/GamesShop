package gabriel.games.repository;

import gabriel.games.model.auth.User;
import gabriel.games.util.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.assertFalse;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JdbcUserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    private User defaultUser;

    @BeforeEach
    public void setUp() {
        this.defaultUser = Users.makeUser("defaultUsername", "defaultPassword");
    }

    @Test
    public void findByUsername_ExistingUsernameGiven_ShouldReturnExistingUser() {
        User expected = Users.makeUser("user1", "password1");
        assertUsersAreTheSame(expected);
    }

    private void assertUsersAreTheSame(User user) {
        UserDetails result = userRepository.findByUsername(user.getUsername())
                .orElse(Users.makeUser("noname", "nopass"));
        User expected = new User(
                user.getUsername().toLowerCase(),
                user.getPassword(),
                user.getAuthorities()
        );

        assertTrue(encoder.matches(user.getPassword(), result.getPassword()));
        assertEquals(expected, result);
    }

    @Test
    public void findByUsername_ExistingDifferentUsernameGiven_ShouldReturnExistingUser() {
        User expected = Users.makeUser("user2", "password2");
        assertUsersAreTheSame(expected);
    }

    @Test
    public void findByUsername_NonExistingUsernameGiven_ShouldReturnOptionalEmpty() {
        User user = Users.makeUser("doesntExist", "password");
        Optional<User> result = userRepository.findByUsername(user.getUsername());
        assertFalse("Optional<UserDetails> should be empty", result.isPresent());
    }

    @Test
    public void findByUsername_ExistingUsernameDifferentCaseGiven_ShouldReturnExistingUser() {
        User expected = Users.makeUser("USER1", "password1");
        assertUsersAreTheSame(expected);
    }

    @Test
    public void findByUsername_ExistingUsernameGiven_ShouldContainCorrectAuthorities() {
        User result = userRepository.findByUsername("user1").orElse(defaultUser);
        Collection<? extends GrantedAuthority> authorities = result.getAuthorities();

        assertAuthoritiesContains(authorities, "ROLE_USER");
        assertEquals(authorities.size(), 1);
    }

    private void assertAuthoritiesContains(Collection<? extends GrantedAuthority> authorities, String role) {
        assertTrue(authorities.contains(new SimpleGrantedAuthority(role)));
    }

    @Test
    public void findByUsername_ExistingOtherUsernameGiven_ShouldContainCorrectAuthorities() {
        User result = userRepository.findByUsername("admin").orElse(defaultUser);
        Collection<? extends GrantedAuthority> authorities = result.getAuthorities();

        assertAuthoritiesContains(authorities, "ROLE_ADMIN");
        assertAuthoritiesContains(authorities, "ROLE_USER");
        assertEquals(authorities.size(), 2);
    }

    @Test
    public void save_NotExistingUserGiven_ShouldExistInDatabase() {
        User expected = Users.makeUser("qwerty1", "newPassword");

        assertSavesUsersCorrectly(expected);
    }

    private void assertSavesUsersCorrectly(User expected) {
        userRepository.save(expected);
        User result = userRepository.findByUsername(expected.getUsername().toLowerCase()).orElse(defaultUser);

        assertEquals(expected, result);
    }

    @Test
    public void save_NotExistingOtherUserGiven_ShouldExistInDatabase() {
        User expected = new User(
                "qwerty_different",
                "newPassword",
                Arrays.asList(
                        new SimpleGrantedAuthority("ROLE_USER"),
                        new SimpleGrantedAuthority("ROLE_ADMIN")
                )
        );
        assertSavesUsersCorrectly(expected);
    }

    @Test
    public void save_ExistingUserGiven_ShouldThrowException() {
        User user = Users.makeUser("user1", "password1");
        assertThrows(DuplicateKeyException.class, () -> userRepository.save(user));
    }

    @Test
    public void save_NotExistingUserGiven_ShouldReturnSavedUser() {
        User result = userRepository.save(defaultUser);
        assertEquals(defaultUser, result);
    }
}
