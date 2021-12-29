package gabriel.games.model.api.mapper;

import gabriel.games.model.api.Platform;
import gabriel.games.model.api.dto.PlatformDto;
import org.springframework.stereotype.Component;

@Component
public class PlatformMapper {

    public PlatformDto toPlatformDto(Platform platform) {
        return new PlatformDto(platform.getName(), platform.getUri());
    }

    public Platform toPlatform(PlatformDto platformDto) {
        return new Platform(platformDto.getName(), platformDto.getUri());
    }
}
