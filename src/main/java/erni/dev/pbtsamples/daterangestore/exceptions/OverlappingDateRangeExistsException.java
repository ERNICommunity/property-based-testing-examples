package erni.dev.pbtsamples.daterangestore.exceptions;

import erni.dev.pbtsamples.daterangestore.daterange.Version;

public class OverlappingDateRangeExistsException extends RuntimeException {

    private final Version version;

    public OverlappingDateRangeExistsException(Version version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return super.toString() + ": " + version;
    }
}
