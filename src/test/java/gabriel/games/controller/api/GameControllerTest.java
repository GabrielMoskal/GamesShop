package gabriel.games.controller.api;

import gabriel.games.controller.JsonValidator;
import gabriel.games.model.dto.GameDto;
import gabriel.games.service.GameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {

    private String uri = "/cms/game/";

    private GameDto gameDto;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    public void description_ExistingResourcePathGiven_ShouldReturnValidJson() throws Exception {
        gameDto = GameDto.builder()
                .name("test name")
                .description("Test short description")
                .webpage("https://test-link.com")
                .playerRating("7.0")
                .reviewerRating("6.5")
                .platforms(Arrays.asList("PC", "XBOX 360"))
                .releaseDate("2000-01-01")
                .producer("Test Producer")
                .publisher("Test Publisher")
                .build();

        updateUri();
        mockFindByName();
        ResultActions resultActions = performGetRequest();
        verifyJson(resultActions);
    }

    private void updateUri() {
        uri += gameDto.getName();
    }

    private void mockFindByName() {
        when(gameService.findByName(gameDto.getName())).thenReturn(gameDto);
    }

    private ResultActions performGetRequest() throws Exception {
        return mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    private void verifyJson(ResultActions resultActions) throws Exception {
        JsonValidator jsonValidator = new JsonValidator(resultActions);

        jsonValidator.expect("description", gameDto.getDescription());
        jsonValidator.expect("webpage", gameDto.getWebpage());
        jsonValidator.expect("playerRating", gameDto.getPlayerRating());
        jsonValidator.expect("reviewerRating", gameDto.getReviewerRating());
        jsonValidator.expect("platforms", gameDto.getPlatforms());
        jsonValidator.expect("releaseDate", gameDto.getReleaseDate());
        jsonValidator.expect("producer", gameDto.getProducer());
        jsonValidator.expect("publisher", gameDto.getPublisher());
    }

    @Test
    public void description_ExistingDifferentResourcePathGiven_ShouldReturnValidJson() throws Exception {
        gameDto = GameDto.builder()
                .name("different name")
                .description("Test different description")
                .webpage("https://test-different-link.com")
                .playerRating("1.0")
                .reviewerRating("1.5")
                .platforms(Collections.singletonList("Playstation 5"))
                .releaseDate("2011-11-11")
                .producer("Test different Producer")
                .publisher("Test different Publisher")
                .build();

        updateUri();
        mockFindByName();
        ResultActions resultActions = performGetRequest();
        verifyJson(resultActions);
    }
}
