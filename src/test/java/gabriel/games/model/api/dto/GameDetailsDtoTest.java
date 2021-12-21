package gabriel.games.model.api.dto;

import gabriel.games.model.util.EntityValidator;
import gabriel.games.model.util.GenericWord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameDetailsDtoTest {

    private GameDetailsDto actual;
    private GenericWord genericWord;

    @BeforeEach
    public void setUp() {
        this.genericWord = new GenericWord();
    }

    @Test
    public void validGameDto_HasNoErrors() {
        actual = GameDetailsDto.builder()
                .description("description")
                .webpage("www.webpage.com")
                .ratingPlayers(2.5)
                .ratingReviewer(4.0)
                .build();
        EntityValidator.assertErrors(actual, 0);
    }

    @Test
    public void descriptionShouldNotBeNull() {
        actual = GameDetailsDto.builder().build();
        EntityValidator.assertPropertyErrors(actual, "description");
    }

    @Test
    public void descriptionShouldBeMax1024CharacterLong() {
        actual = GameDetailsDto.builder()
                .description(genericWord.make(1025))
                .build();
        EntityValidator.assertPropertyErrors(actual, "description");
    }

    @Test
    public void webpageShouldNotBeNull() {
        actual = GameDetailsDto.builder().build();
        EntityValidator.assertPropertyErrors(actual, "webpage");
    }

    @Test
    public void webpageShouldBeMax256CharacterLong() {
        actual = GameDetailsDto.builder()
                .description(genericWord.make(257))
                .build();
        EntityValidator.assertPropertyErrors(actual, "webpage");
    }

    @Test
    public void ratingPlayersShouldNotBeNegative() {
        actual = GameDetailsDto.builder()
                .ratingPlayers(-0.5)
                .build();
        EntityValidator.assertPropertyErrors(actual, "ratingPlayers");
    }

    @Test
    public void ratingPlayersShouldBeMax10() {
        actual = GameDetailsDto.builder()
                .ratingPlayers(10.1)
                .build();
        EntityValidator.assertPropertyErrors(actual, "ratingPlayers");
    }

    @Test
    public void ratingReviewerShouldNotBeNegative() {
        actual = GameDetailsDto.builder()
                .ratingReviewer(-0.1)
                .build();
        EntityValidator.assertPropertyErrors(actual, "ratingReviewer");
    }

    @Test
    public void ratingReviewerShouldBeMax10() {
        actual = GameDetailsDto.builder()
                .ratingReviewer(11.0)
                .build();
        EntityValidator.assertPropertyErrors(actual, "ratingReviewer");
    }
}
