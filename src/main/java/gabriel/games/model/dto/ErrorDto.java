package gabriel.games.model.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ErrorDto {
    public enum Type {
        FIELD_ERROR, OBJECT_ERROR
    }

    private final Type type;
    private final String name;
    private final String message;
}
