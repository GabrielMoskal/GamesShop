package gabriel.games.model.api.mapper;

import gabriel.games.model.api.*;
import gabriel.games.model.api.dto.*;
import gabriel.games.model.util.Models;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GameMapperTest {

    private Game game;
    private GameMapper gameMapper;
    private GameDetailsMapper gameDetailsMapper;
    private GamePlatformMapper gamePlatformMapper;
    private CompanyMapper companyMapper;
    private GameDto expected;

    @BeforeEach
    public void setUp() {
        this.game = mock(Game.class);
        this.gameDetailsMapper = mock(GameDetailsMapper.class);
        this.gamePlatformMapper = mock(GamePlatformMapper.class);
        this.companyMapper = mock(CompanyMapper.class);
        this.gameMapper = new GameMapper(gameDetailsMapper, gamePlatformMapper, companyMapper);
    }

    @Test
    public void toGameDto_NameGiven_ShouldContainValidName() {
        expected = GameDto.builder().name("name").build();
        when(game.getName()).thenReturn(expected.getName());

        assertMappingCorrect();
    }

    private void assertMappingCorrect() {
        GameDto actual = gameMapper.toGameDto(game);

        assertEquals(expected, actual);
    }

    @Test
    public void toGameDto_DifferentNameGiven_ShouldContainValidName() {
        expected = GameDto.builder().name("different").build();
        when(game.getName()).thenReturn(expected.getName());

        assertMappingCorrect();
    }

    @Test
    public void toGameDto_UriGiven_ShouldContainValidUri() {
        expected = GameDto.builder().uri("uri").build();
        when(game.getUri()).thenReturn(expected.getUri());

        assertMappingCorrect();
    }

    @Test
    public void toGameDto_DifferentUriGiven_ShouldContainValidUri() {
        expected = GameDto.builder().uri("different").build();
        when(game.getUri()).thenReturn(expected.getUri());

        assertMappingCorrect();
    }

    @Test
    public void toGameDto_GameDetailsGiven_ShouldContainValidGameDetails() {
        GameDetailsDto detailsDto = Models.makeGameDetailsDto("test");

        assertGameDetailsDtoValid(detailsDto);
    }

    private void assertGameDetailsDtoValid(GameDetailsDto detailsDto) {
        stubDetails(detailsDto);
        expected = GameDto.builder().details(detailsDto).build();

        assertMappingCorrect();
    }

    private void stubDetails(GameDetailsDto detailsDto) {
        when(gameDetailsMapper.toGameDetailsDto(any())).thenReturn(detailsDto);
        when(game.getDetails()).thenReturn(mock(GameDetails.class));
    }

    @Test
    public void toGameDto_DifferentGameDetailsGiven_ShouldContainValidGameDetails() {
        GameDetailsDto detailsDto = Models.makeGameDetailsDto("different");

        assertGameDetailsDtoValid(detailsDto);
    }

    @Test
    public void toGameDto_GamePlatformsGiven_ShouldContainValidGamePlatforms() {
        List<GamePlatformDto> platformDtos = Models.makeGamePlatformDtoList("test");

        assertGamePlatformsValid(platformDtos);
    }

    private void assertGamePlatformsValid(List<GamePlatformDto> platformDtos) {
        expected = GameDto.builder().platforms(platformDtos).build();
        stubDetails(platformDtos);

        assertMappingCorrect();
    }

    private void stubDetails(List<GamePlatformDto> platformDtos) {
        Set<GamePlatform> platforms = new HashSet<>(Arrays.asList(mock(GamePlatform.class), mock(GamePlatform.class)));
        when(game.getPlatforms()).thenReturn(platforms);
        when(gamePlatformMapper.toGamePlatformDto(any()))
                .thenReturn(platformDtos.get(0), platformDtos.get(1));
    }

    @Test
    public void toGameDto_DifferentGamePlatformsGiven_ShouldContainValidGamePlatforms() {
        List<GamePlatformDto> platformDtos = Models.makeGamePlatformDtoList("different");

        assertGamePlatformsValid(platformDtos);
    }

    @Test
    public void toGameDto_CompaniesGiven_ShouldReturnValidCompanies() {
        List<CompanyDto> companyDtos = Models.makeCompanyDtoList("test");

        assertCompaniesValid(companyDtos);
    }

    private void assertCompaniesValid(List<CompanyDto> companyDtos) {
        expected = GameDto.builder().companies(companyDtos).build();
        stubCompanies(companyDtos);

        assertMappingCorrect();
    }

    private void stubCompanies(List<CompanyDto> companyDtos) {
        Set<Company> companies = new HashSet<>(Arrays.asList(mock(Company.class), mock(Company.class)));
        when(game.getCompanies()).thenReturn(companies);
        when(companyMapper.toCompanyDto(any()))
                .thenReturn(companyDtos.get(0), companyDtos.get(1));
    }

    @Test
    public void toGameDto_DifferentCompaniesGiven_ShouldReturnValidCompanies() {
        List<CompanyDto> companyDtos = Models.makeCompanyDtoList("different");

        assertCompaniesValid(companyDtos);
    }
}
