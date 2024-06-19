package erni.dev.pbtsamples.helloworld;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HelloSwitch {

    @Test
    void test() {

        assertThatThrownBy(() -> switchOn(null)).isInstanceOf(NullPointerException.class);
        assertThatCode(() -> switchOnNullable(null)).doesNotThrowAnyException();
    }

    private static void switchOn(E e) {
        switch (e) {
            default:
                throw new IllegalStateException("Unexpected value: " + e);
        }
    }

    private static void switchOnNullable(E e) {
        switch (e) {
            case null:
                System.out.println("null");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + e);
        }
    }

    enum E {}
}
