package gabriel.games.repository;

import gabriel.games.model.api.Game;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GameRepository extends CrudRepository<Game, Long> {
    Optional<Game> findByUri(String uri);

    void deleteByUri(String uri);

    @Query("select g.id from Game g where g.uri=:uri")
    Long findIdByUri(String uri);
}
