package gabriel.games.repository;

import gabriel.games.model.api.Platform;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface PlatformRepository extends CrudRepository<Platform, Long> {
    Optional<Platform> findByUri(String uri);

    @Transactional
    void deleteByUri(String uri);
}
