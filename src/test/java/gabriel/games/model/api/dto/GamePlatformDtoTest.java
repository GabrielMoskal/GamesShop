package gabriel.games.model.api.dto;

import gabriel.games.model.util.*;
import org.junit.jupiter.api.*;

import java.sql.Date;

public class GamePlatformDtoTest {

    private EntityValidator<GamePlatformDto> validator;
    private ReflectionSetter<GamePlatformDto> setter;

    @BeforeEach
    public void setUp() {
        GamePlatformDto gamePlatformDto = new GamePlatformDto("platformName", new Date(System.currentTimeMillis()));
        this.validator = new EntityValidator<>(gamePlatformDto);
        this.setter = new ReflectionSetter<>(gamePlatformDto);
    }

    @Test
    public void validGamePlatformDtoGiven_HasNoErrors() {
        validator.assertErrors(0);
    }

    @Test
    public void platformNameShouldNotBeNull() {
        setter.set("platformName", null);
        validator.assertErrors(1);
    }

    @Test
    public void platformNameShouldBeAtLeast1CharacterLong() {
        setter.set("platformName", "");
        validator.assertErrors(1);
    }

    @Test
    public void platformNameShouldBeMax50CharacterLong() {
        GenericWord genericWord = new GenericWord();
        setter.set("platformName", genericWord.make(51));
        validator.assertErrors(1);
    }

    @Test
    public void releaseDateShouldNotBeNull() {
        setter.set("releaseDate", null);
        validator.assertErrors(1);
    }
}
