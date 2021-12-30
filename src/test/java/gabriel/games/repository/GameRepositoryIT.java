package gabriel.games.repository;

import gabriel.games.model.api.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

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
        return new Game("Test Name", "test-name");
    }

    private Game makeActual() {
        return repository.findByUri("test-name").orElse(mock(Game.class));
    }

    private void assertGameValid(Game expectedGame, Game actualGame) {
        assertNotNull(actualGame);
        assertEquals(expectedGame, actualGame);
    }
}
