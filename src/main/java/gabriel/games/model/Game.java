package gabriel.games.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 128)
    private String name;

    @NotNull
    @Size(min = 1, max = 128)
    @Pattern(regexp = "([a-z_0-9]*)")
    private String uri;

    public Game(final Long id, final String name, final String uri) {
        this.id = id;
        this.name = name;
        this.uri = uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Game game = (Game) o;
        return id != null && Objects.equals(id, game.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
