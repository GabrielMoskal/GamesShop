package gabriel.games.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gabriel.games.dto.validator.FieldMatch;
import gabriel.games.dto.validator.Word;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

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

    private Map<String, Map<String, String>> errors;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public UserDto(
            @JsonProperty("username") final String username,
            @JsonProperty("password") final String password,
            @JsonProperty("confirmedPassword") final String confirmedPassword
    ) {
        this.username = username;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
        this.errors = new HashMap<>();
    }
}
