package erni.dev.pbtsamples.vertragsversionen;

import erni.dev.pbtsamples.vertragsversionen.daterange.Version;
import erni.dev.pbtsamples.vertragsversionen.exceptions.UeberlappendeVersionExistiertException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class Vertrag1 {

    final Collection<Version> versionen = new ArrayList<>();

    void fuegeHinzu(Version version) {
        versionen.forEach(dr -> {
            if (!version.ende().isBefore(dr.beginn())
                    && !version.beginn().isAfter(dr.ende())) {
                throw new UeberlappendeVersionExistiertException(dr);
            }
        });
        versionen.add(version);
    }

    Version getVersion(LocalDate date) {
        return versionen.stream()
                .filter(v -> v.istGueltigAm(date))
                .findAny()
                .orElseThrow();
    }
}
