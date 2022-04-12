package gabriel.games.model.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.hateoas.EntityModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({"links"})
public class CompanyDto extends EntityModel<CompanyDto> {

    @NotNull
    @Size(min = 1, max = 128, message = "{company.nameSize}")
    private final String name;

    @NotNull
    @Size(min = 1, message = "{company.typesSize")
    private final List<@Size(min = 1, max = 50) String> types;
}
