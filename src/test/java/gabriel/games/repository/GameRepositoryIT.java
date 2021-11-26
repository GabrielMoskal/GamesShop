package gabriel.games.repository;

import gabriel.games.model.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class GameRepositoryIT {

    @Autowired
    private GameRepository repository;

    @Test
    public void findAll_ReturnsCorrectNumberOfGames() {
        long gamesCount = repository.count();
        assertEquals(4, gamesCount);
    }

    @Test
    public void findByUri_ExistingGameUriGiven_ReturnsExistingGame() {
        String name = "Age of Empires IV";
        String uri = "age-of-empires-iv";
        Game actual = repository.findByUri(uri).orElse(null);
        Game expected = new Game(1L, name, uri);
        assertEquals(expected, actual);
    }
}
