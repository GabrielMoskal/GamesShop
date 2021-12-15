package gabriel.games.controller.util;

import org.springframework.test.web.servlet.ResultActions;

public class DtoValidator {

    JsonValidator jsonValidator;
    private String uri;

    void setMembers(ResultActions resultActions, String uri) {
        this.jsonValidator = new JsonValidator(resultActions);
        this.uri = uri;
    }

    void validateLink() {
        jsonValidator.verifyJsonLinks(uri);
    }
}
