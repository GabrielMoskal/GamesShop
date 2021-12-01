package gabriel.games.model;

import gabriel.games.model.dto.util.EntityValidator;
import gabriel.games.model.dto.util.GenericWord;
import gabriel.games.util.ModelUtil;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    private Game game;
    private EntityValidator<Game> validator;
    private GenericWord genericWord;

    @BeforeEach
    public void setUp() {
        this.game = new Game(1L, "valid_name", "valid_uri");
        this.game.setPlatforms(ModelUtil.makeValidGamePlatforms());
        this.game.setCompanies(ModelUtil.makeValidCompanies());
        this.validator = new EntityValidator<>(this.game);
        this.genericWord = new GenericWord();
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
    public void companiesShouldNotBeNull() {
        game.setCompanies(null);
        validator.assertErrors(1);
    }

    @Test
    public void companiesShouldNotBeEmpty() {
        game.setCompanies(Collections.emptySet());
        validator.assertErrors(1);
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Game.class)
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .withPrefabValues(Game.class, ModelUtil.makeGame(1L), ModelUtil.makeGame(2L))
                .withPrefabValues(GamePlatform.class, ModelUtil.makeGamePlatform(1L), ModelUtil.makeGamePlatform(2L))
                .withPrefabValues(Company.class, ModelUtil.makeCompany(1L), ModelUtil.makeCompany(2L))
                .verify();
    }

}
