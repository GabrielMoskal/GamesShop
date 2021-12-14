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

    private String uri;
    private GameDto gameDto;
    private GameValidator gameValidator;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private GameMapper gameMapper;

    @BeforeEach
    public void setUp() {
        this.uri = "/api/game/";
        this.gameDto = Models.makeGameDto("Multiple words value");
        this.gameValidator = new GameValidator();
        mockGameMapper();
    }

    @Test
    public void description_ExistingResourcePathGiven_ShouldReturnValidJson() throws Exception {
        shouldReturnValidJson();
    }

    private void shouldReturnValidJson() throws Exception {
        ResultActions resultActions = performGetRequest();
        gameValidator.validate(resultActions, gameDto, uri);
    }

    private ResultActions performGetRequest() throws Exception {
        updateUri();
        mockGameMapper();
        return mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    private void mockGameMapper() {
        when(gameMapper.toGameDto(any())).thenReturn(gameDto);
    }

    private void updateUri() {
        uri += gameDto.getUri();
    }

    @Test
    public void description_ExistingDifferentResourcePathGiven_ShouldReturnValidJson() throws Exception {
        gameDto = Models.makeGameDto("different values");
        shouldReturnValidJson();
    }

    @Test
    public void description_NonExistingResourcePathGiven_ShouldReturn404() throws Exception {
        when(gameService.findByUri(gameDto.getUri())).thenThrow(new ObjectNotFoundException("msg"));
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
        mockGameMapper();
        String gameDtoAsJson = writeGameDtoAsJson();
        return mockMvc.perform(
                post(uri)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameDtoAsJson)
        );
    }

    private String writeGameDtoAsJson() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(gameDto);
    }

    @Test
    public void postGame_GameDtoGiven_ShouldReturnValidJson() throws Exception {
        ResultActions resultActions = performPostRequest();
        updateUri();
        gameValidator.validate(resultActions, gameDto, uri);
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

    @Test
    public void postGame_InvalidGameDtoGiven_ShouldThrowException() throws Exception {
        gameDto = GameDto.builder().build();
        performPostRequest();
    }
}
