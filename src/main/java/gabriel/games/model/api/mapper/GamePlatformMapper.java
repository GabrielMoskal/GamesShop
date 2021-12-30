package gabriel.games.model.api.mapper;

import gabriel.games.model.api.Game;
import gabriel.games.model.api.GamePlatform;
import gabriel.games.model.api.Platform;
import gabriel.games.model.api.dto.GamePlatformDto;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class GamePlatformMapper {

    public GamePlatformDto toGamePlatformDto(GamePlatform gamePlatform) {
        String gameName = gamePlatform.getGameName();
        String platformName = gamePlatform.getPlatformName();
        Date releaseDate = gamePlatform.getReleaseDate();
        return new GamePlatformDto(gameName, platformName, releaseDate);
    }

    public GamePlatform toGamePlatform(GamePlatformDto gamePlatformDto) {
        // TODO
        Game game = new Game(gamePlatformDto.getGameName(), null);
        // TODO
        Platform platform = new Platform(gamePlatformDto.getPlatformName(), "uri");
        Date date = gamePlatformDto.getReleaseDate();
        return new GamePlatform(game, platform, date);
    }
}
