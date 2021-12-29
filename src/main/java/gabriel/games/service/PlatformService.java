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
        //TODO
        return null;
    }
}
