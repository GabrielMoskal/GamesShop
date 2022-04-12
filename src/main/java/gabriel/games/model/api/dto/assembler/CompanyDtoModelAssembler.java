package gabriel.games.model.api.dto.assembler;

import gabriel.games.controller.api.CompanyController;
import gabriel.games.model.api.Company;
import gabriel.games.model.api.dto.CompanyDto;
import gabriel.games.model.api.mapper.CompanyMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CompanyDtoModelAssembler extends RepresentationModelAssemblerSupport<Company, CompanyDto> {

    private CompanyMapper mapper;

    protected CompanyDtoModelAssembler() {
        super(CompanyController.class, CompanyDto.class);
    }

    public CompanyDtoModelAssembler(CompanyMapper mapper) {
        this();
        this.mapper = mapper;
    }

    @Override
    @NonNull
    public CompanyDto instantiateModel(@NonNull Company company) {
        return mapper.toCompanyDto(company);
    }

    @Override
    @NonNull
    public CompanyDto toModel(@NonNull Company company) {
        return createModelWithId(company.getName(), company);
    }
}
