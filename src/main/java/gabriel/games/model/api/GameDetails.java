package gabriel.games.model.api;

import gabriel.games.model.api.embedded.Rating;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Objects;

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
    @ToString.Include
    private Rating ratingPlayers;

    @Embedded
    @Valid
    @AttributeOverride(name = "rating", column = @Column(name = "rating_reviewer"))
    @ToString.Include
    private Rating ratingReviewer;

    @OneToOne
    @MapsId
    @JoinColumn(name = "game_id")
    private Game game;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameDetails)) return false;

        GameDetails that = (GameDetails) o;

        if (!Objects.equals(description, that.description)) return false;
        if (!Objects.equals(webpage, that.webpage)) return false;
        if (!Objects.equals(ratingPlayers, that.ratingPlayers))
            return false;
        return Objects.equals(ratingReviewer, that.ratingReviewer);
    }

    @Override
    public int hashCode() {
        int result = description != null ? description.hashCode() : 0;
        result = 31 * result + (webpage != null ? webpage.hashCode() : 0);
        result = 31 * result + (ratingPlayers != null ? ratingPlayers.hashCode() : 0);
        result = 31 * result + (ratingReviewer != null ? ratingReviewer.hashCode() : 0);
        return result;
    }
}
