package erni.dev.pbtsamples.daterangestore;

import erni.dev.pbtsamples.daterangestore.daterange.Version;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class Vertrag1_1stTest {

    Vertrag1 store = new Vertrag1();

    @Test
    void store() {
        Version dateRange1 = new Version(LocalDate.MIN, LocalDate.parse("2019-12-31"));
        Version dateRange2 = new Version(LocalDate.parse("2020-01-01"), LocalDate.MAX);

        store.addDateRange(dateRange1);
        store.addDateRange(dateRange2);

        assertThat(store.getDateRange(dateRange1.begin())).isEqualTo(dateRange1);
        assertThat(store.getDateRange(dateRange2.begin())).isEqualTo(dateRange2);
    }
}
