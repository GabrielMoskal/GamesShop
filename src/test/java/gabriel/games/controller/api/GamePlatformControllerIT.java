package gabriel.games.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import gabriel.games.controller.util.GamePlatformValidator;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GamePlatformController.class)
public class GamePlatformControllerIT {

    private final String PATH = "/api/game-platform/";
    private final String PLATFORM_URI = "test-platform-uri";
    private final String GAME_URI = "test-game-uri";

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private GamePlatformService gamePlatformService;
    @MockBean private GamePlatformMapper gamePlatformMapper;
    private GamePlatformValidator validator;

    @BeforeEach
    public void setUp() {
         this.validator = new GamePlatformValidator();
    }

    @Test
    public void getGamePlatform_ValidUriGiven_VerifyInteractions() throws Exception {
        //given
        GamePlatformDto gamePlatformDto = new GamePlatformDto("game-name", "platform-name", new Date(0));
        GamePlatform gamePlatform = mockGamePlatform(gamePlatformDto);
        when(gamePlatformService.find(anyString(), anyString())).thenReturn(gamePlatform);
        when(gamePlatformMapper.toGamePlatformDto(gamePlatform)).thenReturn(gamePlatformDto);

        //when
        ResultActions resultActions = performGetRequest(GAME_URI + "/" + PLATFORM_URI);

        //then
        resultActions.andExpect(status().isOk());
        verifyGetServiceInteractions();
        verifyMapperToGamePlatformDtoInteractions(gamePlatformDto);
        validator.validate(resultActions, gamePlatformDto, PATH + GAME_URI + "/" + PLATFORM_URI);
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

    private ResultActions performGetRequest(String uri) throws Exception {
        return mockMvc.perform(get(PATH + uri)
                .contentType(MediaType.APPLICATION_JSON)
        );
    }

    private void verifyGetServiceInteractions() {
        ArgumentCaptor<String> gameUriCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> platformUriCaptor = ArgumentCaptor.forClass(String.class);
        verify(gamePlatformService).find(gameUriCaptor.capture(), platformUriCaptor.capture());
        assertEquals(GAME_URI, gameUriCaptor.getValue());
        assertEquals(PLATFORM_URI, platformUriCaptor.getValue());
    }

    private void verifyMapperToGamePlatformDtoInteractions(GamePlatformDto expected) {
        ArgumentCaptor<GamePlatform> gamePlatformCaptor = ArgumentCaptor.forClass(GamePlatform.class);
        verify(gamePlatformMapper).toGamePlatformDto(gamePlatformCaptor.capture());
        GamePlatform actual = gamePlatformCaptor.getValue();
        assertEquals(expected.getReleaseDate(), actual.getReleaseDate());
        assertEquals(expected.getGameName(), actual.getGameName());
        assertEquals(expected.getPlatformName(), actual.getPlatformName());
    }

    @Test
    public void getGamePlatform_InvalidNamesGiven_ShouldReturn404() throws Exception {
        when(gamePlatformService.find(anyString(), anyString())).thenThrow(new ObjectNotFoundException("msg"));

        performGetRequest("invalid/invalid")
                .andExpect(status().isNotFound());
    }

    @Test
    public void postGamePlatform_ValidGamePlatformDtoGiven_VerifyInteractions() throws Exception {
        // given
        GamePlatformDto gamePlatformDto = new GamePlatformDto("game-name", "platform-name", new Date(0));
        stubPostMembers(gamePlatformDto);

        // when
        ResultActions resultActions = performPostRequest(gamePlatformDto);

        // then
        resultActions.andExpect(status().isCreated());
        verifyPostInteractions(gamePlatformDto);
        validator.validate(resultActions, gamePlatformDto, PATH + GAME_URI + "/" + PLATFORM_URI);
    }

    private void stubPostMembers(GamePlatformDto gamePlatformDto) {
        GamePlatform gamePlatform = mockGamePlatform(gamePlatformDto);

        when(gamePlatformMapper.toGamePlatform(any())).thenReturn(gamePlatform);
        when(gamePlatformService.save(any())).thenReturn(gamePlatform);
        when(gamePlatformMapper.toGamePlatformDto(any())).thenReturn(gamePlatformDto);
    }

    private ResultActions performPostRequest(GamePlatformDto gamePlatformDto) throws Exception {
        return mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gamePlatformDto))
        );
    }

    private void verifyPostInteractions(GamePlatformDto gamePlatformDto) {
        verifyPostMapperToGamePlatformInteractions(gamePlatformDto);
        verifyPostServiceInteractions(gamePlatformDto);
        verifyMapperToGamePlatformDtoInteractions(gamePlatformDto);
    }

    private void verifyPostMapperToGamePlatformInteractions(GamePlatformDto gamePlatformDto) {
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
}
