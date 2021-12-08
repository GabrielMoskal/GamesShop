package gabriel.games.model.api.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Builder
@Getter
@EqualsAndHashCode
public class GameDto {

    @NotNull
    @Size(min = 1, max = 128, message = "{uri.size}")
    private final String uri;

    @NotNull
    @Size(min = 1, max = 128, message = "{name.size}")
    private final String name;

    @NotNull
    @Size(max = 1024, message = "{description.size}")
    private final String description;

    @NotNull
    @Size(max = 256, message = "{webpage.size}")
    private final String webpage;

    @NotNull
    @Range(min = 0L, max = 10L, message = "{playerRating.size}")
    private final Double playerRating;

    @NotNull
    @Range(min = 0L, max = 10L, message = "{reviewerRating.size}")
    private final Double reviewerRating;

    @NotNull
    @Size(min = 1, message = "{platforms.size}")
    private final List<@Valid GamePlatformDto> platforms;

    @NotNull
    @Size(min = 1, message = "{companies.size}")
    private final List<@Valid CompanyDto> companies;

}
