package gabriel.games.mapper;

import gabriel.games.dto.UserDto;
import gabriel.games.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class UserMapper {

    public User toUserModel(UserDto userDto) {
        return new User(
                userDto.getUsername(),
                userDto.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
