package gabriel.games.repository;

import gabriel.games.model.api.Platform;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlatformRepository extends CrudRepository<Platform, Long> {
    Optional<Platform> findByUri(String uri);

    void deleteByUri(String uri);
}
