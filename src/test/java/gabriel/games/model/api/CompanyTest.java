package gabriel.games.model.api;

import gabriel.games.model.util.EntityValidator;
import gabriel.games.model.util.GenericWord;
import gabriel.games.util.ModelUtil;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class CompanyTest {

    private Company company;
    private EntityValidator<Company> validator;

    @BeforeEach
    public void setUp() {
        this.company = new Company(1L, "company name", Collections.emptySet(), Collections.emptySet());
        this.validator = new EntityValidator<>(this.company);
    }

    @Test
    public void validCompanyGiven_HasNoErrors() {
        validator.assertErrors(0);
    }

    @Test
    public void nameShouldNotBeNull() {
        company.setName(null);
        validator.assertErrors(1);
    }

    @Test
    public void nameShouldBeAtLeast1CharacterLong() {
        company.setName("");
        validator.assertErrors(1);
    }

    @Test
    public void nameShouldBeMax128CharactersLong() {
        GenericWord genericWord = new GenericWord();
        company.setName(genericWord.make(129));
        validator.assertErrors(1);
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Company.class)
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .withPrefabValues(Game.class, ModelUtil.makeGame(1), ModelUtil.makeGame(2))
                .withPrefabValues(Company.class, ModelUtil.makeCompany(1), ModelUtil.makeCompany(2))
                .verify();
    }
}
