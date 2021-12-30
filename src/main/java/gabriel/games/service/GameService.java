package gabriel.games.service;

import gabriel.games.model.api.Game;
import gabriel.games.repository.GameRepository;
import gabriel.games.service.exception.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameService {

    private static final String NOT_FOUND = "Game with given uri not found.";
    private GameRepository repository;

    public Game findByUri(String uri) {
        return repository.findByUri(uri).orElseThrow(() -> new ObjectNotFoundException(NOT_FOUND));
    }

    public Game save(Game game) {
        return repository.save(game);
    }

    public Game update(String uri, Game patch) {
        Game game = findByUri(uri);

        if (patch.getName() != null) {
            game.setName(patch.getName());
        }
        if (patch.getUri() != null) {
            game.setUri(patch.getUri());
        }
        if (patch.getDetails() != null) {
            game.setDetails(patch.getDetails());
        }
        if (patch.getPlatforms() != null) {
            game.setPlatforms(patch.getPlatforms());
        }
        if (patch.getCompanies() != null) {
            game.setCompanies(patch.getCompanies());
        }
        return save(game);
    }

    public void deleteByUri(String uri) {
        repository.deleteByUri(uri);
    }
}
