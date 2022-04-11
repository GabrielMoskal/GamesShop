package gabriel.games.controller.api;

import gabriel.games.model.api.Company;
import gabriel.games.model.api.dto.CompanyDto;
import gabriel.games.model.api.mapper.CompanyMapper;
import gabriel.games.service.CompanyService;
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
@RequestMapping("/api/company")
@AllArgsConstructor
public class CompanyController {

    private CompanyService companyService;
    private CompanyMapper companyMapper;

    @GetMapping("/{name}")
    public ResponseEntity<EntityModel<CompanyDto>> getCompany(@PathVariable String name) {
        try {
            return find(name);
        } catch (ObjectNotFoundException e) {
            return notFound();
        }
    }

    private ResponseEntity<EntityModel<CompanyDto>> find(String name) {
        Company company = companyService.findByName(name);
        CompanyDto companyDto = companyMapper.toCompanyDto(company);
        return makeResponse(companyDto, HttpStatus.OK);
    }

    private ResponseEntity<EntityModel<CompanyDto>> makeResponse(CompanyDto companyDto, HttpStatus status) {
        EntityModel<CompanyDto> entityModel = EntityModel.of(companyDto);
        entityModel.add(makeLink(companyDto.getName()));
        return new ResponseEntity<>(entityModel, status);
    }

    // TODO mam dodany CompanyDtoModelAssembler
    private Link makeLink(String companyName) {
        return linkTo(methodOn(CompanyController.class).getCompany(companyName)).withSelfRel();
    }

    private ResponseEntity<EntityModel<CompanyDto>> notFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<EntityModel<CompanyDto>> postCompany(@RequestBody @Valid CompanyDto companyDto) {
        Company company = companyMapper.toCompany(companyDto);
        company = companyService.save(company);
        CompanyDto result = companyMapper.toCompanyDto(company);
        return makeResponse(result, HttpStatus.CREATED);
    }
}
