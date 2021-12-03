package gabriel.games.model.api;

import gabriel.games.model.util.EntityValidator;
import gabriel.games.model.util.GenericWord;
import gabriel.games.model.api.embedded.Rating;
import gabriel.games.model.util.ReflectionSetter;
import gabriel.games.util.Models;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameDetailsTest {

    private EntityValidator<GameDetails> validator;
    private ReflectionSetter<GameDetails> setter;
    private GenericWord genericWord;

    @BeforeEach
    public void setUp() {
        GameDetails gameDetails = GameDetails.builder()
                .gameId(1L)
                .description("test description")
                .webpage("www.test-webpage.com")
                .ratingPlayers(new Rating("4.5"))
                .build();
        this.validator = new EntityValidator<>(gameDetails);
        this.setter = new ReflectionSetter<>(gameDetails);
        this.genericWord = new GenericWord();
    }

    @Test
    public void validGameDetailsGiven_HasNoErrors() {
        validator.assertErrors(0);
    }

    @Test
    public void descriptionShouldNotBeNull() {
        setter.set("description", null);
        validator.assertErrors(1);
    }

    @Test
    public void descriptionShouldBeMax1024CharactersLong() {
        setter.set("description", genericWord.make(1025));
        validator.assertErrors(1);
    }

    @Test
    public void webpageShouldNotBeNull() {
        setter.set("webpage", null);
        validator.assertErrors(1);
    }

    @Test
    public void webpageShouldBeMax256CharactersLong() {
        setter.set("webpage", genericWord.make(257));
        validator.assertErrors(1);
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(GameDetails.class)
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .withPrefabValues(Game.class, Models.makeGame(1L), Models.makeGame(2L))
                .verify();
    }
}
