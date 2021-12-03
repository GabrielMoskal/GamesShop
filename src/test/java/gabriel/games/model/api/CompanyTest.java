package gabriel.games.model.api;

import gabriel.games.model.util.EntityValidator;
import gabriel.games.model.util.GenericWord;
import gabriel.games.model.util.ReflectionSetter;
import gabriel.games.util.Models;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class CompanyTest {

    private EntityValidator<Company> validator;
    private ReflectionSetter<Company> setter;

    @BeforeEach
    public void setUp() {
        Company company = new Company(1L, "company name", Collections.emptySet(), Collections.emptySet());
        this.validator = new EntityValidator<>(company);
        this.setter = new ReflectionSetter<>(company);
    }

    @Test
    public void validCompanyGiven_HasNoErrors() {
        int[] test = new int[5];
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
    public void equalsContract() {
        EqualsVerifier.forClass(Company.class)
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .withPrefabValues(Game.class, Models.makeGame(1), Models.makeGame(2))
                .withPrefabValues(Company.class, Models.makeCompany(1), Models.makeCompany(2))
                .verify();
    }
}
