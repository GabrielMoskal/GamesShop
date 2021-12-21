package gabriel.games.model.api;

import gabriel.games.model.util.*;
import nl.jqno.equalsverifier.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class GameTest {

    private Game actual;
    private GenericWord genericWord;

    @BeforeEach
    public void setUp() {
        this.actual = makeGame("valid name");
        this.genericWord = new GenericWord();
    }

    private Game makeGame(String name) {
        Game game = new Game(name);
        game.setPlatforms(makeGamePlatforms());
        game.setCompanies(makeCompanies());
        return game;
    }

    private Set<GamePlatform> makeGamePlatforms() {
        Set<GamePlatform> gamePlatforms = new HashSet<>();
        gamePlatforms.add(mock(GamePlatform.class));
        return gamePlatforms;
    }

    private Set<Company> makeCompanies() {
        Set<Company> companies = new HashSet<>();
        companies.add(mock(Company.class));
        return companies;
    }

    @Test
    public void constructorTest() {
        assertEquals("valid name", actual.getName());
        assertEquals("valid-name", actual.getUri());
    }

    @Test
    public void validGameGiven_HasNoErrors() {
        EntityValidator.assertErrors(actual,0);
    }

    @Test
    public void nameShouldNotBeNull() {
        actual.setName(null);
        EntityValidator.assertErrors(actual,1);
    }

    @Test
    public void nameShouldBeAtLeast1CharacterLong() {
        actual.setName("");
        EntityValidator.assertErrors(actual,1);
    }

    @Test
    public void nameShouldBeMax128CharacterLong() {
        actual.setName(genericWord.make(129));
        EntityValidator.assertErrors(actual,1);
    }

    @Test
    public void uriShouldNotBeNull() {
        actual.setUri(null);
        EntityValidator.assertErrors(actual,1);
    }

    @Test
    public void uriShouldBeAtLeast1CharacterLong() {
        actual.setUri("");
        EntityValidator.assertErrors(actual,1);
    }

    @Test
    public void uriShouldBeMax128CharacterLong() {
        actual.setUri(genericWord.make(129));
        EntityValidator.assertErrors(actual,1);
    }

    @Test
    public void uriShouldHaveNoWhiteSpaces() {
        actual.setUri("some white spaces");
        EntityValidator.assertErrors(actual,1);
    }

    @Test
    public void uriShouldBeLowercase() {
        actual.setUri("Uppercase");
        EntityValidator.assertErrors(actual,1);
    }

    @Test
    public void platformsShouldNotBeNull() {
        actual.setPlatforms(null);
        EntityValidator.assertErrors(actual,1);
    }

    @Test
    public void platformsShouldNotBeEmpty() {
        actual.setPlatforms(Collections.emptySet());
        EntityValidator.assertErrors(actual,1);
    }

    @Test
    public void companiesShouldNotBeNull() {
        actual.setCompanies(null);
        EntityValidator.assertErrors(actual,1);
    }

    @Test
    public void companiesShouldNotBeEmpty() {
        actual.setCompanies(Collections.emptySet());
        EntityValidator.assertErrors(actual,1);
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Game.class)
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .withPrefabValues(Game.class, mock(Game.class), mock(Game.class))
                .withPrefabValues(GamePlatform.class, mock(GamePlatform.class), mock(GamePlatform.class))
                .withPrefabValues(Company.class, mock(Company.class), mock(Company.class))
                .verify();
    }

}
