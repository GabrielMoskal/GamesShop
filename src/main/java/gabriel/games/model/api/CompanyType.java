package gabriel.games.model.api;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "company_general_type")
public class CompanyType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    private String type;

    @ManyToMany(mappedBy = "types")
    @ToString.Exclude
    private Set<Company> companies;

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
