package gabriel.games.model;

import gabriel.games.model.dto.util.EntityValidator;
import gabriel.games.model.dto.util.GenericWord;
import gabriel.games.util.GameUtil;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameTest {

    private Game game;
    private EntityValidator<Game> validator;
    private GenericWord genericWord;

    @BeforeEach
    public void setUp() {
        this.game = new Game(1L, "valid_name", "valid_uri");
        this.game.setPlatforms(GameUtil.makeValidPlatforms());
        this.validator = new EntityValidator<>(this.game);
        this.genericWord = new GenericWord();
    }

    @Test
    public void defaultConstructorTest() {
        this.game = new Game();
        assertNotNull(game.getPlatforms());
    }

    @Test
    public void constructorTest() {
        assertEquals(1L, this.game.getId());
        assertEquals("valid_name", this.game.getName());
        assertEquals("valid_uri", this.game.getUri());
    }

    @Test
    public void validGameGiven_HasNoErrors() {
        validator.assertErrors(0);
    }

    @Test
    public void nameShouldNotBeNull() {
        game.setName(null);
        validator.assertErrors(1);
    }

    @Test
    public void nameShouldBeAtLeast1CharacterLong() {
        game.setName("");
        validator.assertErrors(1);
    }

    @Test
    public void nameShouldBeMax128CharacterLong() {
        game.setName(genericWord.make(129));
        validator.assertErrors(1);
    }

    @Test
    public void uriShouldNotBeNull() {
        game.setUri(null);
        validator.assertErrors(1);
    }

    @Test
    public void uriShouldBeAtLeast1CharacterLong() {
        game.setUri(genericWord.make(0));
        validator.assertErrors(1);
    }

    @Test
    public void uriShouldBeMax128CharacterLong() {
        game.setUri(genericWord.make(129));
        validator.assertErrors(1);
    }

    @Test
    public void uriShouldHaveNoWhiteSpaces() {
        game.setUri("some white spaces");
        validator.assertErrors(1);
    }

    @Test
    public void uriShouldBeLowercase() {
        game.setUri("Uppercase");
        validator.assertErrors(1);
    }

    @Test
    public void platformsShouldNotBeNull() {
        game.setPlatforms(null);
        validator.assertErrors(1);
    }

    @Test
    public void platformsShouldNotBeEmpty() {
        game.setPlatforms(Collections.emptySet());
        validator.assertErrors(1);
    }

    @Test
    public void equalsContract() {
        Game prefab1 = GameUtil.makGame(1);
        Game prefab2 = GameUtil.makGame(2);

        EqualsVerifier.forClass(Game.class)
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .withPrefabValues(Game.class, prefab1, prefab2)
                .verify();
    }

}
