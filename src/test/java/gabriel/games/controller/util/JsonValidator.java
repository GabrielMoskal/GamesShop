package gabriel.games.controller.util;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    public void verifyJsonLinks(String path) throws Exception {
        final String expectedLink = makeExpectedLink(path);

        expect("_links.self.href", expectedLink);
    }

    private String makeExpectedLink(String path) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + path;
    }
}
