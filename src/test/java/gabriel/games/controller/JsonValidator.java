package gabriel.games.controller;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.core.Is.is;

public class JsonValidator {

    private final ResultActions resultActions;

    public JsonValidator(ResultActions resultActions) {
        this.resultActions = resultActions;
    }

    private <T> ResultMatcher jsonPath(String expression, T matcher) {
        return MockMvcResultMatchers.jsonPath(expression, is(matcher));
    }

    public <T> void expect(String expression, T matcher) throws Exception {
        resultActions.andExpect(jsonPath(expression, matcher));
    }
}