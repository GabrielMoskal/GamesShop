package gabriel.games.model;

import gabriel.games.model.dto.util.EntityValidator;
import gabriel.games.model.dto.util.GenericWord;
import gabriel.games.util.GameUtil;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PlatformTest {

    private Platform platform;
    private EntityValidator<Platform> validator;

    @BeforeEach
    public void setUp() {
        this.platform = GameUtil.makePlatform(1L);
        this.validator = new EntityValidator<>(this.platform);
    }

    @Test
    public void defaultConstructorTest() {
        platform = new Platform();
        assertNotNull(platform.getGames());
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
        Game prefab1 = GameUtil.makGame(1);
        Game prefab2 = GameUtil.makGame(2);

        EqualsVerifier.forClass(Platform.class)
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .withPrefabValues(Game.class, prefab1, prefab2)
                .verify();
    }
}
