package gabriel.games.model.api.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

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
    @Valid
    private final GameDetailsDto details;

    @NotNull
    @Size(min = 1, message = "{platforms.size}")
    private final List<@Valid GamePlatformDto> platforms;

    @NotNull
    @Size(min = 1, message = "{companies.size}")
    private final List<@Valid CompanyDto> companies;

}
