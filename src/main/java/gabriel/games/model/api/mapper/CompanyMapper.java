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
        Set<CompanyType> companyTypes = new HashSet<>();
        companyDto.getTypes().forEach((type) -> companyTypes.add(new CompanyType(type)));

        return new Company(companyDto.getName(), companyTypes);
    }
}
