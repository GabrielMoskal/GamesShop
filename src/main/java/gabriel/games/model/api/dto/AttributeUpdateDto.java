package gabriel.games.model.api.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class AttributeUpdateDto {

    private String attributeName;
    private String attributeValue;
}
