package gabriel.games.dto;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.*;

public class UserDtoTest {

    private Validator validator;
    private String correctUsername;
    private String correctPassword;
    private String correctConfirmedPassword;

    @Before
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        correctUsername = "correctUsername";
        correctPassword = "correctPassword";
        correctConfirmedPassword = "correctPassword";
    }

    @Test
    public void validUserDtoHasNoErrors() {
        UserDto userDto = new UserDto(correctUsername, correctPassword, correctConfirmedPassword);

        assertValidationErrors(userDto, 0);
    }

    private void assertValidationErrors(UserDto userDto, int numOfErrors) {
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertEquals(numOfErrors, violations.size());
    }

    @Test
    public void usernameShouldBeAtLeast5CharLong() {
        String username = makeGenericWord(4);
        UserDto userDto = new UserDto(username, correctPassword, correctConfirmedPassword);

        assertValidationErrors(userDto, 1);
    }

    @Test
    public void usernameShouldBeMax20CharsLong() {
        String username = makeGenericWord(21);
        UserDto userDto = new UserDto(username, correctPassword, correctConfirmedPassword);

        assertValidationErrors(userDto, 1);
    }

    private String makeGenericWord(int length) {
        return String.join("", Collections.nCopies(length, "a"));
    }

    @Test
    public void usernameShouldHaveNoWhitespaces() {
        UserDto userDto = new UserDto("white spaces", correctPassword, correctConfirmedPassword);

        assertValidationErrors(userDto, 1);
    }

    @Test
    public void usernameShouldNotBeNull() {
        UserDto userDto = new UserDto(null, correctPassword, correctConfirmedPassword);

        assertValidationErrors(userDto, 1);
    }

    @Test
    public void passwordShouldBeAtLeast7CharLong() {
        String password = makeGenericWord(6);
        UserDto userDto = new UserDto(correctUsername, password, correctConfirmedPassword);

        assertValidationErrors(userDto, 2);
    }

    @Test
    public void passwordShouldBeMax20CharsLong() {
        String password = makeGenericWord(21);
        UserDto userDto = new UserDto(correctUsername, password, correctConfirmedPassword);

        assertValidationErrors(userDto, 2);
    }

    @Test
    public void passwordShouldHaveNoWhitespaces() {
        UserDto userDto = new UserDto(correctUsername, "white spaces", correctConfirmedPassword);

        assertValidationErrors(userDto, 2);
    }

    @Test
    public void passwordShouldNotBeNull() {
        UserDto userDto = new UserDto(correctUsername, null, correctConfirmedPassword);

        assertValidationErrors(userDto, 2);
    }

    @Test
    public void confirmedPasswordShouldBeAtLeast7CharsLong() {
        String confirmedPassword = makeGenericWord(6);
        UserDto userDto = new UserDto(correctUsername, correctPassword, confirmedPassword);

        assertValidationErrors(userDto, 2);
    }

    @Test
    public void confirmedPasswordShouldBeMax20CharsLong() {
        String confirmedPassword = makeGenericWord(21);
        UserDto userDto = new UserDto(correctUsername, correctPassword, confirmedPassword);

        assertValidationErrors(userDto, 2);
    }

    @Test
    public void confirmedPasswordShouldHaveNoWhitespaces() {
        UserDto userDto = new UserDto(correctUsername, correctPassword, "white spaces");

        assertValidationErrors(userDto, 2);
    }

    @Test
    public void confirmedPasswordShouldNotBeNull() {
        UserDto userDto = new UserDto(correctUsername, correctPassword, null);

        assertValidationErrors(userDto, 2);
    }

    @Test
    public void passwordAndConfirmedPasswordShouldMatch() {
        UserDto userDto = new UserDto(correctUsername, correctPassword, correctConfirmedPassword);

        assertValidationErrors(userDto, 0);
    }

    @Test
    public void passwordAndConfirmedPasswordShouldNotMatch() {
        UserDto userDto = new UserDto(correctUsername, "password", "different");

        assertValidationErrors(userDto, 1);
    }
}
