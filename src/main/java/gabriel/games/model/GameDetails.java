package gabriel.games.model;

import gabriel.games.model.embedded.Rating;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Objects;

@Getter
@Setter
@Builder
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    @Embedded
    @Valid
    @AttributeOverride(name = "rating", column = @Column(name = "rating_players"))
    private Rating ratingPlayers;

    //TODO
    // rating reviewer

    @OneToOne
    @MapsId
    @JoinColumn(name = "game_id")
    private Game game;

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
