package gabriel.games.controller.api;

import gabriel.games.model.dto.GameDto;
import gabriel.games.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/cms/game", produces = "application/json")
@CrossOrigin(origins = "*")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<GameDto> description(@PathVariable String name) {
        GameDto gameDto = gameService.findByName(name);

        return new ResponseEntity<>(gameDto, HttpStatus.OK);
    }
}
