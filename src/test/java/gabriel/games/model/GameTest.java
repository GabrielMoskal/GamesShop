package gabriel.games.model;

import gabriel.games.model.dto.util.EntityValidator;
import gabriel.games.model.dto.util.GenericWord;
import gabriel.games.model.dto.util.ReflectionSetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    private Game game;
    private ReflectionSetter<Game> setter;
    private EntityValidator<Game> validator;
    private GenericWord genericWord;

    @BeforeEach
    public void setUp() {
        this.game = new Game(1L, "valid_name", "valid_uri");
        this.setter = new ReflectionSetter<>(this.game);
        this.validator = new EntityValidator<>(this.game);
        this.genericWord = new GenericWord();
    }

    @Test
    public void constructorTest() {
        assertEquals(1L, this.game.getId());
        assertEquals("valid_name", this.game.getName());
        assertEquals("valid_uri", this.game.getUri());
    }

    @Test
    public void validGameHasNoErrors() {
        validator.assertErrors(0);
    }

    @Test
    public void nameShouldNotBeNull() {
        setter.setValue("name", null);
        validator.assertErrors(1);
    }

    @Test
    public void nameShouldBeAtLeast1CharacterLong() {
        setter.setValue("name", "");
        validator.assertErrors(1);
    }

    @Test
    public void nameShouldBeMax128CharacterLong() {
        setter.setValue("name", genericWord.make(129));
        validator.assertErrors(1);
    }

    @Test
    public void uriShouldNotBeNull() {
        setter.setValue("uri", null);
        validator.assertErrors(1);
    }

    @Test
    public void uriShouldBeAtLeast1CharacterLong() {
        setter.setValue("uri", genericWord.make(0));
        validator.assertErrors(1);
    }

    @Test
    public void uriShouldBeMax128CharacterLong() {
        setter.setValue("uri", genericWord.make(129));
        validator.assertErrors(1);
    }

    @Test
    public void uriShouldHaveNoWhiteSpaces() {
        setter.setValue("uri", "some white spaces");
        validator.assertErrors(1);
    }

    @Test
    public void uriShouldBeLowercase() {
        setter.setValue("uri", "Uppercase");
        validator.assertErrors(1);
    }

}
