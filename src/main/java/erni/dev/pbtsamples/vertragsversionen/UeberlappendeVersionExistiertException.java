package erni.dev.pbtsamples.vertragsversionen;

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
