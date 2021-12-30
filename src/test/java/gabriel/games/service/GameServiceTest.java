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
        Game expected = new Game("name", null);
        mockFindByUri(expected);
        Game actual = service.findByUri(expected.getUri());
        assertThat(actual).isEqualToComparingFieldByField(expected);
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
        mockFindByUri(new Game("name", "uri"));
        service.findByUri("uri");
        verify(repository).findByUri("uri");
    }

    @Test
    public void save_ValidGameGiven_ShouldReturnValidGame() {
        testSave("name");
    }

    private void testSave(String name) {
        Game expected = new Game(name, null);
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
        Game game = new Game("name", null);
        service.save(game);
        verify(repository).save(any());
    }

    @Test
    public void update_EmptyGameGiven_ShouldNotUpdateGame() {
        Game game = new Game("name", null);
        Game patch = new Game("patch", null);
        mockSave(game);
        mockFindByUri(game);

        Game actual = service.update(game.getUri(), patch);

        assertThat(actual).isEqualToComparingFieldByField(game);
    }

    @Test
    public void update_NameGiven_ShouldUpdateName() {
        Game game = new Game("name", null);
        Game patch = new Game("patch", null);
        mockSave(game);
        mockFindByUri(game);

        Game actual = service.update(game.getUri(), patch);

        assertThat(actual.getName()).isEqualTo(patch.getName());
    }

    @Test
    public void update_UriGiven_ShouldUpdateUri() {
        Game game = new Game("name", null);
        Game patch = new Game("different", null);
        patch.setUri("different");
        mockSave(game);
        mockFindByUri(game);

        Game actual = service.update(game.getUri(), patch);

        assertThat(actual.getUri()).isEqualTo(patch.getUri());
    }

    @Test
    public void update_DetailsGiven_ShouldUpdateDetails() {
        Game game = new Game("name", null);
        Game patch = new Game("patch", null);
        patch.setDetails(mock(GameDetails.class));
        mockSave(game);
        mockFindByUri(game);

        Game actual = service.update(game.getUri(), patch);

        assertThat(actual.getDetails()).isEqualToComparingFieldByField(patch.getDetails());
    }

    @Test
    public void update_PlatformsGiven_ShouldUpdatePlatforms() {
        Game game = new Game("name", null);
        Game patch = new Game("patch", null);
        patch.setPlatforms(new HashSet<>(Collections.singletonList(mock(GamePlatform.class))));
        mockSave(game);
        mockFindByUri(game);

        Game actual = service.update(game.getUri(), patch);

        assertThat(actual.getPlatforms()).isEqualTo(patch.getPlatforms());
    }

    @Test
    public void update_CompaniesGiven_ShouldUpdateCompanies() {
        Game game = new Game("name", null);
        Game patch = new Game("patch", null);
        patch.setCompanies(new HashSet<>(Collections.singletonList(mock(Company.class))));
        mockSave(game);
        mockFindByUri(game);

        Game actual = service.update(game.getUri(), patch);

        assertThat(actual.getCompanies()).isEqualTo(patch.getCompanies());
    }

    @Test
    public void update_NonExistentUriGiven_ShouldThrowException() {
        Executable executable = () -> service.update("non existent", new Game("name", null));

        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, executable);
        assertThat(exception.getMessage()).isEqualTo("Game with given uri not found.");
    }

    @Test
    public void update_VerifyInteractions() {
        Game game = new Game("name", null);
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
