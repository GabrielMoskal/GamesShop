package gabriel.games.util;

import gabriel.games.model.Game;
import gabriel.games.model.Platform;
import gabriel.games.model.dto.GameDto;

import java.util.*;

public class GameUtil {
    public static GameDto makeValidGameDto() {
        return GameDto.builder()
                .uri("multiple-words-name")
                .name("Multiple Words Name")
                .description("Test short description")
                .webpage("https://test-link.com")
                .playerRating(7.0)
                .reviewerRating(6.5)
                .platforms(Arrays.asList("PC", "XBOX 360"))
                .releaseDate(new java.sql.Date(new GregorianCalendar(2000, Calendar.JANUARY, 1).getTimeInMillis()))
                .producer("Test Producer")
                .publisher("Test Publisher")
                .build();
    }

    public static GameDto makeDifferentValidGameDto() {
        return GameDto.builder()
                .uri("two-words")
                .name("Two words")
                .description("Test different description")
                .webpage("https://test-different-link.com")
                .playerRating(1.0)
                .reviewerRating(1.5)
                .platforms(Collections.singletonList("Playstation 5"))
                .releaseDate(new java.sql.Date(new GregorianCalendar(2011, Calendar.NOVEMBER, 11).getTimeInMillis()))
                .producer("Test different Producer")
                .publisher("Test different Publisher")
                .build();
    }

    public static Game makGame(long id) {
        String value = Long.toString(id);
        return new Game(id, value, value);
    }

    public static Platform makePlatform(long id) {
        String value = Long.toString(id);
        return new Platform(id, value, Collections.emptySet());
    }

    public static Set<Platform> makeValidPlatforms() {
        Set<Platform> platforms = new HashSet<>();
        platforms.add(makePlatform(1));
        return platforms;
    }
}
