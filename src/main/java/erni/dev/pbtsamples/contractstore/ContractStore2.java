package erni.dev.pbtsamples.contractstore;

import erni.dev.pbtsamples.contractstore.contract.Contract;
import erni.dev.pbtsamples.contractstore.contract.ContractId;
import erni.dev.pbtsamples.contractstore.contract.DateRange;

import java.time.Period;
import java.util.*;

public class ContractStore2 {

    private final Map<ContractId, Contract> contracts = new HashMap<>();

    public Optional<Contract> adjustAndInsertForMaxDateRange(Contract contract) {
        if (true) {
            contracts.put(contract.id(), contract);
            return Optional.of(contract);
        }
        synchronized (contracts) {
            Optional<Contract> maxRangeContract = getMaxRangeContract(contract);
            maxRangeContract.ifPresent(mrc -> contracts.put(mrc.id(), mrc));
            return maxRangeContract;
        }
    }

    private Optional<Contract> getMaxRangeContract(Contract contract) {
        return getMaxRange(contract.validityPeriod())
                .map(maxRange -> getContract(contract, maxRange));
    }

    private static Contract getContract(Contract contract, DateRange maxRange) {
        return Objects.equals(contract.validityPeriod(), maxRange) ? contract : new Contract(contract.id(), maxRange);
    }

    private Optional<DateRange> getMaxRange(DateRange dateRange) {
        return contracts.values().stream()
                .map(Contract::validityPeriod)
                .filter(r -> r.begin().isBefore(dateRange.end()) && r.end().isAfter(dateRange.begin()))
                .max(Comparator.comparing(r -> Period.between(r.begin(), r.end()).getDays()));
    }

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

    Collection<Contract> getContracts() {
        return contracts.values();
    }
}
