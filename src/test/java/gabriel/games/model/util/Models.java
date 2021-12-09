package gabriel.games.model.util;

import gabriel.games.model.api.Company;
import gabriel.games.model.api.Game;
import gabriel.games.model.api.GamePlatform;
import gabriel.games.model.api.Platform;
import gabriel.games.model.api.dto.CompanyDto;
import gabriel.games.model.api.dto.GameDto;
import gabriel.games.model.api.dto.GamePlatformDto;
import gabriel.games.model.api.embedded.GamePlatformKey;

import java.lang.reflect.Constructor;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Models {

    private Models() {
        throw new AssertionError();
    }

    public static GameDto makeGameDto(String filler) {
        String uri = filler.toLowerCase().replace(" ", "-");
        double rating = ThreadLocalRandom.current().nextInt(0, 11);

        return GameDto.builder()
                .uri(uri)
                .name(filler)
                .description(filler)
                .webpage("https://" + uri + ".com")
                .playerRating(rating)
                .reviewerRating(rating)
                .platforms(makeGamePlatformDtoList(filler))
                .companies(makeCompanyDtoList(filler))
                .build();
    }

    private static List<GamePlatformDto> makeGamePlatformDtoList(String filler) {
        Date date = new Date(System.currentTimeMillis());
        return Arrays.asList(
                new GamePlatformDto(filler + "1", date),
                new GamePlatformDto(filler + "2", date)
        );
    }

    private static List<CompanyDto> makeCompanyDtoList(String filler) {
        return Arrays.asList(
                new CompanyDto(filler + "1", Arrays.asList(filler + "1", filler + "2")),
                new CompanyDto(filler + "2", Collections.singletonList(filler + "2"))
        );
    }

    public static Game makeGame(long id) {
        String value = Long.toString(id);
        return new Game(id, value);
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
        GamePlatform gamePlatform = null;
        try {
            gamePlatform = makeGamePlatformInstance();
            fillFields(gamePlatform, id);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return gamePlatform;
    }

    private static GamePlatform makeGamePlatformInstance() throws ReflectiveOperationException {
        Constructor<GamePlatform> constructor = GamePlatform.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private static void fillFields(GamePlatform gamePlatform, long id) {
        ReflectionSetter<GamePlatform> setter = new ReflectionSetter<>(gamePlatform);
        setter.set("id", new GamePlatformKey(id, id));
        setter.set("releaseDate", new Date(System.currentTimeMillis()));
    }

    public static Set<GamePlatform> makeValidGamePlatforms() {
        Set<GamePlatform> gamePlatforms = new HashSet<>();
        gamePlatforms.add(makeGamePlatform(1));
        return gamePlatforms;
    }
}
