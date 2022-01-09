package gabriel.games.model.api.dto.assembler;

import gabriel.games.model.api.Platform;
import gabriel.games.model.api.dto.PlatformDto;
import gabriel.games.model.api.mapper.PlatformMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Component
public class PlatformDtoModelAssemblerTest {

    private PlatformMapper platformMapper;
    private PlatformDtoModelAssembler assembler;

    @BeforeEach
    public void setUp() {
        this.platformMapper = mock(PlatformMapper.class);
        this.assembler = new PlatformDtoModelAssembler(this.platformMapper);
    }

    @Test
    public void instantiateModel_PlatformGiven_ReturnsValidPlatformDto() {
        Platform platform = mock(Platform.class);
        PlatformDto expected = new PlatformDto("name", "uri");
        when(platformMapper.toPlatformDto(platform)).thenReturn(expected);

        PlatformDto actual = assembler.instantiateModel(platform);
        assertEquals(expected, actual);
    }

    @Test
    public void toModel_PlatformGiven_ContainsValidLink() {
        Platform platform = mock(Platform.class);
        when(platform.getUri()).thenReturn("uri");
        PlatformDto platformDto = new PlatformDto("name", "uri");
        when(platformMapper.toPlatformDto(platform)).thenReturn(platformDto);

        PlatformDto actual = assembler.toModel(platform);

        String expectedLink = "/api/platform/uri";
        String actualLink = actual.getLink("self").orElse(mock(Link.class)).getHref();
        assertEquals(expectedLink, actualLink);
    }
}
