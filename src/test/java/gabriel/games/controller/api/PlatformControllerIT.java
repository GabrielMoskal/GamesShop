package gabriel.games.controller.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlatformController.class)
public class PlatformControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getPlatform_NameGiven_ShouldReturnValidPlatform() throws Exception {
        mockMvc.perform(get("/api/platform/playstation3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
