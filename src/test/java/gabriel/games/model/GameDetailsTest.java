package gabriel.games.model;

import gabriel.games.model.dto.util.EntityValidator;
import gabriel.games.model.dto.util.GenericWord;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameDetailsTest {

    private GameDetails gameDetails;
    private EntityValidator<GameDetails> validator;
    private GenericWord genericWord;

    @BeforeEach
    public void setUp() {
        this.gameDetails = new GameDetails(1L, "test description", "www.test-webpage.com");
        this.validator = new EntityValidator<>(this.gameDetails);
        this.genericWord = new GenericWord();
    }

    @Test
    public void constructorTest() {
        assertEquals(1L, this.gameDetails.getGameId());
        assertEquals("test description", this.gameDetails.getDescription());
        assertEquals("www.test-webpage.com", this.gameDetails.getWebpage());
    }

    @Test
    public void validGameDescriptionGiven_HasNoErrors() {
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
