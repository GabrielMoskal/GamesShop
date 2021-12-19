package gabriel.games.model.api;

import gabriel.games.model.util.*;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GamePlatformTest {

    private GamePlatform gamePlatform;
    private EntityValidator<GamePlatform> validator;
    private ReflectionSetter<GamePlatform> setter;

    @BeforeEach
    public void setUp() {
        this.gamePlatform = Models.makeGamePlatform(1L);
        this.validator = new EntityValidator<>(this.gamePlatform);
        this.setter = new ReflectionSetter<>(this.gamePlatform);
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
    public void getPlatformName_ReturnsPlatformName() {
        assertGetPlatformCorrect("platform name");
    }

    private void assertGetPlatformCorrect(String expected) {
        Platform platform = mockPlatform(expected);
        setter.set("platform", platform);
        String actual = gamePlatform.getPlatformName();

        assertEquals(expected, actual);
    }

    private Platform mockPlatform(String name) {
        Platform platform = mock(Platform.class);
        when(platform.getName()).thenReturn(name);
        return platform;
    }

    @Test
    public void getPlatformName_DifferentPlatformGiven_ShouldReturnPlatformName() {
        assertGetPlatformCorrect("different name");
    }

    @Test
    public void getPlatformName_NullPlatformGiven_ShouldThrowException() {
        setter.set("platform", null);
        Throwable exception = assertThrows(NullPointerException.class, () -> gamePlatform.getPlatformName());
        String expected = "platform should not be null";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(GamePlatform.class)
                .suppress(Warning.SURROGATE_KEY)
                .withPrefabValues(Game.class, mock(Game.class), mock(Game.class))
                .withPrefabValues(Platform.class, mock(Platform.class), mock(Platform.class))
                .verify();
    }
}
