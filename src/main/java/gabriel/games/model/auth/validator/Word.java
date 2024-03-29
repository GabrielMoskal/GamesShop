package gabriel.games.model.auth.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = WordValidator.class)
@Documented
public @interface Word {

    String message() default "Should be word ([a-zA-Z_0-9]*)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
