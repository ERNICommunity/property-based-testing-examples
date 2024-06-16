package erni.dev.pbtsamples.daterangestore;

import erni.dev.pbtsamples.daterangestore.daterange.Version;

import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

public class Vertrag2 {

    private final Collection<Version> versionen = new ArrayList<>();

    public Optional<Version> fuegeMaximalGueltigeVersionEin(Version version) {
        Optional<Version> maxVersion = getMaximalGueltigeVersion(version);
        maxVersion.ifPresent(mr -> versionen.add(mr));
        return maxVersion;
    }

    private Optional<Version> getMaximalGueltigeVersion(Version version) {
        return versionen.stream()
                .filter(r -> r.begin().isBefore(version.end()) && r.end().isAfter(version.begin()))
                .max(Comparator.comparing(r -> Period.between(r.begin(), r.end()).getDays()));
    }

    public void insertRange(Version version) {

        // Nicht behandelter Fall: Entferne Versionen, die von der neuen komplett überdeckt werden.

        versionen.add(version);

        // Verschiebe das Ende jener Versionen, die sich mit einer Nachfolge-Version überlappen, in Richtung eines früheren Datums.

        // Nicht behandelter Fall: Fülle entstandene Lücken auf.
    }

    Collection<Version> getDateRanges() {
        return versionen;
    }
}
