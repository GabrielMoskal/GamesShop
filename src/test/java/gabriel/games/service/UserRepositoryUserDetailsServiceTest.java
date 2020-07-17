package gabriel.games.service;

import gabriel.games.utilities.UserUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryUserDetailsServiceTest {

    @Autowired
    private UserRepositoryUserDetailsService service;

    @Test
    public void loadUserByUsernameReturnsExistingUser() {
        UserDetails expected = UserUtil.makeUser("user1", "password1");
        assertServiceLoadsTheSameUser(expected);
    }

    private void assertServiceLoadsTheSameUser(UserDetails expected) {
        UserDetails result = service.loadUserByUsername(expected.getUsername());
        assertEquals(expected, result);
    }

    @Test
    public void loadUserByUsernameReturnsDifferentExistingUser() {
        UserDetails expected = UserUtil.makeUser("user2", "password2");
        assertServiceLoadsTheSameUser(expected);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void usernameNotFoundThrowsException() {
        service.loadUserByUsername("nonExistent");
    }
}
