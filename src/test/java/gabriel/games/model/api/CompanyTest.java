package gabriel.games.model.api;

import gabriel.games.model.util.*;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class CompanyTest {

    private Company actual;

    @Test
    public void constructorTest() {
        actual = new Company("name");
        assertEquals("name", actual.getName());
        assertTrue(actual.getCompanyTypes().isEmpty());
    }

    @Test
    public void addCompanyType_CompanyTypeGiven_ContainsCompanyType() {
        actual = new Company("name");
        CompanyType companyType = mock(CompanyType.class);
        actual.addCompanyType(companyType);
        assertTrue(actual.getCompanyTypes().contains(companyType));
    }

    @Test
    public void validCompanyGiven_HasNoErrors() {
        actual = new Company("company name");
        EntityValidator.assertErrors(actual, 0);
    }

    @Test
    public void nameShouldNotBeNull() {
        actual = new Company(null);
        EntityValidator.assertErrors(actual, 1);
    }

    @Test
    public void nameShouldBeAtLeast1CharacterLong() {
        actual = new Company("");
        EntityValidator.assertErrors(actual, 1);
    }

    @Test
    public void nameShouldBeMax128CharactersLong() {
        actual = new Company(GenericWord.make(129));
        EntityValidator.assertErrors(actual, 1);
    }

    @Test
    public void getCompanyTypeNames_ShouldReturnValidNames() {
        actual = new Company("name");
        actual.addCompanyType(new CompanyType("type1"));
        actual.addCompanyType(new CompanyType("type2"));
        List<String> expected = Arrays.asList("type1", "type2");
        assertThat(expected).hasSameElementsAs(actual.getTypeNames());
    }

    @Test
    public void getCompanyTypesNames_ShouldNeverReturnNull() {
        actual = new Company("name");
        assertNotNull(actual.getTypeNames());
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Company.class)
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .withPrefabValues(Game.class, mock(Game.class), mock(Game.class))
                .withPrefabValues(Company.class, mock(Company.class), mock(Company.class))
                .verify();
    }
}
