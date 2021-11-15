package gabriel.games.controller.api;

import gabriel.games.controller.util.JsonValidator;
import gabriel.games.model.dto.GameDto;
import gabriel.games.service.GameService;
import gabriel.games.service.exception.ObjectNotFoundException;
import gabriel.games.util.GameUtil;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {

    private String uri = "/api/game/";
    private GameDto gameDto;
    private JsonValidator jsonValidator;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    public void description_ExistingResourcePathGiven_ShouldReturnValidJson() throws Exception {
        gameDto = GameUtil.makeValidGameDto();
        shouldReturnValidJson();
    }

    private void shouldReturnValidJson() throws Exception {
        mockFindByName();
        ResultActions resultActions = performGetRequest();
        verifyJson(resultActions);
    }

    private void mockFindByName() {
        when(gameService.findByUri(gameDto.getUri())).thenReturn(gameDto);
    }

    private ResultActions performGetRequest() throws Exception {
        updateUri();
        return mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    private void updateUri() {
        uri += gameDto.getUri();
    }

    private void verifyJson(ResultActions resultActions) throws Exception {
        jsonValidator = new JsonValidator(resultActions);

        jsonValidator.expect("uri", gameDto.getUri());
        jsonValidator.expect("name", gameDto.getName());
        jsonValidator.expect("description", gameDto.getDescription());
        jsonValidator.expect("webpage", gameDto.getWebpage());
        jsonValidator.expect("playerRating", gameDto.getPlayerRating());
        jsonValidator.expect("reviewerRating", gameDto.getReviewerRating());
        jsonValidator.expect("platforms", gameDto.getPlatforms());
        jsonValidator.expect("releaseDate", gameDto.getReleaseDate().toString());
        jsonValidator.expect("producer", gameDto.getProducer());
        jsonValidator.expect("publisher", gameDto.getPublisher());
    }

    @Test
    public void description_ExistingDifferentResourcePathGiven_ShouldReturnValidJson() throws Exception {
        gameDto = GameUtil.makeDifferentValidGameDto();
        shouldReturnValidJson();
    }

    @Test
    public void description_ExistingResourcePathGiven_ShouldReturnValidLink() throws Exception {
        gameDto = GameUtil.makeValidGameDto();
        shouldReturnValidLink();
    }

    private void shouldReturnValidLink() throws Exception {
        mockFindByName();
        ResultActions resultActions = performGetRequest();
        jsonValidator = new JsonValidator(resultActions);
        jsonValidator.verifyJsonLinks(uri);
    }

    @Test
    public void description_ExistingDifferentResourcePathGiven_ShouldReturnValidLink() throws Exception {
        gameDto = GameUtil.makeDifferentValidGameDto();
        shouldReturnValidLink();
    }

    @Test
    public void description_NonExistingResourcePathGiven_ShouldReturn404() throws Exception {
        gameDto = GameUtil.makeValidGameDto();
        when(gameService.findByUri(gameDto.getUri())).thenThrow(new ObjectNotFoundException("msg"));
        performGetRequest().andExpect(status().isNotFound());
    }

    @Test
    public void description_ExistingResourcePathGiven_ShouldReturn200() throws Exception {
        gameDto = GameUtil.makeValidGameDto();
        mockFindByName();
        performGetRequest().andExpect(status().isOk());
    }
}
