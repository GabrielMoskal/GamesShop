package gabriel.games.model.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@EqualsAndHashCode
public class GameDto {

    private final String uri;
    private final String name;
    private final String description;
    private final String webpage;
    private final String playerRating;
    private final String reviewerRating;
    private final List<String> platforms;
    private final String releaseDate;
    private final String producer;
    private final String publisher;
}
