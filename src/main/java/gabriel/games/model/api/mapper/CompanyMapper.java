package gabriel.games.model.api.mapper;

import gabriel.games.model.api.Company;
import gabriel.games.model.api.dto.CompanyDto;

import java.util.List;

public class CompanyMapper {
    public CompanyDto toCompanyDto(final Company company) {
        List<String> types = company.getCompanyTypeNames();
        return new CompanyDto(company.getName(), types);
    }
}
