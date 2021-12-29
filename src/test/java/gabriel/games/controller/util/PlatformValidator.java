package gabriel.games.controller.util;

import gabriel.games.model.api.dto.PlatformDto;
import org.springframework.test.web.servlet.ResultActions;

public class PlatformValidator extends DtoValidator {

    private PlatformDto expected;

    public void validate(ResultActions resultActions, PlatformDto expected) {
        setMembers(resultActions, expected);
        validatePlatform();
        validateLink(expected.getUri());
    }

    private void setMembers(ResultActions resultActions, PlatformDto expected) {
        super.setMembers(resultActions);
        this.expected = expected;
    }

    private void validatePlatform() {
        jsonValidator.expect("name", expected.getName());
        jsonValidator.expect("uri", expected.getUri());
    }
}
