package gabriel.games.service;

import gabriel.games.dto.UserDto;
import gabriel.games.exception.InvalidObjectValuesException;
import gabriel.games.exception.UserAlreadyExistsException;
import gabriel.games.mapper.UserMapper;
import gabriel.games.model.User;
import gabriel.games.repository.UserRepository;
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
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
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

    public void register(UserDto userDto) {
        @Valid User user = userMapper.toUser(userDto);

        validate(user);
        save(user);
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

    private void save(User user) {
        try {
            userRepository.save(user);
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyExistsException("User already exists.");
        }
    }
}
