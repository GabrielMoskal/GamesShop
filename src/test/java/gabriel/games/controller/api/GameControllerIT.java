package gabriel.games.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import gabriel.games.controller.util.GameValidator;
import gabriel.games.model.api.Game;
import gabriel.games.model.api.dto.*;
import gabriel.games.model.api.mapper.*;
import gabriel.games.service.GameService;
import gabriel.games.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;

import java.sql.Date;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
public class GameControllerIT {

    private final String PATH = "/api/game/";
    private GameValidator gameValidator;
    @Autowired private MockMvc mockMvc;
    @MockBean private GameMapper gameMapper;
    @MockBean private GameService gameService;
    @Autowired ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        this.gameValidator = new GameValidator();
    }

    private GameDto makeGameDto(String filler) {
        return GameDto.builder()
                .uri(filler)
                .name(filler)
                .details(
                        GameDetailsDto.builder()
                                .description(filler)
                                .webpage(filler)
                                .ratingPlayers(1.0)
                                .ratingReviewer(2.0)
                                .build()
                )
                .platforms(
                        Collections.singletonList(new GamePlatformDto(filler, new Date(0)))
                )
                .companies(
                        Collections.singletonList(new CompanyDto(filler, Collections.singletonList(filler)))
                )
                .build();
    }

    @Test
    public void description_ExistingResourcePathGiven_ShouldReturnValidJson() throws Exception {
        GameDto expected = makeGameDto("filler");

        when(gameMapper.toGameDto(any())).thenReturn(expected);

        ResultActions resultActions = performGetRequest(expected);
        gameValidator.validate(resultActions, expected);
    }

    @Test
    public void description_GameDtoGiven_VerifyMethodCalls() throws Exception {
        GameDto expected = makeGameDto("filler");

        when(gameMapper.toGameDto(any())).thenReturn(expected);
        when(gameService.findByUri(expected.getUri())).thenReturn(new Game(expected.getName()));

        performGetRequest(expected);
        verifyDescriptionMethodServiceInteractions(expected);
        verifyDescriptionMethodGameMapperInteractions(expected);
    }

    private ResultActions performGetRequest(GameDto expected) throws Exception {
        String urlTemplate = PATH + expected.getUri();
        return mockMvc.perform(get(urlTemplate).contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    private void verifyDescriptionMethodServiceInteractions(GameDto expected) {
        ArgumentCaptor<String> uriCaptor = ArgumentCaptor.forClass(String.class);
        verify(gameService).findByUri(uriCaptor.capture());
        assertThat(uriCaptor.getValue()).isEqualTo(expected.getUri());
    }

    private void verifyDescriptionMethodGameMapperInteractions(GameDto expected) {
        ArgumentCaptor<Game> gameCaptor = ArgumentCaptor.forClass(Game.class);
        verify(gameMapper).toGameDto(gameCaptor.capture());
        assertThat(gameCaptor.getValue().getName()).isEqualTo(expected.getName());
        assertThat(gameCaptor.getValue().getUri()).isEqualTo(expected.getUri());
    }

    @Test
    public void description_NonExistingResourcePathGiven_ShouldReturn404() throws Exception {
        GameDto expected = makeGameDto("filler");

        when(gameService.findByUri(expected.getUri())).thenThrow(new ObjectNotFoundException("msg"));

        performGetRequest(expected).andExpect(status().isNotFound());
    }

    @Test
    public void description_ExistingResourcePathGiven_ShouldReturn200() throws Exception {
        GameDto expected = makeGameDto("filler");

        when(gameMapper.toGameDto(any())).thenReturn(expected);

        performGetRequest(expected).andExpect(status().isOk());
    }

    @Test
    public void postGame_ValidGameDtoGiven_ShouldReturn201() throws Exception {
        GameDto expected = makeGameDto("filler");

        when(gameMapper.toGameDto(any())).thenReturn(expected);

        performPostRequest(expected).andExpect(status().isCreated());
    }

    private ResultActions performPostRequest(GameDto expected) throws Exception {
        String gameDtoAsJson = objectMapper.writeValueAsString(expected);

        return mockMvc.perform(
                post(PATH)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameDtoAsJson)
        );
    }

    @Test
    public void postGame_ValidGameDtoGiven_ShouldReturnValidJson() throws Exception {
        GameDto expected = makeGameDto("filler");

        when(gameMapper.toGameDto(any())).thenReturn(expected);

        ResultActions resultActions = performPostRequest(expected);
        gameValidator.validate(resultActions, expected);
    }

    @Test
    public void postGame_GameDtoGiven_VerifyMethodCalls() throws Exception {
        GameDto gameDto = makeGameDto("filler");
        Game game = new Game(gameDto.getName());

        stubPostGameInteractions(gameDto, game);

        performPostRequest(gameDto);
        verifyMethodCalls(gameDto, game);
    }

    private void stubPostGameInteractions(GameDto gameDto, Game game) {
        when(gameMapper.toGame(any())).thenReturn(game);
        when(gameService.save(any())).thenReturn(game);
        when(gameMapper.toGameDto(any())).thenReturn(gameDto);
    }

    private void verifyMethodCalls(GameDto gameDto, Game game) {
        verifyPostGameGameMapperToGameInteractions(gameDto);
        verifyPostGameGameServiceSaveInteractions(game);
        verifyPostGameGameMapperToGameDtoInteractions(game);
    }

    private void verifyPostGameGameMapperToGameInteractions(GameDto expected) {
        ArgumentCaptor<GameDto> gameDtoCaptor = ArgumentCaptor.forClass(GameDto.class);
        verify(gameMapper).toGame(gameDtoCaptor.capture());
        GameDto actual = gameDtoCaptor.getValue();
        assertThat(actual).isEqualTo(expected);
    }

    private void verifyPostGameGameServiceSaveInteractions(Game expected) {
        ArgumentCaptor<Game> gameCaptor = ArgumentCaptor.forClass(Game.class);
        verify(gameService).save(gameCaptor.capture());
        Game actual = gameCaptor.getValue();
        assertThat(actual).isEqualTo(expected);
    }

    private void verifyPostGameGameMapperToGameDtoInteractions(Game expected) {
        ArgumentCaptor<Game> gameCaptor = ArgumentCaptor.forClass(Game.class);
        verify(gameMapper).toGameDto(gameCaptor.capture());
        Game actual = gameCaptor.getValue();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void postGame_InvalidGameDtoGiven_ShouldReturn400() throws Exception {
        GameDto invalid = makeGameDto(null);

        performPostRequest(invalid).andExpect(status().isBadRequest());
    }

    @Test
    public void patchGame_SingleAttributeUpdateGiven_ShouldReturn200() throws Exception {
        GameDto expected = makeGameDto("filler");

        when(gameMapper.toGameDto(any())).thenReturn(expected);

        performPatchRequest(expected).andExpect(status().isOk());
    }

    private ResultActions performPatchRequest(GameDto expected) throws Exception {
        String content = objectMapper.writeValueAsString(expected);

        return mockMvc.perform(patch(PATH + expected.getUri())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        );
    }

    @Test
    public void patchGame_SingleAttributeUpdateGiven_ShouldReturnValidJson() throws Exception {
        GameDto expected = makeGameDto("different");

        when(gameMapper.toGameDto(any())).thenReturn(expected);

        ResultActions resultActions = performPatchRequest(expected);
        gameValidator.validate(resultActions, expected);
    }

    @Test
    public void patchGame_SingleAttributeUpdateGiven_VerifyMethodCalls() throws Exception {
        GameDto gameDto = makeGameDto("filler");
        Game updatedGame = new Game(gameDto.getName());
        updatedGame.setName("different name");

        stubPatchGameInteractions(gameDto, updatedGame);
        performPatchRequest(gameDto);

        verifyPatchInteractions(gameDto, updatedGame);
    }

    private void stubPatchGameInteractions(GameDto gameDto, Game updatedGame) {
        when(gameMapper.toGame(any())).thenReturn(updatedGame);
        when(gameMapper.toGameDto(any())).thenReturn(gameDto);
        when(gameService.update(any(), any())).thenReturn(updatedGame);
    }

    private void verifyPatchInteractions(GameDto gameDto, Game updatedGame) {
        verifyPatchGameGameMapperToGameInteractions(gameDto);
        verifyPatchGameGameServiceUpdateInteractions(updatedGame);
        verifyPatchGameGameMapperToGameDtoInteractions(updatedGame);
    }

    private void verifyPatchGameGameMapperToGameInteractions(GameDto gameDto) {
        ArgumentCaptor<GameDto> gameDtoCaptor = ArgumentCaptor.forClass(GameDto.class);

        verify(gameMapper).toGame(gameDtoCaptor.capture());

        GameDto actual = gameDtoCaptor.getValue();
        assertThat(actual).isEqualTo(gameDto);
    }

    private void verifyPatchGameGameServiceUpdateInteractions(Game game) {
        ArgumentCaptor<String> uriCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Game> gameCaptor = ArgumentCaptor.forClass(Game.class);

        verify(gameService).update(uriCaptor.capture(), gameCaptor.capture());

        assertThat(gameCaptor.getValue()).isEqualTo(game);
        assertThat(uriCaptor.getValue()).isEqualTo(game.getUri());
    }

    private void verifyPatchGameGameMapperToGameDtoInteractions(Game updatedGame) {
        ArgumentCaptor<Game> gameCaptor = ArgumentCaptor.forClass(Game.class);

        verify(gameMapper).toGameDto(gameCaptor.capture());

        Game actual = gameCaptor.getValue();
        assertThat(actual).isEqualTo(updatedGame);
    }

    @Test
    public void deleteGame_ExistingUriGiven_ShouldReturn204() throws Exception {
        String uri = "uri";

        performDeleteRequest(uri).andExpect(status().isNoContent());

        verifyDeleteGameInteractions(uri);
    }

    private ResultActions performDeleteRequest(String uri) throws Exception {
        return mockMvc.perform(delete(PATH + uri)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(uri))
        );
    }

    private void verifyDeleteGameInteractions(String uri) {
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        verify(gameService).deleteByUri(stringCaptor.capture());
        String actual = stringCaptor.getValue();
        assertThat(actual).isEqualTo(uri);
    }

    @Test
    public void deleteGame_ExceptionThrown_ShouldReturn204() throws Exception {
        String uri = "non-existent";

        doThrow(EmptyResultDataAccessException.class).when(gameService).deleteByUri(uri);
        
        performDeleteRequest(uri).andExpect(status().isNoContent());
    }
}
