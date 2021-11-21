package gabriel.games.repository;

import gabriel.games.model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcGameRepositoryIT {

    @Test
    public void findByUri_ExistingGameUriGiven_ShouldReturnExistingGame() {
        Game expected = new Game(1L);
        GameRepository gameRepository = new JdbcGameRepository();
        Game actual = gameRepository.findByUri("existing").orElse(null);
        assertEquals(expected, actual);
    }
}
