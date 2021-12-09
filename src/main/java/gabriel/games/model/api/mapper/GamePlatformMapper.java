package gabriel.games.model.api.mapper;

import gabriel.games.model.api.GamePlatform;
import gabriel.games.model.api.dto.GamePlatformDto;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class GamePlatformMapper {
    public GamePlatformDto toGamePlatformDto(GamePlatform gamePlatform) {
        String platformName = gamePlatform.getPlatformName();
        Date releaseDate = gamePlatform.getReleaseDate();
        return new GamePlatformDto(platformName, releaseDate);
    }
}
