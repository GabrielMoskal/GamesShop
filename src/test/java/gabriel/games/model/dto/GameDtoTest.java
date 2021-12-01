package gabriel.games.model.dto;

import gabriel.games.model.dto.util.EntityValidator;
import gabriel.games.model.dto.util.GenericWord;
import gabriel.games.model.dto.util.ReflectionSetter;
import gabriel.games.util.GameUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class GameDtoTest {

    private ReflectionSetter<GameDto> setter;
    private EntityValidator<GameDto> validator;
    private GenericWord genericWord;

    @BeforeEach
    public void setUp() {
        GameDto gameDto = GameUtil.makeValidGameDto();
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
        setter.setValue("uri", null);
        validator.assertErrors(1);
    }

    @Test
    public void uriShouldBeAtLeast1CharacterLong() {
        setter.setValue("uri", "");
        validator.assertErrors(1);
    }

    @Test
    public void uriShouldBeMax128CharacterLong() {
        setter.setValue("uri", genericWord.make(129));
        validator.assertErrors(1);
    }

    @Test
    public void nameShouldNotBeNull() {
        setter.setValue("name", null);
        validator.assertErrors(1);
    }

    @Test
    public void nameShouldBeAtLeast1CharacterLong() {
        setter.setValue("name", "");
        validator.assertErrors(1);
    }

    @Test
    public void nameShouldBeMax128CharacterLong() {
        setter.setValue("name", genericWord.make(129));
        validator.assertErrors(1);
    }

    @Test
    public void descriptionShouldNotBeNull() {
        setter.setValue("description", null);
        validator.assertErrors(1);
    }

    @Test
    public void descriptionShouldBeMax1024CharacterLong() {
        setter.setValue("description", genericWord.make(1025));
        validator.assertErrors(1);
    }

    @Test
    public void webpageShouldNotBeNull() {
        setter.setValue("webpage", null);
        validator.assertErrors(1);
    }

    @Test
    public void webpageShouldBeMax256CharacterLong() {
        setter.setValue("webpage", genericWord.make(257));
        validator.assertErrors(1);
    }

    @Test
    public void playerRatingShouldNotBeNull() {
        setter.setValue("playerRating", null);
        validator.assertErrors(1);
    }

    @Test
    public void playerRatingShouldNotBeNegative() {
        setter.setValue("playerRating", -0.5);
        validator.assertErrors(1);
    }

    @Test
    public void playerRatingShouldBeMax10() {
        setter.setValue("playerRating", 10.1);
        validator.assertErrors(1);
    }

    @Test
    public void reviewerRatingShouldNotBeNull() {
        setter.setValue("reviewerRating", null);
        validator.assertErrors(1);
    }

    @Test
    public void reviewerRatingShouldNotBeNegative() {
        setter.setValue("reviewerRating", -0.1);
        validator.assertErrors(1);
    }

    @Test
    public void reviewerRatingShouldBeMax10() {
        setter.setValue("reviewerRating", 11.0);
        validator.assertErrors(1);
    }

    @Test
    public void platformsShouldNotBeNull() {
        setter.setValue("platforms", null);
        validator.assertErrors(1);
    }

    @Test
    public void platformsContentShouldNotBeNull() {
        List<String> platforms = new ArrayList<>(Arrays.asList(null, null));
        setter.setValue("platforms", platforms);
        validator.assertErrors(2);
    }

    @Test
    public void platformsShouldNotBeEmpty() {
        setter.setValue("platforms", Collections.emptyList());
        validator.assertErrors(1);
    }

    @Test
    public void platformsContentShouldBeAtLeast1CharacterLong() {
        setter.setValue("platforms", Arrays.asList("", "valid", ""));
        validator.assertErrors(2);
    }

    @Test
    public void platformsContentShouldBeMax50CharacterLong() {
        setter.setValue("platforms", Collections.singletonList(genericWord.make(51)));
        validator.assertErrors(1);
    }

    @Test
    public void releaseDateShouldNotBeNull() {
        setter.setValue("releaseDate", null);
        validator.assertErrors(1);
    }

    @Test
    public void producerShouldNotBeNull() {
        setter.setValue("producer", null);
        validator.assertErrors(1);
    }

    @Test
    public void producerShouldBeMax128CharacterLong() {
        setter.setValue("producer", genericWord.make(129));
        validator.assertErrors(1);
    }

    @Test
    public void publisherShouldNotBeNull() {
        setter.setValue("publisher", null);
        validator.assertErrors(1);
    }

    @Test
    public void publisherShouldBeMax128CharacterLong() {
        setter.setValue("publisher", genericWord.make(129));
        validator.assertErrors(1);
    }
}
