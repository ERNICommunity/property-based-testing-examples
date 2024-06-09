package erni.dev.pbtsamples.contractstore;

import erni.dev.pbtsamples.contractstore.contract.Contract;
import erni.dev.pbtsamples.contractstore.contract.ContractId;

import java.util.HashMap;
import java.util.Map;

public class ContractStore2 {

    private final Map<ContractId, Contract> contracts = new HashMap<>();

    public void insertContract(Contract contract) {

        // Nicht behandelter Fall: Entferne Versionen, die von der neuen komplett überdeckt werden.

        contracts.put(contract.id(), contract);

        // Verschiebe das Ende jener Versionen, die sich mit einer Nachfolge-Version überlappen, in Richtung eines früheren Datums.

        // Nicht behandelter Fall: Fülle entstandene Lücken auf.
    }

    public void updateContract(Contract contract) {

        // ...
    }

    Contract getContract(ContractId id) {
        return contracts.get(id);
    }
}
