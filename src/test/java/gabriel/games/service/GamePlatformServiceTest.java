package gabriel.games.service;

import gabriel.games.model.api.*;
import gabriel.games.model.api.embedded.GamePlatformKey;
import gabriel.games.repository.*;
import gabriel.games.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

    @Test
    public void save_ValidGamePlatformGiven_ShouldReturnValidGamePlatform() {
        GamePlatform expected = new GamePlatform(mock(Date.class));
        when(gamePlatformRepository.save(expected)).thenReturn(expected);

        GamePlatform actual = service.save(expected);

        assertEquals(expected, actual);
        verify(gamePlatformRepository).save(expected);
    }

    @Test
    public void update_EmptyGamePlatformGiven_ShouldContainNoChanges() {
        GamePlatform patch = new GamePlatform(null);
        GamePlatform toUpdate = stubRepository();

        GamePlatform actual = service.update("gameUri", "platformUri", patch);

        assertThat(actual).isEqualToComparingFieldByField(toUpdate);
    }

    private GamePlatform stubRepository() {
        GamePlatform toUpdate = new GamePlatform(mock(Date.class));
        toUpdate.setGame(mock(Game.class));
        toUpdate.setPlatform(mock(Platform.class));

        when(gamePlatformRepository.findById(any())).thenReturn(Optional.of(toUpdate));
        when(gamePlatformRepository.save(toUpdate)).thenReturn(toUpdate);

        return toUpdate;
    }

    @Test
    public void update_DateGiven_ShouldUpdateDate() {
        GamePlatform patch = new GamePlatform(mock(Date.class));
        GamePlatform toUpdate = stubRepository();

        GamePlatform actual = service.update("game-uri", "platform-uri", patch);

        assertEquals(toUpdate.getReleaseDate(), actual.getReleaseDate());
    }
}
