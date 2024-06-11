package erni.dev.pbtsamples.contractstore;

import erni.dev.pbtsamples.contractstore.contract.Contract;
import erni.dev.pbtsamples.contractstore.contract.DateRange;
import net.jqwik.api.*;
import net.jqwik.api.domains.Domain;
import net.jqwik.api.domains.DomainContext;
import net.jqwik.api.domains.DomainContextBase;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class ContractStore2_1stTest {

    ContractStore2 store = new ContractStore2();

    @Property
    @Domain(ContractStoreDomain.class)
    void addContracts(@ForAll Contract contract1, @ForAll Contract contract2) {
        assertThatCode(() -> {
            store.adjustAndInsertForMaxDateRange(contract1);
            store.adjustAndInsertForMaxDateRange(contract2);
        }).doesNotThrowAnyException();

        store.getContracts().stream()
                .map(Contract::validityPeriod)
                .forEach(this::assertNoOverlap);
    }

    private void assertNoOverlap(DateRange p1) {
        Stream<DateRange> overlapping = store.getContracts().stream()
                .map(Contract::validityPeriod)
                .filter(p2 -> p1.begin().isAfter(p2.end()) || p1.end().isBefore(p2.begin()));

        assertThat(overlapping.count()).isZero();
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
