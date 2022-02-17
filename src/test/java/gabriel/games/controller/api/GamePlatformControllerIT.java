package gabriel.games.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import gabriel.games.controller.util.GamePlatformDtoValidator;
import gabriel.games.model.api.GamePlatform;
import gabriel.games.model.api.dto.GamePlatformDto;
import gabriel.games.model.api.mapper.GamePlatformMapper;
import gabriel.games.service.GamePlatformService;
import gabriel.games.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GamePlatformController.class)
public class GamePlatformControllerIT {

    private final String PATH = "/api/game-platform";
    private final String PLATFORM_URI = "test-platform-uri";
    private final String GAME_URI = "test-game-uri";

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private GamePlatformService gamePlatformService;
    @MockBean private GamePlatformMapper gamePlatformMapper;
    private GamePlatformDtoValidator validator;

    @BeforeEach
    public void setUp() {
         this.validator = new GamePlatformDtoValidator();
    }

    @Test
    public void getGamePlatform_ValidUriGiven_VerifyInteractions() throws Exception {
        //given
        GamePlatformDto gamePlatformDto = new GamePlatformDto("game-name", "platform-name", new Date(0));
        GamePlatform gamePlatform = mockGamePlatform(gamePlatformDto);
        when(gamePlatformService.find(anyString(), anyString())).thenReturn(gamePlatform);
        when(gamePlatformMapper.toGamePlatformDto(gamePlatform)).thenReturn(gamePlatformDto);

        //when
        ResultActions resultActions = performGetRequest();

        //then
        resultActions.andExpect(status().isOk());
        verifyGetServiceInteractions();
        verifyMapperToGamePlatformDtoInteractions(gamePlatform);
        validateUri(resultActions, gamePlatformDto);
    }

    private GamePlatform mockGamePlatform(GamePlatformDto gamePlatformDto) {
        GamePlatform gamePlatform = mock(GamePlatform.class);

        when(gamePlatform.getReleaseDate()).thenReturn(gamePlatformDto.getReleaseDate());
        when(gamePlatform.getGameName()).thenReturn(gamePlatformDto.getGameName());
        when(gamePlatform.getPlatformName()).thenReturn(gamePlatformDto.getPlatformName());
        when(gamePlatform.getGameUri()).thenReturn(GAME_URI);
        when(gamePlatform.getPlatformUri()).thenReturn(PLATFORM_URI);

        return gamePlatform;
    }

    private ResultActions performGetRequest() throws Exception {
        String uri = makeUri(PATH, GAME_URI, PLATFORM_URI);
        return mockMvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON)
        );
    }

    private String makeUri(String... path) {
        return String.join("/", path);
    }

    private void verifyGetServiceInteractions() {
        ArgumentCaptor<String> gameUriCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> platformUriCaptor = ArgumentCaptor.forClass(String.class);
        verify(gamePlatformService).find(gameUriCaptor.capture(), platformUriCaptor.capture());
        assertEquals(GAME_URI, gameUriCaptor.getValue());
        assertEquals(PLATFORM_URI, platformUriCaptor.getValue());
    }

    private void verifyMapperToGamePlatformDtoInteractions(GamePlatform expected) {
        ArgumentCaptor<GamePlatform> gamePlatformCaptor = ArgumentCaptor.forClass(GamePlatform.class);
        verify(gamePlatformMapper).toGamePlatformDto(gamePlatformCaptor.capture());
        GamePlatform actual = gamePlatformCaptor.getValue();
        assertEquals(expected, actual);
    }

    private void validateUri(ResultActions resultActions, GamePlatformDto gamePlatformDto) {
        String uri = makeUri(PATH, GAME_URI, PLATFORM_URI);
        validator.validate(resultActions, gamePlatformDto, uri);
    }

    @Test
    public void getGamePlatform_InvalidUrisGiven_ShouldReturn404() throws Exception {
        when(gamePlatformService.find(anyString(), anyString())).thenThrow(new ObjectNotFoundException("msg"));

        performGetRequest()
                .andExpect(status().isNotFound());
    }

    @Test
    public void postGamePlatform_ValidGamePlatformDtoGiven_VerifyInteractions() throws Exception {
        // given
        GamePlatformDto gamePlatformDto = new GamePlatformDto("game-name", "platform-name", new Date(0));
        GamePlatform gamePlatform = mockGamePlatform(gamePlatformDto);
        when(gamePlatformMapper.toGamePlatform(any())).thenReturn(gamePlatform);
        when(gamePlatformService.save(any())).thenReturn(gamePlatform);
        when(gamePlatformMapper.toGamePlatformDto(any())).thenReturn(gamePlatformDto);

        // when
        ResultActions resultActions = performPostRequest(gamePlatformDto);

        // then
        resultActions.andExpect(status().isCreated());
        verifyMapperToGamePlatformInteractions(gamePlatformDto);
        verifyPostServiceInteractions(gamePlatformDto);
        verifyMapperToGamePlatformDtoInteractions(gamePlatform);
        validateUri(resultActions, gamePlatformDto);
    }

    private ResultActions performPostRequest(GamePlatformDto gamePlatformDto) throws Exception {
        return mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gamePlatformDto))
        );
    }

    private void verifyMapperToGamePlatformInteractions(GamePlatformDto gamePlatformDto) {
        ArgumentCaptor<GamePlatformDto> gamePlatformDtoCaptor = ArgumentCaptor.forClass(GamePlatformDto.class);
        verify(gamePlatformMapper).toGamePlatform(gamePlatformDtoCaptor.capture());
        GamePlatformDto actual = gamePlatformDtoCaptor.getValue();
        assertEquals(gamePlatformDto, actual);
    }

    private void verifyPostServiceInteractions(GamePlatformDto gamePlatformDto) {
        ArgumentCaptor<GamePlatform> gamePlatformCaptor = ArgumentCaptor.forClass(GamePlatform.class);
        verify(gamePlatformService).save(gamePlatformCaptor.capture());
        GamePlatform actual = gamePlatformCaptor.getValue();
        assertEquals(gamePlatformDto.getReleaseDate(), actual.getReleaseDate());
        assertEquals(gamePlatformDto.getGameName(), actual.getGameName());
        assertEquals(gamePlatformDto.getPlatformName(), actual.getPlatformName());
    }

    @Test
    public void postGamePlatform_InvalidGamePlatformDtoGiven_ShouldReturn400() throws Exception {
        GamePlatformDto gamePlatformDto = new GamePlatformDto(null, null, null);

        performPostRequest(gamePlatformDto).andExpect(status().isBadRequest());
    }

    @Test
    public void patchGamePlatform_DateGiven_ShouldUpdateGamePlatform() throws Exception {
        GamePlatformDto gamePlatformDto = new GamePlatformDto("gameName", "platformName", new Date(0));
        GamePlatform gamePlatform = mockGamePlatform(gamePlatformDto);
        when(gamePlatformMapper.toGamePlatform(any())).thenReturn(gamePlatform);
        when(gamePlatformService.update(GAME_URI, PLATFORM_URI, gamePlatform)).thenReturn(gamePlatform);
        when(gamePlatformMapper.toGamePlatformDto(gamePlatform)).thenReturn(gamePlatformDto);

        ResultActions resultActions = performPatchRequest(gamePlatformDto);

        resultActions.andExpect(status().isOk());
        verifyMapperToGamePlatformInteractions(gamePlatformDto);
        verifyPatchServiceInteractions(gamePlatform);
        verifyMapperToGamePlatformDtoInteractions(gamePlatform);
    }

    private void verifyPatchServiceInteractions(GamePlatform gamePlatform) {
        ArgumentCaptor<String> gameUriCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> platformUriCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<GamePlatform> gamePlatformCaptor = ArgumentCaptor.forClass(GamePlatform.class);
        verify(gamePlatformService).update(gameUriCaptor.capture(), platformUriCaptor.capture(), gamePlatformCaptor.capture());

        assertEquals(GAME_URI, gameUriCaptor.getValue());
        assertEquals(PLATFORM_URI, platformUriCaptor.getValue());
        assertEquals(gamePlatform, gamePlatformCaptor.getValue());
    }

    private ResultActions performPatchRequest(GamePlatformDto gamePlatformDto) throws Exception {
        String uri = String.join("/", PATH, GAME_URI, PLATFORM_URI);
        String content = objectMapper.writeValueAsString(gamePlatformDto);

        return mockMvc.perform(patch(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        );
    }

    @Test
    public void deleteGamePlatform_UriGiven_ShouldReturn204() throws Exception {
        ResultActions resultActions = performDeleteRequest();

        resultActions.andExpect(status().isNoContent());
        verifyDeleteServiceInteractions();
    }

    private ResultActions performDeleteRequest() throws Exception {
        String uri = makeUri(PATH, GAME_URI, PLATFORM_URI);
        return mockMvc.perform(delete(uri));
    }

    private void verifyDeleteServiceInteractions() {
        ArgumentCaptor<String> gameUriCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> platformUriCaptor = ArgumentCaptor.forClass(String.class);

        verify(gamePlatformService).delete(gameUriCaptor.capture(), platformUriCaptor.capture());
        assertEquals(GAME_URI, gameUriCaptor.getValue());
        assertEquals(PLATFORM_URI, platformUriCaptor.getValue());
    }

    @Test
    public void deleteGamePlatform_ExceptionThrown_ShouldReturn204() throws Exception {
        doThrow(EmptyResultDataAccessException.class).when(gamePlatformService).delete(GAME_URI, PLATFORM_URI);

        ResultActions resultActions = performDeleteRequest();

        resultActions.andExpect(status().isNoContent());
    }
}
