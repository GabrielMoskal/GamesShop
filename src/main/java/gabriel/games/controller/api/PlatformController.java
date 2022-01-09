package gabriel.games.controller.api;

import gabriel.games.model.api.Platform;
import gabriel.games.model.api.dto.PlatformDto;
import gabriel.games.model.api.dto.assembler.PlatformDtoModelAssembler;
import gabriel.games.model.api.mapper.PlatformMapper;
import gabriel.games.service.PlatformService;
import gabriel.games.service.exception.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/platform", produces = "application/json")
@AllArgsConstructor
public class PlatformController {

    private final PlatformService platformService;
    private final PlatformMapper platformMapper;
    private final PlatformDtoModelAssembler platformAssembler;

    @GetMapping(path = "{uri}")
    public ResponseEntity<PlatformDto> getPlatform(@PathVariable String uri) {
        try {
            Platform platform = platformService.findByUri(uri);
            return makeResponse(platform, HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return notFound();
        }
    }

    private ResponseEntity<PlatformDto> makeResponse(Platform platform, HttpStatus httpStatus) {
        PlatformDto platformDto = platformAssembler.toModel(platform);
        return new ResponseEntity<>(platformDto, httpStatus);
    }

    private ResponseEntity<PlatformDto> notFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<PlatformDto> postPlatform(@Valid @RequestBody PlatformDto platformDto) {
        Platform platform = platformMapper.toPlatform(platformDto);
        platform = platformService.save(platform);
        return makeResponse(platform, HttpStatus.CREATED);
    }

    @PatchMapping(path = "{uri}")
    public ResponseEntity<PlatformDto> patchPlatform(@PathVariable String uri,
                                                     @RequestBody PlatformDto platformDto) {
        Platform platform = platformMapper.toPlatform(platformDto);
        platform = platformService.update(uri, platform);
        return makeResponse(platform, HttpStatus.OK);
    }

    @DeleteMapping(path = "{uri}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletePlatform(@PathVariable String uri) {
        try {
            platformService.deleteByUri(uri);
        } catch (EmptyResultDataAccessException ignored) {}
    }
}
