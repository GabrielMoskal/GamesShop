package gabriel.games.model.api.mapper;

import gabriel.games.model.api.GameDetails;
import gabriel.games.model.api.dto.GameDetailsDto;
import gabriel.games.model.api.embedded.Rating;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameDetailsMapperTest {

    private GameDetailsMapper mapper;

    @BeforeEach
    public void setUp() {
        this.mapper = new GameDetailsMapper();
    }

    @Test
    public void toGameDetailsDto_GameDetailsGiven_ShouldReturnValidGameDetailsDto() {
        GameDetailsDto expected = GameDetailsDto.builder()
                .description("description")
                .webpage("www.webpage.com")
                .ratingPlayers(1.0)
                .ratingReviewer(2.0)
                .build();
        assertMappingCorrect(expected);
    }

    private void assertMappingCorrect(GameDetailsDto expected) {
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
        GameDetailsDto expected = GameDetailsDto.builder()
                .description("different")
                .webpage("www.different.com")
                .ratingPlayers(1.5)
                .ratingReviewer(2.5)
                .build();
        assertMappingCorrect(expected);
    }

    @Test
    public void toGameDetails_DescriptionGiven_ShouldContainValidDescription() {
        GameDetailsDto gameDetailsDto = GameDetailsDto.builder().description("description").build();
        GameDetails expected = GameDetails.builder().description(gameDetailsDto.getDescription()).build();
        GameDetails actual = mapper.toGameDetails(gameDetailsDto);
        assertEquals(expected.getDescription(), actual.getDescription());
    }
    
    @Test
    public void toGameDetails_WebpageGiven_ShouldContainValidWebpage() {
        GameDetailsDto gameDetailsDto = GameDetailsDto.builder().webpage("www.webpage.com").build();
        GameDetails expected = GameDetails.builder().webpage(gameDetailsDto.getWebpage()).build();
        GameDetails actual = mapper.toGameDetails(gameDetailsDto);
        assertEquals(expected.getWebpage(), actual.getWebpage());
    }

    @Test
    public void toGameDetails_RatingPlayersGiven_ShouldContainValidRatingPlayers() {
        GameDetailsDto gameDetailsDto = GameDetailsDto.builder().ratingPlayers(1.0).build();
        Rating rating = new Rating(String.valueOf(gameDetailsDto.getRatingPlayers()));
        GameDetails expected = GameDetails.builder().ratingPlayers(rating).build();
        GameDetails actual = mapper.toGameDetails(gameDetailsDto);
        assertEquals(expected.getRatingPlayers(), actual.getRatingPlayers());
    }

    @Test
    public void toGameDetails_RatingReviewerGiven_ShouldContainValidRatingReviewer() {
        GameDetailsDto gameDetailsDto = GameDetailsDto.builder().ratingReviewer(2.0).build();
        Rating rating = new Rating(String.valueOf(gameDetailsDto.getRatingReviewer()));
        GameDetails expected = GameDetails.builder().ratingReviewer(rating).build();
        GameDetails actual = mapper.toGameDetails(gameDetailsDto);
        assertEquals(expected.getRatingReviewer(), actual.getRatingReviewer());
    }
}
