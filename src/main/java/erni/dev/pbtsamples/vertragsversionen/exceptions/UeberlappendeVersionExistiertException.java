package erni.dev.pbtsamples.vertragsversionen.exceptions;

import erni.dev.pbtsamples.vertragsversionen.daterange.Version;

public class UeberlappendeVersionExistiertException extends RuntimeException {

    private final Version version;

    public UeberlappendeVersionExistiertException(Version version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return super.toString() + ": " + version;
    }
}
