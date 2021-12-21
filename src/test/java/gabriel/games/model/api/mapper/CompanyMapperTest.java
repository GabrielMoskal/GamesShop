package gabriel.games.model.api.mapper;

import gabriel.games.model.api.Company;
import gabriel.games.model.api.dto.CompanyDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompanyMapperTest {

    private CompanyMapper mapper;

    @BeforeEach
    public void setUp() {
        this.mapper = new CompanyMapper();
    }

    @Test
    public void toCompanyDto_CompanyGiven_ShouldReturnValidCompanyDto() {
        assertMappingCorrect("name", Arrays.asList("type1", "type2"));
    }

    private void assertMappingCorrect(String name, List<String> types) {
        Company company = mockCompany(name, types);
        CompanyDto expected = new CompanyDto(name, types);
        CompanyDto actual = mapper.toCompanyDto(company);

        assertEquals(expected, actual);
    }

    private Company mockCompany(String name, List<String> types) {
        Company company = mock(Company.class);
        when(company.getName()).thenReturn(name);
        when(company.getTypeNames()).thenReturn(types);
        return company;
    }

    @Test
    public void toCompanyDto_DifferentCompanyGiven_ShouldReturnValidCompanyDto() {
        assertMappingCorrect("different name", Arrays.asList("different1", "different2"));
    }

    @Test
    public void toCompany_NameGiven_ShouldContainValidName() {
        CompanyDto companyDto = new CompanyDto("name", Collections.emptyList());
        Company actual = mapper.toCompany(companyDto);
        assertEquals(companyDto.getName(), actual.getName());
    }

    @Test
    public void toCompany_TypesGiven_ShouldContainValidTypes() {
        CompanyDto companyDto = new CompanyDto("name", Collections.singletonList("type name"));
        Company actual = mapper.toCompany(companyDto);
        assertEquals(companyDto.getTypes(), actual.getTypeNames());
    }
}
