package gabriel.games.model.auth.dto;

import gabriel.games.model.util.*;
import org.junit.jupiter.api.Test;

public class UserDtoTest {

    private UserDto actual;

    @Test
    public void validUserDtoHasNoErrors() {
        actual = new UserDto("username", "password", "password");
        EntityValidator.assertErrors(actual, 0);
    }

    @Test
    public void usernameShouldBeAtLeast5CharLong() {
        actual = new UserDto(GenericWord.make(4), "", "");
        EntityValidator.assertPropertyErrors(actual, "username");
    }

    @Test
    public void usernameShouldBeMax20CharsLong() {
        actual = new UserDto(GenericWord.make(21), "", "");
        EntityValidator.assertPropertyErrors(actual, "username");
    }

    @Test
    public void usernameShouldHaveNoWhitespaces() {
        actual = new UserDto("white spaces", "", "");
        EntityValidator.assertPropertyErrors(actual, "username");
    }

    @Test
    public void usernameShouldNotBeNull() {
        actual = new UserDto(null, "", "");
        EntityValidator.assertPropertyErrors(actual, "username");
    }

    @Test
    public void passwordShouldBeAtLeast7CharLong() {
        actual = new UserDto("", GenericWord.make(6), "");
        EntityValidator.assertPropertyErrors(actual, "password");
    }

    @Test
    public void passwordShouldBeMax20CharsLong() {
        actual = new UserDto("", GenericWord.make(21), "");
        EntityValidator.assertPropertyErrors(actual, "password");
    }

    @Test
    public void passwordShouldHaveNoWhitespaces() {
        actual = new UserDto("", "white spaces", "");
        EntityValidator.assertPropertyErrors(actual, "password");
    }

    @Test
    public void passwordShouldNotBeNull() {
        actual = new UserDto("", null, "");
        EntityValidator.assertPropertyErrors(actual, "password");
    }

    @Test
    public void confirmedPasswordShouldBeAtLeast7CharsLong() {
        actual = new UserDto("", "", GenericWord.make(6));
        EntityValidator.assertPropertyErrors(actual, "confirmedPassword");
    }

    @Test
    public void confirmedPasswordShouldBeMax20CharsLong() {
        actual = new UserDto("", "", GenericWord.make(21));
        EntityValidator.assertPropertyErrors(actual, "confirmedPassword");
    }

    @Test
    public void confirmedPasswordShouldHaveNoWhitespaces() {
        actual = new UserDto("", "", "white spaces");
        EntityValidator.assertPropertyErrors(actual, "confirmedPassword");
    }

    @Test
    public void confirmedPasswordShouldNotBeNull() {
        actual = new UserDto("", "", null);
        EntityValidator.assertPropertyErrors(actual, "confirmedPassword");
    }

    @Test
    public void passwordAndConfirmedPasswordShouldMatch() {
        actual = new UserDto("username", "password", "password");
        EntityValidator.assertErrors(actual, 0);
    }

    @Test
    public void passwordAndConfirmedPasswordShouldNotMatch() {
        actual = new UserDto("username", "password", "different");
        EntityValidator.assertErrors(actual, 1);
    }
}
