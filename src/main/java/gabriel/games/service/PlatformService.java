package gabriel.games.service;

import gabriel.games.model.api.Platform;
import gabriel.games.repository.PlatformRepository;
import gabriel.games.service.exception.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlatformService {

    private final String NOT_FOUND = "Platform with given uri not found.";
    private PlatformRepository platformRepository;

    public Platform findByUri(String uri) {
        return platformRepository.findByUri(uri).orElseThrow(() -> new ObjectNotFoundException(NOT_FOUND));
    }

    public Platform save(Platform platform) {
        return platformRepository.save(platform);
    }

    public Platform update(String uri, Platform patch) {
        Platform platform = findByUri(uri);

        if (patch.getName() != null) {
            platform.setName(patch.getName());
        }
        if (patch.getUri() != null) {
            platform.setUri(patch.getUri());
        }
        return save(platform);
    }

    public void deleteByUri(String uri) {
        platformRepository.deleteByUri(uri);
    }
}
