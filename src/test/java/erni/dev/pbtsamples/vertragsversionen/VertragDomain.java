package erni.dev.pbtsamples.vertragsversionen;

import erni.dev.pbtsamples.vertragsversionen.daterange.Version;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Combinators;
import net.jqwik.api.Provide;
import net.jqwik.api.domains.Domain;
import net.jqwik.api.domains.DomainContext;
import net.jqwik.api.domains.DomainContextBase;

import java.time.LocalDate;

@Domain(DomainContext.Global.class)
class VertragDomain extends DomainContextBase {

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
