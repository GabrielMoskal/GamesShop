package gabriel.games.controller.cms;

import gabriel.games.model.dto.GameDescriptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(path = "/cms/gamedescription", produces = "application/json")
@CrossOrigin(origins = "*")
public class GameDescriptionController {

    @GetMapping
    public ResponseEntity<GameDescriptionDto> description() {
        GameDescriptionDto gameDescriptionDto = GameDescriptionDto.builder()
                .description("Test short description")
                .webpage("https://test-link.com")
                .playerRating("7.0")
                .reviewerRating("6.5")
                .platforms(Arrays.asList("PC", "XBOX 360"))
                .releaseDate("2000-01-01")
                .producer("Test Producer")
                .publisher("Test Publisher")
                .build();

        return new ResponseEntity<>(gameDescriptionDto, HttpStatus.OK);
    }
}
