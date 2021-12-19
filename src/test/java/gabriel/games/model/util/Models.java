package gabriel.games.model.util;

import gabriel.games.model.api.*;
import gabriel.games.model.api.embedded.*;

import java.lang.reflect.Constructor;
import java.sql.Date;
import java.util.*;

public class Models {

    private Models() {
        throw new AssertionError();
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
