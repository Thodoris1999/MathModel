package com.ttyrovou.examples;

import com.ttyrovou.math.numbers.Fraction;
import org.junit.Test;

import java.math.BigDecimal;

public class FractionTest {

    @Test
    public void decimalFraction() {
        Fraction f = Fraction.ofDouble(10001.0000001);
        System.out.println(f);
    }

    @Test
    public void bigDecimalFraction() {
        Fraction f = new Fraction(new BigDecimal("1231240.000000000000000000000000000001"));
        System.out.println(f);
    }
}
