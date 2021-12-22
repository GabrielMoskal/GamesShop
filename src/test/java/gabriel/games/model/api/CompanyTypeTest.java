package gabriel.games.model.api;

import gabriel.games.model.util.*;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class CompanyTypeTest {

    private CompanyType actual;

    @Test
    public void constructorTest() {
        actual = new CompanyType("type");
        assertEquals("type", actual.getType());
        assertTrue(actual.getCompanies().isEmpty());
    }

    @Test
    public void addCompany_CompanyGiven_ContainsCompany() {
        actual = new CompanyType("type");
        Company company = mock(Company.class);
        actual.addCompany(company);
        assertTrue(actual.getCompanies().contains(company));
    }

    @Test
    public void validCompanyTypeGiven_HasNoErrors() {
        actual = new CompanyType("type");
        EntityValidator.assertErrors(actual, 0);
    }

    @Test
    public void typeShouldNotBeNull() {
        actual = new CompanyType(null);
        EntityValidator.assertErrors(actual, 1);
    }

    @Test
    public void typeShouldBeAtLeast1CharacterLong() {
        actual = new CompanyType("");
        EntityValidator.assertErrors(actual, 1);
    }

    @Test
    public void typeShouldBeMax50CharactersLong() {
        GenericWord genericWord = new GenericWord();
        actual = new CompanyType(genericWord.make(51));
        EntityValidator.assertErrors(actual, 1);
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(CompanyType.class)
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .withPrefabValues(Company.class, mock(Company.class), mock(Company.class))
                .verify();
    }
}
