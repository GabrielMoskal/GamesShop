package gabriel.games.model.api.mapper;

import gabriel.games.model.api.GameDetails;
import gabriel.games.model.api.dto.GameDetailsDto;

public class GameDetailsMapper {

    public GameDetailsDto toGameDetailsDto(GameDetails gameDetails) {
        return GameDetailsDto.builder()
                .description(gameDetails.getDescription())
                .webpage(gameDetails.getWebpage())
                .ratingPlayers(gameDetails.getRatingPlayers().doubleValue())
                .ratingReviewer(gameDetails.getRatingReviewer().doubleValue())
                .build();
    }
}
