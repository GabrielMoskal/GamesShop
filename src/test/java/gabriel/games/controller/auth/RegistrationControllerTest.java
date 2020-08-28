package gabriel.games.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import gabriel.games.dto.UserDto;
import gabriel.games.exception.UserAlreadyExistsException;
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

import java.util.Arrays;
import java.util.List;

import static gabriel.games.util.ResponseBodyMatchers.responseBody;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    public void register_ShouldReturnEmptyUserDtoJson() throws Exception {
        UserDto userDto = UserUtil.makeUserDto("", "");
        mockMvc.perform(
                get("/register")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(responseBody().containsObjectAsJson("user", userDto, UserDto.class));
    }

    @Test
    public void processRegistration_ValidUserDtoGiven_ShouldSaveUserAndReturnUserDtoAsJson() throws Exception {
        UserDto userDto = UserUtil.makeUserDto("valid_name", "valid_pass");
        UserDto expected = UserUtil.makeUserDto(userDto.getUsername(), "");

        performPostRegister(userDto)
                .andExpect(status().is2xxSuccessful())
                .andExpect(responseBody().containsObjectAsJson("user", expected, UserDto.class));

        verifyMethodCalls(userDto);
    }

    private ResultActions performPostRegister(UserDto userDto) throws Exception {
        String inputJson = objectMapper.writeValueAsString(userDto);

        return mockMvc.perform(
                post("/register")
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

        performMockMvcExpectErrors(userDto);
    }

    private ResultActions performMockMvcExpectErrors(UserDto userDto) throws Exception {
        return performPostRegister(userDto)
                .andExpect(status().is4xxClientError())
                .andExpect(responseBody().containsErrorsAsJson())
                .andExpect(responseBody().containsObjectAsJson("user", userDto, UserDto.class));
    }

    @Test
    public void processRegistration_ExistingUserDtoGiven_ShouldReturnTheSameUserDtoAsJsonWithLocalizedErrors() throws Exception {
        doThrow(new UserAlreadyExistsException("msg")).when(userService).register(any());

        UserDto userDto = UserUtil.makeUserDto("valid_name", "valid_pass");

        performMockMvcExpectLocalizedError(userDto);

        verify(userService, times(1)).register(userDto);
        verifyNoMoreInteractions(userRepository);
    }

    private void performMockMvcExpectLocalizedError(UserDto userDto) throws Exception {
        List<String> path = Arrays.asList("errors", "fieldErrors", "username");

        String message = "Użytkownik o podanej nazwie istnieje.";

        performMockMvcExpectErrors(userDto)
                .andExpect(responseBody().containErrorAsJson(message, path));
    }
}
