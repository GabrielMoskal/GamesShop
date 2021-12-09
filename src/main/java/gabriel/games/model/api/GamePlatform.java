package gabriel.games.model.api;

import gabriel.games.model.api.embedded.GamePlatformKey;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Objects;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class GamePlatform {

    @EmbeddedId
    private GamePlatformKey id;

    @ManyToOne
    @MapsId("gameId")
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @MapsId("platformId")
    @JoinColumn(name = "platform_id")
    private Platform platform;

    @NotNull
    @Getter
    private Date releaseDate;

    public String getPlatformName() {
        Objects.requireNonNull(platform, "platform should not be null");
        return platform.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GamePlatform)) return false;

        GamePlatform that = (GamePlatform) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
