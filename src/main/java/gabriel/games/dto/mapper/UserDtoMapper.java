package gabriel.games.dto.mapper;

import gabriel.games.dto.UserDto;
import gabriel.games.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserDtoMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDtoMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toUser(UserDto userDto) {
        return new User(
                userDto.getUsername(),
                passwordEncoder.encode(userDto.getPassword()),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public UserDto toUserDto(User user) {
        return new UserDto(
                user.getUsername(),
                user.getPassword(),
                user.getPassword()
        );
    }
}
