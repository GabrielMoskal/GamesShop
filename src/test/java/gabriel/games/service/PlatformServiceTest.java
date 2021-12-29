package gabriel.games.service;

import gabriel.games.model.api.Platform;
import gabriel.games.repository.PlatformRepository;
import gabriel.games.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PlatformServiceTest {

    private PlatformService platformService;
    private PlatformRepository platformRepository;

    @BeforeEach
    public void setUp() {
        this.platformRepository = mock(PlatformRepository.class);
        this.platformService = new PlatformService(this.platformRepository);
    }

    @Test
    public void findByUri_ExistingUriGiven_ShouldReturnValidPlatform() {
        Platform expected = new Platform("name", "uri");
        mockFindByUri(expected);
        Platform actual = platformService.findByUri(expected.getName());

        assertEquals(expected, actual);
    }

    private void mockFindByUri(Platform expected) {
        when(platformRepository.findByUri(anyString())).thenReturn(Optional.of(expected));
    }

    @Test
    public void findByUri_NonExistentUriGiven_ShouldThrowException() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> platformService.findByUri("name"));
        assertEquals("Platform with given uri not found.", exception.getMessage());
    }

    @Test
    public void findByUri_UriGiven_VerifyInteractions() {
        Platform platform =  new Platform("name", "uri");
        mockFindByUri(platform);

        platformService.findByUri(platform.getUri());

        verify(platformRepository).findByUri(platform.getUri());
    }

    @Test
    public void save_ValidPlatformGiven_ShouldReturnValidPlatform() {
        Platform expected = new Platform("name", "uri");

        when(platformRepository.save(expected)).thenReturn(expected);
        Platform actual = platformService.save(expected);

        assertEquals(expected, actual);
    }

    @Test
    public void save_ValidPlatformGiven_VerifyInteractions() {
        Platform platform = new Platform("name", "uri");

        platformService.save(platform);

        verify(platformRepository).save(platform);
    }
}
