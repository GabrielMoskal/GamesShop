package gabriel.games.dto.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordValidator implements ConstraintValidator<Word, String> {

    private final Pattern pattern;

    public WordValidator() {
        pattern = Pattern.compile("\\w*");
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        Matcher matcher = pattern.matcher(value);

        return matcher.matches();
    }
}
