package erni.dev.pbtsamples.vertragsversionen;

import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import net.jqwik.api.state.ActionChain;
import net.jqwik.api.statistics.Statistics;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.stream.Gatherers;

import static erni.dev.pbtsamples.vertragsversionen.Vertrag2Actions.ersetzeBestehendeVersionen;
import static org.assertj.core.api.Assertions.assertThat;

class Vertrag2_3rdTest {

//    @Property(shrinking = ShrinkingMode.FULL)
    @Property
    void checkVertragsMutationen(@ForAll("vertragActions") ActionChain<Vertrag2> chain) {

        chain
                .withInvariant(this::noGaps)
                .run();

        Statistics.collect(chain);
    }

    private void noGaps(Vertrag2 vertrag) {

        assertThat(vertrag.getVersionen().stream().min(Comparator.comparing(Version::beginn)).orElseThrow().beginn()).isEqualTo(LocalDate.MIN);

        assertThat(vertrag.getVersionen().stream().max(Comparator.comparing(Version::beginn)).orElseThrow().ende()).isEqualTo(LocalDate.MAX);

        assertNoGapsAssumingNoOverlaps(vertrag);

        assertNoGaps(vertrag);
    }

    private static void assertNoGapsAssumingNoOverlaps(Vertrag2 vertrag) {
        if (vertrag.getVersionen().size() > 1) {
            vertrag.getVersionen().stream()
                    .sorted(Comparator.comparing(Version::beginn))
                    .gather(Gatherers.windowSliding(2))
                    .forEach(join -> {
                        assertThat(join.get(0).ende().plusDays(1))
                                .isEqualTo(join.get(1).beginn())
                                .as("Vertrag lückenhaft: " + vertrag.getVersionen());
                    });
        }
    }

    private void assertNoGaps(Vertrag2 vertrag) {
        Version summe = vertrag.getVersionen().stream()
                .sorted(Comparator.comparing(Version::beginn))
                .reduce(Version::plus)
                .orElseThrow();
        assertThat(summe.beginn()).isEqualTo(LocalDate.MIN);
        assertThat(summe.ende()).isEqualTo(LocalDate.MAX);
    }

    @Provide
    Arbitrary<ActionChain<Vertrag2>> vertragActions() {
        return ActionChain.startWith(this::ewigerVertrag)
                .withAction(ersetzeBestehendeVersionen());
    }

    private Vertrag2 ewigerVertrag() {
        Vertrag2 vertrag2 = new Vertrag2();
        vertrag2.getVersionen().add(new Version(LocalDate.MIN, LocalDate.MAX));
        return vertrag2;
    }
}
