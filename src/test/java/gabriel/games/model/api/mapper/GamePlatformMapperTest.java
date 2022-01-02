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
    public void toGamePlatformDto_GameNameGiven_ShouldContainValidGameName() {
        GamePlatform gamePlatform = mock(GamePlatform.class);
        when(gamePlatform.getGameName()).thenReturn("gameName");

        GamePlatformDto actual = mapper.toGamePlatformDto(gamePlatform);

        assertEquals("gameName", actual.getGameName());
    }

    @Test
    public void toGamePlatformDto_PlatformNameGiven_ShouldContainValidPlatformName() {
        GamePlatform gamePlatform = mock(GamePlatform.class);
        when(gamePlatform.getPlatformName()).thenReturn("platformName");

        GamePlatformDto actual = mapper.toGamePlatformDto(gamePlatform);

        assertEquals("platformName", actual.getPlatformName());
    }

    @Test
    public void toGamePlatformDto_ReleaseDateGiven_ShouldContainValidReleaseDate() {
        GamePlatform gamePlatform = mock(GamePlatform.class);
        when(gamePlatform.getReleaseDate()).thenReturn(mock(Date.class));

        GamePlatformDto actual = mapper.toGamePlatformDto(gamePlatform);

        assertEquals(gamePlatform.getReleaseDate(), actual.getReleaseDate());
    }

    @Test
    public void toGamePlatform_ReleaseDateGiven_ShouldContainValidReleaseDate() {
        GamePlatformDto gamePlatformDto = mock(GamePlatformDto.class);
        when(gamePlatformDto.getReleaseDate()).thenReturn(mock(Date.class));

        GamePlatform actual = mapper.toGamePlatform(gamePlatformDto);

        assertEquals(gamePlatformDto.getReleaseDate(), actual.getReleaseDate());
    }
}
