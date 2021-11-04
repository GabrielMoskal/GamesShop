package gabriel.games.dto.mapper;

import gabriel.games.dto.UserDto;
import gabriel.games.model.User;
import gabriel.games.util.UserUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    private UserDtoMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private UserDto userDto;

    @Autowired
    public void setUserMapper(UserDtoMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
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
