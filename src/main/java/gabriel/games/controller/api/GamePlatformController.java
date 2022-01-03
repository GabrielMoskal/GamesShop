package gabriel.games.controller.api;

import gabriel.games.model.api.GamePlatform;
import gabriel.games.model.api.dto.GamePlatformDto;
import gabriel.games.model.api.mapper.GamePlatformMapper;
import gabriel.games.service.GamePlatformService;
import gabriel.games.service.exception.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/game-platform", produces = "application/json")
@CrossOrigin("*")
@AllArgsConstructor
public class GamePlatformController {

    private GamePlatformService gamePlatformService;
    private GamePlatformMapper gamePlatformMapper;

    @GetMapping("/{game-uri}/{platform-uri}")
    public ResponseEntity<EntityModel<GamePlatformDto>> getGamePlatform(@PathVariable(name = "game-uri") String gameUri,
                                                       @PathVariable(name = "platform-uri") String platformUri) {
        try {
            return findGamePlatform(gameUri, platformUri);
        } catch (ObjectNotFoundException e) {
            return notFound();
        }
    }

    private ResponseEntity<EntityModel<GamePlatformDto>> findGamePlatform(String gameUri, String platformUri) {
        GamePlatform gamePlatform = gamePlatformService.find(gameUri, platformUri);

        return makeResponse(gamePlatform, HttpStatus.OK);
    }

    private ResponseEntity<EntityModel<GamePlatformDto>> makeResponse(GamePlatform gamePlatform, HttpStatus httpStatus) {
        GamePlatformDto gamePlatformDto = gamePlatformMapper.toGamePlatformDto(gamePlatform);
        EntityModel<GamePlatformDto> entityModel = EntityModel.of(gamePlatformDto);
        entityModel.add(makeLink(gamePlatform.getGameUri(), gamePlatform.getPlatformUri()));

        return new ResponseEntity<>(entityModel, httpStatus);
    }

    private Link makeLink(String gameUri, String platformUri) {
        return linkTo(methodOn(GamePlatformController.class).getGamePlatform(gameUri, platformUri)).withSelfRel();
    }

    private ResponseEntity<EntityModel<GamePlatformDto>> notFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<EntityModel<GamePlatformDto>> postGamePlatform(@Valid @RequestBody GamePlatformDto gamePlatformDto) {
        GamePlatform gamePlatform = gamePlatformMapper.toGamePlatform(gamePlatformDto);
        gamePlatform = gamePlatformService.save(gamePlatform);

        return makeResponse(gamePlatform, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{game-uri}/{platform-uri}")
    public ResponseEntity<EntityModel<GamePlatformDto>> patchGamePlatform(@PathVariable(name = "game-uri") String gameUri,
                                                                          @PathVariable(name = "platform-uri") String platformUri,
                                                                          @RequestBody GamePlatformDto patch) {
        GamePlatform gamePlatform = gamePlatformMapper.toGamePlatform(patch);
        gamePlatform = gamePlatformService.update(gameUri, platformUri, gamePlatform);

        return makeResponse(gamePlatform, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{game-uri}/{platform-uri}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGamePlatform(@PathVariable(name = "game-uri") String gameUri,
                                   @PathVariable(name = "platform-uri") String platformUri) {
        try {
            gamePlatformService.delete(gameUri, platformUri);
        } catch (EmptyResultDataAccessException ignore) {
        }
    }
}
