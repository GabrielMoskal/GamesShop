package gabriel.games.model.auth.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordValidatorTest {

    private WordValidator wordValidator;
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        wordValidator = new WordValidator();
        context = Mockito.mock(ConstraintValidatorContext.class);
    }

    @Test
    public void isValid_ValidWordGiven_ShouldReturnTrue() {
        assertTrue(wordValidator.isValid("proper_word", context));
    }

    @Test
    public void isValid_NullGiven_ShouldReturnTrue() {
        assertTrue(wordValidator.isValid(null, context));
    }

    @Test
    public void isValid_WordWithWhitespaceGiven_ShouldReturnFalse() {
        assertFalse(wordValidator.isValid("white_space ", context));
    }

    @Test
    public void isValid_NonWordCharacterGiven_ShouldReturnFalse() {
        assertFalse(wordValidator.isValid("@", context));
    }
}
