package gabriel.games.model.api;

import gabriel.games.model.util.*;
import nl.jqno.equalsverifier.*;
import org.junit.jupiter.api.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class GameTest {

    private Game expected;
    private GenericWord genericWord;

    @BeforeEach
    public void setUp() {
        this.expected = makeGame("valid name");
        this.genericWord = new GenericWord();
    }

    private Game makeGame(String name) {
        Game game = new Game(name);
        game.setPlatforms(Models.makeValidGamePlatforms());
        game.setCompanies(Models.makeValidCompanies());
        return game;
    }

    @Test
    public void constructorTest() {
        Game expected = makeGame("valid name");
        assertEquals("valid name", expected.getName());
        assertEquals("valid-name", expected.getUri());
    }

    @Test
    public void validGameGiven_HasNoErrors() {
        EntityValidatorNew.assertErrors(expected,0);
    }

    @Test
    public void nameShouldNotBeNull() {
        expected.setName(null);
        EntityValidatorNew.assertErrors(expected,1);
    }

    @Test
    public void nameShouldBeAtLeast1CharacterLong() {
        expected.setName("");
        EntityValidatorNew.assertErrors(expected,1);
    }

    @Test
    public void nameShouldBeMax128CharacterLong() {
        expected.setName(genericWord.make(129));
        EntityValidatorNew.assertErrors(expected,1);
    }

    @Test
    public void uriShouldNotBeNull() {
        expected.setUri(null);
        EntityValidatorNew.assertErrors(expected,1);
    }

    @Test
    public void uriShouldBeAtLeast1CharacterLong() {
        expected.setUri("");
        EntityValidatorNew.assertErrors(expected,1);
    }

    @Test
    public void uriShouldBeMax128CharacterLong() {
        expected.setUri(genericWord.make(129));
        EntityValidatorNew.assertErrors(expected,1);
    }

    @Test
    public void uriShouldHaveNoWhiteSpaces() {
        expected.setUri("some white spaces");
        EntityValidatorNew.assertErrors(expected,1);
    }

    @Test
    public void uriShouldBeLowercase() {
        expected.setUri("Uppercase");
        EntityValidatorNew.assertErrors(expected,1);
    }

    @Test
    public void platformsShouldNotBeNull() {
        expected.setPlatforms(null);
        EntityValidatorNew.assertErrors(expected,1);
    }

    @Test
    public void platformsShouldNotBeEmpty() {
        expected.setPlatforms(Collections.emptySet());
        EntityValidatorNew.assertErrors(expected,1);
    }

    @Test
    public void companiesShouldNotBeNull() {
        expected.setCompanies(null);
        EntityValidatorNew.assertErrors(expected,1);
    }

    @Test
    public void companiesShouldNotBeEmpty() {
        expected.setCompanies(Collections.emptySet());
        EntityValidatorNew.assertErrors(expected,1);
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
