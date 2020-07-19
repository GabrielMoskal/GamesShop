package gabriel.games.model;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.junit.Assert.assertTrue;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        this.user = new User(
                "username",
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    public void isAccountNonExpired_ShouldReturnTrue() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    public void isAccountNonLocked_ShouldReturnTrue() {
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    public void isCredentialsNonExpired_ShouldReturnTrue() {
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    public void isEnabled_ShouldReturnTrue() {
        assertTrue(user.isEnabled());
    }
}
