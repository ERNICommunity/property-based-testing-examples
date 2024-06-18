package erni.dev.pbtsamples.strings;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConcatenationTest {

    String concat(List<String> strings) {
        return String.join("", strings);
    }

    @Property
    void lengthOfConcatenatedStringIsTheSumOfTheLengthOfTheParts(@ForAll List<String> strings) {
        int sumOfLengths = strings.stream().mapToInt(String::length).sum();

        var result = concat(strings);

        assertEquals(sumOfLengths, result.length());
    }

    String concat2(List<String> strings) {
        return String.join("", strings)
                .replace('我', 'I')
                .replace('你', 'U');
    }

    @Property
    void concatenatedStringContainsEachPart(@ForAll List<String> strings) {
        var result = concat2(strings);

        for (String string : strings) {
            assertTrue(result.contains(string), "Result does not contain " + string);
        }
    }
}
