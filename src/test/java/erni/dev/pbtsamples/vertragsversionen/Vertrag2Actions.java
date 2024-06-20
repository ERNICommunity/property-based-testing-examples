package erni.dev.pbtsamples.vertragsversionen;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Combinators;
import net.jqwik.api.state.Action;
import net.jqwik.api.state.Transformer;

import java.time.LocalDate;

class Vertrag2Actions {

    static Action.Independent<Vertrag2> fuegeMinimalVerkuerzteVersionHinzu() {

        return () -> versionen()
                .map(v -> Transformer.mutate(
                        "fuegeMinimalVerkuerzteVersionHinzu(%s)".formatted(v),
                        vertrag -> {
                            vertrag.fuegeMinimalVerkuerzteVersionHinzu(v);
                        }));
    }

    static Action.Independent<Vertrag2> ersetzeBestehendeVersionen() {

        return () -> versionen()
                .map(v -> Transformer.mutate(
                        "ersetzeBestehendeVersionen(%s)".formatted(v),
                        vertrag -> {
                            vertrag.ersetzeBestehendeVersionen(v);
                        }));
    }

    static Arbitrary<Version> versionen() {
        return Combinators.combine(
                        Arbitraries.defaultFor(LocalDate.class),
                        Arbitraries.defaultFor(LocalDate.class)
                )
                .filter((begin, end) -> !begin.isAfter(end))
                .as(Version::new);
    }
}
