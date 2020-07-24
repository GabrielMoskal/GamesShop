package gabriel.games.controller.auth;

import gabriel.games.dto.UserDto;
import gabriel.games.exception.UserAlreadyExistsException;
import gabriel.games.repository.UserRepository;
import gabriel.games.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void registerForm_ShouldReturnRegistrationPage() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    public void processRegistration_ValidUserDtoGiven_ShouldSaveUserAndRedirectToLoginPage() throws Exception {
        UserDto userDto = new UserDto("valid_name", "valid_pass", "valid_pass");
        performMockMvc(userDto)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
        verifyMethodCalls(userDto);
    }

    private ResultActions performMockMvc(UserDto userDto) throws Exception {
        return mockMvc.perform(
                post("/register")
                        .with(csrf())
                        .param("username", userDto.getUsername())
                        .param("password", userDto.getPassword())
                        .param("confirmedPassword", userDto.getConfirmedPassword())
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
    public void processRegistration_InvalidUserDtoGiven_ShouldReturnToRegistrationPageWithErrors() throws Exception {
        UserDto userDto = new UserDto("a", "valid_pass", "valid_pass");

        performMockMvcExpectErrors(userDto);
    }

    private void performMockMvcExpectErrors(UserDto userDto) throws Exception {
        performMockMvc(userDto)
                .andExpect(model().hasErrors())
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    public void processRegistration_ExistingUserDtoGiven_ShouldReturnToRegistrationPageWithErrors() throws Exception {
        doThrow(new UserAlreadyExistsException("msg")).when(userService).register(any());

        UserDto userDto = new UserDto("valid_username", "valid_pass", "valid_pass");

        performMockMvcExpectErrors(userDto);

        verify(userService, times(1)).register(userDto);
        verifyNoMoreInteractions(userRepository);
    }
}
