package gabriel.games.model.api.dto;

import gabriel.games.model.util.*;
import org.junit.jupiter.api.*;

import java.util.*;

public class GameDtoTest {

    private ReflectionSetter<GameDto> setter;
    private EntityValidator<GameDto> validator;
    private GenericWord genericWord;

    @BeforeEach
    public void setUp() {
        GameDto gameDto = Models.makeGameDto("Multiple words value");
        this.setter = new ReflectionSetter<>(gameDto);
        this.validator = new EntityValidator<>(gameDto);
        this.genericWord = new GenericWord();
    }

    @Test
    public void validGameDtoHasNoErrors() {
        validator.assertErrors(0);
    }

    @Test
    public void uriShouldNotBeNull() {
        setter.set("uri", null);
        validator.assertErrors(1);
    }

    @Test
    public void uriShouldBeMax128CharacterLong() {
        setter.set("uri", genericWord.make(129));
        validator.assertErrors(1);
    }

    @Test
    public void nameShouldNotBeNull() {
        setter.set("name", null);
        validator.assertErrors(1);
    }

    @Test
    public void nameShouldBeAtLeast1CharacterLong() {
        setter.set("name", "");
        validator.assertErrors(1);
    }

    @Test
    public void nameShouldBeMax128CharacterLong() {
        setter.set("name", genericWord.make(129));
        validator.assertErrors(1);
    }

    @Test
    public void detailsShouldNotBeNull() {
        setter.set("details", null);
        validator.assertErrors(1);
    }

    @Test
    public void detailsShouldBeValidated() {
        GameDetailsDto details = GameDetailsDto.builder()
                .description(null)
                .webpage(null)
                .build();
        setter.set("details", details);
        validator.assertErrors(2);
    }

    @Test
    public void platformsShouldNotBeNull() {
        setter.set("platforms", null);
        validator.assertErrors(1);
    }

    @Test
    public void platformsShouldNotBeEmpty() {
        setter.set("platforms", Collections.emptyList());
        validator.assertErrors(1);
    }

    @Test
    public void platformsAreValidated() {
        setter.set("platforms", Collections.singletonList(new GamePlatformDto(null, null)));
        validator.assertErrors(2);
    }

    @Test
    public void companiesShouldNotBeNull() {
        setter.set("companies", null);
        validator.assertErrors(1);
    }

    @Test
    public void companiesShouldNotBeEmpty() {
        setter.set("companies", Collections.emptyList());
        validator.assertErrors(1);
    }

    @Test
    public void companiesAreValidated() {
        setter.set("companies", Collections.singletonList(new CompanyDto(null, null)));
        validator.assertErrors(2);
    }
}
