package erni.dev.pbtsamples.vertragsversionen;

import erni.dev.pbtsamples.vertragsversionen.daterange.Version;
import net.jqwik.api.*;
import net.jqwik.api.state.ActionChain;
import net.jqwik.api.statistics.Statistics;

import static erni.dev.pbtsamples.vertragsversionen.Vertrag2Actions.fuegeMinimalVerkuerzteVersionHinzu;
import static org.assertj.core.api.Assertions.assertThat;

class Vertrag2_2ndTest {

    @Property
//    @Report(Reporting.GENERATED)
    void checkVertragsMutationen(@ForAll("vertragActions") ActionChain<Vertrag2> chain) {
        chain
                .withInvariant(this::noOverlaps)
                .run();
        Statistics.collect(chain);
    }

    private void noOverlaps(Vertrag2 vertrag) {
        vertrag.getVersionen().forEach(v1 -> {
            long overlaps = vertrag.getVersionen().stream()
                    .filter(v2 -> v1 != v2)
                    .filter(v2 -> overlap(v1, v2))
                    .count();

            assertThat(overlaps)
                    .as("Ueberlappende Versionen vorhanden: " + vertrag.getVersionen())
                    .isZero();
        });
    }

    private static boolean overlap(Version v1, Version v2) {
        return !v1.beginn().isAfter(v2.ende()) && !v1.ende().isBefore(v2.beginn());
    }

    @Provide
    Arbitrary<ActionChain<Vertrag2>> vertragActions() {
        return ActionChain.startWith(Vertrag2::new)
                .withAction(fuegeMinimalVerkuerzteVersionHinzu());
    }
}
