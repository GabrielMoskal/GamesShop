package gabriel.games.model.api;

import gabriel.games.model.util.*;
import gabriel.games.model.api.embedded.Rating;
import nl.jqno.equalsverifier.*;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class GameDetailsTest {

    private GameDetails gameDetails;
    private EntityValidator<GameDetails> validator;
    private ReflectionSetter<GameDetails> setter;
    private GenericWord genericWord;

    @BeforeEach
    public void setUp() {
        this.gameDetails = GameDetails.builder()
                .gameId(1L)
                .description("description")
                .webpage("www.webpage.com")
                .ratingPlayers(new Rating("2.5"))
                .ratingReviewer(new Rating("3.5"))
                .build();
        this.validator = new EntityValidator<>(this.gameDetails);
        this.setter = new ReflectionSetter<>(this.gameDetails);
        this.genericWord = new GenericWord();
    }

    @Test
    public void validGameDetailsGiven_HasNoErrors() {
        validator.assertErrors(0);
    }

    @Test
    public void descriptionShouldNotBeNull() {
        setter.set("description", null);
        validator.assertErrors(1);
    }

    @Test
    public void descriptionShouldBeMax1024CharactersLong() {
        setter.set("description", genericWord.make(1025));
        validator.assertErrors(1);
    }

    @Test
    public void webpageShouldNotBeNull() {
        setter.set("webpage", null);
        validator.assertErrors(1);
    }

    @Test
    public void webpageShouldBeMax256CharactersLong() {
        setter.set("webpage", genericWord.make(257));
        validator.assertErrors(1);
    }

    @Test
    public void ratingPlayersShouldBeValidated() {
        setter.set("ratingPlayers", new Rating("11.0"));
        validator.assertErrors(1);
    }

    @Test
    public void ratingReviewerShouldBeValidated() {
        setter.set("ratingReviewer", new Rating("11.0"));
        validator.assertErrors(1);
    }

    @Test
    public void getRatingPlayers_ShouldReturnValidBigDecimal() {
        setter.set("ratingPlayers", new Rating("5.0"));
        BigDecimal expected = new BigDecimal("5.0");
        BigDecimal actual = gameDetails.getRatingPlayers();
        assertEquals(expected, actual);
    }

    @Test
    public void getRatingPlayers_NullRatingPlayersGiven_ShouldThrowException() {
        setter.set("ratingPlayers", null);
        Throwable exception = assertThrows(NullPointerException.class, () -> gameDetails.getRatingPlayers());
        String expected = "ratingPlayers should not be null";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    public void getRatingReviewer_ShouldReturnValidBigDecimal() {
        setter.set("ratingReviewer", new Rating("3.0"));
        BigDecimal expected = new BigDecimal("3.0");
        BigDecimal actual = gameDetails.getRatingReviewer();
        assertEquals(expected, actual);
    }

    @Test
    public void getRatingReviewer_NullRatingReviewerGiven_ShouldThrowException() {
        setter.set("ratingReviewer", null);
        Throwable exception = assertThrows(NullPointerException.class, () -> gameDetails.getRatingReviewer());
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
