package gabriel.games.model.api.dto.assembler;

import gabriel.games.controller.api.PlatformController;
import gabriel.games.model.api.Platform;
import gabriel.games.model.api.dto.PlatformDto;
import gabriel.games.model.api.mapper.PlatformMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class PlatformDtoModelAssembler extends RepresentationModelAssemblerSupport<Platform, PlatformDto> {

    private PlatformMapper platformMapper;

    protected PlatformDtoModelAssembler() {
        super(PlatformController.class, PlatformDto.class);
    }

    @Autowired
    public PlatformDtoModelAssembler(PlatformMapper platformMapper) {
        this();
        this.platformMapper = platformMapper;
    }

    @Override
    @NonNull
    protected PlatformDto instantiateModel(@NonNull Platform platform) {
        return platformMapper.toPlatformDto(platform);
    }

    @Override
    @NonNull
    public PlatformDto toModel(@NonNull Platform platform) {
        return createModelWithId(platform.getUri(), platform);
    }
}
