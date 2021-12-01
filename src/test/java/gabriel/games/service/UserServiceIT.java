package gabriel.games.service;

import gabriel.games.model.auth.dto.UserDto;
import gabriel.games.controller.auth.exception.UserAlreadyExistsException;
import gabriel.games.model.auth.User;
import gabriel.games.repository.UserRepository;
import gabriel.games.service.exception.InvalidObjectValuesException;
import gabriel.games.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserDto userDto;

    private User user;

    @BeforeEach
    public void setUp() {
        this.user = UserUtil.makeUser(userDto.getUsername(), userDto.getPassword());
    }

    @Test
    public void loadUserByUsername_ExistingUsernameGiven_ShouldReturnExistingUser() {
        mockFindByUsername(Optional.of(user));

        UserDetails result = userService.loadUserByUsername(user.getUsername());

        String capturedUsername = verifyFindByUsernameInteractions();

        assertEquals(user.getUsername(), capturedUsername);
        assertEquals(user, result);
    }

    private void mockFindByUsername(Optional<User> optionalUser) {
        when(userRepository.findByUsername(anyString())).thenReturn(optionalUser);
    }

    private String verifyFindByUsernameInteractions() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(userRepository, times(1)).findByUsername(captor.capture());
        verifyNoMoreInteractions(userRepository);

        return captor.getValue();
    }

    @Test
    public void loadUserByUsername_NonExistentUsernameGiven_ShouldThrowException() {
        mockFindByUsername(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(user.getUsername()));

        String capturedUsername = verifyFindByUsernameInteractions();
        assertEquals(user.getUsername(), capturedUsername);
    }

    @Test
    public void register_ValidUserDtoGiven_ShouldSaveValidUserToDatabase() throws UserAlreadyExistsException {
        mockSave();

        userService.register(userDto);

        User result = verifySaveInteractions();
        assertUserIsValid(userDto, result);
    }

    private void mockSave() {
        when(userRepository.save(any())).thenReturn(user);
    }

    private User verifySaveInteractions() {
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository, times(1)).save(userCaptor.capture());
        verifyNoMoreInteractions(userRepository);

        return userCaptor.getValue();
    }

    @Test
    public void register_ValidUserDtoGiven_ShouldReturnEqualUserDto() {
        mockSave();

        UserDto result = userService.register(userDto);

        assertEquals(userDto, result);
    }

    private void assertUserIsValid(UserDto expected, User result) {
        Collection<GrantedAuthority> expectedAuthorities =  Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        assertEquals(expected.getUsername(), result.getUsername());
        assertTrue(encoder.matches(expected.getPassword(), result.getPassword()));
        assertEquals(expectedAuthorities, result.getAuthorities());
    }

    @Test
    public void register_ExistentUsernameGiven_ShouldThrowException() {
        doThrow(new DuplicateKeyException("msg")).when(userRepository).save(any());

        assertThrows(UserAlreadyExistsException.class, () -> userService.register(userDto));
        verifySaveInteractions();
    }

    @Test
    public void register_InvalidUserGiven_ShouldThrowException() {
        UserDto invalidUserDto = new UserDto("a a", "p", "p");

        assertThrows(InvalidObjectValuesException.class, () -> userService.register(invalidUserDto));
    }
}
