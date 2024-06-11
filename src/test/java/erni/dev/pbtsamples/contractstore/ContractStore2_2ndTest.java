package erni.dev.pbtsamples.contractstore;

import erni.dev.pbtsamples.contractstore.contract.Contract;
import erni.dev.pbtsamples.contractstore.contract.DateRange;
import net.jqwik.api.*;
import net.jqwik.api.state.Action;
import net.jqwik.api.state.ActionChain;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ContractStore2_2ndTest {

    @Property
    void checkMyStack(@ForAll("contractStoreActions") ActionChain<ContractStore2> chain) {
        chain.run();
    }

    @Provide
    Arbitrary<ActionChain<ContractStore2>> contractStoreActions() {
        return ActionChain.startWith(ContractStore2::new)
                .withAction(adjustAndInsertForMaxDateRange());
    }

    Action<ContractStore2> adjustAndInsertForMaxDateRange() {
        Arbitrary<Contract> contracts = contracts();
        return Action.<ContractStore2>builder().describeAs("adjustAndInsertForMaxDateRange").justMutate(store -> {
            store.adjustAndInsertForMaxDateRange(contracts.sample());
            store.getContracts().stream()
                    .map(Contract::validityPeriod)
                    .forEach(p1 -> {

                        Stream<DateRange> overlapping = store.getContracts().stream()
                                .map(Contract::validityPeriod)
                                .filter(p2 -> p1.begin().isAfter(p2.end()) || p1.end().isBefore(p2.begin()));

                        assertThat(overlapping.count()).isZero();
                    });
        });
    }

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
