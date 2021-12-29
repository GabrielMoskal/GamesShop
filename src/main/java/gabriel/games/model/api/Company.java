package gabriel.games.model.api;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 128)
    @Getter
    private String name;

    @ManyToMany(mappedBy = "companies")
    @ToString.Exclude
    @Getter
    @Setter
    private Set<Game> games;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "company_type",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    @ToString.Exclude
    @Getter
    private Set<CompanyType> companyTypes;

    public Company(String name) {
        this.name = name;
        this.companyTypes = new HashSet<>();
    }

    public void addCompanyType(CompanyType companyType) {
        companyTypes.add(companyType);
    }

    public List<String> getTypeNames() {
        if (Objects.isNull(companyTypes)) {
            return Collections.emptyList();
        } else {
            return convertToListOfCompanyTypeNames();
        }
    }

    private List<String> convertToListOfCompanyTypeNames() {
        List<String> companyTypeNames = new ArrayList<>();
        companyTypes.forEach((type) -> companyTypeNames.add(type.getType()));
        return companyTypeNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }

        Company company = (Company) o;

        return Objects.equals(name, company.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
