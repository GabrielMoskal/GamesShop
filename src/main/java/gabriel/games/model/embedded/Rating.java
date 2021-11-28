package gabriel.games.model.embedded;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Rating {

    @DecimalMin("0.0")
    @DecimalMax("10.0")
    @Digits(integer = 2, fraction = 2)
    private BigDecimal rating;

    public Rating(String rating) {
        this.rating = new BigDecimal(rating);
    }
}
