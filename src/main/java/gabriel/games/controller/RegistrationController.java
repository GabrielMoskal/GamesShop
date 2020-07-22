package gabriel.games.controller;

import gabriel.games.dto.UserDto;
import gabriel.games.exception.UserAlreadyExistsException;
import gabriel.games.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(@ModelAttribute @Valid UserDto userDto, Errors errors) {
        if (errors.hasErrors()) {
            // todo change response error code to 4xx, later when i add better return entities than Strings
            return "registration";
        }
        return register(userDto, errors);
    }

    private String register(UserDto userDto, Errors errors) {
        try {
            return register(userDto);
        } catch (UserAlreadyExistsException e) {
            return addErrorAndReturnToRegistration(errors);
        }
    }

    private String register(UserDto userDto) {
        userService.register(userDto);

        return "redirect:/login";
    }

    private String addErrorAndReturnToRegistration(Errors errors) {
        errors.rejectValue("username", "", "User with given username already exists.");

        return "registration";
    }
}
