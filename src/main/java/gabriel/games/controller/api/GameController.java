package gabriel.games.controller.api;

import gabriel.games.model.dto.GameDto;
import gabriel.games.service.GameService;
import gabriel.games.service.exception.ObjectNotFoundException;
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
    public ResponseEntity<EntityModel<GameDto>> description(@PathVariable String uri) {
        try {
            return findGame(uri);
        } catch (ObjectNotFoundException e) {
            return notFound();
        }
    }

    private ResponseEntity<EntityModel<GameDto>> findGame(String uri) {
        GameDto gameDto = gameService.findByUri(uri);
        EntityModel<GameDto> entityModel = EntityModel.of(gameDto);
        entityModel.add(makeLink(gameDto));
        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    private Link makeLink(GameDto gameDto) {
        return linkTo(methodOn(GameController.class).description(gameDto.getUri())).withSelfRel();
    }

    private ResponseEntity<EntityModel<GameDto>> notFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
