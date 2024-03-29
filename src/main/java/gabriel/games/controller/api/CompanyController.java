package gabriel.games.controller.api;

import gabriel.games.model.api.Company;
import gabriel.games.model.api.dto.CompanyDto;
import gabriel.games.model.api.dto.assembler.CompanyDtoModelAssembler;
import gabriel.games.model.api.mapper.CompanyMapper;
import gabriel.games.service.CompanyService;
import gabriel.games.service.exception.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/company")
@AllArgsConstructor
public class CompanyController {

    private CompanyService companyService;
    private CompanyMapper companyMapper;
    private CompanyDtoModelAssembler assembler;

    @GetMapping("/{name}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable String name) {
        try {
            return find(name);
        } catch (ObjectNotFoundException e) {
            return notFound();
        }
    }

    private ResponseEntity<CompanyDto> find(String name) {
        Company company = companyService.findByName(name);

        return makeResponse(company, HttpStatus.OK);
    }

    private ResponseEntity<CompanyDto> makeResponse(Company company, HttpStatus status) {
        CompanyDto companyDto = assembler.toModel(company);
        return new ResponseEntity<>(companyDto, status);
    }

    private ResponseEntity<CompanyDto> notFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<CompanyDto> postCompany(@RequestBody @Valid CompanyDto companyDto) {
        Company company = companyMapper.toCompany(companyDto);
        company = companyService.save(company);
        return makeResponse(company, HttpStatus.CREATED);
    }
}
