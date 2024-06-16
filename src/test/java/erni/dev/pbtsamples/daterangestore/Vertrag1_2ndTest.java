package erni.dev.pbtsamples.daterangestore;

import erni.dev.pbtsamples.daterangestore.daterange.Version;
import net.jqwik.api.*;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class Vertrag1_2ndTest {

    Vertrag1 store = new Vertrag1();

    @Property
    void addDateRanges(@ForAll LocalDate begin1, @ForAll LocalDate begin2, @ForAll LocalDate end1, @ForAll LocalDate end2) {
        Version dateRange1 = new Version(begin1, end1);
        Version dateRange2 = new Version(begin2, end2);

        assertThatCode(() -> {
            store.addDateRange(dateRange1);
            store.addDateRange(dateRange2);
        }).doesNotThrowAnyException();
    }
}
