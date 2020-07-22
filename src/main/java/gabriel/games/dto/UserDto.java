package gabriel.games.dto;

import gabriel.games.util.validator.FieldMatch;
import gabriel.games.util.validator.Word;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@FieldMatch(first = "password", second = "confirmedPassword", message = "Password fields must match.")
public class UserDto {

    @NotNull
    @Word
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters long.")
    private final String username;

    @NotNull
    @Word
    @Size(min = 7, max = 20, message = "Password must be between 7 and 20 characters long.")
    private final String password;

    @NotNull
    @Word
    @Size(min = 7, max = 20, message = "Confirmed password must be between 7 and 20 characters long.")
    private final String confirmedPassword;
}
