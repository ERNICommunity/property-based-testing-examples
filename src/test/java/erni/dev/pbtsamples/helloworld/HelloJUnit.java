package erni.dev.pbtsamples.helloworld;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HelloJUnit {

    HelloJUnit() {
        System.out.println("1 new instance");
    }

    @BeforeEach
    void setUp() {
        System.out.println("2 @BeforeEach");
    }

    @BeforeAll
    static void setUpClass() {
        System.out.println("0 @BeforeAll");
    }

    @Test
    void test1() {
        System.out.println("test1");
    }

    @Test
    void test2() {
        System.out.println("test2");
    }
}
