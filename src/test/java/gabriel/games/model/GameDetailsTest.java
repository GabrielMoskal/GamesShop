package gabriel.games.model;

import gabriel.games.model.dto.util.EntityValidator;
import gabriel.games.model.dto.util.GenericWord;
import gabriel.games.model.embedded.Rating;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameDetailsTest {

    private GameDetails gameDetails;
    private EntityValidator<GameDetails> validator;
    private GenericWord genericWord;

    @BeforeEach
    public void setUp() {
        this.gameDetails = GameDetails.builder()
                .gameId(1L)
                .description("test description")
                .webpage("www.test-webpage.com")
                .ratingPlayers(new Rating("4.5"))
                .build();
        this.validator = new EntityValidator<>(this.gameDetails);
        this.genericWord = new GenericWord();
    }

    @Test
    public void validGameDetailsGiven_HasNoErrors() {
        validator.assertErrors(0);
    }

    @Test
    public void descriptionShouldNotBeNull() {
        gameDetails.setDescription(null);
        validator.assertErrors(1);
    }

    @Test
    public void descriptionShouldBeMax1024CharactersLong() {
        gameDetails.setDescription(genericWord.make(1025));
        validator.assertErrors(1);
    }

    @Test
    public void webpageShouldNotBeNull() {
        gameDetails.setWebpage(null);
        validator.assertErrors(1);
    }

    @Test
    public void webpageShouldBeMax256CharactersLong() {
        gameDetails.setWebpage(genericWord.make(257));
        validator.assertErrors(1);
    }

    @Test
    public void equalsContract() {
        Game prefab1 = new Game(1L, "name1", "uri1");
        Game prefab2 = new Game(2L, "name2", "uri2");

        EqualsVerifier.forClass(GameDetails.class)
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .withPrefabValues(Game.class, prefab1, prefab2)
                .verify();
    }
}
