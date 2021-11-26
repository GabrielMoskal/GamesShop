package gabriel.games.model.dto.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityValidator<T> {

    private final T validated;
    private final Validator validator;

    public EntityValidator(final T validated) {
        this.validated = validated;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public void assertErrors(int numOfErrors) {
        Set<ConstraintViolation<T>> violations = validator.validate(validated);

        assertEquals(numOfErrors, violations.size());
    }
}
