package gabriel.games.mapper;

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

    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Test
    public void toUserModel_UserDtoGiven_ReturnsCorrectUser() {
        assertConversionCorrect("username", "password");
    }

    private void assertConversionCorrect(String username, String password) {
        UserDto userDto = new UserDto(username, password, password);

        User expected = UserUtil.makeUser(username, password);
        User result = userMapper.toUser(userDto);

        assertAllFieldsAreTheSame(expected, result);
    }

    private void assertAllFieldsAreTheSame(User expected, User result) {
        assertEquals(expected, result);
        assertTrue(passwordEncoder.matches(expected.getPassword(), result.getPassword()));
    }

    @Test
    public void toUserModel_DifferentUserDtoGiven_ReturnsCorrectUser() {
        assertConversionCorrect("differentUsername", "differentUsername");
    }
}
