package erni.dev.pbtsamples.helloworld;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static java.util.stream.Gatherers.scan;

class HelloGatherers {

    @Test
    void hello() {

        System.out.println(
                Stream.of(1, 2, 3)
                        .gather(scan(() -> 1, (a, b) -> a + b))
                        .toList()
        );
    }
}
