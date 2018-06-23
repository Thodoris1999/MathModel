package com.ttyrovou.examples;

import com.ttyrovou.math.functions.Polynomial;
import com.ttyrovou.math.numbers.Complex;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PolynomialTest {
    @Test
    public void polynomialAddition() {
        Polynomial poly1 = new Polynomial(Complex.ofInt(2), Complex.ofInt(-4), Complex.ofInt(5));
        Polynomial poly2 = new Polynomial(Complex.ofInt(-5), Complex.ofInt(10), Complex.ofInt(9));
        Polynomial expected = new Polynomial(Complex.ofInt(-3), Complex.ofInt(6), Complex.ofInt(14));
        Polynomial result = poly1.add(poly2);
        assertEquals(expected, result);
    }

    @Test
    public void polynomialMultiplication() {
        Polynomial poly1 = new Polynomial(Complex.ofInt(2), Complex.ofInt(-4), Complex.ofInt(5));
        Polynomial poly2 = new Polynomial(Complex.ofInt(-5), Complex.ofInt(10), Complex.ofInt(9));
        Polynomial expected = new Polynomial(Complex.ofInt(-10), Complex.ofInt(40), Complex.ofInt(-47), Complex.ofInt(14), Complex.ofInt(45));
        Polynomial result = poly1.multiply(poly2);
        assertEquals(expected, result);
    }

    @Test
    public void polynomialDivision() {
        Polynomial poly1 = new Polynomial(Complex.ofInt(4), Complex.ofInt(1), Complex.ofInt(-3), Complex.ofInt(2));
        Polynomial poly2 = new Polynomial(Complex.ofInt(-1), Complex.ofInt(1));
        Polynomial expected = new Polynomial(Complex.ZERO, Complex.ofInt(-1), Complex.ofInt(2));
        Polynomial result = poly1.divide(poly2);
        assertEquals(expected, result);
    }
}
