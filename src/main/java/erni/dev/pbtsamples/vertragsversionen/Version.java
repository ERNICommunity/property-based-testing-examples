package erni.dev.pbtsamples.vertragsversionen;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Stream;

import static erni.dev.pbtsamples.vertragsversionen.Bugs.Versionsdifferenzenberechnung.b;

public record Version(LocalDate beginn, LocalDate ende) {

    public Version {
        if (ende.isBefore(beginn)) {
            throw new IllegalArgumentException("Das Ende darf nicht vor dem Beginn liegen.");
        }
    }

    public boolean istGueltigAm(LocalDate datum) {
        return !beginn.isAfter(datum) && !ende.isBefore(datum);
    }

    public boolean ueberschneidetSichMit(Version andereVersion) {
        return !ueberschneidetSichNichtMit(andereVersion);
    }

    public boolean ueberschneidetSichNichtMit(Version andereVersion) {
        return beginn.isAfter(andereVersion.ende) || ende.isBefore(andereVersion.beginn);
    }

    static Bugs.Versionsdifferenzenberechnung bug = b;

    public Collection<Version> minus(Version andereVersion) {

        return switch (bug) {
            case a -> aBuggyMinus(andereVersion);
            case b -> bBuggyMinus(andereVersion);
            case null -> aGoodMinus(andereVersion);
        };
    }

    // produziert Überlappungen, da Bedingungen nicht vollständig sind
    private Collection<Version> aBuggyMinus(Version andereVersion) {
        Collection<Version> differenz = new ArrayList<>();

        if (andereVersion.ende.isBefore(beginn)) {
            differenz.add(this);
        }
        if (andereVersion.ende.isBefore(ende)) {
            differenz.add(new Version(andereVersion.ende.plusDays(1), ende));
        }
        if (andereVersion.beginn.isAfter(beginn)) {
            differenz.add(new Version(beginn, andereVersion.beginn.minusDays(1)));
        }
        if (andereVersion.beginn.isAfter(ende)) {
            differenz.add(this);
        }

        return differenz;
    }

    // produziert Lücken, da andereVersion.ende.equals(beginn) und andereVersion.beginn.equals(ende) fehlen
    private Collection<Version> bBuggyMinus(Version andereVersion) {
        Collection<Version> differenz = new ArrayList<>();

        if (andereVersion.ende.isBefore(beginn)) {
            differenz.add(this);
        }
        if (andereVersion.ende.isAfter(beginn) && andereVersion.ende.isBefore(ende)) {
            differenz.add(new Version(andereVersion.ende.plusDays(1), ende));
        }
        if (andereVersion.beginn.isAfter(beginn) && andereVersion.beginn.isBefore(ende)) {
            differenz.add(new Version(beginn, andereVersion.beginn.minusDays(1)));
        }
        if (andereVersion.beginn.isAfter(ende)) {
            differenz.add(this);
        }

        return differenz;
    }

    private Collection<Version> aGoodMinus(Version andereVersion) {
        Collection<Version> differenz = new ArrayList<>();

        if (andereVersion.ende.isBefore(beginn)) {
            differenz.add(this);
        }
        else if (andereVersion.ende.isBefore(ende)) {
            differenz.add(new Version(andereVersion.ende.plusDays(1), ende));
        }
        if (andereVersion.beginn.isAfter(ende)) {
            differenz.add(this);
        }
        else if (andereVersion.beginn.isAfter(beginn)) {
            differenz.add(new Version(beginn, andereVersion.beginn.minusDays(1)));
        }

        return differenz;
    }

    public Version plus(Version andereVersion) {
        if (istNichtAngrenzend(andereVersion) && ueberschneidetSichNichtMit(andereVersion)) {
            throw new IllegalArgumentException("plus darf nur auf angrenzende oder überlappende Versionen angewandt werden");
        }
        return new Version(
                Stream.of(beginn, andereVersion.beginn).min(Comparator.naturalOrder()).orElseThrow(),
                Stream.of(ende, andereVersion.ende).max(Comparator.naturalOrder()).orElseThrow()
        );
    }

    private boolean istNichtAngrenzend(Version andereVersion) {
        return !istAngrenzend(andereVersion);
    }

    private boolean istAngrenzend(Version andereVersion) {
        return (ende.isBefore(LocalDate.MAX) && ende.plusDays(1).equals(andereVersion.beginn))
                || (andereVersion.ende.isBefore(LocalDate.MAX) && andereVersion.ende.plusDays(1).equals(beginn));
    }
}
