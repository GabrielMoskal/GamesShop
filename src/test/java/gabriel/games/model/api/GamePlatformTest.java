package gabriel.games.model.api;

import gabriel.games.model.util.*;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class GamePlatformTest {

    private GamePlatform actual;

    @Test
    public void constructorTest() {
        actual = new GamePlatform(new Platform("name", "uri"), new Date(0));
        assertEquals("name", actual.getPlatformName());
        assertEquals(new Date(0), actual.getReleaseDate());
    }

    private GamePlatform makeGamePlatform(String name, Date date) {
        return new GamePlatform(new Platform(name, "uri"), date);
    }

    @Test
    public void validGamePlatformGiven_HasNoErrors() {
        actual = makeGamePlatform("name", new Date(0));
        EntityValidator.assertErrors(actual, 0);
    }

    @Test
    public void releaseDateIsNotNull() {
        actual = makeGamePlatform("name", null);
        EntityValidator.assertErrors(actual, 1);
    }

    @Test
    public void getPlatformName_ReturnsPlatformName() {
        actual = makeGamePlatform("name", new Date(0));

        assertEquals("name", actual.getPlatformName());
    }

    @Test
    public void getPlatformName_DifferentPlatformGiven_ShouldReturnPlatformName() {
        actual = makeGamePlatform("different", new Date(0));

        assertEquals("different", actual.getPlatformName());
    }

    @Test
    public void getPlatformName_NullPlatformGiven_ShouldThrowException() {
        actual = new GamePlatform(null, new Date(0));

        Throwable exception = assertThrows(NullPointerException.class, () -> actual.getPlatformName());
        String expectedMsg = "platform should not be null";
        String actualMsg = exception.getMessage();

        assertEquals(expectedMsg, actualMsg);
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
