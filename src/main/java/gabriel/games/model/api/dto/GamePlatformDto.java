package gabriel.games.model.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GamePlatformDto {

    @NotNull
    @Size(min = 1, max = 50)
    private final String name;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final Date releaseDate;
}
