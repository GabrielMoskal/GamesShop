package gabriel.games.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import gabriel.games.controller.util.JsonValidator;
import gabriel.games.model.dto.UserDto;
import gabriel.games.controller.auth.exception.UserAlreadyExistsException;
import gabriel.games.repository.UserRepository;
import gabriel.games.service.UserService;
import gabriel.games.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RegistrationController.class)
@AutoConfigureMockMvc
public class RegistrationControllerIT {

    private UserDto userDto;
    private JsonValidator jsonValidator;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userDto = UserUtil.makeUserDto("valid_name", "valid_pass");
    }

    @Test
    public void register_ShouldReturnEmptyUserJsonWithLink() throws Exception {
        userDto = UserUtil.makeUserDto("", "");
        ResultActions resultActions = performGet().andExpect(status().isOk());
        verifyJson(resultActions);
    }

    private ResultActions performGet() throws Exception {
        return mockMvc.perform(
                get("/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
    }

    private void verifyJson(ResultActions resultActions) throws Exception {
        jsonValidator = new JsonValidator(resultActions);
        verifyJsonContent();
        jsonValidator.verifyJsonLinks("/register");
    }

    private void verifyJsonContent() throws Exception {
        jsonValidator.expect("username", userDto.getUsername());
        jsonValidator.expect("password", userDto.getPassword());
        jsonValidator.expect("confirmedPassword", userDto.getConfirmedPassword());
        jsonValidator.expect("errors", userDto.getErrors());
    }

    @Test
    public void processRegistration_ValidUserDtoGiven_ShouldSaveUserAndReturnUserDtoAsJson() throws Exception {
        ResultActions resultActions = performPostRegister()
                .andExpect(status().is2xxSuccessful());

        verifyMethodCalls();
        userDto = UserUtil.makeUserDto(userDto.getUsername(), "");
        verifyJson(resultActions);
    }

    private ResultActions performPostRegister() throws Exception {
        String inputJson = transformUserDtoToJson();

        return mockMvc.perform(
                post("/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson)
        );
    }

    private String transformUserDtoToJson() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(userDto);
    }

    private void verifyMethodCalls() {
        ArgumentCaptor<UserDto> userDtoCaptor = verifyInteractions();
        assertCapturedValuesEqualsUserDto(userDtoCaptor.getValue());
    }

    private ArgumentCaptor<UserDto> verifyInteractions() {
        ArgumentCaptor<UserDto> userDtoCaptor = ArgumentCaptor.forClass(UserDto.class);

        verify(userService, times(1)).register(userDtoCaptor.capture());
        verifyNoMoreInteractions(userRepository);

        return userDtoCaptor;
    }

    private void assertCapturedValuesEqualsUserDto(UserDto captured) {
        assertThat(captured.getUsername()).isEqualTo(userDto.getUsername());
        assertThat(captured.getPassword()).isEqualTo(userDto.getPassword());
    }

    @Test
    public void processRegistration_InvalidUserDtoGiven_ShouldReturnTheSameUserDtoAsJsonWithErrors() throws Exception {
        userDto = UserUtil.makeUserDto("a", userDto.getPassword());
        addExpectedErrorsToUserDto("Nazwa użytkownika musi zawierać między 5 a 20 znaków.");

        ResultActions resultActions = performPostRegister().andExpect(status().is4xxClientError());

        verifyJson(resultActions);
    }

    private void addExpectedErrorsToUserDto(String expectedErrorMessage) {
        addExpectedErrors(expectedErrorMessage);
    }

    private void addExpectedErrors(final String errorMessage) {
        Map<String, String> fieldErrors = addExpectedFieldErrors(errorMessage);
        Map<String, Map<String, String>> errors = addErrors(fieldErrors);
        userDto.setErrors(errors);
    }

    private Map<String, String> addExpectedFieldErrors(String errorMessage) {
        Map<String, String> fieldErrors = new HashMap<>();
        fieldErrors.put("username", errorMessage);
        return fieldErrors;
    }

    private Map<String, Map<String, String>> addErrors(Map<String, String> fieldErrors) {
        Map<String, Map<String, String>> errors = new HashMap<>();
        errors.put("objectErrors", new HashMap<>());
        errors.put("fieldErrors", fieldErrors);
        return errors;
    }

    @Test
    public void processRegistration_ExistingUserDtoGiven_ShouldReturnTheSameUserDtoAsJsonWithLocalizedErrors() throws Exception {
        mockUserService();
        addExpectedErrorsToUserDto("Użytkownik o podanej nazwie istnieje.");

        ResultActions resultActions = performPostRegister().andExpect(status().is4xxClientError());

        verifyInteractions();
        verifyJson(resultActions);
    }

    private void mockUserService() {
        doThrow(new UserAlreadyExistsException("msg")).when(userService).register(any());
    }
}
