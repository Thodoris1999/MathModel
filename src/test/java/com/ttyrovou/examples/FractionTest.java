package com.ttyrovou.examples;

import com.ttyrovou.math.numbers.Fraction;
import org.junit.Test;

public class FractionTest {

    @Test
    public void decimalFraction() {
        Fraction f = Fraction.ofDouble(Math.PI);
        System.out.println(f);
    }
}
