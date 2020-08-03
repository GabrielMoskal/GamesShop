package gabriel.games.security;

import gabriel.games.model.User;
import gabriel.games.util.UserUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
    public void logoutPostRequestGiven_ShouldSucceedWith3xx() throws Exception {
        authenticateFakeUser();

        performLogout();

        assertUserIsLoggedOut();
    }

    private void authenticateFakeUser() {
        User user = UserUtil.makeUser("username", "password");
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void performLogout() throws Exception {
        mockMvc.perform(
                post("/logout")
                        .with(csrf())
        )
                .andExpect(status().is3xxRedirection());
    }

    private void assertUserIsLoggedOut() {
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
