package gabriel.games.dto;

import gabriel.games.util.validator.FieldMatch;
import gabriel.games.util.validator.Word;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
}
