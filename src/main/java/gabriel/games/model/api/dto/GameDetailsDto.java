package gabriel.games.model.api.dto;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class GameDetailsDto {

    @NotNull
    @Size(max = 1024, message = "{description.size}")
    private final String description;

    @NotNull
    @Size(max = 256, message = "{webpage.size}")
    private final String webpage;

    @Range(min = 0L, max = 10L, message = "{ratingPlayers.size}")
    private final double ratingPlayers;

    @Range(min = 0L, max = 10L, message = "{ratingReviewer.size}")
    private final double ratingReviewer;
}
