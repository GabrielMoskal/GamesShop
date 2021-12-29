package gabriel.games.controller.api;

import gabriel.games.controller.util.PlatformValidator;
import gabriel.games.model.api.Platform;
import gabriel.games.model.api.dto.PlatformDto;
import gabriel.games.model.api.mapper.PlatformMapper;
import gabriel.games.service.PlatformService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlatformController.class)
public class PlatformControllerIT {

    @Autowired private MockMvc mockMvc;
    @MockBean private PlatformService platformService;
    @MockBean private PlatformMapper platformMapper;
    private PlatformValidator platformValidator;

    @BeforeEach
    public void setUp() {
        this.platformValidator = new PlatformValidator();
    }

    @Test
    public void getPlatform_NameGiven_ShouldReturnValidPlatform() throws Exception {
        PlatformDto platformDto = new PlatformDto("Playstation 3", "playstation-3");
        mockGetPlatformMembers(platformDto);

        ResultActions resultActions = performGetPlatform("playstation-3")
                .andExpect(status().isOk());

        verifyGetPlatformInteractions(platformDto);
        platformValidator.validate(resultActions, platformDto);
    }

    private void mockGetPlatformMembers(PlatformDto platformDto) {
        when(platformService.findByUri(any())).thenReturn(new Platform(platformDto.getName()));
        when(platformMapper.toPlatformDto(any())).thenReturn(platformDto);
    }

    private ResultActions performGetPlatform(String uri) throws Exception {
        return mockMvc.perform(
                get("/api/platform/{uri}", uri)
                        .contentType(MediaType.APPLICATION_JSON)
        );
    }

    private void verifyGetPlatformInteractions(PlatformDto platformDto) {
        verifyGetPlatformServiceInteraction(platformDto);
        verifyGetPlatformMapperInteraction(platformDto);
    }

    private void verifyGetPlatformServiceInteraction(PlatformDto platformDto) {
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        verify(platformService).findByUri(stringCaptor.capture());
        assertEquals(platformDto.getUri(), stringCaptor.getValue());
    }

    private void verifyGetPlatformMapperInteraction(PlatformDto platformDto) {
        ArgumentCaptor<Platform> platformCaptor = ArgumentCaptor.forClass(Platform.class);
        verify(platformMapper).toPlatformDto(platformCaptor.capture());
        assertEquals(platformDto.getName(), platformCaptor.getValue().getName());
        assertEquals(platformDto.getUri(), platformCaptor.getValue().getUri());
    }

    @Test
    public void getPlatform_NonExistentPlatformUriGiven_ShouldReturn404() throws Exception {
        String uri = "non-existent";

        when(platformService.findByUri(uri)).thenThrow(new ObjectNotFoundException("msg"));

        performGetPlatform(uri).andExpect(status().isNotFound());
    }
}
