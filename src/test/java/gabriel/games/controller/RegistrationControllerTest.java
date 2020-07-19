package gabriel.games.controller;

import gabriel.games.dto.UserDto;
import gabriel.games.model.User;
import gabriel.games.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void registerForm_ShouldReturnRegistrationPage() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    public void processRegistration_RegistrationFormGiven_ShouldSaveUserAndRedirectToLoginPage() throws Exception {
        UserDto userDto = new UserDto("name", "pass");
        verifyMockMvc(userDto);
        verifyMethodCalls(userDto);
    }

    private void verifyMockMvc(UserDto userDto) throws Exception {
        mockMvc.perform(
                post("/register")
                        .with(csrf())
                        .param("username", userDto.getUsername())
                        .param("password", userDto.getPassword())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
    }

    private void verifyMethodCalls(UserDto userDto) {
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository, times(1)).save(userCaptor.capture());
        verifyNoMoreInteractions(userRepository);

        assertThat(userCaptor.getValue().getUsername()).isEqualTo(userDto.getUsername());
        assertThat(userCaptor.getValue().getPassword()).isEqualTo(userDto.getPassword());
    }
}
