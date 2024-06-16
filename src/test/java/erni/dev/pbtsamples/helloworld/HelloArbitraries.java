package erni.dev.pbtsamples.helloworld;

import net.jqwik.time.api.Dates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HelloArbitraries {

    @Test
    void dates() {

        Dates.dates().yearBetween(2000, 2010).stream()
                .sample()
                .forEach(System.out::println);

        assertTrue(true);
    }
}
