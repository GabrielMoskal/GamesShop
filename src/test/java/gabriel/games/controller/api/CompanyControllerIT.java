package gabriel.games.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanyController.class)
public class CompanyControllerIT {

    private final String PATH = "/api/company/name";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
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

    @Test
    public void postCompany_ValidCompanyDtoGiven_ShouldReturn201() throws Exception {
        // given
        CompanyDto companyDto = new CompanyDto("name", Collections.singletonList("type"));
        Company company = mock(Company.class);

        when(companyMapper.toCompany(any())).thenReturn(company);
        when(companyService.save(company)).thenReturn(company);
        when(companyMapper.toCompanyDto(company)).thenReturn(companyDto);

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyDto))
        );

        //then
        verifyToCompanyInteractions(companyDto);
        verifySaveInteractions(company);
        verifyToCompanyDtoInteractions(company);
        resultActions.andExpect(status().isCreated());
    }

    private void verifyToCompanyInteractions(CompanyDto expected) {
        ArgumentCaptor<CompanyDto> captor = ArgumentCaptor.forClass(CompanyDto.class);
        verify(companyMapper).toCompany(captor.capture());
        CompanyDto actual = captor.getValue();
        assertEquals(expected, actual);
    }

    private void verifySaveInteractions(Company expected) {
        ArgumentCaptor<Company> captor = ArgumentCaptor.forClass(Company.class);
        verify(companyService).save(captor.capture());
        Company actual = captor.getValue();
        assertEquals(expected, actual);
    }

    @Test
    public void postCompany_invalidCompanyDtoGiven_ShouldReturn400() throws Exception {
        CompanyDto companyDto = new CompanyDto(null, null);

        mockMvc.perform(post("/api/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyDto)))
                .andExpect(status().isBadRequest());
    }
}
