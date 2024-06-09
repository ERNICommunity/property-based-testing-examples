package erni.dev.pbtsamples.contractstore.exceptions;

import erni.dev.pbtsamples.contractstore.contract.Contract;

public class OverlappingContractExistsException extends RuntimeException {

    private final transient Contract storedContract;

    public OverlappingContractExistsException(Contract storedContract) {
        this.storedContract = storedContract;
    }

    @Override
    public String toString() {
        return super.toString() + ": " + storedContract;
    }
}
