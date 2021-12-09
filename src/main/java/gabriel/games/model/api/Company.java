package gabriel.games.model.api;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter(value = AccessLevel.NONE)
    private Long id;

    @NotNull
    @Size(min = 1, max = 128)
    private String name;

    @ManyToMany(mappedBy = "companies")
    @ToString.Exclude
    @Getter(AccessLevel.NONE)
    private Set<Game> games;

    @ManyToMany
    @JoinTable(
            name = "company_type",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    @ToString.Exclude
    private Set<CompanyType> types;

    public List<String> getCompanyTypeNames() {
        if (Objects.isNull(types)) {
            return Collections.emptyList();
        } else {
            return convertToListOfCompanyTypeNames();
        }
    }

    private List<String> convertToListOfCompanyTypeNames() {
        List<String> companyTypeNames = new ArrayList<>();
        types.forEach((type) -> companyTypeNames.add(type.getType()));
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
