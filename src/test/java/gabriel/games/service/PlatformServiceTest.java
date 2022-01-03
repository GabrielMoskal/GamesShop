package gabriel.games.service;

import gabriel.games.model.api.Platform;
import gabriel.games.repository.PlatformRepository;
import gabriel.games.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
        verify(platformRepository).save(expected);
    }

    @Test
    public void update_EmptyPlatformGiven_ShouldContainNoChanges() {
        Platform patch = new Platform(null, null);
        Platform expected = new Platform("name", "uri");
        expected.setId(1L);
        stubRepository(expected);

        Platform result = platformService.update(anyString(), patch);

        assertThat(result).isEqualToComparingFieldByField(expected);
    }

    private void stubRepository(Platform platform) {
        when(platformRepository.findByUri(any())).thenReturn(Optional.of(platform));
        when(platformRepository.save(platform)).thenReturn(platform);
    }

    @Test
    public void update_NameGiven_ShouldUpdateName() {
        Platform patch = new Platform("different name", null);
        Platform toUpdate = new Platform("name", "uri");
        stubRepository(toUpdate);

        Platform result = platformService.update("uri", patch);

        assertThat(result.getName()).isEqualTo("different name");
    }

    @Test
    public void update_UriGiven_ShouldUpdateUri() {
        Platform patch = new Platform(null, "different uri");
        Platform toUpdate = new Platform("name", "uri");
        stubRepository(toUpdate);

        Platform result = platformService.update("uri", patch);

        assertThat(result.getUri()).isEqualTo("different uri");
    }

    @Test
    public void update_NonExistentUriGiven_ShouldThrowException() {
        Platform patch = new Platform("name", "uri");

        Executable executable = () -> platformService.update("uri", patch);
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, executable);
        assertThat(exception.getMessage()).isEqualTo("Platform with given uri not found.");
    }

    @Test
    public void deleteByUri_VerifyInteractions() {
        platformService.deleteByUri("uri");

        verify(platformRepository).deleteByUri("uri");
    }
}
