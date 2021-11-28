package gabriel.games.repository;

import gabriel.games.model.Game;
import gabriel.games.model.GameDetails;
import gabriel.games.model.embedded.Rating;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        Game expected = makeExpected();
        Game actual = repository.findByUri(expected.getUri()).orElse(null);

        assertGameValid(expected, actual);
    }

    private Game makeExpected() {
        Game expected = makeExpectedGame();
        expected.setDetails(makeExpectedGameDetails());

        return expected;
    }

    private Game makeExpectedGame() {
        return new Game(4L, "Test Name", "test-name");
    }

    private GameDetails makeExpectedGameDetails() {
        return GameDetails.builder()
                .gameId(4L)
                .description("Test description")
                .webpage("https://test-link.com")
                .ratingPlayers(new Rating("7.00"))
                .ratingReviewer(new Rating("6.50"))
                .build();
    }

    private void assertGameValid(Game expectedGame, Game actualGame) {
        assertNotNull(actualGame);
        assertEquals(expectedGame, actualGame);
        assertEquals(expectedGame.getDetails(), actualGame.getDetails());
    }
}
