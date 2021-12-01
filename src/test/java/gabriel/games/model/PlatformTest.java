package gabriel.games.model;

import gabriel.games.model.dto.util.EntityValidator;
import gabriel.games.model.dto.util.GenericWord;
import gabriel.games.util.ModelUtil;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlatformTest {

    private Platform platform;
    private EntityValidator<Platform> validator;

    @BeforeEach
    public void setUp() {
        this.platform = ModelUtil.makePlatform(1L);
        this.validator = new EntityValidator<>(this.platform);
    }

    @Test
    public void validPlatformGiven_HasNoErrors() {
        validator.assertErrors(0);
    }

    @Test
    public void nameShouldNotBeNull() {
        platform.setName(null);
        validator.assertErrors(1);
    }

    @Test
    public void nameShouldBeAtLeast1CharacterLong() {
        platform.setName("");
        validator.assertErrors(1);
    }

    @Test
    public void nameShouldBeMax50CharactersLong() {
        GenericWord genericWord = new GenericWord();
        platform.setName(genericWord.make(51));
        validator.assertErrors(1);
    }

    @Test
    public void gamesShouldNotBeNull() {
        platform.setGames(null);
        validator.assertErrors(1);
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Platform.class)
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .withPrefabValues(GamePlatform.class, ModelUtil.makeGamePlatform(1L), ModelUtil.makeGamePlatform(2L))
                .verify();
    }
}
