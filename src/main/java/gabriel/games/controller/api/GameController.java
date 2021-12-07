package gabriel.games.controller.api;

import gabriel.games.model.api.Game;
import gabriel.games.model.api.dto.GameDto;
import gabriel.games.model.api.mapper.GameMapper;
import gabriel.games.service.GameService;
import gabriel.games.service.exception.ObjectNotFoundException;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class GameController {

    private final GameService gameService;
    private final GameMapper gameMapper;

    @GetMapping(path = "/{uri}")
    public ResponseEntity<EntityModel<GameDto>> description(@PathVariable String uri) {
        try {
            return findGame(uri);
        } catch (ObjectNotFoundException e) {
            return notFound();
        }
    }

    private ResponseEntity<EntityModel<GameDto>> findGame(String uri) {
        Game game = gameService.findByUri(uri);
        GameDto gameDto = gameMapper.toGameDto(game);

        return makeResponse(gameDto);
    }

    private ResponseEntity<EntityModel<GameDto>> makeResponse(GameDto gameDto) {
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
