package gabriel.games.model.api.mapper;

import gabriel.games.model.api.GameDetails;
import gabriel.games.model.api.dto.GameDetailsDto;
import gabriel.games.model.api.embedded.Rating;
import org.springframework.stereotype.Component;

@Component
public class GameDetailsMapper {

    public GameDetailsDto toGameDetailsDto(GameDetails gameDetails) {
        return GameDetailsDto.builder()
                .description(gameDetails.getDescription())
                .webpage(gameDetails.getWebpage())
                .ratingPlayers(gameDetails.getRatingPlayers().doubleValue())
                .ratingReviewer(gameDetails.getRatingReviewer().doubleValue())
                .build();
    }

    public GameDetails toGameDetails(GameDetailsDto gameDetailsDto) {
        return GameDetails.builder()
                .description(gameDetailsDto.getDescription())
                .webpage(gameDetailsDto.getWebpage())
                .ratingPlayers(new Rating(String.valueOf(gameDetailsDto.getRatingPlayers())))
                .ratingReviewer(new Rating(String.valueOf(gameDetailsDto.getRatingReviewer())))
                .build();
    }
}
