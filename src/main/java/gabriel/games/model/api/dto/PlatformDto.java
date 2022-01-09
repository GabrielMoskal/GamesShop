package gabriel.games.model.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.hateoas.EntityModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"links"})
public class PlatformDto extends EntityModel<PlatformDto> {

    @NotNull
    @Size(min = 1, max = 50)
    private final String name;

    @NotNull
    @Size(min = 1, max = 50)
    private final String uri;
}
