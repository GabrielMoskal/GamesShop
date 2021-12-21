package gabriel.games.model.api;

import gabriel.games.model.util.*;
import nl.jqno.equalsverifier.*;
import org.junit.jupiter.api.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class PlatformTest {

    private Platform actual;

    @Test
    public void constructorTest() {
        actual = new Platform("name");
        assertEquals("name", actual.getName());
        assertEquals(Collections.emptySet(), actual.getGames());
    }

    @Test
    public void validPlatformGiven_HasNoErrors() {
        actual = new Platform("name");
        EntityValidator.assertErrors(actual, 0);
    }

    @Test
    public void nameShouldNotBeNull() {
        actual = new Platform(null);
        EntityValidator.assertErrors(actual, 1);
    }

    @Test
    public void nameShouldBeAtLeast1CharacterLong() {
        actual = new Platform("");
        EntityValidator.assertErrors(actual, 1);
    }

    @Test
    public void nameShouldBeMax50CharactersLong() {
        GenericWord genericWord = new GenericWord();
        actual = new Platform(genericWord.make(51));
        EntityValidator.assertErrors(actual, 1);
    }

    @Test
    public void gamesShouldNotBeNull() {
        actual = new Platform("name");
        EntityValidator.assertErrors(actual, 0);
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Platform.class)
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .withPrefabValues(GamePlatform.class, mock(GamePlatform.class), mock(GamePlatform.class))
                .verify();
    }
}
