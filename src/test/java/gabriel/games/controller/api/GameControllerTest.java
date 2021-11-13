package gabriel.games.controller.api;

import gabriel.games.controller.JsonValidator;
import gabriel.games.model.dto.GameDto;
import gabriel.games.service.GameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {

    private String uri = "/api/game/";
    private GameDto gameDto;
    private JsonValidator jsonValidator;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    public void description_ExistingResourcePathGiven_ShouldReturnValidJson() throws Exception {
        gameDto = buildValidGameDto();
        ResultActions resultActions = performGetRequest();
        verifyJson(resultActions);
    }

    private GameDto buildValidGameDto() {
        return GameDto.builder()
                .uri("test")
                .name("test")
                .description("Test short description")
                .webpage("https://test-link.com")
                .playerRating("7.0")
                .reviewerRating("6.5")
                .platforms(Arrays.asList("PC", "XBOX 360"))
                .releaseDate("2000-01-01")
                .producer("Test Producer")
                .publisher("Test Publisher")
                .build();
    }

    private ResultActions performGetRequest() throws Exception {
        updateUri();
        mockFindByName();
        return mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    private void updateUri() {
        uri += gameDto.getUri();
    }

    private void mockFindByName() {
        when(gameService.findByUri(gameDto.getUri())).thenReturn(gameDto);
    }

    private void verifyJson(ResultActions resultActions) throws Exception {
        jsonValidator = new JsonValidator(resultActions);

        jsonValidator.expect("uri", gameDto.getUri());
        jsonValidator.expect("name", gameDto.getName());
        jsonValidator.expect("description", gameDto.getDescription());
        jsonValidator.expect("webpage", gameDto.getWebpage());
        jsonValidator.expect("playerRating", gameDto.getPlayerRating());
        jsonValidator.expect("reviewerRating", gameDto.getReviewerRating());
        jsonValidator.expect("platforms", gameDto.getPlatforms());
        jsonValidator.expect("releaseDate", gameDto.getReleaseDate());
        jsonValidator.expect("producer", gameDto.getProducer());
        jsonValidator.expect("publisher", gameDto.getPublisher());
    }

    @Test
    public void description_ExistingDifferentResourcePathGiven_ShouldReturnValidJson() throws Exception {
        gameDto = buildDifferentValidGameDto();
        ResultActions resultActions = performGetRequest();
        verifyJson(resultActions);
    }

    private GameDto buildDifferentValidGameDto() {
        return GameDto.builder()
                .uri("two-words")
                .name("Two words")
                .description("Test different description")
                .webpage("https://test-different-link.com")
                .playerRating("1.0")
                .reviewerRating("1.5")
                .platforms(Collections.singletonList("Playstation 5"))
                .releaseDate("2011-11-11")
                .producer("Test different Producer")
                .publisher("Test different Publisher")
                .build();
    }

    @Test
    public void description_ExistingResourcePathGiven_ShouldReturnValidLink() throws Exception {
        gameDto = buildValidGameDto();
        ResultActions resultActions = performGetRequest();
        jsonValidator = new JsonValidator(resultActions);
        jsonValidator.verifyJsonLinks(uri);
    }

    @Test
    public void description_ExistingDifferentResourcePathGiven_ShouldReturnValidLink() throws Exception {
        gameDto = buildDifferentValidGameDto();
        ResultActions resultActions = performGetRequest();
        jsonValidator = new JsonValidator(resultActions);
        jsonValidator.verifyJsonLinks(uri);
    }

    @Test
    public void description_MultipleWordsResourcePathGiven_ShouldReturnLinkWithValidFormat() throws Exception {
        gameDto = buildDifferentValidGameDto();
        ResultActions resultActions = performGetRequest();
        jsonValidator = new JsonValidator(resultActions);
        verifyJsonLinks();
    }

    public void verifyJsonLinks() throws Exception {
        final String expectedLink = makeExpectedLink();
        jsonValidator.expect("_links.self.href", expectedLink);
    }

    private String makeExpectedLink() {
        UriComponents uriComponents = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .build();
        return uriComponents.toUriString() + uri;
    }
}
