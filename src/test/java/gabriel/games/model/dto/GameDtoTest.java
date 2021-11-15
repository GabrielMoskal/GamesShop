package gabriel.games.model.dto;

import gabriel.games.model.dto.util.DtoValidator;
import gabriel.games.util.GameUtil;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;

public class GameDtoTest {

    private GameDto gameDto;
    private DtoValidator<GameDto> validator;

    @Before
    public void setUp() {
        this.gameDto = GameUtil.makeValidGameDto();
        validator = new DtoValidator<>(gameDto);
    }

    @Test
    public void validGameDtoHasNoErrors() {
        validator.assertErrors(0);
    }

    @Test
    public void uriShouldNotBeNull() {
        setInvalidValue("uri", null);
        validator.assertErrors(1);
    }

    private void setInvalidValue(String fieldName, Object value) {
        try {
            setValue(fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setValue(String fieldName, Object s) throws Exception {
        Field uri = gameDto.getClass().getDeclaredField(fieldName);
        uri.setAccessible(true);
        uri.set(gameDto, s);
    }

    @Test
    public void uriShouldBeAtLeast1CharacterLong() {
        setInvalidValue("uri", "");
        validator.assertErrors(1);
    }

    @Test
    public void uriShouldBeMax128CharacterLong() {
        setInvalidValue("uri", validator.makeGenericWord(129));
        validator.assertErrors(1);
    }

    @Test
    public void nameShouldNotBeNull() {
        setInvalidValue("name", null);
        validator.assertErrors(1);
    }

    @Test
    public void nameShouldBeAtLeast1CharacterLong() {
        setInvalidValue("name", "");
        validator.assertErrors(1);
    }

    @Test
    public void nameShouldBeMax128CharacterLong() {
        setInvalidValue("name", validator.makeGenericWord(129));
        validator.assertErrors(1);
    }

    @Test
    public void descriptionShouldNotBeNull() {
        setInvalidValue("description", null);
        validator.assertErrors(1);
    }

    @Test
    public void descriptionShouldBeMax1024CharacterLong() {
        setInvalidValue("description", validator.makeGenericWord(1025));
        validator.assertErrors(1);
    }

    @Test
    public void webpageShouldNotBeNull() {
        setInvalidValue("webpage", null);
        validator.assertErrors(1);
    }

    @Test
    public void webpageShouldBeMax256CharacterLong() {
        setInvalidValue("webpage", validator.makeGenericWord(257));
        validator.assertErrors(1);
    }

    @Test
    public void playerRatingShouldNotBeNull() {
        setInvalidValue("playerRating", null);
        validator.assertErrors(1);
    }

    @Test
    public void playerRatingShouldNotBeNegative() {
        setInvalidValue("playerRating", -0.5);
        validator.assertErrors(1);
    }

    @Test
    public void playerRatingShouldBeMax10() {
        setInvalidValue("playerRating", 10.1);
        validator.assertErrors(1);
    }

    @Test
    public void reviewerRatingShouldNotBeNull() {
        setInvalidValue("reviewerRating", null);
        validator.assertErrors(1);
    }

    @Test
    public void reviewerRatingShouldNotBeNegative() {
        setInvalidValue("reviewerRating", -0.1);
        validator.assertErrors(1);
    }

    @Test
    public void reviewerRatingShouldBeMax10() {
        setInvalidValue("reviewerRating", 11.0);
        validator.assertErrors(1);
    }

    @Test
    public void platformsShouldNotBeNull() {
        setInvalidValue("platforms", null);
        validator.assertErrors(1);
    }

    @Test
    public void platformsContentShouldNotBeNull() {
        List<String> platforms = new ArrayList<>(Arrays.asList(null, null));
        setInvalidValue("platforms", platforms);
        validator.assertErrors(2);
    }

    @Test
    public void platformsContentShouldContainAtLeast1String() {
        setInvalidValue("platforms", Collections.emptyList());
        validator.assertErrors(1);
    }

    @Test
    public void platformsContentShouldBeAtLeast1CharacterLong() {
        setInvalidValue("platforms", Arrays.asList("", "valid", ""));
        validator.assertErrors(2);
    }

    @Test
    public void platformsContentShouldBeMax50CharacterLong() {
        setInvalidValue("platforms", Collections.singletonList(validator.makeGenericWord(51)));
        validator.assertErrors(1);
    }

    @Test
    public void releaseDateShouldNotBeNull() {
        setInvalidValue("releaseDate", null);
        validator.assertErrors(1);
    }

    @Test
    public void producerShouldNotBeNull() {
        setInvalidValue("producer", null);
        validator.assertErrors(1);
    }

    @Test
    public void producerShouldBeMax128CharacterLong() {
        setInvalidValue("producer", validator.makeGenericWord(129));
        validator.assertErrors(1);
    }

    @Test
    public void publisherShouldNotBeNull() {
        setInvalidValue("publisher", null);
        validator.assertErrors(1);
    }

    @Test
    public void publisherShouldBeMax128CharacterLong() {
        setInvalidValue("publisher", validator.makeGenericWord(129));
        validator.assertErrors(1);
    }
}
