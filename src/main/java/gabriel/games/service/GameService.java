package gabriel.games.service;

import gabriel.games.model.api.Game;
import gabriel.games.repository.GameRepository;
import gabriel.games.service.exception.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameService {

    private GameRepository repository;

    public Game findByUri(final String uri) {
        return repository.findByUri(uri)
                .orElseThrow(() -> new ObjectNotFoundException("Game with given uri not found."));
    }

    public Game save(final Game game) {
        return repository.save(game);
    }
}
