package gabriel.games.model.api.dto;

import gabriel.games.model.util.EntityValidator;
import gabriel.games.model.util.GenericWord;
import org.junit.jupiter.api.Test;

public class PlatformDtoTest {

    @Test
    public void nameShouldNotBeNull() {
        PlatformDto actual = new PlatformDto(null, "uri");
        EntityValidator.assertPropertyErrors(actual, "name");
    }

    @Test
    public void nameShouldBeAtLeast1CharacterLong() {
        PlatformDto actual = new PlatformDto("", "uri");
        EntityValidator.assertPropertyErrors(actual, "name");
    }

    @Test
    public void nameShouldBeMax50CharactersLong() {
        PlatformDto actual = new PlatformDto(GenericWord.make(51), "uri");
        EntityValidator.assertPropertyErrors(actual, "name");
    }

    @Test
    public void uriShouldNotBeNull() {
        PlatformDto actual = new PlatformDto("name", null);
        EntityValidator.assertPropertyErrors(actual, "uri");
    }

    @Test
    public void uriShouldBeAtLeast1CharacterLong() {
        PlatformDto actual = new PlatformDto("name", "");
        EntityValidator.assertPropertyErrors(actual, "uri");
    }

    @Test
    public void uriShouldBeMax50CharactersLong() {
        PlatformDto actual = new PlatformDto("name", GenericWord.make(51));
        EntityValidator.assertPropertyErrors(actual, "uri");
    }
}
