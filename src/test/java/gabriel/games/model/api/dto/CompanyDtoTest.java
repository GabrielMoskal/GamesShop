package gabriel.games.model.api.dto;

import gabriel.games.model.util.EntityValidator;
import gabriel.games.model.util.GenericWord;
import gabriel.games.model.util.ReflectionSetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CompanyDtoTest {

    private EntityValidator<CompanyDto> validator;
    private ReflectionSetter<CompanyDto> setter;
    private GenericWord genericWord;

    @BeforeEach
    public void setUp() {
        CompanyDto companyDto = makeCompanyDto();
        this.validator = new EntityValidator<>(companyDto);
        this.setter = new ReflectionSetter<>(companyDto);
        this.genericWord = new GenericWord();
    }

    private CompanyDto makeCompanyDto() {
        String companyName = "Company Name";
        List<String> companyTypes = Arrays.asList("publisher", "producer");
        return new CompanyDto(companyName, companyTypes);
    }

    @Test
    public void validCompanyDtoGiven_HasNoErrors() {
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
        setter.set("name", genericWord.make(129));
        validator.assertErrors(1);
    }

    @Test
    public void typesShouldNotBeNull() {
        setter.set("types", null);
        validator.assertErrors(1);
    }

    @Test
    public void typesShouldNotBeEmpty() {
        setter.set("types", Collections.emptyList());
        validator.assertErrors(1);
    }

    @Test
    public void typeShouldBeAtLeast1CharacterLong() {
        setter.set("types", Collections.singletonList(""));
        validator.assertErrors(1);
    }

    @Test
    public void typeShouldBeMax50CharactersLong() {
        setter.set("types", Collections.singletonList(genericWord.make(51)));
        validator.assertErrors(1);
    }
}
