package erni.dev.pbtsamples.vertragsversionen.daterange;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static erni.dev.pbtsamples.vertragsversionen.daterange.Bugs.Versionsdifferenzenberechnung.a;
import static erni.dev.pbtsamples.vertragsversionen.daterange.Bugs.Versionsdifferenzenberechnung.b;

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
    
    static Bugs.Versionsdifferenzenberechnung bug = a;

    public Collection<Version> minus(Version andereVersion) {
        Collection<Version> differenz = new ArrayList<>();

        if (bug == null) {
            if (andereVersion.ende.isBefore(beginn)) {
                differenz.add(this);
            } else if (andereVersion.ende.isBefore(ende)) {
                differenz.add(new Version(andereVersion.ende.plusDays(1), ende));
            }
            if (andereVersion.beginn.isAfter(ende)) {
                differenz.add(this);
            } else if (andereVersion.beginn.isAfter(beginn)) {
                differenz.add(new Version(beginn, andereVersion.beginn.minusDays(1)));
            }
        }

        if (bug == a) {

            // produziert Überlappungen, da Bedingungen nicht vollständig sind

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
        }
        
        if (bug == b) {

            // produziert Lücken, da andereVersion.ende.equals(beginn) und andereVersion.beginn.equals(ende) fehlen

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
        }
        
        return differenz;
    }
}
