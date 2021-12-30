package gabriel.games.model.api.mapper;

import gabriel.games.model.api.*;
import gabriel.games.model.api.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@AllArgsConstructor
public class GameMapper {

    private final GameDetailsMapper detailsMapper;
    private final GamePlatformMapper platformMapper;
    private final CompanyMapper companyMapper;

    public GameDto toGameDto(Game game) {
        GameDetailsDto detailsDto = toGameDetailsDto(game.getDetails());
        List<GamePlatformDto> platformDtos = toGamePlatformDtos(game.getPlatforms());
        List<CompanyDto> companyDtos = toCompanyDtos(game.getCompanies());

        return GameDto.builder()
                .name(game.getName())
                .uri(game.getUri())
                .details(detailsDto)
                .platforms(platformDtos)
                .companies(companyDtos)
                .build();
    }

    private GameDetailsDto toGameDetailsDto(GameDetails details) {
        GameDetailsDto detailsDto = null;
        if (details != null) {
            detailsDto = detailsMapper.toGameDetailsDto(details);
        }
        return detailsDto;
    }

    private List<GamePlatformDto> toGamePlatformDtos(Set<GamePlatform> platforms) {
        List<GamePlatformDto> platformDtos = new ArrayList<>();
        platforms.forEach((platform) -> platformDtos.add(platformMapper.toGamePlatformDto(platform)));
        return platformDtos;
    }

    private List<CompanyDto> toCompanyDtos(Set<Company> companies) {
        List<CompanyDto> companyDtos = new ArrayList<>();
        companies.forEach((company) -> companyDtos.add(companyMapper.toCompanyDto(company)));
        return companyDtos;
    }

    public Game toGame(GameDto gameDto) {
        Game game = new Game(gameDto.getName(), null);
        addGameDetails(game, gameDto);
        addGamePlatforms(game, gameDto);
        addCompanies(game, gameDto);
        return game;
    }

    private void addGameDetails(Game game, GameDto gameDto) {
        GameDetails gameDetails = detailsMapper.toGameDetails(gameDto.getDetails());
        game.setDetails(gameDetails);
        gameDetails.setGame(game);
    }

    private void addGamePlatforms(Game game, GameDto gameDto) {
        Set<GamePlatform> platforms = toGamePlatforms(gameDto.getPlatforms());
        game.setPlatforms(platforms);
    }

    private Set<GamePlatform> toGamePlatforms(List<GamePlatformDto> platformDtos) {
        Set<GamePlatform> platforms = new HashSet<>();
        platformDtos.forEach((platformDto) -> platforms.add(platformMapper.toGamePlatform(platformDto)));
        return platforms;
    }

    private void addCompanies(Game game, GameDto gameDto) {
        Set<Company> companies = toCompanies(gameDto.getCompanies(), game);
        game.setCompanies(companies);
    }

    private Set<Company> toCompanies(List<CompanyDto> companyDtos, Game game) {
        Set<Company> companies = new HashSet<>();
        companyDtos.forEach((companyDto) -> {
            Set<Game> games = new HashSet<>();
            games.add(game);
            Company company = companyMapper.toCompany(companyDto);
            company.setGames(games);
            companies.add(company);

        });
        return companies;
    }

}
