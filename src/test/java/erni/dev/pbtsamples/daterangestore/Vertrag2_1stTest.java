package erni.dev.pbtsamples.daterangestore;

import erni.dev.pbtsamples.daterangestore.daterange.Version;
import net.jqwik.api.*;
import net.jqwik.api.domains.Domain;
import net.jqwik.api.domains.DomainContext;
import net.jqwik.api.domains.DomainContextBase;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class Vertrag2_1stTest {

    Vertrag2 store = new Vertrag2();

    @Property
    @Domain(DateRangeStoreDomain.class)
    void addDateRanges(@ForAll Version dateRange1, @ForAll Version dateRange2) {
        assertThatCode(() -> {
            store.fuegeMaximalGueltigeVersionEin(dateRange1);
            store.fuegeMaximalGueltigeVersionEin(dateRange2);
        }).doesNotThrowAnyException();

        store.getDateRanges().stream()
                .forEach(this::assertNoOverlap);
    }

    private void assertNoOverlap(Version p1) {
        Stream<Version> overlapping = store.getDateRanges().stream()
                .filter(p2 -> p1.begin().isAfter(p2.end()) || p1.end().isBefore(p2.begin()));

        assertThat(overlapping.count()).isZero();
    }

    @Domain(DomainContext.Global.class)
    static class DateRangeStoreDomain extends DomainContextBase {

        @Provide
        Arbitrary<Version> versionen() {
            return Combinators.combine(
                            Arbitraries.defaultFor(LocalDate.class),
                            Arbitraries.defaultFor(LocalDate.class)
                    )
                    .filter((begin, end) -> !begin.isAfter(end))
                    .as(Version::new);
        }
    }
}
