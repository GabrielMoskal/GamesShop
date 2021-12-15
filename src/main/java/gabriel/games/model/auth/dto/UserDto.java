package gabriel.games.model.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gabriel.games.model.auth.validator.FieldMatch;
import gabriel.games.model.auth.validator.Word;
import gabriel.games.model.dto.ErrorDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@FieldMatch(first = "password", second = "confirmedPassword", message = "{password.match}")
public class UserDto {

    @NotNull
    @Word
    @Size(min = 5, max = 20, message = "{username.size}")
    private final String username;

    @NotNull
    @Word
    @Size(min = 7, max = 20, message = "{password.size}")
    private final String password;

    @NotNull
    @Word
    @Size(min = 7, max = 20, message = "{confirmedPassword.size}")
    private final String confirmedPassword;

    private List<ErrorDto> errors;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public UserDto(
            @JsonProperty("username") final String username,
            @JsonProperty("password") final String password,
            @JsonProperty("confirmedPassword") final String confirmedPassword
    ) {
        this.username = username;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
        this.errors = new ArrayList<>();
    }
}
