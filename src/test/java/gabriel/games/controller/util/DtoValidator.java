package gabriel.games.controller.util;

import org.springframework.test.web.servlet.ResultActions;

public class DtoValidator {

    JsonValidator jsonValidator;

    void setMembers(ResultActions resultActions) {
        this.jsonValidator = new JsonValidator(resultActions);
    }

    void validateLink(String uri) {
        jsonValidator.verifyJsonLinks(uri);
    }
}
