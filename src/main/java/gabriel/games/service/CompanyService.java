package gabriel.games.service;

import gabriel.games.model.api.Company;
import gabriel.games.repository.CompanyRepository;
import gabriel.games.service.exception.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CompanyService {

    private static final String NOT_FOUND = "Company with given name not found.";

    private CompanyRepository repository;

    public Company findByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new ObjectNotFoundException(NOT_FOUND));
    }

    public Company save(Company company) {
        return repository.save(company);
    }
}
