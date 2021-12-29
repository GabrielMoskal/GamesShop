package gabriel.games.model.api;

import gabriel.games.model.api.embedded.Rating;
import gabriel.games.model.util.EntityValidator;
import gabriel.games.model.util.GenericWord;
import nl.jqno.equalsverifier.*;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class GameDetailsTest {

    private GameDetails actual;

    @Test
    public void validGameDetailsGiven_HasNoErrors() {
        actual = GameDetails.builder()
                .gameId(1L)
                .description("description")
                .webpage("www.webpage.com")
                .ratingPlayers(new Rating("2.5"))
                .ratingReviewer(new Rating("3.5"))
                .build();
        EntityValidator.assertErrors(actual, 0);
    }

    @Test
    public void descriptionShouldNotBeNull() {
        actual = GameDetails.builder().description(null).build();
        EntityValidator.assertPropertyErrors(actual, "description");
    }

    @Test
    public void descriptionShouldBeMax1024CharactersLong() {
        actual = GameDetails.builder().description(GenericWord.make(1025)).build();
        EntityValidator.assertPropertyErrors(actual, "description");
    }

    @Test
    public void webpageShouldNotBeNull() {
        actual = GameDetails.builder().webpage(null).build();
        EntityValidator.assertPropertyErrors(actual, "webpage");
    }

    @Test
    public void webpageShouldBeMax256CharactersLong() {
        actual = GameDetails.builder().webpage(GenericWord.make(257)).build();
        EntityValidator.assertPropertyErrors(actual, "webpage");
    }

    @Test
    public void getRatingPlayers_ShouldReturnValidBigDecimal() {
        actual = GameDetails.builder().ratingPlayers(new Rating("5.0")).build();
        BigDecimal expected = new BigDecimal("5.0");
        assertEquals(expected, actual.getRatingPlayers());
    }

    @Test
    public void getRatingPlayers_NullRatingPlayersGiven_ShouldThrowException() {
        actual = GameDetails.builder().ratingPlayers(null).build();
        Throwable exception = assertThrows(NullPointerException.class, () -> actual.getRatingPlayers());
        String expected = "ratingPlayers should not be null";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    public void getRatingReviewer_ShouldReturnValidBigDecimal() {
        actual = GameDetails.builder().ratingReviewer(new Rating("3.0")).build();
        BigDecimal expected = new BigDecimal("3.0");
        assertEquals(expected, actual.getRatingReviewer());
    }

    @Test
    public void getRatingReviewer_NullRatingReviewerGiven_ShouldThrowException() {
        actual = GameDetails.builder().ratingReviewer(null).build();
        Throwable exception = assertThrows(NullPointerException.class, () -> actual.getRatingReviewer());
        String expected = "ratingReviewer should not be null";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(GameDetails.class)
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .withPrefabValues(Game.class, mock(Game.class), mock(Game.class))
                .verify();
    }
}
