package gabriel.games.controller.api;

import gabriel.games.controller.util.GamePlatformValidator;
import gabriel.games.model.api.GamePlatform;
import gabriel.games.model.api.dto.GamePlatformDto;
import gabriel.games.model.api.mapper.GamePlatformMapper;
import gabriel.games.service.GamePlatformService;
import gabriel.games.service.exception.ObjectNotFoundException;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GamePlatformController.class)
public class GamePlatformControllerIT {

    private final String PATH = "/api/game-platform/";
    @Autowired private MockMvc mockMvc;
    @MockBean private GamePlatformService gamePlatformService;
    @MockBean private GamePlatformMapper gamePlatformMapper;

    @Test
    public void getGamePlatform_ValidNamesGiven_VerifyInteractions() throws Exception {
        //given
        GamePlatform gamePlatform = mock(GamePlatform.class);
        when(gamePlatformService.find(anyString(), anyString())).thenReturn(gamePlatform);
        GamePlatformDto gamePlatformDto = new GamePlatformDto("game-name", "platform-name", new Date(0));
        when(gamePlatformMapper.toGamePlatformDto(gamePlatform)).thenReturn(gamePlatformDto);

        //when
        ResultActions resultActions = performGetRequest("game-name/platform-name");

        //then
        resultActions.andExpect(status().isOk());
        verifyGetServiceInteractions(gamePlatformDto);
        verifyGetMapperInteractions(gamePlatform);
        GamePlatformValidator.validate(resultActions, gamePlatformDto, PATH + "game-name/platform-name");
    }

    private ResultActions performGetRequest(String uri) throws Exception {
        return mockMvc.perform(get(PATH + uri)
                .contentType(MediaType.APPLICATION_JSON)
        );
    }

    private void verifyGetServiceInteractions(GamePlatformDto gamePlatformDto) {
        ArgumentCaptor<String> gameNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> platformNameCaptor = ArgumentCaptor.forClass(String.class);
        verify(gamePlatformService).find(gameNameCaptor.capture(), platformNameCaptor.capture());
        assertEquals(gamePlatformDto.getGameName(), gameNameCaptor.getValue());
        assertEquals(gamePlatformDto.getPlatformName(), platformNameCaptor.getValue());
    }

    private void verifyGetMapperInteractions(GamePlatform expected) {
        ArgumentCaptor<GamePlatform> gamePlatformCaptor = ArgumentCaptor.forClass(GamePlatform.class);
        verify(gamePlatformMapper).toGamePlatformDto(gamePlatformCaptor.capture());
        GamePlatform actual = gamePlatformCaptor.getValue();
        assertEquals(expected, actual);
    }

    @Test
    public void getGamePlatform_InvalidNamesGiven_ShouldReturn404() throws Exception {
        when(gamePlatformService.find(anyString(), anyString())).thenThrow(new ObjectNotFoundException("msg"));

        performGetRequest("invalid/invalid")
                .andExpect(status().isNotFound());
    }
}
