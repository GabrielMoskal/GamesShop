package gabriel.games.model.api;

import gabriel.games.model.util.*;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompanyTest {

    private Company company;
    private EntityValidator<Company> validator;
    private ReflectionSetter<Company> setter;

    @BeforeEach
    public void setUp() {
        this.company = new Company(1L, "company name", Collections.emptySet(), Collections.emptySet());
        this.validator = new EntityValidator<>(this.company);
        this.setter = new ReflectionSetter<>(this.company);
    }

    @Test
    public void validCompanyGiven_HasNoErrors() {
        validator.assertErrors(0);
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
    public void nameShouldBeMax128CharactersLong() {
        GenericWord genericWord = new GenericWord();
        setter.set("name", genericWord.make(129));
        validator.assertErrors(1);
    }

    @Test
    public void getCompanyTypeNames_ShouldReturnValidNames() {
        addValidCompanyTypes();
        List<String> expected = Arrays.asList("type1", "type2");
        List<String> actual = company.getCompanyTypeNames();
        assertThat(expected).hasSameElementsAs(actual);
    }

    private void addValidCompanyTypes() {
        Set<CompanyType> companyTypes = new HashSet<>();
        companyTypes.add(mockCompanyType(1));
        companyTypes.add(mockCompanyType(2));
        setter.set("types", companyTypes);
    }

    private CompanyType mockCompanyType(int index) {
        CompanyType companyType = mock(CompanyType.class);
        when(companyType.getType()).thenReturn("type" + index);
        return companyType;
    }

    @Test
    public void getCompanyTypesNames_ShouldNeverReturnNull() {
        setter.set("types", null);
        assertNotNull(company.getCompanyTypeNames());
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
