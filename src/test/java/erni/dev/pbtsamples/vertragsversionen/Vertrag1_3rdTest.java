package erni.dev.pbtsamples.vertragsversionen;

import erni.dev.pbtsamples.vertragsversionen.daterange.Version;
import net.jqwik.api.*;
import net.jqwik.time.api.Dates;

import static org.assertj.core.api.Assertions.assertThatCode;

class Vertrag1_3rdTest {

    Vertrag1 vertrag = new Vertrag1();

    @Property
    void addDateRanges(@ForAll("versionen") Version version1, @ForAll("versionen") Version version2) {

        assertThatCode(() -> {    vertrag.fuegeHinzu(version1);
                                  vertrag.fuegeHinzu(version2);    }).doesNotThrowAnyException();
    }

    @Provide
    Arbitrary<? extends Version> versionen() {
        return Combinators.combine(
                        Dates.dates().yearBetween(2000, 2020), Dates.dates().yearBetween(2000, 2020)
                )
                .filter((beginn, ende) -> !beginn.isAfter(ende))
                .as(Version::new);
    }
}
