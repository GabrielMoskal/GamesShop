package gabriel.games.controller.util;

import gabriel.games.model.api.dto.PlatformDto;
import org.springframework.test.web.servlet.ResultActions;

public class PlatformDtoValidator {

    private JsonValidator jsonValidator;
    private PlatformDto expected;

    public void validate(ResultActions resultActions, PlatformDto expected) {
        setMembers(resultActions, expected);
        validatePlatform();
        jsonValidator.validateJsonLinks(expected.getUri());
    }

    private void setMembers(ResultActions resultActions, PlatformDto expected) {
        this.jsonValidator = new JsonValidator(resultActions);
        this.expected = expected;
    }

    private void validatePlatform() {
        jsonValidator.expect("name", expected.getName());
        jsonValidator.expect("uri", expected.getUri());
    }
}
