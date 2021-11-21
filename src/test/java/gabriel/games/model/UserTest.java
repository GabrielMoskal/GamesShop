package gabriel.games.model;

import gabriel.games.service.exception.InvalidObjectValuesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.*;

import static gabriel.games.util.UserUtil.makeUser;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    private Validator validator;
    private String correctUsername;
    private String correctPassword;

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        correctUsername = "correctUsername";
        correctPassword = "correctPassword";
        user = makeUser(correctUsername, correctPassword);
    }

    @Test
    public void validUserHasNoErrors() {
        user = makeUser(correctUsername, correctPassword);
        assertValidationErrors(0);
    }

    private void assertValidationErrors(int numOfErrors) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(numOfErrors, violations.size());
    }

    @Test
    public void usernameShouldBeAtLeast5CharLong() {
        user = makeUser("four", correctPassword);
        assertValidationErrors(1);
    }

    @Test
    public void usernameShouldBeMax20CharsLong() {
        String username = makeGenericWord(21);
        user = makeUser(username, correctPassword);
        assertValidationErrors(1);
    }

    private String makeGenericWord(int length) {
        return String.join("", Collections.nCopies(length, "a"));
    }

    @Test
    public void usernameShouldHaveNoWhitespaces() {
        user = makeUser("white spaces", correctPassword);
        assertValidationErrors(1);
    }

    @Test
    public void usernameShouldNotBeNull() {
        user = makeUser(null, correctPassword);
        assertValidationErrors(1);
    }

    @Test
    public void passwordShouldNotBeNull() {
        user = makeUser(correctUsername, null);
        assertValidationErrors(1);
    }

    @Test
    public void authoritiesMustNotBeNull() {
        user = new User(correctUsername, correctPassword, null);
        assertValidationErrors(1);
    }

    @Test
    public void authoritiesMustContainAtLeast1Element() {
        user = new User(correctUsername, correctPassword, new ArrayList<>());
        assertValidationErrors(1);
    }

    @Test
    public void authoritiesValuesMustNotBeNull() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(null);
        user = new User(correctUsername, correctPassword, authorities);
        assertValidationErrors(1);
    }

    @Test
    public void isAccountNonExpired_ShouldReturnTrue() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    public void isAccountNonLocked_ShouldReturnTrue() {
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    public void isCredentialsNonExpired_ShouldReturnTrue() {
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    public void isEnabled_ShouldReturnTrue() {
        assertTrue(user.isEnabled());
    }

    @Test
    public void validate_ValidUserGiven_ShouldNotThrowException() {
        assertDoesNotThrow(() -> user.validate());
    }

    @Test
    public void validate_InvalidUserGiven_ShouldThrowInvalidObjectValuesException() {
        user = makeUser("a", "password");
        assertThrows(InvalidObjectValuesException.class, () -> user.validate());
    }
}
