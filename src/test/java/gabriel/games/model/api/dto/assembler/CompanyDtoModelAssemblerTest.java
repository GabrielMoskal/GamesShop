package gabriel.games.model.api.dto.assembler;

import gabriel.games.model.api.Company;
import gabriel.games.model.api.dto.CompanyDto;
import gabriel.games.model.api.mapper.CompanyMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompanyDtoModelAssemblerTest {

    private CompanyMapper mapper;
    private CompanyDtoModelAssembler assembler;

    @BeforeEach
    public void setUp() {
        mapper = mock(CompanyMapper.class);
        assembler = new CompanyDtoModelAssembler(mapper);
    }

    @Test
    public void instantiateModel_CompanyGiven_ReturnsValidCompanyDto() {
        Company company = mock(Company.class);
        CompanyDto expected = mock(CompanyDto.class);

        when(mapper.toCompanyDto(company)).thenReturn(expected);

        CompanyDto actual = assembler.instantiateModel(company);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void toModel_CompanyGiven_ContainsValidLink() {
        Company company = mock(Company.class);
        when(company.getName()).thenReturn("test_name");

        CompanyDto companyDto = new CompanyDto("test_name", Collections.emptyList());

        when(mapper.toCompanyDto(company)).thenReturn(companyDto);

        CompanyDto actual = assembler.toModel(company);

        String expectedLink = "/api/company/test_name";
        String actualLink = actual.getLink("self").orElse(mock(Link.class)).getHref();
        assertNotNull(actual);
        assertEquals(expectedLink, actualLink);
    }
}
