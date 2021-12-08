package gabriel.games.model.api;

import gabriel.games.model.util.EntityValidator;
import gabriel.games.model.util.GenericWord;
import gabriel.games.model.util.ReflectionSetter;
import gabriel.games.model.util.Models;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class CompanyTypeTest {

    private EntityValidator<CompanyType> validator;
    private ReflectionSetter<CompanyType> setter;

    @BeforeEach
    public void setUp() {
        CompanyType companyType = new CompanyType(1L, "producer", Collections.emptySet());
        this.validator = new EntityValidator<>(companyType);
        this.setter = new ReflectionSetter<>(companyType);
    }

    @Test
    public void validCompanyTypeGiven_HasNoErrors() {
        validator.assertErrors(0);
    }

    @Test
    public void typeShouldNotBeNull() {
        setter.set("type", null);
        validator.assertErrors(1);
    }

    @Test
    public void typeShouldBeAtLeast1CharacterLong() {
        setter.set("type", "");
        validator.assertErrors(1);
    }

    @Test
    public void typeShouldBeMax50CharactersLong() {
        GenericWord genericWord = new GenericWord();
        setter.set("type", genericWord.make(51));
        validator.assertErrors(1);
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(CompanyType.class)
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .withPrefabValues(Company.class, Models.makeCompany(1), Models.makeCompany(2))
                .verify();
    }
}
