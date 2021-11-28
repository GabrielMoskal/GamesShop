package gabriel.games.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class GameDetails {

    @Id
    @ToString.Include
    private Long gameId;

    @NotNull
    @Size(max = 1024)
    @ToString.Include
    private String description;

    @NotNull
    @Size(max = 256)
    @ToString.Include
    private String webpage;

    @OneToOne
    @MapsId
    @JoinColumn(name = "game_id")
    private Game game;

    //TODO
    // rating player
    // rating reviewer

    public GameDetails(long gameId, final String description, final String webpage) {
        this.gameId = gameId;
        this.description = description;
        this.webpage = webpage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameDetails)) {
            return false;
        }
        GameDetails that = (GameDetails) o;
        return description != null && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(description);
    }
}
