package gabriel.games.model.api;

import gabriel.games.model.api.util.UriGenerator;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Getter
    @Setter
    private String name;

    @NotNull
    @Size(min = 1, max = 50)
    @Pattern(regexp = "([a-z-_0-9]*)")
    @Getter
    @Setter
    private String uri;

    @NotNull
    @OneToMany(mappedBy = "platform")
    @ToString.Exclude
    @Getter
    private Set<GamePlatform> games;

    public Platform(String name) {
        this.name = name;
        this.uri = UriGenerator.generate(name);
        this.games = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Platform)) return false;

        Platform platform = (Platform) o;

        if (!Objects.equals(name, platform.name)) return false;
        return Objects.equals(uri, platform.uri);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        return result;
    }
}
