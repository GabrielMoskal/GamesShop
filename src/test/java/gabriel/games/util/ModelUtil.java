package gabriel.games.util;

import gabriel.games.model.Company;
import gabriel.games.model.Game;
import gabriel.games.model.GamePlatform;
import gabriel.games.model.Platform;
import gabriel.games.model.dto.GameDto;
import gabriel.games.model.embedded.GamePlatformKey;

import java.sql.Date;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ModelUtil {

    public static GameDto makeGameDto(String filler) {
        String uri = filler.toLowerCase().replace(" ", "-");
        Double rating = ThreadLocalRandom.current().nextDouble(0.0, 10.0);
        return GameDto.builder()
                .uri(uri)
                .name(filler)
                .description(filler)
                .webpage("https://" + uri + ".com")
                .playerRating(rating)
                .reviewerRating(rating)
                .platforms(Arrays.asList(filler + "1", filler + "2"))
                .releaseDate(new java.sql.Date(System.currentTimeMillis()))
                .producer(filler)
                .publisher(filler)
                .build();
    }

    public static Game makeGame(long id) {
        String value = Long.toString(id);
        return new Game(id, value, value);
    }

    public static Platform makePlatform(long id) {
        String value = Long.toString(id);
        return new Platform(id, value, Collections.emptySet());
    }

    public static Set<Company> makeValidCompanies() {
        Set<Company> companies = new HashSet<>();
        companies.add(makeCompany(1));
        return companies;
    }

    public static Company makeCompany(long id) {
        String value = Long.toString(id);
        return new Company(id, value, Collections.emptySet(), Collections.emptySet());
    }

    public static GamePlatform makeGamePlatform(long id) {
        GamePlatform gamePlatform = new GamePlatform();
        gamePlatform.setId(new GamePlatformKey(id, id));
        gamePlatform.setReleaseDate(new Date(System.currentTimeMillis()));
        return gamePlatform;
    }

    public static Set<GamePlatform> makeValidGamePlatforms() {
        Set<GamePlatform> gamePlatforms = new HashSet<>();
        gamePlatforms.add(makeGamePlatform(1));
        return gamePlatforms;
    }
}
