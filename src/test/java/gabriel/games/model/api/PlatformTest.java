package gabriel.games.model.api;

import gabriel.games.model.util.*;
import nl.jqno.equalsverifier.*;
import org.junit.jupiter.api.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class PlatformTest {

    private Platform actual;

    @BeforeEach
    public void setUp() {
        actual = new Platform("name", "uri");
    }

    @Test
    public void constructorTest() {
        assertEquals("name", actual.getName());
        assertEquals("uri", actual.getUri());
        assertEquals(Collections.emptySet(), actual.getGames());
    }

    @Test
    public void validPlatformGiven_HasNoErrors() {
        EntityValidator.assertErrors(actual, 0);
    }

    @Test
    public void nameShouldNotBeNull() {
        actual.setName(null);
        EntityValidator.assertPropertyErrors(actual, "name");
    }

    @Test
    public void nameShouldBeAtLeast1CharacterLong() {
        actual.setName("");
        EntityValidator.assertPropertyErrors(actual, "name");
    }

    @Test
    public void nameShouldBeMax50CharactersLong() {
        actual.setName(GenericWord.make(51));
        EntityValidator.assertPropertyErrors(actual, "name");
    }

    @Test
    public void uriShouldNotBeNull() {
        actual.setUri(null);
        EntityValidator.assertPropertyErrors(actual, "uri");
    }

    @Test
    public void uriShouldBeAtLeast1CharacterLong() {
        actual.setUri("");
        EntityValidator.assertPropertyErrors(actual, "uri");
    }

    @Test
    public void uriShouldBeMax50CharactersLong() {
        actual.setUri(GenericWord.make(51));
        EntityValidator.assertPropertyErrors(actual, "uri");
    }

    @Test
    public void uriShouldHaveValidForm() {
        actual.setUri("Invalid Uri");
        EntityValidator.assertPropertyErrors(actual, "uri");
    }

    @Test
    public void gamesShouldNotBeNull() {
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
