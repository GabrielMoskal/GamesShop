package gabriel.games.model.api;

import gabriel.games.model.util.*;
import nl.jqno.equalsverifier.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class GameTest {

    private Game actual;

    @BeforeEach
    public void setUp() {
        this.actual = new Game("valid name", "valid-uri");
    }

    @Test
    public void constructorTest() {
        assertEquals("valid name", actual.getName());
        assertEquals("valid-uri", actual.getUri());
    }

    @Test
    public void validGameGiven_HasNoErrors() {
        EntityValidator.assertErrors(actual,0);
    }

    @Test
    public void nameShouldNotBeNull() {
        actual.setName(null);
        EntityValidator.assertErrors(actual,1);
    }

    @Test
    public void nameShouldBeAtLeast1CharacterLong() {
        actual.setName("");
        EntityValidator.assertErrors(actual,1);
    }

    @Test
    public void nameShouldBeMax128CharacterLong() {
        actual.setName(GenericWord.make(129));
        EntityValidator.assertErrors(actual,1);
    }

    @Test
    public void uriShouldNotBeNull() {
        actual.setUri(null);
        EntityValidator.assertErrors(actual,1);
    }

    @Test
    public void uriShouldBeAtLeast1CharacterLong() {
        actual.setUri("");
        EntityValidator.assertErrors(actual,1);
    }

    @Test
    public void uriShouldBeMax128CharacterLong() {
        actual.setUri(GenericWord.make(129));
        EntityValidator.assertErrors(actual,1);
    }

    @Test
    public void uriShouldHaveNoWhiteSpaces() {
        actual.setUri("some white spaces");
        EntityValidator.assertErrors(actual,1);
    }

    @Test
    public void uriShouldBeLowercase() {
        actual.setUri("Uppercase");
        EntityValidator.assertErrors(actual,1);
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Game.class)
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .withPrefabValues(Game.class, mock(Game.class), mock(Game.class))
                .withPrefabValues(GamePlatform.class, mock(GamePlatform.class), mock(GamePlatform.class))
                .withPrefabValues(Company.class, mock(Company.class), mock(Company.class))
                .verify();
    }

}
