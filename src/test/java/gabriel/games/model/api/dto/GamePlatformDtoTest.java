package gabriel.games.model.api.dto;

import gabriel.games.model.util.*;
import org.junit.jupiter.api.*;

import java.sql.Date;

public class GamePlatformDtoTest {

    private GamePlatformDto actual;

    @Test
    public void validGamePlatformDtoGiven_HasNoErrors() {
        actual = new GamePlatformDto("platformName", new Date(0));
        EntityValidatorNew.assertErrors(actual, 0);
    }

    @Test
    public void platformNameShouldNotBeNull() {
        actual = new GamePlatformDto(null, new Date(0));
        EntityValidatorNew.assertErrors(actual, 1);
    }

    @Test
    public void platformNameShouldBeAtLeast1CharacterLong() {
        actual = new GamePlatformDto("", new Date(0));
        EntityValidatorNew.assertErrors(actual, 1);
    }

    @Test
    public void platformNameShouldBeMax50CharacterLong() {
        GenericWord genericWord = new GenericWord();
        actual = new GamePlatformDto(genericWord.make(51), new Date(0));
        EntityValidatorNew.assertErrors(actual, 1);
    }

    @Test
    public void releaseDateShouldNotBeNull() {
        actual = new GamePlatformDto("name", null);
        EntityValidatorNew.assertErrors(actual, 1);
    }
}
