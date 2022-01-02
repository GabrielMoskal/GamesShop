package gabriel.games.service;

import gabriel.games.model.api.GamePlatform;
import gabriel.games.model.api.embedded.GamePlatformKey;
import gabriel.games.repository.*;
import gabriel.games.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GamePlatformServiceTest {

    private GamePlatformRepository gamePlatformRepository;
    private GameRepository gameRepository;
    private PlatformRepository platformRepository;
    private GamePlatformService service;

    @BeforeEach
    public void setUp() {
        this.gamePlatformRepository = mock(GamePlatformRepository.class);
        this.gameRepository = mock(GameRepository.class);
        this.platformRepository = mock(PlatformRepository.class);
        this.service = new GamePlatformService(gamePlatformRepository, gameRepository, platformRepository);
    }

    @Test
    public void find_ValidNamesGiven_ShouldReturnValidGamePlatform() {
        GamePlatform expected = new GamePlatform(mock(Date.class));
        GamePlatformKey gamePlatformKey = new GamePlatformKey(1L, 2L);
        when(gamePlatformRepository.findById(gamePlatformKey)).thenReturn(Optional.of(expected));
        when(gameRepository.findIdByUri("game-uri")).thenReturn(1L);
        when(platformRepository.findIdByUri("platform-uri")).thenReturn(2L);

        GamePlatform actual = service.find("game-uri", "platform-uri");

        assertEquals(expected, actual);
    }

    @Test
    public void find_InvalidGameNameGiven_ShouldThrowException() {
        when(gameRepository.findIdByUri("game-uri")).thenReturn(1L);
        when(platformRepository.findIdByUri("platform-uri")).thenReturn(2L);

        Executable executable = () -> service.find("game-uri", "platform-uri");

        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, executable);
        assertEquals("GamePlatform with given id not found.", exception.getMessage());
    }
}
