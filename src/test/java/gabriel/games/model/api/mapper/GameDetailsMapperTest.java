package gabriel.games.model.api.mapper;

import gabriel.games.model.api.GameDetails;
import gabriel.games.model.api.dto.GameDetailsDto;
import gabriel.games.model.api.embedded.Rating;
import gabriel.games.model.util.Models;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameDetailsMapperTest {

    private GameDetailsMapper mapper;

    @BeforeEach
    public void setUp() {
        this.mapper = new GameDetailsMapper();
    }

    @Test
    public void toGameDetailsDto_GameDetailsGiven_ShouldReturnValidGameDetailsDto() {
        GameDetailsDto expected = Models.makeGameDetailsDto("1");
        assertConversionCorrect(expected);
    }

    private void assertConversionCorrect(GameDetailsDto expected) {
        GameDetails gameDetails = convertToDetails(expected);
        GameDetailsDto actual = mapper.toGameDetailsDto(gameDetails);

        assertEquals(expected, actual);
    }

    private GameDetails convertToDetails(GameDetailsDto details) {
        return GameDetails.builder()
                .description(details.getDescription())
                .webpage(details.getWebpage())
                .ratingPlayers(new Rating(String.valueOf(details.getRatingPlayers())))
                .ratingReviewer(new Rating(String.valueOf(details.getRatingReviewer())))
                .build();
    }

    @Test
    public void toGameDetailsDto_DifferentGameDetailsGiven_ShouldReturnValidGameDto() {
        GameDetailsDto expected = Models.makeGameDetailsDto("2");
        assertConversionCorrect(expected);
    }
}
