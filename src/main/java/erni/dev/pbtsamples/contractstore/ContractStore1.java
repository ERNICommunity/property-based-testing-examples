package erni.dev.pbtsamples.contractstore;

import erni.dev.pbtsamples.contractstore.contract.Contract;
import erni.dev.pbtsamples.contractstore.contract.ContractId;
import erni.dev.pbtsamples.contractstore.exceptions.OverlappingContractExistsException;

import java.util.HashMap;
import java.util.Map;

public class ContractStore1 {

    private final Map<ContractId, Contract> contracts = new HashMap<>();

    void addContract(Contract contract) {
        contracts.values().forEach(storedContract -> {
            if (!contract.validityPeriod().end().isBefore(storedContract.validityPeriod().begin())
                    && !contract.validityPeriod().begin().isAfter(storedContract.validityPeriod().end())) {
                throw new OverlappingContractExistsException(storedContract);
            }
        });
        contracts.put(contract.id(), contract);
    }

    Contract getContract(ContractId id) {
        return contracts.get(id);
    }
}
