package gabriel.games.model.api;

import gabriel.games.model.util.EntityValidator;
import gabriel.games.model.util.ReflectionSetter;
import gabriel.games.util.Models;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GamePlatformTest {

    private EntityValidator<GamePlatform> validator;
    private ReflectionSetter<GamePlatform> setter;

    @BeforeEach
    public void setUp() {
        GamePlatform gamePlatform = Models.makeGamePlatform(1L);
        this.validator = new EntityValidator<>(gamePlatform);
        this.setter = new ReflectionSetter<>(gamePlatform);
    }

    @Test
    public void validGamePlatformGiven_HasNoErrors() {
        validator.assertErrors(0);
    }

    @Test
    public void releaseDateIsNotNull() {
        setter.set("releaseDate", null);
        validator.assertErrors(1);
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(GamePlatform.class)
                .suppress(Warning.SURROGATE_KEY)
                .withPrefabValues(Game.class, Models.makeGame(1), Models.makeGame(2))
                .withPrefabValues(Platform.class, Models.makePlatform(1), Models.makePlatform(2))
                .verify();
    }
}
