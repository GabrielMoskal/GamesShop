package gabriel.games.model.api;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @Getter
    @Setter
    private Long id;

    @NotNull
    @Size(min = 1, max = 128)
    @ToString.Include
    @Getter
    @Setter
    private String name;

    @NotNull
    @Size(min = 1, max = 128)
    @Pattern(regexp = "([a-z-_0-9]*)")
    @ToString.Include
    @Getter
    @Setter
    private String uri;

    @OneToOne(mappedBy = "game", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @Getter
    @Setter
    private GameDetails details;

    @OneToMany(mappedBy = "game")
    @Getter
    @Setter
    private Set<GamePlatform> platforms;

    @ManyToMany
    @JoinTable(
            name = "game_company",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    @Getter
    @Setter
    private Set<Company> companies;

    public Game(String name, String uri) {
        this.name = name;
        this.uri = uri;
        this.platforms = new HashSet<>();
        this.companies = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;

        Game game = (Game) o;

        if (!Objects.equals(name, game.name)) return false;
        return Objects.equals(uri, game.uri);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        return result;
    }
}
