package gabriel.games.service;

import gabriel.games.model.api.GamePlatform;
import gabriel.games.model.api.embedded.GamePlatformKey;
import gabriel.games.repository.*;
import gabriel.games.service.exception.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GamePlatformService {

    private final String NOT_FOUND = "GamePlatform with given id not found.";
    private GamePlatformRepository gamePlatformRepository;
    private GameRepository gameRepository;
    private PlatformRepository platformRepository;

    public GamePlatform find(String gameUri, String platformUri) {
        long gameId = gameRepository.findIdByUri(gameUri);
        long platformId = platformRepository.findIdByUri(platformUri);
        GamePlatformKey key = new GamePlatformKey(gameId, platformId);

        return gamePlatformRepository.findById(key).orElseThrow(() -> new ObjectNotFoundException(NOT_FOUND));
    }

    public GamePlatform save(GamePlatform gamePlatform) {
        return gamePlatformRepository.save(gamePlatform);
    }
}
