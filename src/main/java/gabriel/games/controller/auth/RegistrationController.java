package gabriel.games.controller.auth;

import gabriel.games.dto.UserDto;
import gabriel.games.exception.UserAlreadyExistsException;
import gabriel.games.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(path = "/register", produces = "application/json")
@CrossOrigin(origins = "*")
public class RegistrationController {

    private final UserService userService;
    private final MessageSource messageSource;

    @Autowired
    public RegistrationController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public ResponseEntity<Object> register() {
        Map<String, Object> body = new HashMap<>();
        UserDto userDto = new UserDto("", "", "");

        body.put("user", userDto);

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Object> processRegistration(@RequestBody @Valid UserDto userDto, Errors errors) {
        if (errors.hasErrors()) {
            return handleErrors(userDto, errors);
        }
        return register(userDto, errors);
    }

    private ResponseEntity<Object> handleErrors(UserDto userDto, Errors errors) {
        Map<String, Object> body = new HashMap<>();

        addErrors(body, errors);
        addUser(body, userDto);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    private void addErrors(Map<String, Object> body, Errors errors) {
        Map<String, Object> stringMapErrors = new HashMap<>();

        addFieldErrors(stringMapErrors, errors);
        addObjectErrors(stringMapErrors, errors);

        body.put("errors", stringMapErrors);
    }

    private void addFieldErrors(Map<String, Object> stringMapErrors, Errors errors) {
        Map<String, String> fieldErrors = new HashMap<>();

        errors.getFieldErrors().forEach(
                fieldError -> fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage())
        );

        stringMapErrors.put("fieldErrors", fieldErrors);
    }

    private void addObjectErrors(Map<String, Object> stringMapErrors, Errors errors) {
        Map<String, String> objectErrors = new HashMap<>();

        errors.getGlobalErrors().forEach(
                objectError -> objectErrors.put("user", objectError.getDefaultMessage())
        );

        stringMapErrors.put("objectErrors", objectErrors);
    }

    private void addUser(Map<String, Object> body, UserDto userDto) {
        body.put("user", userDto);
    }

    private ResponseEntity<Object> register(UserDto userDto, Errors errors) {
        try {
            return register(userDto);
        } catch (UserAlreadyExistsException e) {
            return addError(userDto, errors);
        }
    }

    private ResponseEntity<Object> register(UserDto userDto) {
        userService.register(userDto);

        Map<String, Object> body = new HashMap<>();
        addUser(body, new UserDto(userDto.getUsername(), "", ""));

        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    private ResponseEntity<Object> addError(UserDto userDto, Errors errors) {

        String message = messageSource.getMessage(
                "username.exists",
                null,
                LocaleContextHolder.getLocale()
        );

        errors.rejectValue("username", "", Objects.requireNonNull(message));

        return handleErrors(userDto, errors);
    }
}
