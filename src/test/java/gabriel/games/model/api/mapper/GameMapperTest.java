package gabriel.games.model.api.mapper;

import gabriel.games.model.api.*;
import gabriel.games.model.api.dto.*;
import gabriel.games.model.api.embedded.Rating;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GameMapperTest {

    private Game game;
    private GameDto gameDto;
    private GameDetailsMapper gameDetailsMapper;
    private GamePlatformMapper gamePlatformMapper;
    private CompanyMapper companyMapper;
    private GameMapper gameMapper;

    @BeforeEach
    public void setUp() {
        this.game = mock(Game.class);
        this.gameDto = mock(GameDto.class);
        this.gameDetailsMapper = mock(GameDetailsMapper.class);
        this.gamePlatformMapper = mock(GamePlatformMapper.class);
        this.companyMapper = mock(CompanyMapper.class);
        this.gameMapper = new GameMapper(gameDetailsMapper, gamePlatformMapper, companyMapper);
    }

    @Test
    public void toGameDto_NameGiven_ShouldContainValidName() {
        GameDto expected = GameDto.builder().name("name").build();
        assertToGameDtoMappingCorrect(expected);
    }

    private void assertToGameDtoMappingCorrect(GameDto expected) {
        when(game.getName()).thenReturn(expected.getName());
        when(game.getUri()).thenReturn(expected.getUri());
        GameDto actual = gameMapper.toGameDto(game);
        assertEquals(expected, actual);
    }

    @Test
    public void toGameDto_DifferentNameGiven_ShouldContainValidName() {
        GameDto expected = GameDto.builder().name("different").build();
        assertToGameDtoMappingCorrect(expected);
    }

    @Test
    public void toGameDto_UriGiven_ShouldContainValidUri() {
        GameDto expected = GameDto.builder().uri("uri").build();
        assertToGameDtoMappingCorrect(expected);
    }

    @Test
    public void toGameDto_DifferentUriGiven_ShouldContainValidUri() {
        GameDto expected = GameDto.builder().uri("different").build();
        assertToGameDtoMappingCorrect(expected);
    }

    @Test
    public void toGameDto_GameDetailsGiven_ShouldContainValidGameDetails() {
        GameDetailsDto detailsDto = stubGameDetailsDto();
        GameDto expected = GameDto.builder().details(detailsDto).build();
        assertToGameDtoMappingCorrect(expected);
    }

    private GameDetailsDto stubGameDetailsDto() {
        GameDetailsDto gameDetailsDto = mock(GameDetailsDto.class);
        when(gameDetailsMapper.toGameDetailsDto(any())).thenReturn(gameDetailsDto);
        when(game.getDetails()).thenReturn(mock(GameDetails.class));
        return gameDetailsDto;
    }

    @Test
    public void toGameDto_GamePlatformsGiven_ShouldContainValidGamePlatforms() {
        List<GamePlatformDto> platformDtos = stubGamePlatformDtos();
        GameDto expected = GameDto.builder().platforms(platformDtos).build();
        assertToGameDtoMappingCorrect(expected);
    }

    private List<GamePlatformDto> stubGamePlatformDtos() {
        List<GamePlatformDto> platformDtos = Collections.singletonList(mock(GamePlatformDto.class));
        Set<GamePlatform> platforms = new HashSet<>(Collections.singletonList(mock(GamePlatform.class)));
        when(game.getPlatforms()).thenReturn(platforms);
        when(gamePlatformMapper.toGamePlatformDto(any())).thenReturn(platformDtos.get(0));
        return platformDtos;
    }

    @Test
    public void toGameDto_CompaniesGiven_ShouldReturnValidCompanies() {
        List<CompanyDto> companyDtos = stubCompanyDtos();
        GameDto expected = GameDto.builder().companies(companyDtos).build();
        assertToGameDtoMappingCorrect(expected);
    }

    private List<CompanyDto> stubCompanyDtos() {
        List<CompanyDto> companyDtos = Collections.singletonList(mock(CompanyDto.class));
        Set<Company> companies = new HashSet<>(Collections.singletonList(mock(Company.class)));
        when(game.getCompanies()).thenReturn(companies);
        when(companyMapper.toCompanyDto(any())).thenReturn(companyDtos.get(0));
        return companyDtos;
    }

    @Test
    public void toGame_NameGiven_ShouldContainValidName() {
        Game expected = new Game("name");
        assertToGameMappingCorrect(expected);
    }

    private void assertToGameMappingCorrect(Game expected) {
        when(gameDto.getName()).thenReturn(expected.getName());
        when(gameDetailsMapper.toGameDetails(any())).thenReturn(mock(GameDetails.class));
        Game actual = gameMapper.toGame(gameDto);
        assertEquals(expected, actual);
    }

    @Test
    public void toGame_DifferentNameGiven_ShouldContainValidName() {
        Game expected = new Game("different name");
        assertToGameMappingCorrect(expected);
    }

    @Test
    public void toGame_GameDetailsGiven_ShouldContainValidDetails() {
        stubGetName();
        GameDetails expectedGameDetails = stubGameDetails();
        Game actual = gameMapper.toGame(gameDto);
        GameDetails actualGameDetails = actual.getDetails();
        assertEquals(expectedGameDetails, actualGameDetails);
        assertEquals(actual, actualGameDetails.getGame());
    }

    private GameDetails stubGameDetails() {
        GameDetails gameDetails = GameDetails.builder()
                .description("description")
                .webpage("webpage")
                .ratingPlayers(new Rating("1.0"))
                .ratingReviewer(new Rating("2.0"))
                .build();
        when(gameDetailsMapper.toGameDetails(any())).thenReturn(gameDetails);
        return gameDetails;
    }

    private void stubGetName() {
        when(gameDto.getName()).thenReturn("name");
    }

    @Test
    public void toGame_GamePlatformDtosGiven_ShouldContainValidGamePlatforms() {
        Set<GamePlatform> gamePlatforms = stubGamePlatforms();
        stubGetName();
        when(gameDetailsMapper.toGameDetails(any())).thenReturn(mock(GameDetails.class));
        Game actual = gameMapper.toGame(gameDto);
        assertEquals(gamePlatforms, actual.getPlatforms());
    }

    private Set<GamePlatform> stubGamePlatforms() {
        Set<GamePlatform> gamePlatforms = new HashSet<>(Collections.singletonList(mock(GamePlatform.class)));
        when(gamePlatformMapper.toGamePlatform(any())).thenReturn(gamePlatforms.iterator().next());
        when(gameDto.getPlatforms()).thenReturn(Collections.singletonList(mock(GamePlatformDto.class)));
        return gamePlatforms;
    }

    @Test
    public void toGame_CompaniesDtoGiven_ShouldContainValidCompanies() {
        Set<Company> companies = stubCompanies();
        stubGetName();
        stubGameDetails();
        Game actual = gameMapper.toGame(gameDto);
        assertEquals(companies, actual.getCompanies());
        Company actualCompany = actual.getCompanies().iterator().next();
        assertEquals(actual, actualCompany.getGames().iterator().next());
    }

    private Set<Company> stubCompanies() {
        Set<Company> companies = new HashSet<>();
        Company company = new Company("name");
        company.addCompanyType(mock(CompanyType.class));
        companies.add(company);
        when(companyMapper.toCompany(any())).thenReturn(companies.iterator().next());
        when(gameDto.getCompanies()).thenReturn(Collections.singletonList(mock(CompanyDto.class)));
        return companies;
    }

}
