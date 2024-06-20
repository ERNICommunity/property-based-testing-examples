package erni.dev.pbtsamples.vertragsversionen;

import erni.dev.pbtsamples.vertragsversionen.daterange.Version;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.domains.Domain;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class Vertrag2_1stTest {

    Vertrag2 vertrag = new Vertrag2();

    Vertrag2_1stTest() {
        System.err.println("instantiate");
    }

    @BeforeEach
    void setUp() {
        System.err.println("before");
    }

    @BeforeAll
    static void setUp2() {
        System.err.println("beforeAll");
    }

    @Property
    @Domain(VertragDomain.class)
    void fuegeMinimalVerkuerzteVersionHinzu(@ForAll Version version1, @ForAll Version version2) {

//        vertrag.versionen.clear();

        vertrag.fuegeMinimalVerkuerzteVersionHinzu(version1);
        Optional<Version> hinzugefuegteVersion2 = vertrag.fuegeMinimalVerkuerzteVersionHinzu(version2);

        if (hinzugefuegteVersion2.isPresent() && !hinzugefuegteVersion2.get().equals(version2)) {
            System.err.println("version2 wurde beim Hinzufügen verkürzt");
        }

        vertrag.getVersionen().forEach(this::assertKeineUeberlappung);
    }

    private void assertKeineUeberlappung(Version version) {
        Collection<Version> ueberlappendeVersionen = vertrag.getVersionen().stream()
                .filter(andereVersion -> andereVersion != version && andereVersion.ueberschneidetSichMit(version))
                .toList();

        assertThat(ueberlappendeVersionen).as("Overlap! " + version + " / " + ueberlappendeVersionen).isEmpty();
    }
}
