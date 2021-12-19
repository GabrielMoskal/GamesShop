package gabriel.games.model.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityValidatorNew {

    private static final Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static <T> void assertErrors(T validated, int numOfErrors) {
        Set<ConstraintViolation<T>> violations = validator.validate(validated);
        assertEquals(numOfErrors, violations.size(), errorMessage(violations));
    }

    private static <T> String errorMessage(Set<ConstraintViolation<T>> violations) {
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

