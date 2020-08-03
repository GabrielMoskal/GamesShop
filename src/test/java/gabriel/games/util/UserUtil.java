package gabriel.games.util;

import gabriel.games.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@Configuration
@Profile({"test", "default"})
public class UserUtil {
    public static User makeUser(String username, String password) {
        return new User(
                username,
                password,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Bean
    public User user() {
        return makeUser("username", "password");
    }
}
