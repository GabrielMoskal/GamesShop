package gabriel.games.model.api.mapper;

import gabriel.games.model.api.Company;
import gabriel.games.model.api.Game;
import gabriel.games.model.api.GameDetails;
import gabriel.games.model.api.GamePlatform;
import gabriel.games.model.api.dto.CompanyDto;
import gabriel.games.model.api.dto.GameDetailsDto;
import gabriel.games.model.api.dto.GameDto;
import gabriel.games.model.api.dto.GamePlatformDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class GameMapper {

    private final GameDetailsMapper detailsMapper;
    private final GamePlatformMapper platformMapper;
    private final CompanyMapper companyMapper;

    public GameDto toGameDto(Game game) {
        GameDetailsDto detailsDto = mapGameDetails(game.getDetails());
        List<GamePlatformDto> platformDtos = mapGamePlatforms(game.getPlatforms());
        List<CompanyDto> companyDtos = mapCompanies(game.getCompanies());

        return GameDto.builder()
                .name(game.getName())
                .uri(game.getUri())
                .details(detailsDto)
                .platforms(platformDtos)
                .companies(companyDtos)
                .build();
    }

    private GameDetailsDto mapGameDetails(GameDetails details) {
        GameDetailsDto detailsDto = null;
        if (details != null) {
            detailsDto = detailsMapper.toGameDetailsDto(details);
        }
        return detailsDto;
    }

    private List<GamePlatformDto> mapGamePlatforms(Set<GamePlatform> platforms) {
        List<GamePlatformDto> platformDtos = new ArrayList<>();
        platforms.forEach((platform) -> platformDtos.add(platformMapper.toGamePlatformDto(platform)));
        return platformDtos;
    }

    private List<CompanyDto> mapCompanies(Set<Company> companies) {
        List<CompanyDto> companyDtos = new ArrayList<>();
        companies.forEach((company) -> companyDtos.add(companyMapper.toCompanyDto(company)));
        return companyDtos;
    }

}
