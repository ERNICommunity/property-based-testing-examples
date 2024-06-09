package erni.dev.pbtsamples.contractstore;

import erni.dev.pbtsamples.contractstore.contract.Contract;
import net.jqwik.api.*;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatCode;

class ContractStore1_3rdTest {

    ContractStore1 store = new ContractStore1();

    @Property
    void addContracts(@ForAll("contracts") Contract contract1, @ForAll("contracts") Contract contract2) {
        assertThatCode(() -> {
            store.addContract(contract1);
            store.addContract(contract2);
        }).doesNotThrowAnyException();
    }

    @Provide
    Arbitrary<? extends Contract> contracts() {
        return Combinators.combine(
                        Arbitraries.forType(LocalDate.class),
                        Arbitraries.forType(LocalDate.class)
                )
                .filter((begin, end) -> !begin.isAfter(end))
                .as(Contract::new);
    }
}
