package gabriel.games.service;

import gabriel.games.dto.UserDto;
import gabriel.games.exception.InvalidObjectValuesException;
import gabriel.games.exception.UserAlreadyExistsException;
import gabriel.games.model.User;
import gabriel.games.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static gabriel.games.util.UserUtil.makeUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @MockBean
    private UserRepository userRepository;

    private User user;

    @Before
    public void setUp() {
        user = makeUser("validUsername", "validPassword");
    }

    @Test
    public void loadUserByUsername_ExistingUsernameGiven_ShouldReturnExistingUser() {
        mockRepository(Optional.of(user));

        UserDetails result = userService.loadUserByUsername(user.getUsername());

        String capturedUsername = verifyFindByUsernameInteractions();

        assertEquals(user.getUsername(), capturedUsername);
        assertEquals(user, result);
    }

    private void mockRepository(Optional<User> optionalUser) {
        when(userRepository.findByUsername(anyString())).thenReturn(optionalUser);
    }

    private String verifyFindByUsernameInteractions() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(userRepository, times(1)).findByUsername(captor.capture());
        verifyNoMoreInteractions(userRepository);

        return captor.getValue();
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_NonExistentUsernameGiven_ShouldThrowException() {
        mockRepository(Optional.empty());

        userService.loadUserByUsername(user.getUsername());

        String capturedUsername = verifyFindByUsernameInteractions();
        assertEquals(user.getUsername(), capturedUsername);
    }

    @Test
    public void register_ValidUserDtoGiven_ShouldSaveValidUserToDatabase() throws UserAlreadyExistsException {
        UserDto expected = new UserDto("validUsername1", "valid_pass", "valid_pass");

        userService.register(expected);

        User result = verifySaveInteractions();
        assertUserIsValid(expected, result);
    }

    private User verifySaveInteractions() {
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository, times(1)).save(userCaptor.capture());
        verifyNoMoreInteractions(userRepository);

        return userCaptor.getValue();
    }

    private void assertUserIsValid(UserDto expected, User result) {
        Collection<GrantedAuthority> expectedAuthorities =  Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        assertEquals(expected.getUsername(), result.getUsername());
        assertTrue(encoder.matches(expected.getPassword(), result.getPassword()));
        assertEquals(expectedAuthorities, result.getAuthorities());
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void register_ExistentUsernameGiven_ShouldThrowException() {
        doThrow(new DuplicateKeyException("msg")).when(userRepository).save(any());

        userService.register(new UserDto("existentUsername", "valid_pass", "valid_pass"));

        verifySaveInteractions();
    }

    @Test(expected = InvalidObjectValuesException.class)
    public void register_InvalidUserGiven_ShouldThrowException() {
        UserDto invalidUserDto = new UserDto("a a", "p", "p");

        userService.register(invalidUserDto);
    }
}
