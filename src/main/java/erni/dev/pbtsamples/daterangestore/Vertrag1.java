package erni.dev.pbtsamples.daterangestore;

import erni.dev.pbtsamples.daterangestore.daterange.Version;
import erni.dev.pbtsamples.daterangestore.exceptions.OverlappingDateRangeExistsException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class Vertrag1 {

    private final Collection<Version> versionen = new ArrayList<>();

    void addDateRange(Version version) {
        versionen.forEach(dr -> {
            if (!version.end().isBefore(dr.begin())
                    && !version.begin().isAfter(dr.end())) {
                throw new OverlappingDateRangeExistsException(dr);
            }
        });
        versionen.add(version);
    }

    Version getDateRange(LocalDate date) {
        return versionen.stream()
                .filter(r -> r.isWithin(date))
                .findAny()
                .orElseThrow();
    }

}
