package gabriel.games.model.api.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PlatformDto {

    @NotNull
    @Size(min = 1, max = 50)
    private final String name;

    @NotNull
    @Size(min = 1, max = 50)
    private final String uri;
}
