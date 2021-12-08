package gabriel.games.model.api.dto;

import gabriel.games.model.util.EntityValidator;
import gabriel.games.model.util.GenericWord;
import gabriel.games.model.util.ReflectionSetter;
import gabriel.games.util.Models;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class GameDtoTest {

    private ReflectionSetter<GameDto> setter;
    private EntityValidator<GameDto> validator;
    private GenericWord genericWord;

    @BeforeEach
    public void setUp() {
        GameDto gameDto = Models.makeGameDto("Multiple words value");
        this.setter = new ReflectionSetter<>(gameDto);
        this.validator = new EntityValidator<>(gameDto);
        this.genericWord = new GenericWord();
    }

    @Test
    public void validGameDtoHasNoErrors() {
        validator.assertErrors(0);
    }

    @Test
    public void uriShouldNotBeNull() {
        setter.set("uri", null);
        validator.assertErrors(1);
    }

    @Test
    public void uriShouldBeAtLeast1CharacterLong() {
        setter.set("uri", "");
        validator.assertErrors(1);
    }

    @Test
    public void uriShouldBeMax128CharacterLong() {
        setter.set("uri", genericWord.make(129));
        validator.assertErrors(1);
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
    public void nameShouldBeMax128CharacterLong() {
        setter.set("name", genericWord.make(129));
        validator.assertErrors(1);
    }

    @Test
    public void descriptionShouldNotBeNull() {
        setter.set("description", null);
        validator.assertErrors(1);
    }

    @Test
    public void descriptionShouldBeMax1024CharacterLong() {
        setter.set("description", genericWord.make(1025));
        validator.assertErrors(1);
    }

    @Test
    public void webpageShouldNotBeNull() {
        setter.set("webpage", null);
        validator.assertErrors(1);
    }

    @Test
    public void webpageShouldBeMax256CharacterLong() {
        setter.set("webpage", genericWord.make(257));
        validator.assertErrors(1);
    }

    @Test
    public void playerRatingShouldNotBeNull() {
        setter.set("playerRating", null);
        validator.assertErrors(1);
    }

    @Test
    public void playerRatingShouldNotBeNegative() {
        setter.set("playerRating", -0.5);
        validator.assertErrors(1);
    }

    @Test
    public void playerRatingShouldBeMax10() {
        setter.set("playerRating", 10.1);
        validator.assertErrors(1);
    }

    @Test
    public void reviewerRatingShouldNotBeNull() {
        setter.set("reviewerRating", null);
        validator.assertErrors(1);
    }

    @Test
    public void reviewerRatingShouldNotBeNegative() {
        setter.set("reviewerRating", -0.1);
        validator.assertErrors(1);
    }

    @Test
    public void reviewerRatingShouldBeMax10() {
        setter.set("reviewerRating", 11.0);
        validator.assertErrors(1);
    }

    @Test
    public void platformsShouldNotBeNull() {
        setter.set("platforms", null);
        validator.assertErrors(1);
    }

    @Test
    public void platformsShouldNotBeEmpty() {
        setter.set("platforms", Collections.emptyList());
        validator.assertErrors(1);
    }

    @Test
    public void platformsAreValidated() {
        setter.set("platforms", Collections.singletonList(new GamePlatformDto(null, null)));
        validator.assertErrors(2);
    }

    @Test
    public void producerShouldNotBeNull() {
        setter.set("producer", null);
        validator.assertErrors(1);
    }

    @Test
    public void producerShouldBeMax128CharacterLong() {
        setter.set("producer", genericWord.make(129));
        validator.assertErrors(1);
    }

    @Test
    public void publisherShouldNotBeNull() {
        setter.set("publisher", null);
        validator.assertErrors(1);
    }

    @Test
    public void publisherShouldBeMax128CharacterLong() {
        setter.set("publisher", genericWord.make(129));
        validator.assertErrors(1);
    }
}
