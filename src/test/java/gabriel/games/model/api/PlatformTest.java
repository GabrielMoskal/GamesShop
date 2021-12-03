package gabriel.games.model.api;

import gabriel.games.model.util.EntityValidator;
import gabriel.games.model.util.GenericWord;
import gabriel.games.model.util.ReflectionSetter;
import gabriel.games.util.Models;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlatformTest {

    private EntityValidator<Platform> validator;
    private ReflectionSetter<Platform> setter;

    @BeforeEach
    public void setUp() {
        Platform platform = Models.makePlatform(1L);
        this.validator = new EntityValidator<>(platform);
        this.setter = new ReflectionSetter<>(platform);
    }

    @Test
    public void validPlatformGiven_HasNoErrors() {
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
    public void nameShouldBeMax50CharactersLong() {
        GenericWord genericWord = new GenericWord();
        setter.set("name", genericWord.make(51));
        validator.assertErrors(1);
    }

    @Test
    public void gamesShouldNotBeNull() {
        setter.set("games", null);
        validator.assertErrors(1);
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Platform.class)
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .withPrefabValues(GamePlatform.class, Models.makeGamePlatform(1L), Models.makeGamePlatform(2L))
                .verify();
    }
}
