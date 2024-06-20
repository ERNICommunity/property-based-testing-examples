package erni.dev.pbtsamples.vertragsversionen;

import erni.dev.pbtsamples.vertragsversionen.daterange.Version;

import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public class Vertrag2 {

    private final Collection<Version> versionen = new ArrayList<>();

    public Collection<Version> getVersionen() {
        return versionen;
    }

    public Optional<Version> fuegeMinimalVerkuerzteVersionHinzu(Version version) {
        Optional<Version> maxVersion = ermittleMinimalVerkuerzteVersion(version);
        maxVersion.ifPresent(versionen::add);
        return maxVersion;
    }

    private Optional<Version> ermittleMinimalVerkuerzteVersion(Version version) {

        return versionen.stream()
                .reduce(
                        Stream.of(version),
                        (rest, v) -> rest.flatMap(restversion -> restversion.minus(v).stream()),
                        (_, _) -> { throw new UnsupportedOperationException(); }
                )
                .max(Comparator.comparing(v -> Period.between(v.beginn(), v.ende()).getDays()));
    }

    public void ersetzeBestehendeVersionen(Version version) {

        Collection<Version> bisherigeVersionen = new ArrayList<>(versionen);
        versionen.clear();
        versionen.addAll(bisherigeVersionen.stream().flatMap(v -> v.minus(version).stream()).toList());
        versionen.add(version);
    }

    @Override
    public String toString() {
        return "Vertrag2{versionen=" + versionen + '}';
    }
}
