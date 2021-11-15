package gabriel.games.model.dto.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class DtoValidator<T> {

    private final T validated;
    private final Validator validator;

    public DtoValidator(final T validated) {
        this.validated = validated;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public void assertErrors(int numOfErrors) {
        Set<ConstraintViolation<T>> violations = validator.validate(validated);

        assertEquals(numOfErrors, violations.size());
    }

    public String makeGenericWord(int length) {
        return String.join("", Collections.nCopies(length, "a"));
    }
}
