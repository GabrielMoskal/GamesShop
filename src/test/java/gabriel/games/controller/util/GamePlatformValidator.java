package gabriel.games.controller.util;

import gabriel.games.model.api.dto.GamePlatformDto;
import org.springframework.test.web.servlet.ResultActions;

public class GamePlatformValidator {

    public static void validate(ResultActions resultActions, GamePlatformDto expected, String link) {
        JsonValidator jsonValidator = new JsonValidator(resultActions);

        jsonValidator.expect("gameName", expected.getGameName());
        jsonValidator.expect("platformName", expected.getPlatformName());
        jsonValidator.expect("releaseDate", expected.getReleaseDate().toString());
        jsonValidator.verifyJsonLinks(link);
    }
}
