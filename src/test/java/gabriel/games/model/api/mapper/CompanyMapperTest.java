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
        assertConversionCorrect("name", Arrays.asList("type1", "type2"));
    }

    private void assertConversionCorrect(String name, List<String> types) {
        Company company = mockCompany(name, types);
        CompanyDto expected = new CompanyDto(name, types);
        CompanyDto actual = mapper.toCompanyDto(company);

        assertEquals(expected, actual);
    }

    private Company mockCompany(String name, List<String> types) {
        Company company = mock(Company.class);
        when(company.getName()).thenReturn(name);
        when(company.getCompanyTypeNames()).thenReturn(types);
        return company;
    }

    @Test
    public void toCompanyDto_DifferentCompanyGiven_ShouldReturnValidCompanyDto() {
        assertConversionCorrect("different name", Arrays.asList("different1", "different2"));
    }
}
