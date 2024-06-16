package erni.dev.pbtsamples.daterangestore;

import erni.dev.pbtsamples.daterangestore.daterange.Version;
import net.jqwik.api.*;
import net.jqwik.time.api.Dates;

import static org.assertj.core.api.Assertions.assertThatCode;

class Vertrag1_3rdTest {

    Vertrag1 store = new Vertrag1();

    @Property
    void addDateRanges(@ForAll("versionen") Version dateRange1, @ForAll("versionen") Version dateRange2) {
        assertThatCode(() -> {
            store.addDateRange(dateRange1);
            store.addDateRange(dateRange2);
        }).doesNotThrowAnyException();
    }

    @Provide
    Arbitrary<? extends Version> versionen() {
        return Combinators.combine(
                        Dates.years().between(2000, 2020).map(y -> y.atDay(1)),
                        Dates.years().between(2000, 2020).map(y -> y.atDay(1))
                )
                .filter((begin, end) -> !begin.isAfter(end))
                .as(Version::new);
    }
}
