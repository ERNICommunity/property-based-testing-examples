package erni.dev.pbtsamples.contractstore.contract;

import java.time.LocalDate;

public record DateRange(LocalDate begin, LocalDate end) {
    public DateRange {
        if (begin.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
    }
}
