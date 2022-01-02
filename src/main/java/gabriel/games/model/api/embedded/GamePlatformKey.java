package gabriel.games.model.api.embedded;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GamePlatformKey implements Serializable {

    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "platform_id")
    private Long platformId;

}
