package erni.dev.pbtsamples.daterangestore;

import erni.dev.pbtsamples.daterangestore.daterange.Version;
import net.jqwik.api.*;
import net.jqwik.api.state.Action;
import net.jqwik.api.state.ActionChain;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Vertrag2_2ndTest {

    @Property
    void checkMyStack(@ForAll("dateRangeStoreActions") ActionChain<Vertrag2> chain) {
        chain
                .withInvariant(store -> store.getDateRanges().stream()
                        .forEach(p1 -> {
                            Stream<Version> overlapping = store.getDateRanges().stream()
                                    .filter(p2 -> p1.begin().isAfter(p2.end())
                                            || p1.end().isBefore(p2.begin())
                                    );

                            assertThat(overlapping.count()).isZero();
                        }))
                .run();
    }

    @Provide
    Arbitrary<ActionChain<Vertrag2>> dateRangeStoreActions() {
        return ActionChain.startWith(Vertrag2::new)
                .withAction(fuegeMaximalGueltigeVersionEin());
    }

    Action<Vertrag2> fuegeMaximalGueltigeVersionEin() {
        Arbitrary<Version> versionen = versionen();
        return Action.<Vertrag2>builder().describeAs("fuegeMaximalGueltigeVersionEin").justMutate(store -> {
//            store.insertRange(versionen.sample());
            store.fuegeMaximalGueltigeVersionEin(versionen.sample());
        });
    }

    Arbitrary<Version> versionen() {
        return Combinators.combine(
                        Arbitraries.defaultFor(LocalDate.class),
                        Arbitraries.defaultFor(LocalDate.class)
                )
                .filter((begin, end) -> !begin.isAfter(end))
                .as(Version::new);
    }
}
