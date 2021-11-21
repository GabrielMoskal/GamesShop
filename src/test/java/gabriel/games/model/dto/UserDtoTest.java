package gabriel.games.model.dto;

import gabriel.games.model.dto.util.DtoValidator;
import gabriel.games.model.dto.util.GenericWord;
import gabriel.games.model.dto.util.ReflectionSetter;
import gabriel.games.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDtoTest {

    private ReflectionSetter<UserDto> setter;
    private DtoValidator<UserDto> validator;
    private GenericWord genericWord;

    @BeforeEach
    public void setUp() {
        UserDto userDto = UserUtil.makeUserDto("correctUsername", "correctPassword");
        this.setter = new ReflectionSetter<>(userDto);
        this.validator = new DtoValidator<>(userDto);
        this.genericWord = new GenericWord();
    }

    @Test
    public void validUserDtoHasNoErrors() {
        validator.assertErrors(0);
    }

    @Test
    public void usernameShouldBeAtLeast5CharLong() {
        setter.setValue("username", genericWord.make(4));
        validator.assertErrors(1);
    }

    @Test
    public void usernameShouldBeMax20CharsLong() {
        setter.setValue("username", genericWord.make(21));
        validator.assertErrors(1);
    }

    @Test
    public void usernameShouldHaveNoWhitespaces() {
        setter.setValue("username", "white spaces");
        validator.assertErrors(1);
    }

    @Test
    public void usernameShouldNotBeNull() {
        setter.setValue("username", null);
        validator.assertErrors(1);
    }

    @Test
    public void passwordShouldBeAtLeast7CharLong() {
        setter.setValue("password", genericWord.make(6));
        validator.assertErrors(2);
    }

    @Test
    public void passwordShouldBeMax20CharsLong() {
        setter.setValue("password", genericWord.make(21));
        validator.assertErrors(2);
    }

    @Test
    public void passwordShouldHaveNoWhitespaces() {
        setter.setValue("password", "white spaces");
        validator.assertErrors(2);
    }

    @Test
    public void passwordShouldNotBeNull() {
        setter.setValue("password", null);
        validator.assertErrors(2);
    }

    @Test
    public void confirmedPasswordShouldBeAtLeast7CharsLong() {
        setter.setValue("confirmedPassword", genericWord.make(6));
        validator.assertErrors(2);
    }

    @Test
    public void confirmedPasswordShouldBeMax20CharsLong() {
        setter.setValue("confirmedPassword", genericWord.make(21));
        validator.assertErrors(2);
    }

    @Test
    public void confirmedPasswordShouldHaveNoWhitespaces() {
        setter.setValue("confirmedPassword", "white spaces");
        validator.assertErrors(2);
    }

    @Test
    public void confirmedPasswordShouldNotBeNull() {
        setter.setValue("confirmedPassword", null);
        validator.assertErrors(2);
    }

    @Test
    public void passwordAndConfirmedPasswordShouldMatch() {
        validator.assertErrors(0);
    }

    @Test
    public void passwordAndConfirmedPasswordShouldNotMatch() {
        setter.setValue("password", "password");
        setter.setValue("confirmedPassword", "different");
        validator.assertErrors(1);
    }
}
