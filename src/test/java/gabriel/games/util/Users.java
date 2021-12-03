package gabriel.games.util;

import gabriel.games.model.auth.dto.UserDto;
import gabriel.games.model.auth.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class Users {

    public static User makeUser(String username, String password) {
        return new User(
                username,
                password,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    public static UserDto makeUserDto(String username, String password) {
        return new UserDto(username, password, password);
    }

}
