package gabriel.games.controller.api;

import gabriel.games.controller.util.CompanyDtoValidator;
import gabriel.games.model.api.Company;
import gabriel.games.model.api.dto.CompanyDto;
import gabriel.games.model.api.mapper.CompanyMapper;
import gabriel.games.service.CompanyService;
import gabriel.games.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanyController.class)
public class CompanyControllerIT {

    private final String PATH = "/api/company/name";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CompanyService companyService;
    @MockBean
    private CompanyMapper companyMapper;
    private CompanyDtoValidator companyDtoValidator;

    @BeforeEach
    public void setUp() {
        this.companyDtoValidator = new CompanyDtoValidator();
    }

    @Test
    public void getCompany_ExistingCompanyGiven_ShouldReturn200() throws Exception {
        Company company = mock(Company.class);
        when(companyService.findByName("name")).thenReturn(company);
        CompanyDto companyDto = new CompanyDto("name", Collections.singletonList("type"));
        when(companyMapper.toCompanyDto(company)).thenReturn(companyDto);

        ResultActions resultActions = mockMvc.perform(get(PATH));

        verifyFindByNameInteractions("name");
        verifyToCompanyDtoInteractions(company);
        resultActions.andExpect(status().isOk());
        companyDtoValidator.validate(resultActions, companyDto, PATH);
    }

    private void verifyFindByNameInteractions(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(companyService).findByName(captor.capture());
        verifyNoMoreInteractions(companyService);
        assertEquals(expected, captor.getValue());
    }

    private void verifyToCompanyDtoInteractions(Company expected) {
        ArgumentCaptor<Company> captor = ArgumentCaptor.forClass(Company.class);
        verify(companyMapper).toCompanyDto(captor.capture());
        verifyNoMoreInteractions(companyMapper);
        assertEquals(expected, captor.getValue());
    }

    @Test
    public void invalidNameGiven_ShouldThrowException() throws Exception {
        when(companyService.findByName("name")).thenThrow(ObjectNotFoundException.class);

        mockMvc.perform(get(PATH)).andExpect(status().isNotFound());
    }
}
