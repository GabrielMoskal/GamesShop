package gabriel.games.model;

import gabriel.games.dto.validator.Word;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

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
