package gabriel.games.controller.api;

import gabriel.games.model.api.Company;
import gabriel.games.model.api.dto.CompanyDto;
import gabriel.games.model.api.mapper.CompanyMapper;
import gabriel.games.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Company company = companyService.findByName(name);
        CompanyDto companyDto = companyMapper.toCompanyDto(company);
        EntityModel<CompanyDto> responseBody = EntityModel.of(companyDto);
        responseBody.add(makeLink(name));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    private Link makeLink(String companyName) {
        return linkTo(methodOn(CompanyController.class).getCompany(companyName)).withSelfRel();
    }
}
