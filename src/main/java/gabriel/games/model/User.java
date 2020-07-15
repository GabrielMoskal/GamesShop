package gabriel.games.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class User {

    private final String username;

    @EqualsAndHashCode.Exclude
    private final String password;
}
