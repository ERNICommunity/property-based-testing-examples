package erni.dev.pbtsamples.contractstore;

import erni.dev.pbtsamples.contractstore.contract.Contract;
import net.jqwik.api.*;
import net.jqwik.api.domains.Domain;
import net.jqwik.api.domains.DomainContext;
import net.jqwik.api.domains.DomainContextBase;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatCode;

public class ContractStore1_4thTest {

    ContractStore1 store = new ContractStore1();

    @Property
    @Domain(ContractStoreDomain.class)
    void addContracts(@ForAll Contract contract1, @ForAll Contract contract2) {
        assertThatCode(() -> {
            store.addContract(contract1);
            store.addContract(contract2);
        }).doesNotThrowAnyException();
    }

    @Domain(DomainContext.Global.class)
    static class ContractStoreDomain extends DomainContextBase {

        @Provide
        Arbitrary<Contract> contracts() {
            return Combinators.combine(
                            Arbitraries.defaultFor(LocalDate.class),
                            Arbitraries.defaultFor(LocalDate.class)
                    )
                    .filter((begin, end) -> !begin.isAfter(end))
                    .as(Contract::new);
        }
    }
}
