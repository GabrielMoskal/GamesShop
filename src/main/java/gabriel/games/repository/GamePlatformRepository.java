package gabriel.games.repository;

import gabriel.games.model.api.GamePlatform;
import gabriel.games.model.api.embedded.GamePlatformKey;
import org.springframework.data.repository.CrudRepository;

public interface GamePlatformRepository extends CrudRepository<GamePlatform, GamePlatformKey> {
}
