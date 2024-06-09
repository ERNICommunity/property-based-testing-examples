package erni.dev.pbtsamples.contractstore;

import erni.dev.pbtsamples.contractstore.contract.Contract;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ContractStore1_1stTest {

    ContractStore1 store = new ContractStore1();

    @Test
    void store() {
        Contract contract1 = new Contract(LocalDate.MIN, LocalDate.parse("2019-12-31"));
        Contract contract2 = new Contract(LocalDate.parse("2020-01-01"), LocalDate.MAX);

        store.addContract(contract1);
        store.addContract(contract2);

        assertThat(store.getContract(contract1.id())).isEqualTo(contract1);
        assertThat(store.getContract(contract2.id())).isEqualTo(contract2);
    }
}
