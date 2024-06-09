package erni.dev.pbtsamples.helloworld;

import net.jqwik.time.api.Dates;
import org.junit.jupiter.api.Test;

class HelloArbitraries {

    @Test
    void dates() {

        Dates.dates().yearBetween(2000, 2010).stream()
                .sample()
                .forEach(System.out::println);
    }
}