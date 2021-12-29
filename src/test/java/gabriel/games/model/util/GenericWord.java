package gabriel.games.model.util;

import java.util.Collections;

public class GenericWord {

    public static String make(int length) {
        return String.join("", Collections.nCopies(length, "a"));
    }
}
