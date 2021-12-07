package gabriel.games.model.api.mapper;

import gabriel.games.model.api.Game;
import gabriel.games.model.api.GameDetails;
import gabriel.games.model.api.GamePlatform;
import gabriel.games.model.api.dto.GameDto;
import gabriel.games.model.api.embedded.Rating;
import gabriel.games.model.util.ReflectionSetter;
import gabriel.games.util.Models;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GameMapperTest {

    @Test
    public void toGameDto_GameGiven_ReturnsValidGameDto() {

        // TODO

        Game game = new Game(1L, "game name");
        ReflectionSetter<Game> setter = new ReflectionSetter<>(game);
        setter.set("details", GameDetails.builder()
                .game(game)
                .gameId(1L)
                .description("description")
                .webpage("webpage")
                .ratingPlayers(new Rating("1"))
                .ratingReviewer(new Rating("2"))
                .build()
        );
        setter.set("platforms", Models.makeValidGamePlatforms());
        setter.set("companies", Models.makeValidCompanies());

        GameMapper mapper = new GameMapper();
        GameDto gameDto = mapper.toGameDto(game);

        GameDetails details = game.getDetails();

        Set<GamePlatform> platforms = game.getPlatforms();
        List<String> platformNames = new ArrayList<>();
        platforms.forEach((t) -> platformNames.add(t.getPlatform().getName()));

        GameDto expected = GameDto.builder()
                .uri(game.getUri())
                .name(game.getName())
                .description(details.getDescription())
                .webpage(details.getWebpage())
                .playerRating(details.getRatingPlayers().getRating().doubleValue())
                .reviewerRating(details.getRatingReviewer().getRating().doubleValue())
                .platforms(platformNames)
                //.releaseDate()
                .build();

    }
}
