package gabriel.games.model.api.embedded;

import gabriel.games.model.util.EntityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RatingTest {

    private Rating rating;
    private EntityValidator<Rating> validator;

    @BeforeEach
    public void setUp() {
        this.rating = new Rating("5.0");
        this.validator = new EntityValidator<>(this.rating);
    }

    @Test
    public void constructorTest() {
        BigDecimal expected = new BigDecimal("5.0");
        BigDecimal actual = this.rating.getRating();
        assertEquals(expected, actual);
    }

    @Test
    public void validRatingGiven_ShouldHaveNoErrors() {
        validator.assertErrors(0);
    }

    @Test
    public void ratingPlayersShouldNotBeNegative() {
        rating.setRating(new BigDecimal("-0.5"));
        validator.assertErrors(1);
    }

    @Test
    public void ratingPlayersShouldBeMax10() {
        rating.setRating(new BigDecimal("10.1"));
        validator.assertErrors(1);
    }

    @Test
    public void ratingPlayersDecimalPartShouldBeMax2CharacterLong() {
        rating.setRating(new BigDecimal("5.123"));
        validator.assertErrors(1);
    }
}
