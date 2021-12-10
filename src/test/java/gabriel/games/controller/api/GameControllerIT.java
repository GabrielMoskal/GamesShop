package gabriel.games.controller.api;

import gabriel.games.controller.util.JsonValidator;
import gabriel.games.model.api.dto.*;
import gabriel.games.model.api.mapper.GameMapper;
import gabriel.games.service.GameService;
import gabriel.games.service.exception.ObjectNotFoundException;
import gabriel.games.model.util.Models;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.*;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameController.class)
@AutoConfigureMockMvc
public class GameControllerIT {

    private String uri = "/api/game/";
    private GameDto gameDto;
    private JsonValidator jsonValidator;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private GameMapper gameMapper;

    @BeforeEach
    public void setUp() {
        this.gameDto = Models.makeGameDto("Multiple words value");
    }

    @Test
    public void description_ExistingResourcePathGiven_ShouldReturnValidJson() throws Exception {
        shouldReturnValidJson();
    }

    private void shouldReturnValidJson() throws Exception {
        mockFindByName();
        ResultActions resultActions = performGetRequest();
        verifyJson(resultActions);
    }

    private void mockFindByName() {
        when(gameMapper.toGameDto(any())).thenReturn(gameDto);
    }

    private ResultActions performGetRequest() throws Exception {
        updateUri();
        return mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    private void updateUri() {
        uri += gameDto.getUri();
    }

    private void verifyJson(ResultActions resultActions) {
        jsonValidator = new JsonValidator(resultActions);

        validateGame(gameDto);
        validateDetails(gameDto.getDetails());
        validatePlatforms(gameDto.getPlatforms());
        validateCompanies(gameDto.getCompanies());
    }

    private void validateGame(GameDto gameDto) {
        jsonValidator.expect("uri", gameDto.getUri());
        jsonValidator.expect("name", gameDto.getName());
    }

    private void validateDetails(GameDetailsDto details) {
        jsonValidator.expect("details.description", details.getDescription());
        jsonValidator.expect("details.webpage", details.getWebpage());
        jsonValidator.expect("details.ratingPlayers", details.getRatingPlayers());
        jsonValidator.expect("details.ratingReviewer", details.getRatingReviewer());
    }

    private void validatePlatforms(List<GamePlatformDto> platforms) {
        platforms.forEach((platform) -> {
            String platformPath = "platforms[" + platforms.indexOf(platform) + "]";
            jsonValidator.expect(platformPath + ".platformName", platform.getPlatformName());
            jsonValidator.expect(platformPath + ".releaseDate", platform.getReleaseDate().toString());
        });
    }

    private void validateCompanies(List<CompanyDto> companies) {
        companies.forEach((company) -> {
            String companyPath = "companies[" + companies.indexOf(company) + "]";
            jsonValidator.expect(companyPath + ".name", company.getName());
            jsonValidator.expect(companyPath + ".types", company.getTypes());
        });
    }

    @Test
    public void description_ExistingDifferentResourcePathGiven_ShouldReturnValidJson() throws Exception {
        gameDto = Models.makeGameDto("different values");
        shouldReturnValidJson();
    }

    @Test
    public void description_ExistingResourcePathGiven_ShouldReturnValidLink() throws Exception {
        shouldReturnValidLink();
    }

    private void shouldReturnValidLink() throws Exception {
        mockFindByName();
        ResultActions resultActions = performGetRequest();
        jsonValidator = new JsonValidator(resultActions);
        jsonValidator.verifyJsonLinks(uri);
    }

    @Test
    public void description_ExistingDifferentResourcePathGiven_ShouldReturnValidLink() throws Exception {
        gameDto = Models.makeGameDto("different values");
        shouldReturnValidLink();
    }

    @Test
    public void description_NonExistingResourcePathGiven_ShouldReturn404() throws Exception {
        when(gameService.findByUri(gameDto.getUri())).thenThrow(new ObjectNotFoundException("msg"));
        performGetRequest().andExpect(status().isNotFound());
    }

    @Test
    public void description_ExistingResourcePathGiven_ShouldReturn200() throws Exception {
        mockFindByName();
        performGetRequest().andExpect(status().isOk());
    }
}
