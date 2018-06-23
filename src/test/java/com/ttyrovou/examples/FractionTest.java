package com.ttyrovou.examples;

import com.ttyrovou.math.numbers.Fraction;
import org.junit.Test;

public class FractionTest {

    @Test
    public void testApproximation() {
        Fraction pi = Fraction.ofDouble(1.5000001);
        System.out.println(pi);
        for (int i = 0; i < 5; i++) {
            System.out.println("Iteration " + i + ": " + pi.approximate(i));
        }
    }
}
