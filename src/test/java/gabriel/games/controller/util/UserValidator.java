package gabriel.games.controller.util;

import gabriel.games.model.auth.dto.UserDto;
import gabriel.games.model.dto.ErrorDto;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

public class UserValidator extends DtoValidator {

    private UserDto expected;

    public void validate(ResultActions resultActions, String uri, UserDto expected) {
        setMembers(resultActions, expected);
        validateUserDetails();
        validateErrors();
        validateLink(uri);
    }

    private void setMembers(ResultActions resultActions, UserDto expected) {
        super.setMembers(resultActions);
        this.expected = expected;
    }

    private void validateUserDetails() {
        jsonValidator.expect("username", expected.getUsername());
        jsonValidator.expect("password", expected.getPassword());
        jsonValidator.expect("confirmedPassword", expected.getConfirmedPassword());
    }

    private void validateErrors() {
        List<ErrorDto> errorDtos = expected.getErrors();
        for (ErrorDto errorDto : errorDtos) {
            String path = "errors[" + errorDtos.indexOf(errorDto) + "]";
            jsonValidator.expect(path + ".type", errorDto.getType().toString());
            jsonValidator.expect(path + ".name", errorDto.getName());
            jsonValidator.expect(path + ".message", errorDto.getMessage());
        }
    }
}
