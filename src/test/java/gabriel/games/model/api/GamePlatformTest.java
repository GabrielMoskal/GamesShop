package gabriel.games.model.api;

import gabriel.games.model.util.*;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GamePlatformTest {

    private GamePlatform actual;

    @Test
    public void constructorTest() {
        actual = new GamePlatform(new Date(0));
        // TODO check if id is created
        assertEquals(new Date(0), actual.getReleaseDate());
    }

    @Test
    public void validGamePlatformGiven_HasNoErrors() {
        actual = new GamePlatform(new Date(0));
        EntityValidator.assertErrors(actual, 0);
    }

    @Test
    public void releaseDateIsNotNull() {
        actual = new GamePlatform(null);
        EntityValidator.assertPropertyErrors(actual, "releaseDate");
    }

    @Test
    public void getGameName_ReturnsGameName() {
        actual = new GamePlatform(mock(Date.class));
        Game game = mock(Game.class);
        when(game.getName()).thenReturn("name");
        actual.setGame(game);

        assertEquals("name", actual.getGameName());
    }

    @Test
    public void getPlatformName_ReturnsPlatformName() {
        actual = new GamePlatform(mock(Date.class));
        Platform platform = mock(Platform.class);
        when(platform.getName()).thenReturn("name");
        actual.setPlatform(platform);

        assertEquals("name", actual.getPlatformName());
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(GamePlatform.class)
                .suppress(Warning.SURROGATE_KEY)
                .withPrefabValues(Game.class, mock(Game.class), mock(Game.class))
                .withPrefabValues(Platform.class, mock(Platform.class), mock(Platform.class))
                .verify();
    }
}
