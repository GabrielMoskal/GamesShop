package gabriel.games.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

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
