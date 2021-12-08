package gabriel.games.model.auth.dto;

import gabriel.games.model.util.EntityValidator;
import gabriel.games.model.util.GenericWord;
import gabriel.games.model.util.ReflectionSetter;
import gabriel.games.model.util.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDtoTest {

    private ReflectionSetter<UserDto> setter;
    private EntityValidator<UserDto> validator;
    private GenericWord genericWord;

    @BeforeEach
    public void setUp() {
        UserDto userDto = Users.makeUserDto("correctUsername", "correctPassword");
        this.setter = new ReflectionSetter<>(userDto);
        this.validator = new EntityValidator<>(userDto);
        this.genericWord = new GenericWord();
    }

    @Test
    public void validUserDtoHasNoErrors() {
        validator.assertErrors(0);
    }

    @Test
    public void usernameShouldBeAtLeast5CharLong() {
        setter.set("username", genericWord.make(4));
        validator.assertErrors(1);
    }

    @Test
    public void usernameShouldBeMax20CharsLong() {
        setter.set("username", genericWord.make(21));
        validator.assertErrors(1);
    }

    @Test
    public void usernameShouldHaveNoWhitespaces() {
        setter.set("username", "white spaces");
        validator.assertErrors(1);
    }

    @Test
    public void usernameShouldNotBeNull() {
        setter.set("username", null);
        validator.assertErrors(1);
    }

    @Test
    public void passwordShouldBeAtLeast7CharLong() {
        setter.set("password", genericWord.make(6));
        validator.assertErrors(2);
    }

    @Test
    public void passwordShouldBeMax20CharsLong() {
        setter.set("password", genericWord.make(21));
        validator.assertErrors(2);
    }

    @Test
    public void passwordShouldHaveNoWhitespaces() {
        setter.set("password", "white spaces");
        validator.assertErrors(2);
    }

    @Test
    public void passwordShouldNotBeNull() {
        setter.set("password", null);
        validator.assertErrors(2);
    }

    @Test
    public void confirmedPasswordShouldBeAtLeast7CharsLong() {
        setter.set("confirmedPassword", genericWord.make(6));
        validator.assertErrors(2);
    }

    @Test
    public void confirmedPasswordShouldBeMax20CharsLong() {
        setter.set("confirmedPassword", genericWord.make(21));
        validator.assertErrors(2);
    }

    @Test
    public void confirmedPasswordShouldHaveNoWhitespaces() {
        setter.set("confirmedPassword", "white spaces");
        validator.assertErrors(2);
    }

    @Test
    public void confirmedPasswordShouldNotBeNull() {
        setter.set("confirmedPassword", null);
        validator.assertErrors(2);
    }

    @Test
    public void passwordAndConfirmedPasswordShouldMatch() {
        validator.assertErrors(0);
    }

    @Test
    public void passwordAndConfirmedPasswordShouldNotMatch() {
        setter.set("password", "password");
        setter.set("confirmedPassword", "different");
        validator.assertErrors(1);
    }
}
