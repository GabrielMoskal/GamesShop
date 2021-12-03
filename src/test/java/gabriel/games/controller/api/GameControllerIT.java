package gabriel.games.controller.api;

import gabriel.games.controller.util.JsonValidator;
import gabriel.games.model.api.dto.GameDto;
import gabriel.games.service.GameService;
import gabriel.games.service.exception.ObjectNotFoundException;
import gabriel.games.util.Models;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameController.class)
@AutoConfigureMockMvc
public class GameControllerIT {

    private String uri = "/api/game/";
    private GameDto gameDto;
    private JsonValidator jsonValidator;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @BeforeEach
    public void setUp() {
        this.gameDto = Models.makeGameDto("Multiple words value");
    }

    @Test
    public void description_ExistingResourcePathGiven_ShouldReturnValidJson() throws Exception {
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
        gameDto = Models.makeGameDto("different values");
        shouldReturnValidJson();
    }

    @Test
    public void description_ExistingResourcePathGiven_ShouldReturnValidLink() throws Exception {
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
        gameDto = Models.makeGameDto("different values");
        shouldReturnValidLink();
    }

    @Test
    public void description_NonExistingResourcePathGiven_ShouldReturn404() throws Exception {
        when(gameService.findByUri(gameDto.getUri())).thenThrow(new ObjectNotFoundException("msg"));
        performGetRequest().andExpect(status().isNotFound());
    }

    @Test
    public void description_ExistingResourcePathGiven_ShouldReturn200() throws Exception {
        mockFindByName();
        performGetRequest().andExpect(status().isOk());
    }
}
