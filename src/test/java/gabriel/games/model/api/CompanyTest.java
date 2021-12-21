package gabriel.games.model.api;

import gabriel.games.model.util.*;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class CompanyTest {

    private Company actual;

    @Test
    public void constructorTest() {
        Set<CompanyType> companyTypes = makeCompanyTypes();
        actual = new Company("name", companyTypes);

        assertEquals("name", actual.getName());
        assertEquals(companyTypes, actual.getTypes());
    }

    private Set<CompanyType> makeCompanyTypes() {
        Set<CompanyType> companyTypes = new HashSet<>();
        companyTypes.add(new CompanyType("type1"));
        companyTypes.add(new CompanyType("type2"));
        return companyTypes;
    }

    @Test
    public void validCompanyGiven_HasNoErrors() {
        actual = new Company("company name", Collections.emptySet());
        EntityValidator.assertErrors(actual, 0);
    }

    @Test
    public void nameShouldNotBeNull() {
        actual = new Company(null, Collections.emptySet());
        EntityValidator.assertErrors(actual, 1);
    }

    @Test
    public void nameShouldBeAtLeast1CharacterLong() {
        actual = new Company("", Collections.emptySet());
        EntityValidator.assertErrors(actual, 1);
    }

    @Test
    public void nameShouldBeMax128CharactersLong() {
        GenericWord genericWord = new GenericWord();
        actual = new Company(genericWord.make(129), Collections.emptySet());
        EntityValidator.assertErrors(actual, 1);
    }

    @Test
    public void getCompanyTypeNames_ShouldReturnValidNames() {
        Set<CompanyType> companyTypes = makeCompanyTypes();
        actual = new Company("name", companyTypes);
        List<String> expected = Arrays.asList("type1", "type2");
        assertThat(expected).hasSameElementsAs(actual.getTypeNames());
    }

    @Test
    public void getCompanyTypesNames_ShouldNeverReturnNull() {
        actual = new Company("name", null);
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
