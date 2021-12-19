package gabriel.games.model.api;

import gabriel.games.model.util.*;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class GameTest {

    private GenericWord genericWord;

    @BeforeEach
    public void setUp() {
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
        Game expected = makeGame("valid name");
        EntityValidatorNew.assertErrors(expected,0);
    }

    @Test
    public void nameShouldNotBeNull() {
        assertThrows(NullPointerException.class, () -> makeGame(null));
    }

    @Test
    public void nameShouldBeAtLeast1CharacterLong() {
        Game expected = makeGame("");
        EntityValidatorNew.assertErrors(expected,2);
    }

    @Test
    public void nameShouldBeMax128CharacterLong() {
        Game expected = makeGame(genericWord.make(129));
        EntityValidatorNew.assertErrors(expected,2);
    }

    @Test
    public void uriShouldNotBeNull() {
        Game expected = makeGame("valid name");
        assertNotNull(expected.getUri());
        EntityValidatorNew.assertErrors(expected,0);
    }

    @Test
    public void uriShouldBeAtLeast1CharacterLong() {
        Game expected = makeGame("");
        EntityValidatorNew.assertErrors(expected,2);
    }

    @Test
    public void uriShouldBeMax128CharacterLong() {
        Game expected = makeGame(genericWord.make(129));
        EntityValidatorNew.assertErrors(expected,2);
    }

    @Test
    public void uriShouldHaveNoWhiteSpaces() {
        Game expected = makeGame("some white spaces");
        EntityValidatorNew.assertErrors(expected,0);
    }

    @Test
    public void uriShouldBeLowercase() {
        Game expected = makeGame("Uppercase");
        EntityValidatorNew.assertErrors(expected,0);
    }

    @Test
    public void platformsShouldNotBeNull() {
        Game expected = makeGame("valid name");
        expected.setPlatforms(null);
        EntityValidatorNew.assertErrors(expected,1);
    }

    @Test
    public void platformsShouldNotBeEmpty() {
        Game expected = makeGame("valid name");
        expected.setPlatforms(Collections.emptySet());
        EntityValidatorNew.assertErrors(expected,1);
    }

    @Test
    public void companiesShouldNotBeNull() {
        Game expected = makeGame("valid name");
        expected.setCompanies(null);
        EntityValidatorNew.assertErrors(expected,1);
    }

    @Test
    public void companiesShouldNotBeEmpty() {
        Game expected = makeGame("valid name");
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
