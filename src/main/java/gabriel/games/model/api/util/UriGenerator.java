package gabriel.games.model.api.util;

public class UriGenerator {

    public static String generate(String phrase) {
        return phrase.toLowerCase().replace(" ", "-");
    }
}
