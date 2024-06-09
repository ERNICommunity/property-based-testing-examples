package erni.dev.pbtsamples.floatlimitations;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FloatAdderTest {

    public static float add(float a, float b) {
        return a + b;
    }

    @Test
    void add() {
        float a = 1;
        float b = 2;

        float result = add(a, b);

        assertEquals(3, result);
    }

    @ParameterizedTest
    @MethodSource("params")
    void add(float a, float b, float expected) {
        float result = add(a, b);

        assertEquals(expected, result);
    }

    static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of(1, 2, 3),
                Arguments.of(Float.NaN, 1, Float.NaN)
        );
    }

    @Property
    void invariantHolds(@ForAll float a) {
        float result = add(a, 0);

        assertEquals(a, result);
    }

    @Property
    void negationSymmetryHolds(@ForAll float a, @ForAll float b) {
        // metamorphic relation
        float result1 = add(a, b);
        float result2 = -add(-a, -b);

        assertEquals(result1, result2);
    }

    @Property
    void associativityHolds(@ForAll float a, @ForAll float b, @ForAll float c) {
        // associativity as a metamorphic relation
        float result1 = add(a, add(b, c));
        float result2 = add(add(a, b), c);

        assertEquals(result1, result2);
    }
}
