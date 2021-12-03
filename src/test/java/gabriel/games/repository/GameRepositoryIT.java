package gabriel.games.repository;

import gabriel.games.model.api.Game;
import gabriel.games.util.Models;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class GameRepositoryIT {

    @Autowired
    private GameRepository repository;

    @Test
    public void findByUri_ExistingGameUriGiven_ReturnsExistingGame() {
        Game expected = makeExpected();
        Game actual = makeActual();

        assertGameValid(expected, actual);
    }

    private Game makeExpected() {
        return new Game(4L, "Test Name");
    }

    private Game makeActual() {
        return repository.findByUri("test-name").orElse(Models.makeGame(1));
    }

    private void assertGameValid(Game expectedGame, Game actualGame) {
        assertNotNull(actualGame);
        assertEquals(expectedGame, actualGame);
    }
}
