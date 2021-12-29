package gabriel.games.controller.api;

import gabriel.games.model.api.Platform;
import gabriel.games.model.api.dto.PlatformDto;
import gabriel.games.model.api.mapper.PlatformMapper;
import gabriel.games.service.PlatformService;
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
@RequestMapping(path = "/api/platform", produces = "application/json")
@AllArgsConstructor
public class PlatformController {

    private final PlatformService platformService;
    private final PlatformMapper platformMapper;

    @GetMapping(path = "{uri}")
    public ResponseEntity<EntityModel<PlatformDto>> getPlatform(@PathVariable String uri) {
        try {
            return findPlatform(uri);
        } catch (ObjectNotFoundException e) {
            return notFound();
        }
    }

    private ResponseEntity<EntityModel<PlatformDto>> findPlatform(String uri) {
        Platform platform = platformService.findByUri(uri);
        PlatformDto platformDto = platformMapper.toPlatformDto(platform);
        return makeResponse(platformDto, HttpStatus.OK);
    }

    private ResponseEntity<EntityModel<PlatformDto>> makeResponse(PlatformDto platformDto, HttpStatus ok) {
        EntityModel<PlatformDto> entityModel = EntityModel.of(platformDto);
        entityModel.add(makeLink(platformDto));
        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    private Link makeLink(PlatformDto platformDto) {
        return linkTo(methodOn(PlatformController.class).getPlatform(platformDto.getUri())).withSelfRel();
    }

    private ResponseEntity<EntityModel<PlatformDto>> notFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
