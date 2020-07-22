package gabriel.games.model;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.*;

import static gabriel.games.util.UserUtil.makeUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserTest {

    private User user;

    private Validator validator;
    private String correctUsername;
    private String correctPassword;

    @Before
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        correctUsername = "correctUsername";
        correctPassword = "correctPassword";
        user = makeUser(correctUsername, correctPassword);
    }

    @Test
    public void validUserHasNoErrors() {
        User user = makeUser(correctUsername, correctPassword);

        assertValidationErrors(user, 0);
    }

    private void assertValidationErrors(User user, int numOfErrors) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(numOfErrors, violations.size());
    }

    @Test
    public void usernameShouldBeAtLeast5CharLong() {
        User user = makeUser("four", correctPassword);

        assertValidationErrors(user, 1);
    }

    @Test
    public void usernameShouldBeMax20CharsLong() {
        String username = makeGenericWord(21);
        User user = makeUser(username, correctPassword);

        assertValidationErrors(user, 1);
    }

    private String makeGenericWord(int length) {
        return String.join("", Collections.nCopies(length, "a"));
    }

    @Test
    public void usernameShouldHaveNoWhitespaces() {
        User user = makeUser("white spaces", correctPassword);

        assertValidationErrors(user, 1);
    }

    @Test
    public void usernameShouldNotBeNull() {
        User user = makeUser(null, correctPassword);

        assertValidationErrors(user, 1);
    }

    @Test
    public void passwordShouldNotBeNull() {
        User user = makeUser(correctUsername, null);

        assertValidationErrors(user, 1);
    }

    @Test
    public void authoritiesMustNotBeNull() {
        User user = new User(correctUsername, correctPassword, null);

        assertValidationErrors(user, 1);
    }

    @Test
    public void authoritiesMustContainAtLeast1Element() {
        User user = new User(correctUsername, correctPassword, new ArrayList<>());

        assertValidationErrors(user, 1);
    }

    @Test
    public void authoritiesValuesMustNotBeNull() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(null);

        User user = new User(correctUsername, correctPassword, authorities);

        assertValidationErrors(user, 1);
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
}
