package gabriel.games.service;

import gabriel.games.model.api.*;
import gabriel.games.model.api.embedded.Rating;
import gabriel.games.repository.GameRepository;
import gabriel.games.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GameServiceTest {

    private GameRepository repository;
    private GameService service;

    @BeforeEach
    public void setUp() {
        this.repository = mock(GameRepository.class);
        this.service = new GameService(this.repository);
    }

    @Test
    public void findByUri_ExistingUriGiven_ShouldReturnValidGame() {
        Game expected = makeGame("name", null, null);
        mockFindByUri(expected);

        Game actual = service.findByUri(expected.getUri());

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    private Game makeGame(String name, String uri, GameDetails gameDetails) {
        Game game = new Game(name, uri);
        game.setDetails(gameDetails);
        return game;
    }

    private void mockFindByUri(Game game) {
        when(repository.findByUri(game.getUri())).thenReturn(Optional.of(game));
    }

    @Test
    public void findByUri_NonExistingUriGiven_ShouldThrowException() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> service.findByUri("name"));

        assertThat(exception.getMessage()).isEqualTo("Game with given uri not found.");
    }

    @Test
    public void findByUri_AnyUriGiven_VerifyInteractions() {
        mockFindByUri(makeGame(null, "uri", null));

        service.findByUri("uri");

        verify(repository).findByUri("uri");
    }

    @Test
    public void save_ValidGameGiven_ShouldReturnValidGame() {
        testSave("name");
    }

    private void testSave(String name) {
        Game expected = makeGame(name, null, null);
        mockSave(expected);

        Game actual = service.save(expected);

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    private void mockSave(Game game) {
        when(repository.save(any())).thenReturn(game);
    }

    @Test
    public void save_DifferentValidGameGiven_ShouldReturnValidGame() {
        testSave("different");
    }

    @Test
    public void save_AnyGameGiven_VerifyInteractions() {
        Game game = makeGame("name", null, null
        );
        service.save(game);

        verify(repository).save(any());
    }

    @Test
    public void update_EmptyGameGiven_ShouldNotUpdateGame() {
        Game game = makeGame("name", null, null);
        Game patch = makeGame("patch", null, null);
        mockSave(game);
        mockFindByUri(game);

        Game actual = service.update(game.getUri(), patch);

        assertThat(actual).isEqualToComparingFieldByField(game);
    }

    @Test
    public void update_NameGiven_ShouldUpdateName() {
        Game game = makeGame("name", null, null);
        Game patch = makeGame("patch", null, null);
        mockSave(game);
        mockFindByUri(game);

        Game actual = service.update(game.getUri(), patch);

        assertThat(actual.getName()).isEqualTo(patch.getName());
    }

    @Test
    public void update_UriGiven_ShouldUpdateUri() {
        Game game = makeGame("name", "uri", null);
        Game patch = makeGame(null, "different", null);
        mockSave(game);
        mockFindByUri(game);

        Game actual = service.update(game.getUri(), patch);

        assertThat(actual.getUri()).isEqualTo(patch.getUri());
    }

    @Test
    public void update_DetailsGiven_ShouldUpdateDetails() {
        Game game = makeGame(null, null, null);
        game.setId(1L);
        GameDetails gameDetails = GameDetails.builder()
                .description("desc")
                .webpage("page")
                .ratingPlayers(mock(Rating.class))
                .ratingReviewer(mock(Rating.class))
                .build();
        Game patch = makeGame(null, null, gameDetails);
        mockSave(game);
        mockFindByUri(game);

        Game actual = service.update(game.getUri(), patch);

        assertThat(actual.getDetails()).isEqualToComparingFieldByField(patch.getDetails());
        assertThat(actual.getDetails().getGameId()).isEqualTo(game.getId());
        assertThat(actual.getDetails().getGame()).isEqualToComparingFieldByField(game);
    }

    @Test
    public void update_NonExistentUriGiven_ShouldThrowException() {
        Executable executable = () -> service.update("non existent", new Game("name", null));

        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, executable);
        assertThat(exception.getMessage()).isEqualTo("Game with given uri not found.");
    }

    @Test
    public void update_VerifyInteractions() {
        Game game = makeGame("name", null, null);
        mockFindByUri(game);

        service.update(game.getUri(), mock(Game.class));

        verify(repository).save(any());
    }

    @Test
    public void deleteByUri_VerifyInteractions() {
        String uri = "uri";
        service.deleteByUri(uri);
        verify(repository).deleteByUri(uri);
    }
}
