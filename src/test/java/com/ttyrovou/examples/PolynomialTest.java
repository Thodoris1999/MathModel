package com.ttyrovou.examples;

import com.ttyrovou.math.functions.Polynomial;
import com.ttyrovou.math.numbers.Complex;
import com.ttyrovou.math.numbers.Fraction;
import com.ttyrovou.math.solvers.LaguerreSolver;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void findRoots() {
        Polynomial pol = new Polynomial(Complex.ZERO, Complex.ofInt(-6), Complex.ofInt(-7), Complex.ofInt(1), Complex.ofInt(2));
        LaguerreSolver solver = new LaguerreSolver();
        Complex[] allRoots = solver.findAllRoots(pol);
        assertEquals(4, allRoots.length);
        assertTrue(Arrays.asList(allRoots).contains(Complex.ofFraction(Fraction.of(-3, 2))));
        assertTrue(Arrays.asList(allRoots).contains(Complex.ONE.opposite()));
        assertTrue(Arrays.asList(allRoots).contains(Complex.ofInt(2)));
        assertTrue(Arrays.asList(allRoots).contains(Complex.ZERO));
    }

    @Test
    public void findRoots2() {
        Polynomial pol = new Polynomial(Complex.ZERO, Complex.ofInt(-43), Complex.ofInt(-6), Complex.ofInt(1));
        LaguerreSolver solver = new LaguerreSolver();
        Complex[] allRoots = solver.findAllRoots(pol);
        System.out.println(Arrays.toString(allRoots));
    }

    @Test
    public void findRoots3() {
        Polynomial pol = new Polynomial(Complex.ofInt(-90), Complex.ofInt(73), Complex.ofInt(-16), Complex.ofInt(1));
        LaguerreSolver solver = new LaguerreSolver();
        Complex[] allRoots = solver.findAllRoots(pol);
        System.out.println(Arrays.toString(allRoots));
    }
}
