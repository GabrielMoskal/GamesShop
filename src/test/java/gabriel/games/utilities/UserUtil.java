package gabriel.games.utilities;

import gabriel.games.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class UserUtil {
    public static User makeUser(String username, String password) {
        return new User(
                username,
                password,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
