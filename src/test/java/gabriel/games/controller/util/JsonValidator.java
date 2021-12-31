package gabriel.games.controller.util;

import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringEndsWith.endsWith;

public class JsonValidator {

    private final ResultActions resultActions;

    public JsonValidator(ResultActions resultActions) {
        this.resultActions = resultActions;
    }

    public <T> void expect(String expression, T matcher) {
        ResultMatcher resultMatcher = MockMvcResultMatchers.jsonPath(expression, is(matcher));
        tryToExpect(resultMatcher);
    }

    private void tryToExpect(ResultMatcher resultMatcher) {
        try {
            resultActions.andExpect(resultMatcher);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validateJsonLinks(String path) {
        ResultMatcher resultMatcher = MockMvcResultMatchers.jsonPath("_links.self.href", endsWith(path));
        tryToExpect(resultMatcher);
    }
}
