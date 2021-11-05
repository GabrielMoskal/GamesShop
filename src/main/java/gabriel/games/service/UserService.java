package gabriel.games.service;

import gabriel.games.model.dto.UserDto;
import gabriel.games.controller.auth.exception.UserAlreadyExistsException;
import gabriel.games.model.dto.mapper.UserDtoMapper;
import gabriel.games.model.User;
import gabriel.games.repository.UserRepository;
import gabriel.games.service.exception.InvalidObjectValuesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDtoMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserDtoMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> optionalUserDetails = userRepository.findByUsername(username);

        return optionalUserDetails.orElseThrow(
                () -> new UsernameNotFoundException("UserDetails not found in userRepository::findByUsername")
        );
    }

    public UserDto register(UserDto userDto) {
        User user = toValidUser(userDto);
        User saved = save(user);

        return userMapper.toUserDto(saved);
    }

    private User toValidUser(UserDto userDto) {
        @Valid User user = userMapper.toUser(userDto);

        validate(user);
        return user;
    }

    private void validate(User user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!isValid(violations)) {
            throw new InvalidObjectValuesException("User contains invalid values.");
        }
    }

    private boolean isValid(Set<ConstraintViolation<User>> violations) {
        return violations.isEmpty();
    }

    private User save(User user) {
        try {
            return userRepository.save(user);
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyExistsException("User already exists.");
        }
    }
}
