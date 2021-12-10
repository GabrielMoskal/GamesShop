package gabriel.games.model.api.dto;

import gabriel.games.model.util.EntityValidator;
import gabriel.games.model.util.GenericWord;
import gabriel.games.model.util.ReflectionSetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameDetailsDtoTest {

    private EntityValidator<GameDetailsDto> validator;
    private ReflectionSetter<GameDetailsDto> setter;
    private GenericWord genericWord;

    @BeforeEach
    public void setUp() {
        GameDetailsDto gameDetailsDto = GameDetailsDto.builder()
                .description("description")
                .webpage("www.webpage.com")
                .ratingPlayers(2.5)
                .ratingReviewer(4.0)
                .build();
        this.validator = new EntityValidator<>(gameDetailsDto);
        this.setter = new ReflectionSetter<>(gameDetailsDto);
        this.genericWord = new GenericWord();
    }

    @Test
    public void validGameDto_HasNoErrors() {
        validator.assertErrors(0);
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
    public void ratingPlayersShouldNotBeNegative() {
        setter.set("ratingPlayers", -0.5);
        validator.assertErrors(1);
    }

    @Test
    public void ratingPlayersShouldBeMax10() {
        setter.set("ratingPlayers", 10.1);
        validator.assertErrors(1);
    }

    @Test
    public void ratingReviewerShouldNotBeNegative() {
        setter.set("ratingReviewer", -0.1);
        validator.assertErrors(1);
    }

    @Test
    public void ratingReviewerShouldBeMax10() {
        setter.set("ratingReviewer", 11.0);
        validator.assertErrors(1);
    }
}
