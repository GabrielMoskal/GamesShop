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
    private GameRepository gameRepository;

    public Game findByUri(String uri) {
        return gameRepository.findByUri(uri).orElseThrow(() -> new ObjectNotFoundException(NOT_FOUND));
    }

    public Game save(Game game) {
        return gameRepository.save(game);
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
            game.getDetails().setGameId(game.getId());
            game.getDetails().setGame(game);
        }

        return save(game);
    }

    public void deleteByUri(String uri) {
        gameRepository.deleteByUri(uri);
    }
}
