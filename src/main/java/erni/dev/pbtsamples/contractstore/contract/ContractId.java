package erni.dev.pbtsamples.contractstore.contract;

import java.util.UUID;

public record ContractId(String value) {
    public ContractId() {
        this(UUID.randomUUID().toString());
    }
}
