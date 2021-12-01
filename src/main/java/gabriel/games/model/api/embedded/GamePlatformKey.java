package gabriel.games.model.api.embedded;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class GamePlatformKey implements Serializable {

    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "platform_id")
    private Long platformId;

}
