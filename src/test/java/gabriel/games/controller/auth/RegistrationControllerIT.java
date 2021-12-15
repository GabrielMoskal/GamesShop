package gabriel.games.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import gabriel.games.controller.util.UserValidator;
import gabriel.games.model.auth.dto.UserDto;
import gabriel.games.controller.auth.exception.UserAlreadyExistsException;
import gabriel.games.model.dto.ErrorDto;
import gabriel.games.service.*;
import gabriel.games.model.util.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistrationController.class)
public class RegistrationControllerIT {

    private String uri;
    private UserDto userDto;
    private UserValidator userValidator;
    @Autowired private MockMvc mockMvc;
    @MockBean private UserService userService;
    @MockBean private ErrorService errorService;

    @BeforeEach
    public void setUp() {
        this.uri = "/register";
        this.userDto = Users.makeUserDto("valid_name", "valid_pass");
        this.userValidator = new UserValidator();
    }

    @Test
    public void register_ShouldReturnEmptyUserJsonWithLink() throws Exception {
        userDto = Users.makeUserDto("", "");
        ResultActions resultActions = performGet().andExpect(status().isOk());
        userValidator.validate(resultActions, uri, userDto);
    }

    private ResultActions performGet() throws Exception {
        return mockMvc.perform(
                get("/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
    }

    @Test
    public void processRegistration_ValidUserDtoGiven_ShouldSaveUserAndReturnUserDtoAsJson() throws Exception {
        ResultActions resultActions = performPostRegister()
                .andExpect(status().is2xxSuccessful());

        verifyMethodCalls();
        userDto = Users.makeUserDto(userDto.getUsername(), "");
        userValidator.validate(resultActions, uri, userDto);
    }

    private ResultActions performPostRegister() throws Exception {
        String inputJson = writeUserDtoAsJson();

        return mockMvc.perform(
                post("/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson)
        );
    }

    private String writeUserDtoAsJson() throws Exception {
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
        verifyNoMoreInteractions(userService);
        return userDtoCaptor;
    }

    private void assertCapturedValuesEqualsUserDto(UserDto captured) {
        assertThat(captured.getUsername()).isEqualTo(userDto.getUsername());
        assertThat(captured.getPassword()).isEqualTo(userDto.getPassword());
    }

    @Test
    public void processRegistration_InvalidUserDtoGiven_ShouldReturnTheSameUserDtoAsJsonWithErrors() throws Exception {
        userDto = Users.makeUserDto("a", userDto.getPassword());
        addExpectedErrorsToUserDto("Nazwa użytkownika musi zawierać między 5 a 20 znaków.");
        mockErrorService();
        ResultActions resultActions = performPostRegister().andExpect(status().is4xxClientError());
        userValidator.validate(resultActions, uri, userDto);
    }

    private void addExpectedErrorsToUserDto(String errorMessage) {
        List<ErrorDto> fieldErrors = new ArrayList<>();
        fieldErrors.add(new ErrorDto(ErrorDto.Type.FIELD_ERROR, "username", errorMessage));
        userDto.setErrors(fieldErrors);
    }

    private void mockErrorService() {
        when(errorService.toErrorDtos(any())).thenReturn(userDto.getErrors());
    }

    @Test
    public void processRegistration_ExistingUserDtoGiven_ShouldReturnTheSameUserDtoAsJsonWithErrors() throws Exception {
        mockUserService();
        addExpectedErrorsToUserDto("Użytkownik o podanej nazwie istnieje.");
        mockErrorService();
        ResultActions resultActions = performPostRegister().andExpect(status().is4xxClientError());
        verifyInteractions();
        userValidator.validate(resultActions, uri, userDto);
    }

    private void mockUserService() {
        doThrow(new UserAlreadyExistsException("msg")).when(userService).register(any());
    }
}
