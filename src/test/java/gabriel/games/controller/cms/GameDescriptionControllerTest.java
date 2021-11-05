package gabriel.games.controller.cms;

import gabriel.games.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameDescriptionControllerTest {

    private final String uri = "/cms/gamedescription";

    private MockMvc mockMvc;

    private User admin;

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Autowired
    public void setAdmin(User admin) {
        this.admin = admin;
    }

    @Test
    @WithMockUser
    public void description_UserRoleUserGiven_ShouldReturn4xxStatus() throws Exception {
        mockMvc.perform(get(uri))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void description_UserRoleAdminGiven_ShouldReturn200Status() throws Exception {
        authenticateAdmin();

        mockMvc.perform(get(uri))
                .andExpect(status().isOk());
    }

    private void authenticateAdmin() {
        Authentication auth = new UsernamePasswordAuthenticationToken(admin, null, admin.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    public void description_ExistingResourcePathGiven_ShouldReturnValidGameDescriptionDtoJson() throws Exception {

    }

}
