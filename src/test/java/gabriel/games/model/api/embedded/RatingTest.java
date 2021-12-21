package gabriel.games.model.api.embedded;

import gabriel.games.model.util.EntityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RatingTest {

    private Rating actual;

    @BeforeEach
    public void setUp() {
        this.actual = new Rating("5.0");
    }

    @Test
    public void constructorTest() {
        actual = new Rating("5.0");
        BigDecimal expected = new BigDecimal("5.0");
        assertEquals(expected, actual.getRating());
    }

    @Test
    public void validRatingGiven_ShouldHaveNoErrors() {
        actual = new Rating("5.0");
        EntityValidator.assertErrors(actual, 0);
    }

    @Test
    public void ratingPlayersShouldNotBeNegative() {
        actual = new Rating("-0.5");
        EntityValidator.assertErrors(actual, 1);
    }

    @Test
    public void ratingPlayersShouldBeMax10() {
        actual = new Rating("10.1");
        EntityValidator.assertErrors(actual, 1);
    }

    @Test
    public void ratingPlayersDecimalPartShouldBeMax2CharacterLong() {
        actual = new Rating("5.123");
        EntityValidator.assertErrors(actual, 1);
    }
}
