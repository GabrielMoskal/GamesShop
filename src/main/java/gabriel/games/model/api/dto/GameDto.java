package gabriel.games.model.api.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.*;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class GameDto {

    @NotNull
    @Size(max = 128, message = "{uri.size}")
    private final String uri;

    @NotNull
    @Size(min = 1, max = 128, message = "{name.size}")
    private final String name;

    @NotNull
    @Valid
    private final GameDetailsDto details;

    @NotNull
    @Size(min = 1, message = "{platforms.size}")
    @Builder.Default
    private final List<@Valid GamePlatformDto> platforms = new ArrayList<>();

    @NotNull
    @Size(min = 1, message = "{companies.size}")
    @Builder.Default
    private final List<@Valid CompanyDto> companies = new ArrayList<>();

}
