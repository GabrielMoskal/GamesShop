package gabriel.games.util.validator;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FieldMatchValidatorTest {

    FieldMatch fieldMatch;
    FieldMatchValidator fieldMatchValidator;

    @Before
    public void setUp() {
        mockFieldMatch();
        makeFieldMatchValidator();
    }

    private void mockFieldMatch() {
        fieldMatch = mock(FieldMatch.class);
        when(fieldMatch.first()).thenReturn("first");
        when(fieldMatch.second()).thenReturn("second");
    }

    private void makeFieldMatchValidator() {
        fieldMatchValidator = new FieldMatchValidator();
        fieldMatchValidator.initialize(fieldMatch);
    }

    @Test
    public void isValid_EqualStringsGiven_ReturnsTrue() {
        String toCompare = "theSameString";
        Map<String, String> stringMap = makeStringMap(toCompare, toCompare);

        assertTrue(isValid(stringMap));
    }

    private Map<String, String> makeStringMap(String first, String second) {
        Map<String, String> stringMap = new HashMap<String, String>() {};

        stringMap.put(fieldMatch.first(), first);
        stringMap.put(fieldMatch.second(), second);

        return stringMap;
    }

    private boolean isValid(Map<String, String> stringMap) {
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        return fieldMatchValidator.isValid(stringMap, context);
    }

    @Test
    public void isValid_DifferentStringsGiven_ShouldReturnFalse() {
        String string1 = "stringOne";
        String string2 = "stringTwo";
        Map<String, String> stringMap = makeStringMap(string1, string2);

        assertFalse(isValid(stringMap));
    }

    @Test
    public void isValid_NullsGiven_ShouldReturnTrue() {
        Map<String, String> stringMap = makeStringMap(null, null);

        assertTrue(isValid(stringMap));
    }

    @Test
    public void isValid_FirstNullGiven_ShouldReturnFalse() {
        String toCompare = "string";
        Map<String, String> stringMap = makeStringMap(null, toCompare);

        assertFalse(isValid(stringMap));
    }

    @Test
    public void isValid_SecondNullGiven_ShouldReturnFalse() {
        String toCompare = "string";
        Map<String, String> stringMap = makeStringMap(toCompare, null);

        assertFalse(isValid(stringMap));
    }
}
