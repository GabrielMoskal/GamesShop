package gabriel.games.model.api;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "company_general_type")
public class CompanyType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Getter
    private String type;

    @ManyToMany(mappedBy = "companyTypes")
    @ToString.Exclude
    @Getter
    @Setter
    private Set<Company> companies;

    public CompanyType(String type) {
        this.type = type;
        companies = new HashSet<>();
    }

    public void addCompany(Company company) {
        companies.add(company);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyType)) {
            return false;
        }

        CompanyType that = (CompanyType) o;

        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }
}
