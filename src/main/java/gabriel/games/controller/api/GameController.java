package gabriel.games.controller.api;

import gabriel.games.model.api.Game;
import gabriel.games.model.api.dto.GameDto;
import gabriel.games.model.api.mapper.GameMapper;
import gabriel.games.service.GameService;
import gabriel.games.service.exception.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
        return makeResponse(gameDto, HttpStatus.OK);
    }

    private ResponseEntity<EntityModel<GameDto>> makeResponse(GameDto gameDto, HttpStatus status) {
        EntityModel<GameDto> entityModel = EntityModel.of(gameDto);
        entityModel.add(makeLink(gameDto));
        return new ResponseEntity<>(entityModel, status);
    }

    private Link makeLink(GameDto gameDto) {
        return linkTo(methodOn(GameController.class).description(gameDto.getUri())).withSelfRel();
    }

    private ResponseEntity<EntityModel<GameDto>> notFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<EntityModel<GameDto>> postGame(@Valid @RequestBody GameDto gameDto) {
        Game game = gameMapper.toGame(gameDto);
        gameService.save(game);
        GameDto responseBody = gameMapper.toGameDto(game);
        return makeResponse(responseBody, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{uri}")
    public ResponseEntity<EntityModel<GameDto>> patchGame(@PathVariable String uri) {
        Game game = gameService.findByUri(uri);
        GameDto responseBody = gameMapper.toGameDto(game);
        return makeResponse(responseBody, HttpStatus.OK);
    }
}
