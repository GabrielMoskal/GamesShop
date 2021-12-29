package gabriel.games.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlatformController.class)
public class PlatformControllerIT {

    private final String PATH = "/api/platform/";
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
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
        when(platformService.findByUri(any())).thenReturn(new Platform(platformDto.getName(), platformDto.getUri()));
        when(platformMapper.toPlatformDto(any())).thenReturn(platformDto);
    }

    private ResultActions performGetPlatform(String uri) throws Exception {
        return mockMvc.perform(
                get(PATH + "{uri}", uri)
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

    @Test
    public void postPlatform_ValidPlatformDtoGiven_ShouldReturn201() throws Exception {
        PlatformDto platformDto = new PlatformDto("word", "word");
        mockPostPlatformMembers(platformDto);

        ResultActions resultActions = performPostPlatform(platformDto)
                .andExpect(status().isCreated());

        verifyPostPlatformInteractions(platformDto);
        platformValidator.validate(resultActions, platformDto);
    }

    private void mockPostPlatformMembers(PlatformDto platformDto) {
        Platform platform = new Platform(platformDto.getName(), platformDto.getUri());
        when(platformMapper.toPlatform(platformDto)).thenReturn(platform);
        when(platformService.save(any())).thenReturn(platform);
        when(platformMapper.toPlatformDto(any())).thenReturn(platformDto);
    }

    private ResultActions performPostPlatform(PlatformDto platformDto) throws Exception {
        return mockMvc.perform(
                post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(platformDto))
        );
    }

    private void verifyPostPlatformInteractions(PlatformDto platformDto) {
        verifyPostPlatformMapperToPlatformInteractions(platformDto);
        verifyPostPlatformServiceInteractions(platformDto);
        verifyPostPlatformMapperToPlatformDtoInteractions(platformDto);
    }

    private void verifyPostPlatformMapperToPlatformInteractions(PlatformDto platformDto) {
        ArgumentCaptor<PlatformDto> platformDtoCaptor = ArgumentCaptor.forClass(PlatformDto.class);
        verify(platformMapper).toPlatform(platformDtoCaptor.capture());
        PlatformDto actual = platformDtoCaptor.getValue();
        assertEquals(platformDto.getName(), actual.getName());
        assertEquals(platformDto.getUri(), actual.getUri());
    }

    private void verifyPostPlatformServiceInteractions(PlatformDto platformDto) {
        ArgumentCaptor<Platform> platformCaptor = ArgumentCaptor.forClass(Platform.class);
        verify(platformService).save(platformCaptor.capture());
        Platform actual = platformCaptor.getValue();
        assertEquals(platformDto.getName(), actual.getName());
        assertEquals(platformDto.getUri(), actual.getUri());
    }

    private void verifyPostPlatformMapperToPlatformDtoInteractions(PlatformDto platformDto) {
        ArgumentCaptor<Platform> platformCaptor = ArgumentCaptor.forClass(Platform.class);
        verify(platformMapper).toPlatformDto(platformCaptor.capture());
        Platform actual = platformCaptor.getValue();
        assertEquals(platformDto.getName(), actual.getName());
        assertEquals(platformDto.getUri(), actual.getUri());
    }

    @Test
    public void postPlatform_InvalidPlatformDtoGiven_ShouldReturn400() throws Exception {
        PlatformDto platformDto = new PlatformDto(null, "");

        performPostPlatform(platformDto).andExpect(status().isBadRequest());
    }

    @Test
    public void patchPlatform_ValidNameGiven_ShouldReturn200() throws Exception {
        PlatformDto expected = new PlatformDto("valid name", "uri");
        mockPatchPlatformMembers(expected);

        ResultActions resultActions = performPatchPlatform(expected)
                .andExpect(status().isOk());

        verifyPatchPlatformInteractions(expected);
        platformValidator.validate(resultActions, expected);
    }

    private void mockPatchPlatformMembers(PlatformDto platformDto) {
        Platform platform = new Platform(platformDto.getName(), platformDto.getUri());
        when(platformMapper.toPlatform(any())).thenReturn(platform);
        when(platformService.update(anyString(), any())).thenReturn(platform);
        when(platformMapper.toPlatformDto(any())).thenReturn(platformDto);
    }

    private ResultActions performPatchPlatform(PlatformDto expected) throws Exception {
        return mockMvc.perform(
                patch(PATH + "{uri}", expected.getUri())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expected))
        );
    }

    private void verifyPatchPlatformInteractions(PlatformDto expected) {
        verifyPatchPlatformMapperToPlatformInteractions(expected);
        verifyPatchPlatformServiceUpdateInteractions(expected);
        verifyPatchPlatformMapperToPlatformDtoInteractions(expected);
    }

    private void verifyPatchPlatformMapperToPlatformInteractions(PlatformDto expected) {
        ArgumentCaptor<PlatformDto> platformDtoCaptor = ArgumentCaptor.forClass(PlatformDto.class);
        verify(platformMapper).toPlatform(platformDtoCaptor.capture());
        PlatformDto actual = platformDtoCaptor.getValue();
        assertEquals(expected.getName(), actual.getName());
    }

    private void verifyPatchPlatformServiceUpdateInteractions(PlatformDto expected) {
        ArgumentCaptor<Platform> platformCaptor = ArgumentCaptor.forClass(Platform.class);
        verify(platformService).update(any(), platformCaptor.capture());
        Platform actual = platformCaptor.getValue();
        assertEquals(expected.getName(), actual.getName());
    }

    private void verifyPatchPlatformMapperToPlatformDtoInteractions(PlatformDto expected) {
        ArgumentCaptor<Platform> platformCaptor = ArgumentCaptor.forClass(Platform.class);
        verify(platformMapper).toPlatformDto(platformCaptor.capture());
        Platform actual = platformCaptor.getValue();
        assertEquals(expected.getName(), actual.getName());
    }
}
