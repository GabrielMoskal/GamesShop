package gabriel.games.model.util;

import javax.validation.*;
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
        assertEquals(numOfErrors, violations.size(), errorMessage(violations));
    }

    private String errorMessage(Set<ConstraintViolation<T>> violations) {
        StringBuffer msg = new StringBuffer();
        violations.forEach((e) -> {
            msg.append("\n");
            msg.append(e.getPropertyPath());
            msg.append(": ");
            msg.append(e.getMessage());
        });
        return msg.toString();
    }
}
