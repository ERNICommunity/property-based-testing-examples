package erni.dev.pbtsamples.vertragsversionen;

import net.jqwik.api.*;
import net.jqwik.api.domains.Domain;
import net.jqwik.api.domains.DomainContext;
import net.jqwik.api.domains.DomainContextBase;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatCode;

class Vertrag1_4thTest {

    Vertrag1 vertrag = new Vertrag1();

    @Property
    @Domain(VertragDomain.class)
    void addDateRanges(@ForAll Version version1, @ForAll Version version2) {
        assertThatCode(() -> {
            vertrag.fuegeHinzu(version1);
            vertrag.fuegeHinzu(version2);
        }).doesNotThrowAnyException();
    }

    @Domain(DomainContext.Global.class)
    static class VertragDomain extends DomainContextBase {

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
