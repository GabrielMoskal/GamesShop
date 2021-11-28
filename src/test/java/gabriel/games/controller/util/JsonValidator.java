package gabriel.games.controller.util;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringEndsWith.endsWith;

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

    public void verifyJsonLinks(String path) {
        MockMvcResultMatchers.jsonPath("_links.self.href", endsWith(path));
    }
}
