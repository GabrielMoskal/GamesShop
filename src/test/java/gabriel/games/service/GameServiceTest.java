package gabriel.games.service;

import gabriel.games.model.api.*;
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
        testFindByUri("name");
    }

    private void testFindByUri(String name) {
        Game expected = new Game(name);
        mockFindByUri(expected);
        Game actual = service.findByUri(expected.getUri());
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    private void mockFindByUri(Game game) {
        when(repository.findByUri(game.getUri())).thenReturn(Optional.of(game));
    }

    @Test
    public void findByUri_DifferentExistingUriGiven_ShouldReturnValidGame() {
        testFindByUri("different");
    }

    @Test
    public void findByUri_NonExistingUriGiven_ShouldThrowException() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> service.findByUri("name"));
        assertThat(exception.getMessage()).isEqualTo("Game with given uri not found.");
    }

    @Test
    public void findByUri_AnyUriGiven_VerifyInteractions() {
        String gameName = "name";
        mockFindByUri(new Game(gameName));
        service.findByUri(gameName);
        verify(repository).findByUri(gameName);
    }

    @Test
    public void save_ValidGameGiven_ShouldReturnValidGame() {
        testSave("name");
    }

    private void testSave(String name) {
        Game expected = new Game(name);
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
        Game game = new Game("name");
        service.save(game);
        verify(repository).save(any());
    }

    @Test
    public void update_EmptyGameGiven_ShouldNotUpdateGame() {
        Game game = new Game("name");
        Game patch = new Game(null);

        Game actual = update(game, patch);

        assertThat(actual).isEqualToComparingFieldByField(game);
    }

    private Game update(Game game, Game patch) {
        mockSave(game);
        mockFindByUri(game);
        return service.update(game.getUri(), patch);
    }

    @Test
    public void update_NameGiven_ShouldUpdateName() {
        Game game = new Game("name");
        Game patch = new Game(null);
        patch.setName("different");

        Game actual = update(game, patch);

        assertThat(actual.getName()).isEqualTo(patch.getName());
    }

    @Test
    public void update_UriGiven_ShouldUpdateUri() {
        Game game = new Game("name");
        Game patch = new Game(null);
        patch.setUri("different");

        Game actual = update(game, patch);

        assertThat(actual.getUri()).isEqualTo(patch.getUri());
    }

    @Test
    public void update_DetailsGiven_ShouldUpdateDetails() {
        Game game = new Game("name");
        Game patch = new Game(null);
        patch.setDetails(mock(GameDetails.class));

        Game actual = update(game, patch);

        assertThat(actual.getDetails()).isEqualToComparingFieldByField(patch.getDetails());
    }

    @Test
    public void update_PlatformsGiven_ShouldUpdatePlatforms() {
        Game game = new Game("name");
        Game patch = new Game(null);
        patch.setPlatforms(new HashSet<>(Collections.singletonList(mock(GamePlatform.class))));

        Game actual = update(game, patch);

        assertThat(actual.getPlatforms()).isEqualTo(patch.getPlatforms());
    }

    @Test
    public void update_CompaniesGiven_ShouldUpdateCompanies() {
        Game game = new Game("name");
        Game patch = new Game(null);
        patch.setCompanies(new HashSet<>(Collections.singletonList(mock(Company.class))));

        Game actual = update(game, patch);

        assertThat(actual.getCompanies()).isEqualTo(patch.getCompanies());
    }

    @Test
    public void update_NonExistentUriGiven_ShouldThrowException() {
        Executable executable = () -> service.update("non existent", new Game("name"));
        Throwable throwable = assertThrows(ObjectNotFoundException.class, executable);
        String msg = throwable.getMessage();
        assertThat(msg).isEqualTo("Game with given uri not found.");
    }

    @Test
    public void update_VerifyInteractions() {
        Game game = new Game("name");
        mockFindByUri(game);
        update(game, mock(Game.class));
        verify(repository).save(any());
    }

    @Test
    public void deleteByUri_VerifyInteractions() {
        String uri = "uri";
        service.deleteByUri(uri);
        verify(repository).deleteByUri(uri);
    }
}
