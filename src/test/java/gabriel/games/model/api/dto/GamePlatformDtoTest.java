package gabriel.games.model.api.dto;

import gabriel.games.model.util.*;
import org.junit.jupiter.api.*;

import java.sql.Date;

public class GamePlatformDtoTest {

    private GamePlatformDto actual;

    @Test
    public void validGamePlatformDtoGiven_HasNoErrors() {
        actual = new GamePlatformDto("gameName", "platformName", new Date(0));
        EntityValidator.assertErrors(actual, 0);
    }

    @Test
    public void gameNameShouldNotBeNull() {
        actual = new GamePlatformDto(null, null, null);
        EntityValidator.assertPropertyErrors(actual, "gameName");
    }

    @Test
    public void gameNameShouldBeAtLeast1CharacterLong() {
        actual = new GamePlatformDto("", null, null);
        EntityValidator.assertPropertyErrors(actual, "gameName");
    }

    @Test
    public void gameNameShouldBeMax128CharactersLong() {
        actual = new GamePlatformDto(GenericWord.make(129), null, null);
        EntityValidator.assertPropertyErrors(actual, "gameName");
    }

    @Test
    public void platformNameShouldNotBeNull() {
        actual = new GamePlatformDto(null, null, null);
        EntityValidator.assertPropertyErrors(actual, "platformName");
    }

    @Test
    public void platformNameShouldBeAtLeast1CharacterLong() {
        actual = new GamePlatformDto(null, "", null);
        EntityValidator.assertPropertyErrors(actual, "platformName");
    }

    @Test
    public void platformNameShouldBeMax50CharactersLong() {
        actual = new GamePlatformDto(null, GenericWord.make(51), null);
        EntityValidator.assertPropertyErrors(actual, "platformName");
    }

    @Test
    public void releaseDateShouldNotBeNull() {
        actual = new GamePlatformDto(null, "name", null);
        EntityValidator.assertPropertyErrors(actual, "releaseDate");
    }
}
