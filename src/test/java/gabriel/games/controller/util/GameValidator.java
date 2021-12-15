package gabriel.games.controller.util;

import gabriel.games.model.api.dto.CompanyDto;
import gabriel.games.model.api.dto.GameDetailsDto;
import gabriel.games.model.api.dto.GameDto;
import gabriel.games.model.api.dto.GamePlatformDto;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

public class GameValidator extends DtoValidator {

    private GameDto expected;

    public void validate(ResultActions resultActions, String uri, GameDto expected) {
        setMembers(resultActions, uri, expected);
        validateGame();
        validateDetails();
        validatePlatforms();
        validateCompanies();
        validateLink();
    }

    private void setMembers(ResultActions resultActions, String uri, GameDto expected) {
        super.setMembers(resultActions, uri);
        this.expected = expected;
    }

    private void validateGame() {
        jsonValidator.expect("uri", expected.getUri());
        jsonValidator.expect("name", expected.getName());
    }

    private void validateDetails() {
        GameDetailsDto details = expected.getDetails();
        jsonValidator.expect("details.description", details.getDescription());
        jsonValidator.expect("details.webpage", details.getWebpage());
        jsonValidator.expect("details.ratingPlayers", details.getRatingPlayers());
        jsonValidator.expect("details.ratingReviewer", details.getRatingReviewer());
    }

    private void validatePlatforms() {
        List<GamePlatformDto> platforms = expected.getPlatforms();
        platforms.forEach((platform) -> {
            String platformPath = "platforms[" + platforms.indexOf(platform) + "]";
            jsonValidator.expect(platformPath + ".platformName", platform.getPlatformName());
            jsonValidator.expect(platformPath + ".releaseDate", platform.getReleaseDate().toString());
        });
    }

    private void validateCompanies() {
        List<CompanyDto> companies = expected.getCompanies();
        companies.forEach((company) -> {
            String companyPath = "companies[" + companies.indexOf(company) + "]";
            jsonValidator.expect(companyPath + ".name", company.getName());
            jsonValidator.expect(companyPath + ".types", company.getTypes());
        });
    }
}
