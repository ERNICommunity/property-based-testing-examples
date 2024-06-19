package erni.dev.pbtsamples.vertragsversionen;

import erni.dev.pbtsamples.vertragsversionen.daterange.Version;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatCode;

class Vertrag1_2ndTest {

    Vertrag1 vertrag = new Vertrag1();

    @Property
    void addDateRanges(@ForAll LocalDate beginn1, @ForAll LocalDate ende1,
                       @ForAll LocalDate beginn2, @ForAll LocalDate ende2
    ) {
        Version version1 = new Version(beginn1, ende1);
        Version version2 = new Version(beginn2, ende2);

        assertThatCode(() -> {
            vertrag.fuegeHinzu(version1);
            vertrag.fuegeHinzu(version2);
        }).doesNotThrowAnyException();
    }
}
