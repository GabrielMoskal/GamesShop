package gabriel.games.repository;

import gabriel.games.model.Game;

import java.util.Optional;

public interface GameRepository {
    Optional<Game> findByUri(String uri);
}
