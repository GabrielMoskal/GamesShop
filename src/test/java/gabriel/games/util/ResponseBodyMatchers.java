package gabriel.games.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResponseBodyMatchers {

    private final ObjectMapper objectMapper;

    private ResponseBodyMatchers() {
        this.objectMapper = new ObjectMapper();
    }

    public <T> ResultMatcher containsObjectAsJson(String jsonObjectName, Object expectedObject, Class<T> targetClass) {
        return mvcResult -> {
            JsonNode jsonNode = makeJsonNode(mvcResult);

            T actualObject = objectMapper.readValue(jsonNode.get(jsonObjectName).toString(), targetClass);
            assertEquals(actualObject, expectedObject);
        };
    }

    private JsonNode makeJsonNode(MvcResult mvcResult) throws Exception {
        String json = mvcResult.getResponse().getContentAsString();
        return objectMapper.readTree(json);
    }

    public ResultMatcher containsErrorsAsJson() {
        return mvcResult -> {
            JsonNode jsonNode = makeJsonNode(mvcResult);

            assertTrue(jsonNode.has("errors"));
        };
    }

    public ResultMatcher containErrorAsJson(String message, List<String> path) {
        return mvcResult -> {
            String errorMessage = searchForError(mvcResult, path);

            assertEquals(errorMessage, message);
        };
    }

    private String searchForError(MvcResult resultMatcher, List<String> path) throws Exception {
        JsonNode jsonNode = makeJsonNode(resultMatcher);

        for (String node : path) {
            jsonNode = jsonNode.get(node);
        }

        return jsonNode.asText();
    }

    public static ResponseBodyMatchers responseBody() {
        return new ResponseBodyMatchers();
    }
}
