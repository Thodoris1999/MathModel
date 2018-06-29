package com.ttyrovou.examples;

import com.ttyrovou.math.numbers.Fraction;
import org.junit.Test;

public class FractionTest {

    @Test
    public void decimalFraction() {
        Fraction f = Fraction.ofDouble(10001.0000001);
        System.out.println(f);
    }
}
