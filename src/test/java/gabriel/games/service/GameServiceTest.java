package gabriel.games.service;

import gabriel.games.model.api.Game;
import gabriel.games.repository.GameRepository;
import gabriel.games.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        compareByUri("name");
    }

    private void compareByUri(String name) {
        Game expected = makeExpected(name);
        mockRepository(name, expected);
        Game actual = service.findByUri(name);

        assertEquals(expected, actual);
    }

    private Game makeExpected(String name) {
        return new Game(1L, name);
    }

    private void mockRepository(String uri, Game expected) {
        when(repository.findByUri(uri)).thenReturn(Optional.of(expected));
    }

    @Test
    public void findByUri_DifferentExistingUriGiven_ShouldReturnValidGame() {
        compareByUri("different");
    }

    @Test
    public void findByUri_NonExistingUriGiven_ShouldThrowException() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> service.findByUri("name"));
        assertEquals("Game with given uri not found.", exception.getMessage());
    }

    @Test
    public void findByUri_AnyUriGiven_VerifyInteractions() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        repository.findByUri(anyString());
        verify(repository, times(1)).findByUri(captor.capture());
    }
}
