package gabriel.games.model.auth.mapper;

import gabriel.games.model.auth.dto.UserDto;
import gabriel.games.model.auth.User;
import gabriel.games.model.auth.mapper.UserDtoMapper;
import gabriel.games.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserMapperTest {

    private UserDtoMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userMapper = new UserDtoMapper(this.passwordEncoder);
        this.userDto = new UserUtil().userDto();
    }

    @Test
    public void toUser_UserDtoGiven_ReturnsCorrectUser() {
        assertConversionCorrect(userDto);
    }

    private void assertConversionCorrect(UserDto userDto) {
        User expected = UserUtil.makeUser(userDto.getUsername(), userDto.getPassword());
        User result = userMapper.toUser(userDto);
        assertAllFieldsAreTheSame(expected, result);
    }

    private void assertAllFieldsAreTheSame(User expected, User result) {
        assertEquals(expected, result);
        assertTrue(passwordEncoder.matches(expected.getPassword(), result.getPassword()));
    }

    @Test
    public void toUser_DifferentUserDtoGiven_ReturnsCorrectUser() {
        UserDto userDto = new UserDto("differentUsername", "diffPass", "diffPass");
        assertConversionCorrect(userDto);
    }

    @Test
    public void toUserDto_UserGiven_ReturnsCorrectUserDto() {
        User user = UserUtil.makeUser(userDto.getUsername(), userDto.getPassword());
        UserDto result = userMapper.toUserDto(user);
        assertEquals(userDto, result);
    }

    @Test
    public void toUserDto_DifferentUserGiven_ReturnsCorrectUserDto() {
        User user = UserUtil.makeUser("diffUsername", "diffPassword");
        UserDto expected = UserUtil.makeUserDto(user.getUsername(), user.getPassword());
        UserDto result = userMapper.toUserDto(user);
        assertEquals(expected, result);
    }
}
