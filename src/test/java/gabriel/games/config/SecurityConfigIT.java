package gabriel.games.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigIT {

    private MockMvc mockMvc;

    @Autowired
    void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void existingUserGiven_ShouldSucceedWith3xx() throws Exception {
        mockMvc.perform(
                post("/login")
                        .with(csrf())
                        .param("username", "user1")
                        .param("password", "password1")
        )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    public void logoutPostRequestGiven_ShouldSucceedWith3xx() throws Exception {
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());

        performLogout();

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    private void performLogout() throws Exception {
        mockMvc.perform(
                post("/logout")
                        .with(csrf())
        )
                .andExpect(status().is3xxRedirection());
    }
}
