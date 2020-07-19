package gabriel.games.mapper;

import gabriel.games.dto.UserDto;
import gabriel.games.model.User;
import gabriel.games.utilities.UserUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserMapperTest {

    private UserMapper userMapper;

    @Before
    public void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    public void toUserModel_UserDtoGiven_ReturnsCorrectUser() {
        assertConversionCorrect("username", "password");
    }

    private void assertConversionCorrect(String username, String password) {
        User expected = UserUtil.makeUser(username, password);
        UserDto userDto = new UserDto(expected.getUsername(), expected.getPassword());
        User result = userMapper.toUserModel(userDto);

        assertAllFieldsAreTheSame(expected, result);
    }

    private void assertAllFieldsAreTheSame(User expected, User result) {
        assertEquals(expected, result);
        assertEquals(expected.getPassword(), result.getPassword());
    }

    @Test
    public void toUserModel_DifferentUserDtoGiven_ReturnsCorrectUser() {
        assertConversionCorrect("differentUsername", "differentUsername");
    }
}
