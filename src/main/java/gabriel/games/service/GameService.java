package gabriel.games.service;

import gabriel.games.model.api.Game;
import gabriel.games.model.api.dto.AttributeUpdateDto;
import gabriel.games.repository.GameRepository;
import gabriel.games.service.exception.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GameService {

    private GameRepository repository;

    public Game findByUri(String uri) {
        return repository.findByUri(uri)
                .orElseThrow(() -> new ObjectNotFoundException("Game with given uri not found."));
    }

    public Game save(Game game) {
        return repository.save(game);
    }

    public Game update(String uri, List<AttributeUpdateDto> toUpdate) {
        return null;
    }
}
