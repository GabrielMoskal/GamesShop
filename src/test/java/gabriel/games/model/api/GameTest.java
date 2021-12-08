package gabriel.games.model.api;

import gabriel.games.model.util.EntityValidator;
import gabriel.games.model.util.GenericWord;
import gabriel.games.model.util.ReflectionSetter;
import gabriel.games.model.util.Models;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    private EntityValidator<Game> validator;
    private ReflectionSetter<Game> setter;
    private GenericWord genericWord;

    @BeforeEach
    public void setUp() {
        Game game = new Game(1L, "valid name");
        this.validator = new EntityValidator<>(game);
        this.setter = new ReflectionSetter<>(game);
        this.genericWord = new GenericWord();
        setter.set("platforms", Models.makeValidGamePlatforms());
        setter.set("companies", Models.makeValidCompanies());
    }

    @Test
    public void constructorTest() {
        assertEquals(1L, setter.getFieldValue("id"));
        assertEquals("valid name", setter.getFieldValue("name"));
        assertEquals("valid-name", setter.getFieldValue("uri"));
    }

    @Test
    public void validGameGiven_HasNoErrors() {
        validator.assertErrors(0);
    }

    @Test
    public void nameShouldNotBeNull() {
        setter.set("name", null);
        validator.assertErrors(1);
    }

    @Test
    public void nameShouldBeAtLeast1CharacterLong() {
        setter.set("name", "");
        validator.assertErrors(1);
    }

    @Test
    public void nameShouldBeMax128CharacterLong() {
        setter.set("name", genericWord.make(129));
        validator.assertErrors(1);
    }

    @Test
    public void uriShouldNotBeNull() {
        setter.set("uri", null);
        validator.assertErrors(1);
    }

    @Test
    public void uriShouldBeAtLeast1CharacterLong() {
        setter.set("uri", "");
        validator.assertErrors(1);
    }

    @Test
    public void uriShouldBeMax128CharacterLong() {
        setter.set("uri", genericWord.make(129));
        validator.assertErrors(1);
    }

    @Test
    public void uriShouldHaveNoWhiteSpaces() {
        setter.set("uri", "some white spaces");
        validator.assertErrors(1);
    }

    @Test
    public void uriShouldBeLowercase() {
        setter.set("uri", "Uppercase");
        validator.assertErrors(1);
    }

    @Test
    public void platformsShouldNotBeNull() {
        setter.set("platforms", null);
        validator.assertErrors(1);
    }

    @Test
    public void platformsShouldNotBeEmpty() {
        setter.set("platforms", Collections.emptySet());
        validator.assertErrors(1);
    }

    @Test
    public void companiesShouldNotBeNull() {
        setter.set("companies", null);
        validator.assertErrors(1);
    }

    @Test
    public void companiesShouldNotBeEmpty() {
        setter.set("companies", Collections.emptySet());
        validator.assertErrors(1);
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Game.class)
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .withPrefabValues(Game.class, Models.makeGame(1L), Models.makeGame(2L))
                .withPrefabValues(GamePlatform.class, Models.makeGamePlatform(1L), Models.makeGamePlatform(2L))
                .withPrefabValues(Company.class, Models.makeCompany(1L), Models.makeCompany(2L))
                .verify();
    }

}
