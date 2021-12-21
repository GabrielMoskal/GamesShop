package gabriel.games.model.api.dto;

import gabriel.games.model.util.EntityValidator;
import gabriel.games.model.util.GenericWord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class CompanyDtoTest {

    private CompanyDto actual;
    private GenericWord genericWord;

    @BeforeEach
    public void setUp() {
        this.genericWord = new GenericWord();
    }

    private List<String> makeCompanyTypes() {
        return Arrays.asList("publisher", "producer");
    }

    @Test
    public void validCompanyDtoGiven_HasNoErrors() {
        actual = new CompanyDto("name", makeCompanyTypes());
        EntityValidator.assertErrors(actual, 0);
    }

    @Test
    public void nameShouldNotBeNull() {
        actual = new CompanyDto(null, makeCompanyTypes());
        EntityValidator.assertErrors(actual, 1);
    }

    @Test
    public void nameShouldBeAtLeast1CharacterLong() {
        actual = new CompanyDto("", makeCompanyTypes());
        EntityValidator.assertErrors(actual, 1);
    }

    @Test
    public void nameShouldBeMax128CharactersLong() {
        actual = new CompanyDto(genericWord.make(129), makeCompanyTypes());
        EntityValidator.assertErrors(actual, 1);
    }

    @Test
    public void typesShouldNotBeNull() {
        actual = new CompanyDto("name", null);
        EntityValidator.assertErrors(actual, 1);
    }

    @Test
    public void typesShouldNotBeEmpty() {
        actual = new CompanyDto("name", Collections.emptyList());
        EntityValidator.assertErrors(actual, 1);
    }

    @Test
    public void typeShouldBeAtLeast1CharacterLong() {
        actual = new CompanyDto("name", Collections.singletonList(""));
        EntityValidator.assertErrors(actual, 1);
    }

    @Test
    public void typeShouldBeMax50CharactersLong() {
        actual = new CompanyDto("name", Collections.singletonList(genericWord.make(51)));
        EntityValidator.assertErrors(actual, 1);
    }
}
