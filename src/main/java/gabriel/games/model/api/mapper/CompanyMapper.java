package gabriel.games.model.api.mapper;

import gabriel.games.model.api.*;
import gabriel.games.model.api.dto.CompanyDto;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CompanyMapper {

    public CompanyDto toCompanyDto(Company company) {
        List<String> types = company.getTypeNames();
        return new CompanyDto(company.getName(), types);
    }

    public Company toCompany(CompanyDto companyDto) {
        Company company = new Company(companyDto.getName());
        List<String> companyTypes = companyDto.getTypes();

        companyTypes.forEach(
                (type) -> {
                    CompanyType companyType = new CompanyType(type);
                    companyType.addCompany(company);
                    company.addCompanyType(companyType);
                }
        );
        return company;
    }
}
