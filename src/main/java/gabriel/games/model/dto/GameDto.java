package gabriel.games.model.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.sql.Date;
import java.util.List;

@Builder
@Getter
@EqualsAndHashCode
public class GameDto {

    @NotNull
    @Size(min = 1, max = 128)
    private final String uri;

    @NotNull
    @Size(min = 1, max = 128)
    private final String name;

    @NotNull
    @Size(max = 1024)
    private final String description;

    @NotNull
    @Size(max = 256)
    private final String webpage;

    @NotNull
    @Range(min = 0L, max = 10L)
    private final Double playerRating;

    @NotNull
    @Range(min = 0L, max = 10L)
    private final Double reviewerRating;

    @NotNull
    @Size(min = 1)
    private final List<@NotNull @Size(min = 1, max = 50) String> platforms;

    @NotNull
    private final Date releaseDate;

    @NotNull
    @Size(max = 128)
    private final String producer;

    @NotNull
    @Size(max = 128)
    private final String publisher;
}
