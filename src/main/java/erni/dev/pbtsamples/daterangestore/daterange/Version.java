package erni.dev.pbtsamples.daterangestore.daterange;

import java.time.LocalDate;

public record Version(LocalDate begin, LocalDate end) {
    public Version {
        if (begin.isAfter(end)) {
            throw new IllegalArgumentException("Begin can not be after end date");
        }
    }

    public boolean isWithin(LocalDate date) {
        return !begin.isAfter(date) && !end.isBefore(date);
    }
}
