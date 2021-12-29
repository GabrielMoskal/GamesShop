package gabriel.games.model.api.mapper;

import gabriel.games.model.api.Platform;
import gabriel.games.model.api.dto.PlatformDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlatformMapperTest {

    private PlatformMapper platformMapper;

    @BeforeEach
    public void setUp() {
        this.platformMapper = new PlatformMapper();
    }

    @Test
    public void toPlatformDto_NameGiven_ShouldContainValidName() {
        Platform platform = new Platform("name", "uri");

        PlatformDto platformDto = platformMapper.toPlatformDto(platform);

        assertEquals(platform.getName(), platformDto.getName());
    }

    @Test
    public void toPlatformDto_DifferentNameGiven_ShouldContainValidName() {
        Platform platform = new Platform("different name", "uri");

        PlatformDto platformDto = platformMapper.toPlatformDto(platform);

        assertEquals(platform.getName(), platformDto.getName());
    }

    @Test
    public void toPlatformDto_UriGiven_ShouldContainValidUri() {
        Platform platform = new Platform("name", "uri");

        PlatformDto platformDto = platformMapper.toPlatformDto(platform);

        assertEquals(platform.getUri(), platformDto.getUri());
    }

    @Test
    public void toPlatformDto_DifferentUriGiven_ShouldContainValidUri() {
        Platform platform = new Platform("name", "different uri");

        PlatformDto platformDto = platformMapper.toPlatformDto(platform);

        assertEquals(platform.getUri(), platformDto.getUri());
    }

    @Test
    public void toPlatform_NameGiven_ShouldContainValidName() {
        PlatformDto platformDto = new PlatformDto("name", "uri");

        Platform platform = platformMapper.toPlatform(platformDto);

        assertEquals(platformDto.getName(), platform.getName());
    }

    @Test
    public void toPlatform_DifferentNameGiven_ShouldContainValidName() {
        PlatformDto platformDto = new PlatformDto("different name", "uri");

        Platform platform = platformMapper.toPlatform(platformDto);

        assertEquals(platformDto.getName(), platform.getName());
    }

    @Test
    public void toPlatform_UriGiven_ShouldContainValidUri() {
        PlatformDto platformDto = new PlatformDto("name", "uri");

        Platform platform = platformMapper.toPlatform(platformDto);

        assertEquals(platformDto.getUri(), platform.getUri());
    }

    @Test
    public void toPlatform_DifferentUriGiven_ShouldContainValidUir() {
        PlatformDto platformDto = new PlatformDto("name", "different uri");

        Platform platform = platformMapper.toPlatform(platformDto);

        assertEquals(platformDto.getUri(), platform.getUri());
    }
}
