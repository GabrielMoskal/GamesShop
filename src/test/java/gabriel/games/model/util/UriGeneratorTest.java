package gabriel.games.model.util;

import gabriel.games.model.api.util.UriGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UriGeneratorTest {

    @Test
    public void generate_StringGiven_ReturnsTheSameString() {
        String expected = "word";
        String actual = UriGenerator.generate("word");
        assertEquals(expected, actual);
    }

    @Test
    public void generate_BigLettersGiven_ReturnsLowerLetters() {
        String expected = "word";
        String actual = UriGenerator.generate("WoRd");
        assertEquals(expected, actual);
    }

    @Test
    public void generate_SpacesGiven_ReplacesWithHyphens() {
        String expected = "multiple-words-here";
        String actual = UriGenerator.generate("multiple words here");
        assertEquals(expected, actual);
    }
}
