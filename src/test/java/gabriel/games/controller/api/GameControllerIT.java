package gabriel.games.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import gabriel.games.controller.util.GameValidator;
import gabriel.games.model.api.Game;
import gabriel.games.model.api.dto.*;
import gabriel.games.model.api.mapper.GameMapper;
import gabriel.games.service.GameService;
import gabriel.games.service.exception.ObjectNotFoundException;
import gabriel.games.model.util.Models;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
public class GameControllerIT {

    private final String PATH = "/api/game/";
    private GameDto expected;
    private GameValidator gameValidator;
    @Autowired private MockMvc mockMvc;
    @MockBean private GameService gameService;
    @MockBean private GameMapper gameMapper;

    @BeforeEach
    public void setUp() {
        this.expected = Models.makeGameDto("Multiple words value");
        this.gameValidator = new GameValidator();
        mockGameMapper();
    }

    private void mockGameMapper() {
        when(gameMapper.toGameDto(any())).thenReturn(expected);
    }

    @Test
    public void description_ExistingResourcePathGiven_ShouldReturnValidJson() throws Exception {
        shouldReturnValidJson();
    }

    private void shouldReturnValidJson() throws Exception {
        ResultActions resultActions = performGetRequest();
        gameValidator.validate(resultActions, expected);
    }

    private ResultActions performGetRequest() throws Exception {
        String urlTemplate = PATH + expected.getUri();
        return mockMvc.perform(get(urlTemplate).contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void description_ExistingDifferentResourcePathGiven_ShouldReturnValidJson() throws Exception {
        expected = Models.makeGameDto("different values");
        mockGameMapper();
        shouldReturnValidJson();
    }

    @Test
    public void description_NonExistingResourcePathGiven_ShouldReturn404() throws Exception {
        when(gameService.findByUri(expected.getUri())).thenThrow(new ObjectNotFoundException("msg"));
        performGetRequest().andExpect(status().isNotFound());
    }

    @Test
    public void description_ExistingResourcePathGiven_ShouldReturn200() throws Exception {
        performGetRequest().andExpect(status().isOk());
    }

    @Test
    public void postGame_GameDtoGiven_ShouldReturn201() throws Exception {
        performPostRequest().andExpect(status().isCreated());
    }

    private ResultActions performPostRequest() throws Exception {
        String gameDtoAsJson = preparePostRequest();

        return mockMvc.perform(
                post(PATH)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameDtoAsJson)
        );
    }

    private String preparePostRequest() throws Exception {
        GameDto content = Models.makeGameDto(expected.getName(), "");
        return writeGameDtoAsJson(content);
    }

    private String writeGameDtoAsJson(GameDto gameDto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(gameDto);
    }

    @Test
    public void postGame_GameDtoGiven_ShouldReturnValidJson() throws Exception {
        ResultActions resultActions = performPostRequest();
        gameValidator.validate(resultActions, expected);
    }

    @Test
    public void postGame_GameDtoGiven_VerifyMethodCalls() throws Exception {
        performPostRequest();
        verifyPostMethodCalls();
    }

    private void verifyPostMethodCalls() {
        Game game = verify(gameMapper).toGame(any());
        verify(gameService).save(game);
    }
}
