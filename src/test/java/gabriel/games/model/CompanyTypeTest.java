package gabriel.games.model;

import gabriel.games.model.dto.util.EntityValidator;
import gabriel.games.model.dto.util.GenericWord;
import gabriel.games.util.ModelUtil;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class CompanyTypeTest {

    private CompanyType companyType;
    private EntityValidator<CompanyType> validator;

    @BeforeEach
    public void setUp() {
        this.companyType = new CompanyType(1L, "producer", Collections.emptySet());
        this.validator = new EntityValidator<>(this.companyType);
    }

    @Test
    public void validCompanyTypeGiven_HasNoErrors() {
        validator.assertErrors(0);
    }

    @Test
    public void typeShouldNotBeNull() {
        companyType.setType(null);
        validator.assertErrors(1);
    }

    @Test
    public void typeShouldBeAtLeast1CharacterLong() {
        companyType.setType("");
        validator.assertErrors(1);
    }

    @Test
    public void typeShouldBeMax50CharactersLong() {
        GenericWord genericWord = new GenericWord();
        companyType.setType(genericWord.make(51));
        validator.assertErrors(1);
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(CompanyType.class)
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .withPrefabValues(Company.class, ModelUtil.makeCompany(1), ModelUtil.makeCompany(2))
                .verify();
    }
}
