package gabriel.games.service;

import gabriel.games.model.api.Game;
import gabriel.games.repository.GameRepository;
import gabriel.games.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameServiceTest {

    private GameRepository repository;
    private GameService service;

    @BeforeEach
    public void setUp() {
        this.repository = mock(GameRepository.class);
        this.service = new GameService(this.repository);
    }

    private Game makeExpected(String name) {
        return new Game(name);
    }

    @Test
    public void findByUri_ExistingUriGiven_ShouldReturnValidGame() {
        testFindByUri("name");
    }

    private void testFindByUri(String name) {
        Game expected = makeExpected(name);
        mockFindByUri(expected);
        Game actual = service.findByUri(expected.getUri());
        assertEquals(expected, actual);
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
        assertEquals("Game with given uri not found.", exception.getMessage());
    }

    @Test
    public void findByUri_AnyUriGiven_VerifyInteractions() {
        String gameName = "name";
        mockFindByUri(makeExpected(gameName));
        service.findByUri(gameName);
        verify(repository).findByUri(gameName);
    }

    @Test
    public void save_ValidGameGiven_ShouldReturnValidGame() {
        testSave("name");
    }

    private void testSave(String name) {
        Game expected = makeExpected(name);
        mockSave(expected);
        Game actual = service.save(expected);
        assertEquals(expected, actual);
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
        Game game = makeExpected("name");
        service.save(game);
        verify(repository).save(any());
    }
}
