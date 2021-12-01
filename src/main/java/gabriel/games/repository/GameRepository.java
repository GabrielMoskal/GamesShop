package gabriel.games.repository;

import gabriel.games.model.api.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GameRepository extends CrudRepository<Game, Long> {
    Optional<Game> findByUri(String uri);
}
