package gabriel.games.model.auth;

import gabriel.games.model.auth.validator.Word;
import gabriel.games.service.exception.InvalidObjectValuesException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Data
public class User implements UserDetails {

    @NotNull
    @Word
    @Size(min = 5, max = 20)
    private final String username;

    @EqualsAndHashCode.Exclude
    @NotNull
    private final String password;

    @NotEmpty
    private final Collection<@NotNull ? extends GrantedAuthority> authorities;

    public void validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(this);

        if (!violations.isEmpty()) {
            throw new InvalidObjectValuesException("User contains invalid values.");
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
