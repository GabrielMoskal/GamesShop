package gabriel.games.model.api.mapper;

import gabriel.games.model.api.GamePlatform;
import gabriel.games.model.api.dto.GamePlatformDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GamePlatformMapperTest {

    private GamePlatformMapper mapper;

    @BeforeEach
    public void setUp() {
        this.mapper = new GamePlatformMapper();
    }

    @Test
    public void toGamePlatformDto_GamePlatformGiven_ShouldReturnValidGamePlatformDto() {
        String platformName = "platform name";
        Date releaseDate = new Date(1L);

        assertMappingCorrect(platformName, releaseDate);
    }

    private void assertMappingCorrect(String platformName, Date releaseDate) {
        GamePlatform gamePlatform = mockGamePlatform(platformName, releaseDate);
        GamePlatformDto expected = new GamePlatformDto(platformName, releaseDate);
        GamePlatformDto actual = mapper.toGamePlatformDto(gamePlatform);

        assertEquals(expected, actual);
    }

    private GamePlatform mockGamePlatform(String platformName, Date releaseDate) {
        GamePlatform gamePlatform = mock(GamePlatform.class);
        when(gamePlatform.getPlatformName()).thenReturn(platformName);
        when(gamePlatform.getReleaseDate()).thenReturn(releaseDate);

        return gamePlatform;
    }

    @Test
    public void toGamePlatformDto_DifferentGamePlatformGiven_ShouldReturnValidGamePlatformDto() {
        String name = "different name";
        Date releaseDate = Date.valueOf("2000-01-21");

        assertMappingCorrect(name, releaseDate);
    }

    @Test
    public void toGamePlatform_NameGiven_ShouldContainValidName() {
        GamePlatformDto gamePlatformDto = new GamePlatformDto("name", new Date(0));
        GamePlatform actual = mapper.toGamePlatform(gamePlatformDto);

        assertEquals(gamePlatformDto.getName(), actual.getPlatformName());
    }

    @Test
    public void toGamePlatform_ReleaseDateGiven_ShouldContainValidReleaseDate() {
        GamePlatformDto gamePlatformDto = new GamePlatformDto("name", Date.valueOf("2000-01-21"));
        GamePlatform actual = mapper.toGamePlatform(gamePlatformDto);

        assertEquals(gamePlatformDto.getReleaseDate(), actual.getReleaseDate());
    }
}
