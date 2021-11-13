package gabriel.games.controller.api;

import gabriel.games.model.dto.GameDto;
import gabriel.games.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/game", produces = "application/json")
@CrossOrigin(origins = "*")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(path = "/{uri}")
    public ResponseEntity<EntityModel<GameDto>> description(@PathVariable String uri) throws Exception {
        GameDto gameDto = gameService.findByUri(uri);
        EntityModel<GameDto> entityModel = EntityModel.of(gameDto);
        entityModel.add(makeLink(gameDto));

        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    private Link makeLink(GameDto gameDto) throws Exception {
        return linkTo(methodOn(GameController.class).description(gameDto.getUri())).withSelfRel();
    }
}
