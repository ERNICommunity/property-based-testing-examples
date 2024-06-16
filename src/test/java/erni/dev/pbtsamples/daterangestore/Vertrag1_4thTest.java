package erni.dev.pbtsamples.daterangestore;

import erni.dev.pbtsamples.daterangestore.daterange.Version;
import net.jqwik.api.*;
import net.jqwik.api.domains.Domain;
import net.jqwik.api.domains.DomainContext;
import net.jqwik.api.domains.DomainContextBase;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatCode;

public class Vertrag1_4thTest {

    Vertrag1 store = new Vertrag1();

    @Property
    @Domain(DateRangeStoreDomain.class)
    void addDateRanges(@ForAll Version dateRange1, @ForAll Version dateRange2) {
        assertThatCode(() -> {
            store.addDateRange(dateRange1);
            store.addDateRange(dateRange2);
        }).doesNotThrowAnyException();
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
