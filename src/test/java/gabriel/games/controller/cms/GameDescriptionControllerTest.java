package gabriel.games.controller.cms;

import gabriel.games.controller.JsonValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameDescriptionControllerTest {

    private final String uri = "/cms/gamedescription";

    private MockMvc mockMvc;

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void description_ExistingResourcePathGiven_ShouldReturnValidJson() throws Exception {
        String shortDescription = "Test short description";
        String webpage = "https://test-link.com";
        String ratingPlayers = "7.0";
        String ratingReviewer = "6.5";
        String[] platforms = {"PC", "XBOX 360"};
        String releaseDate = "2000-01-01";
        String producer = "Test Producer";
        String publisher = "Test Publisher";

        ResultActions resultActions = mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE));

        JsonValidator jsonValidator = new JsonValidator(resultActions);
        jsonValidator.expect("description", shortDescription);
        jsonValidator.expect("webpage", webpage);
        jsonValidator.expect("playerRating", ratingPlayers);
        jsonValidator.expect("reviewerRating", ratingReviewer);
        jsonValidator.expect("platforms[0]", platforms[0]);
        jsonValidator.expect("platforms[1]", platforms[1]);
        jsonValidator.expect("releaseDate", releaseDate);
        jsonValidator.expect("producer", producer);
        jsonValidator.expect("publisher", publisher);
    }
}
