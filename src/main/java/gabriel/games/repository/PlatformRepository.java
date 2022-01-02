package gabriel.games.repository;

import gabriel.games.model.api.Platform;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface PlatformRepository extends CrudRepository<Platform, Long> {
    Optional<Platform> findByUri(String uri);

    @Transactional
    void deleteByUri(String uri);

    @Query("select p.id from Platform p where p.uri=:uri")
    Long findIdByUri(String uri);
}
