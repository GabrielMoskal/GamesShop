package gabriel.games.repository;

import gabriel.games.model.Game;

import java.util.Optional;

public class JdbcGameRepository implements GameRepository {

    @Override
    public Optional<Game> findByUri(String uri) {
        return Optional.empty();
    }
}
