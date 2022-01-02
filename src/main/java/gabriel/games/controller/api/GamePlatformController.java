package gabriel.games.controller.api;

import gabriel.games.model.api.GamePlatform;
import gabriel.games.model.api.dto.GamePlatformDto;
import gabriel.games.model.api.mapper.GamePlatformMapper;
import gabriel.games.service.GamePlatformService;
import gabriel.games.service.exception.ObjectNotFoundException;
import lombok.AllArgsConstructor;
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

    @GetMapping("/{game-name}/{platform-name}")
    public ResponseEntity<EntityModel<GamePlatformDto>> getGamePlatform(@PathVariable(name = "game-name") String gameName,
                                                       @PathVariable(name = "platform-name") String platformName) {
        try {
            return findGamePlatform(gameName, platformName);
        } catch (ObjectNotFoundException e) {
            return notFound();
        }
    }

    private ResponseEntity<EntityModel<GamePlatformDto>> findGamePlatform(String gameName, String platformName) {
        GamePlatform gamePlatform = gamePlatformService.find(gameName, platformName);
        GamePlatformDto gamePlatformDto = gamePlatformMapper.toGamePlatformDto(gamePlatform);

        return makeResponse(gamePlatformDto, HttpStatus.OK);
    }

    private ResponseEntity<EntityModel<GamePlatformDto>> makeResponse(GamePlatformDto gamePlatformDto, HttpStatus httpStatus) {
        EntityModel<GamePlatformDto> entityModel = EntityModel.of(gamePlatformDto);
        entityModel.add(makeLink(gamePlatformDto.getGameName(), gamePlatformDto.getPlatformName()));

        return new ResponseEntity<>(entityModel, httpStatus);
    }

    private Link makeLink(String gameName, String platformName) {
        return linkTo(methodOn(GamePlatformController.class).getGamePlatform(gameName, platformName)).withSelfRel();
    }

    private ResponseEntity<EntityModel<GamePlatformDto>> notFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<EntityModel<GamePlatformDto>> postGamePlatform(@Valid @RequestBody GamePlatformDto gamePlatformDto) {
        GamePlatform gamePlatform = gamePlatformMapper.toGamePlatform(gamePlatformDto);
        gamePlatform = gamePlatformService.save(gamePlatform);
        GamePlatformDto responseBody = gamePlatformMapper.toGamePlatformDto(gamePlatform);
        return makeResponse(responseBody, HttpStatus.CREATED);
    }
}
