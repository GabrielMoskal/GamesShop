package gabriel.games.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import gabriel.games.model.dto.UserDto;
import gabriel.games.controller.auth.exception.UserAlreadyExistsException;
import gabriel.games.repository.UserRepository;
import gabriel.games.service.UserService;
import gabriel.games.util.UserUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Before
    public void setUp() {
        this.objectMapper = new ObjectMapper();
    }

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void register_ShouldReturnEmptyUserJsonWithLink() throws Exception {
        UserDto expected = UserUtil.makeUserDto("", "");

        ResultActions resultActions = performGet()
                .andExpect(status().isOk());

        verifyJson(resultActions, expected);
    }

    private ResultActions performGet() throws Exception {
        return mockMvc.perform(
                get("/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
    }

    private void verifyJson(ResultActions resultActions, UserDto expected) throws Exception {
        verifyJsonContent(resultActions, expected);
        verifyJsonLinks(resultActions);
    }

    private void verifyJsonContent(ResultActions resultActions, UserDto expected) throws Exception {
        resultActions
                .andExpect(jsonPath("username", is(expected.getUsername())))
                .andExpect(jsonPath("password", is(expected.getPassword())))
                .andExpect(jsonPath("confirmedPassword", is(expected.getConfirmedPassword())))
                .andExpect(jsonPath("errors", is(expected.getErrors())));
    }

    private void verifyJsonLinks(ResultActions resultActions) throws Exception {
        final String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/register";

        resultActions
                .andExpect(jsonPath("_links.self.href", is(path)));
    }

    @Test
    public void processRegistration_ValidUserDtoGiven_ShouldSaveUserAndReturnUserDtoAsJson() throws Exception {
        UserDto userDto = UserUtil.makeUserDto("valid_name", "valid_pass");
        UserDto expected = UserUtil.makeUserDto(userDto.getUsername(), "");

        ResultActions resultActions = performPostRegister(userDto)
                .andExpect(status().is2xxSuccessful());

        verifyJson(resultActions, expected);
        verifyMethodCalls(userDto);
    }

    private ResultActions performPostRegister(UserDto userDto) throws Exception {
        String inputJson = objectMapper.writeValueAsString(userDto);

        return mockMvc.perform(
                post("/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson)
        );
    }

    private void verifyMethodCalls(UserDto userDto) {
        ArgumentCaptor<UserDto> userDtoCaptor = ArgumentCaptor.forClass(UserDto.class);

        verify(userService, times(1)).register(userDtoCaptor.capture());
        verifyNoMoreInteractions(userRepository);

        assertThat(userDtoCaptor.getValue().getUsername()).isEqualTo(userDto.getUsername());
        assertThat(userDtoCaptor.getValue().getPassword()).isEqualTo(userDto.getPassword());
    }

    @Test
    public void processRegistration_InvalidUserDtoGiven_ShouldReturnTheSameUserDtoAsJsonWithErrors() throws Exception {
        UserDto userDto = UserUtil.makeUserDto("a", "valid_pass");

        ResultActions resultActions = performPostRegister(userDto)
                .andExpect(status().is4xxClientError());

        addExpectedErrors(userDto, "Nazwa użytkownika musi zawierać między 5 a 20 znaków.");

        verifyJson(resultActions, userDto);
    }

    private void addExpectedErrors(UserDto userDto, final String errorMessage) {
        Map<String, String> objectErrors = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();
        fieldErrors.put("username", errorMessage);

        Map<String, Map<String, String>> errors = new HashMap<>();
        errors.put("objectErrors", objectErrors);
        errors.put("fieldErrors", fieldErrors);

        userDto.setErrors(errors);
    }

    @Test
    public void processRegistration_ExistingUserDtoGiven_ShouldReturnTheSameUserDtoAsJsonWithLocalizedErrors() throws Exception {
        mockUserService();

        UserDto userDto = UserUtil.makeUserDto("valid_name", "valid_pass");

        ResultActions resultActions = performPostRegister(userDto)
                .andExpect(status().is4xxClientError());

        addExpectedErrors(userDto, "Użytkownik o podanej nazwie istnieje.");

        verifyInteractions(userDto);
        verifyJson(resultActions, userDto);
    }

    private void mockUserService() {
        doThrow(new UserAlreadyExistsException("msg")).when(userService).register(any());
    }

    private void verifyInteractions(UserDto userDto) {
        verify(userService, times(1)).register(userDto);
        verifyNoMoreInteractions(userRepository);
    }
}
