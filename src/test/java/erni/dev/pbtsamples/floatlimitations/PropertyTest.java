package erni.dev.pbtsamples.floatlimitations;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertyTest {

    @Test
    void floatAdditionIsAssociative_example() {
        float a = 1;
        float b = 2;
        float c = 3;

        float left = (a + b) + c;
        float right = a + (b + c);

        assertEquals(left, right);
    }

    @ParameterizedTest
    @MethodSource("params")
    void floatAdditionIsAssociative_params(float a, float b, float c) {
        float left = (a + b) + c;
        float right = a + (b + c);

        assertEquals(left, right);
    }

    static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of(1, 2, 3),
                Arguments.of(Float.NaN, 1, 2)
        );
    }

    @Property
    void floatAdditionIsAssociative(@ForAll float a, @ForAll float b, @ForAll float c) {
        float left = (a + b) + c;
        float right = a + (b + c);

        assertEquals(left, right);
    }
}