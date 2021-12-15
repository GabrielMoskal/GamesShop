package gabriel.games.controller.auth;

import gabriel.games.model.auth.dto.UserDto;
import gabriel.games.controller.auth.exception.UserAlreadyExistsException;
import gabriel.games.service.ErrorService;
import gabriel.games.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
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
@AllArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final ErrorService errorService;
    private final MessageSource messageSource;

    @GetMapping
    public ResponseEntity<EntityModel<UserDto>> register() {
        UserDto response = new UserDto("", "", "");
        EntityModel<UserDto> responseBody = makeResponse(response);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    private EntityModel<UserDto> makeResponse(UserDto userDto) {
        EntityModel<UserDto> responseBody = EntityModel.of(userDto);
        responseBody.add(makeLink());
        return responseBody;
    }

    private Link makeLink() {
        return linkTo(methodOn(RegistrationController.class).register()).withSelfRel();
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<EntityModel<UserDto>> processRegistration(@RequestBody @Valid UserDto userDto, Errors errors) {
        if (errors.hasErrors()) {
            return handleErrors(userDto, errors);
        }
        return register(userDto, errors);
    }

    private ResponseEntity<EntityModel<UserDto>> handleErrors(UserDto userDto, Errors errors) {
        userDto.setErrors(errorService.toErrorDtos(errors));
        EntityModel<UserDto> responseBody = makeResponse(userDto);
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
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
        UserDto response = new UserDto(userDto.getUsername(), "", "");
        EntityModel<UserDto> responseBody = makeResponse(response);
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
