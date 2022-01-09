package gabriel.games.model.api.dto;

import gabriel.games.model.util.EntityValidator;
import gabriel.games.model.util.GenericWord;
import org.junit.jupiter.api.*;

public class GameDtoTest {

    private GameDto actual;

    @Test
    public void validGameDtoHasNoErrors() {
        actual = GameDto.builder()
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
                .build();

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
}
