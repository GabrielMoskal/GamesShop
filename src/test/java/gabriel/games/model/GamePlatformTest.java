package gabriel.games.model;

import gabriel.games.model.dto.util.EntityValidator;
import gabriel.games.model.embedded.GamePlatformKey;
import gabriel.games.util.ModelUtil;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

public class GamePlatformTest {

    private GamePlatform gamePlatform;
    private EntityValidator<GamePlatform> validator;

    @BeforeEach
    public void setUp() {
        this.gamePlatform = new GamePlatform();
        this.gamePlatform.setId(new GamePlatformKey(1L, 1L));
        this.gamePlatform.setReleaseDate(new Date(System.currentTimeMillis()));
        this.validator = new EntityValidator<>(this.gamePlatform);
    }

    @Test
    public void validGamePlatformGiven_HasNoErrors() {
        validator.assertErrors(0);
    }

    @Test
    public void releaseDateIsNotNull() {
        gamePlatform.setReleaseDate(null);
        validator.assertErrors(1);
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(GamePlatform.class)
                .suppress(Warning.SURROGATE_KEY)
                .withPrefabValues(Game.class, ModelUtil.makeGame(1), ModelUtil.makeGame(2))
                .withPrefabValues(Platform.class, ModelUtil.makePlatform(1), ModelUtil.makePlatform(2))
                .verify();
    }
}
