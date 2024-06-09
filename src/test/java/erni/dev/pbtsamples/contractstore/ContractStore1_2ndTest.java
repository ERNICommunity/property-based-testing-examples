package erni.dev.pbtsamples.contractstore;

import erni.dev.pbtsamples.contractstore.contract.Contract;
import net.jqwik.api.*;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class ContractStore1_2ndTest {

    ContractStore1 store = new ContractStore1();

    @Property
    void addContracts(@ForAll LocalDate begin1, @ForAll LocalDate begin2, @ForAll LocalDate end1, @ForAll LocalDate end2) {
        Contract contract1 = new Contract(begin1, end1);
        Contract contract2 = new Contract(begin2, end2);

        assertThatCode(() -> {
            store.addContract(contract1);
            store.addContract(contract2);
        }).doesNotThrowAnyException();
    }
}
