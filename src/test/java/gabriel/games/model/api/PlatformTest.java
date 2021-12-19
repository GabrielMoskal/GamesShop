package gabriel.games.model.api;

import gabriel.games.model.util.*;
import nl.jqno.equalsverifier.*;
import org.junit.jupiter.api.*;

import java.util.Collections;

import static org.mockito.Mockito.mock;

public class PlatformTest {

    private EntityValidator<Platform> validator;
    private ReflectionSetter<Platform> setter;

    @BeforeEach
    public void setUp() {
        Platform platform = new Platform(1L, "name", Collections.emptySet());
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
                .withPrefabValues(GamePlatform.class, mock(GamePlatform.class), mock(GamePlatform.class))
                .verify();
    }
}
