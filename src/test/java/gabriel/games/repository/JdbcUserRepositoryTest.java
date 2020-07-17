package gabriel.games.repository;

import gabriel.games.model.User;
import gabriel.games.utilities.UserUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void findsExistingUserByUsername() {
        User expected = UserUtil.makeUser("user1", "password1");
        assertUsersAreTheSame(expected);
    }

    private void assertUsersAreTheSame(User user) {
        UserDetails result = userRepository.findByUsername(user.getUsername())
                .orElse(UserUtil.makeUser("noname", "nopass"));
        User expected = new User(
                user.getUsername().toLowerCase(),
                user.getPassword(),
                user.getAuthorities()
        );

        assertTrue(encoder.matches(user.getPassword(), result.getPassword()));
        assertEquals(expected, result);
    }

    @Test
    public void findsDifferentExistingUserByUsername() {
        User expected = UserUtil.makeUser("user2", "password2");
        assertUsersAreTheSame(expected);
    }

    @Test
    public void returnsOptionalEmptyIfNotFound() {
        User user = UserUtil.makeUser("doesntExist", "password");
        Optional<UserDetails> result = userRepository.findByUsername(user.getUsername());
        assertFalse("Optional<UserDetails> should be empty", result.isPresent());
    }

    @Test
    public void returnsExistingUserCaseInsensitive() {
        User expected = UserUtil.makeUser("USER1", "password1");
        assertUsersAreTheSame(expected);
    }

    @Test
    public void existingUserHasCorrectAuthorities() {
        User result = (User)userRepository.findByUsername("user1").get();
        Collection<? extends GrantedAuthority> authorities = result.getAuthorities();

        assertAuthoritiesContains(authorities, "ROLE_USER");
        assertEquals(authorities.size(), 1);
    }

    private void assertAuthoritiesContains(Collection<? extends GrantedAuthority> authorities, String role) {
        assertTrue(authorities.contains(new SimpleGrantedAuthority(role)));
    }

    @Test
    public void existingOtherUserHasCorrectAuthorities() {
        User result = (User)userRepository.findByUsername("admin").get();
        Collection<? extends GrantedAuthority> authorities = result.getAuthorities();

        assertAuthoritiesContains(authorities, "ROLE_ADMIN");
        assertAuthoritiesContains(authorities, "ROLE_USER");
        assertEquals(authorities.size(), 2);
    }
}
