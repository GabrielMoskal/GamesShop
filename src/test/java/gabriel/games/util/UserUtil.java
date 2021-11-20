package gabriel.games.util;

import gabriel.games.model.dto.UserDto;
import gabriel.games.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@Profile({"test"})
public class UserUtil {

    private final String username = "username";
    private final String password = "password";

    public static User makeUser(String username, String password) {
        return new User(
                username,
                password,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Bean(name = "user")
    public User user() {
        return makeUser(username, password);
    }

    @Bean(name = "admin")
    public User admin() {
        return new User(
                "adminName",
                password,
                Arrays.asList(
                        new SimpleGrantedAuthority("ROLE_USER"),
                        new SimpleGrantedAuthority("ROLE_ADMIN")
                )
        );
    }

    public static UserDto makeUserDto(String username, String password) {
        return new UserDto(username, password, password);
    }

    @Bean
    public UserDto userDto() {
        return makeUserDto(username, password);
    }
}
