package gabriel.games.controller.auth;

import gabriel.games.dto.UserDto;
import gabriel.games.exception.UserAlreadyExistsException;
import gabriel.games.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public ResponseEntity<EntityModel<UserDto>> register() {
        EntityModel<UserDto> responseBody = addLinks(
                new UserDto("", "", "")
        );

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    private EntityModel<UserDto> addLinks(UserDto userDto) {
        EntityModel<UserDto> entityModel = EntityModel.of(userDto);
        entityModel.add(linkTo(methodOn(RegistrationController.class).register()).withSelfRel());

        return entityModel;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<EntityModel<UserDto>> processRegistration(@RequestBody @Valid UserDto userDto, Errors errors) {
        if (errors.hasErrors()) {
            return handleErrors(userDto, errors);
        }
        return register(userDto, errors);
    }

    private ResponseEntity<EntityModel<UserDto>> handleErrors(UserDto userDto, Errors errors) {
        addErrors(userDto, errors);

        EntityModel<UserDto> responseBody = addLinks(userDto);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    private void addErrors(UserDto userDto, Errors errors) {
        Map<String, Map<String, String >> stringMapErrors = new HashMap<>();

        addFieldErrors(stringMapErrors, errors);
        addObjectErrors(stringMapErrors, errors);

        userDto.setErrors(stringMapErrors);
    }

    private void addFieldErrors(Map<String, Map<String, String>> stringMapErrors, Errors errors) {
        Map<String, String> fieldErrors = new HashMap<>();

        errors.getFieldErrors().forEach(
                fieldError -> fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage())
        );

        stringMapErrors.put("fieldErrors", fieldErrors);
    }

    private void addObjectErrors(Map<String, Map<String, String>> stringMapErrors, Errors errors) {
        Map<String, String> objectErrors = new HashMap<>();

        errors.getGlobalErrors().forEach(
                objectError -> objectErrors.put("user", objectError.getDefaultMessage())
        );

        stringMapErrors.put("objectErrors", objectErrors);
    }


    private ResponseEntity<EntityModel<UserDto>> register(UserDto userDto, Errors errors) {
        try {
            return register(userDto);
        } catch (UserAlreadyExistsException e) {
            return addError(userDto, errors);
        }
    }

    private ResponseEntity<EntityModel<UserDto>> register(UserDto userDto) {
        userService.register(userDto);

        EntityModel<UserDto> responseBody = addLinks(
                new UserDto(userDto.getUsername(), "", "")
        );

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    private ResponseEntity<EntityModel<UserDto>> addError(UserDto userDto, Errors errors) {
        String message = messageSource.getMessage(
                "username.exists",
                null,
                LocaleContextHolder.getLocale()
        );
        errors.rejectValue("username", "", Objects.requireNonNull(message));

        return handleErrors(userDto, errors);
    }
}
