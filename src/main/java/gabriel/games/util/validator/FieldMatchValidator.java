package gabriel.games.util.validator;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// made using suggestion from stackoverflow.com/questions/1972933/cross-field-validation-with-hibernate-validator-jsr-303
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch fieldMatch) {
        firstFieldName = fieldMatch.first();
        secondFieldName = fieldMatch.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Object firstObject = BeanUtils.getProperty(value, firstFieldName);
            Object secondObject = BeanUtils.getProperty(value, secondFieldName);

            return (firstObject == null && secondObject == null) ||
                    (firstObject != null && firstObject.equals(secondObject));
        } catch (Exception ignore) {
        }
        return true;
    }
}
