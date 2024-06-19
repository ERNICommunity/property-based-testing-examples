package erni.dev.pbtsamples.vertragsversionen;

import erni.dev.pbtsamples.vertragsversionen.daterange.Version;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class Vertrag1_1stTest {

    Vertrag1 vertrag = new Vertrag1();

    @Test
    void store() {
        Version version1 = new Version(LocalDate.MIN, LocalDate.parse("2019-12-31"));
        Version version2 = new Version(LocalDate.parse("2020-01-01"), LocalDate.MAX);

        vertrag.fuegeHinzu(version1);
        vertrag.fuegeHinzu(version2);

        assertThat(vertrag.getVersion(version1.beginn())).isEqualTo(version1);
        assertThat(vertrag.getVersion(version2.beginn())).isEqualTo(version2);
    }
}
