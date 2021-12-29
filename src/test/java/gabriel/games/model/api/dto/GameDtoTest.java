package gabriel.games.model.api.dto;

import gabriel.games.model.util.EntityValidator;
import gabriel.games.model.util.GenericWord;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.util.*;

public class GameDtoTest {

    private GameDto actual;

    @BeforeEach
    public void setUp() {
        this.actual = makeGameDto();
    }

    private GameDto makeGameDto() {
        return GameDto.builder()
                .uri("test")
                .name("test")
                .details(
                        GameDetailsDto.builder()
                                .description("description")
                                .webpage("www.webpage.com")
                                .ratingPlayers(1.0)
                                .ratingReviewer(2.0)
                                .build()
                )
                .platforms(
                        Collections.singletonList(new GamePlatformDto("name", new Date(0)))
                )
                .companies(
                        Collections.singletonList(new CompanyDto("company name", Collections.singletonList("type")))
                )
                .build();
    }

    @Test
    public void validGameDtoHasNoErrors() {
        actual = makeGameDto();
        EntityValidator.assertErrors(actual, 0);
    }

    @Test
    public void uriShouldNotBeNull() {
        actual = GameDto.builder().uri(null).build();
        EntityValidator.assertPropertyErrors(actual, "uri");
    }

    @Test
    public void uriShouldBeMax128CharacterLong() {
        actual = GameDto.builder().uri(GenericWord.make(129)).build();
        EntityValidator.assertPropertyErrors(actual, "uri");
    }

    @Test
    public void nameShouldNotBeNull() {
        actual = GameDto.builder().name(null).build();
        EntityValidator.assertPropertyErrors(actual, "name");

    }

    @Test
    public void nameShouldBeAtLeast1CharacterLong() {
        actual = GameDto.builder().name("").build();
        EntityValidator.assertPropertyErrors(actual, "name");
    }

    @Test
    public void nameShouldBeMax128CharacterLong() {
        actual = GameDto.builder().name(GenericWord.make(129)).build();
        EntityValidator.assertPropertyErrors(actual, "name");
    }

    @Test
    public void detailsShouldNotBeNull() {
        actual = GameDto.builder().details(null).build();
        EntityValidator.assertPropertyErrors(actual, "details");
    }

    @Test
    public void platformsShouldNotBeNull() {
        actual = GameDto.builder().platforms(null).build();
        EntityValidator.assertPropertyErrors(actual, "platforms");
    }

    @Test
    public void platformsShouldNotBeEmpty() {
        actual = GameDto.builder()
                .platforms(Collections.emptyList())
                .build();
        EntityValidator.assertPropertyErrors(actual, "platforms");
    }

    @Test
    public void platformsAreValidated() {
        actual = GameDto.builder()
                .platforms(Collections.singletonList(new GamePlatformDto(null, null)))
                .build();
        EntityValidator.assertPropertyErrors(actual.getPlatforms().get(0), "name");
    }

    @Test
    public void companiesShouldNotBeNull() {
        actual = GameDto.builder()
                .companies(null)
                .build();
        EntityValidator.assertPropertyErrors(actual, "companies");
    }

    @Test
    public void companiesShouldNotBeEmpty() {
        actual = GameDto.builder()
                .companies(Collections.emptyList())
                .build();
        EntityValidator.assertPropertyErrors(actual, "companies");
    }
}
