package gabriel.games.model.util;

import java.util.Collections;

public class GenericWord {

    public String make(int length) {
        return String.join("", Collections.nCopies(length, "a"));
    }
}
