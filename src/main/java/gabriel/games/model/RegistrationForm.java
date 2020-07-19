package gabriel.games.model;

import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@Data
public class RegistrationForm {

    private final String username;
    private final String password;

    public User toUser() {
        return new User(username, password, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
