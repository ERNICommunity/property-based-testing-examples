package erni.dev.pbtsamples.contractstore.contract;

import java.time.LocalDate;

public record Contract(ContractId id, DateRange validityPeriod) {

    public Contract(LocalDate start, LocalDate end) {
        this(new ContractId(), new DateRange(start, end));
    }
}
