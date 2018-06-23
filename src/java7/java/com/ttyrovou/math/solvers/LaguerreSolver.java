package com.ttyrovou.math.solvers;

import com.ttyrovou.math.functions.Polynomial;
import com.ttyrovou.math.numbers.Complex;
import com.ttyrovou.math.numbers.Fraction;

import java.util.ArrayList;
import java.util.Arrays;

public class LaguerreSolver {
    public Complex[] findAllRoots(Polynomial poly) {
        ArrayList<Complex> ret = new ArrayList<>(poly.degree());
        Complex maybeRoot;
        while (poly.degree() > 2) {
            maybeRoot = findRoot(poly);
            if (maybeRoot == null) break;
            ret.add(maybeRoot);
            poly = poly.divide(new Polynomial(maybeRoot.opposite(), Complex.ofInt(1)));
        }
        if (poly.degree() == 2) {
            ret.addAll(Arrays.asList(quadraticRoots(poly)));
        } else if (poly.degree() == 1) {
            ret.add(linearRoot(poly));
        }
        return ret.toArray(new Complex[0]);
    }

    public Complex findRoot(Polynomial poly) {
        int iterations = 50;;
        Complex guess = Complex.ZERO;

        Polynomial firstDer = poly.derivative();
        Polynomial secondDer = firstDer.derivative();

        for (int i = 0; i < iterations; i++) {
            if (isSmall(poly.eval(guess))) {
                return guess.approximate();
            }
            Complex g = firstDer.eval(guess).divide(poly.eval(guess));
            Complex h = g.multiply(g).subtract(secondDer.eval(guess).divide(poly.eval(guess)));
            Complex enu = Complex.ofInt(poly.degree());
            Complex fact1 = Complex.ofInt(poly.degree() - 1);
            Complex fact2 = Complex.ofInt(poly.degree()).multiply(h).subtract(g.multiply(g));
            Complex sqrt = fact1.multiply(fact2).sqrt();
            Complex denom1 = g.add(sqrt);
            Complex denom2 = g.subtract(sqrt);
            Complex a = enu.divide(denom1.abs().compareTo(denom2.abs()) > 0 ? denom1 : denom2);
            guess = guess.subtract(a);
        }
        return null;
    }

    public Complex[] quadraticRoots(Polynomial poly) {
        Complex[] ret = new Complex[2];
        Complex c = poly.getCoefficients()[0];
        Complex b = poly.getCoefficients()[1];
        Complex a = poly.getCoefficients()[2];

        Complex root = b.multiply(b).subtract(a.multiply(c).multiply(Complex.ofInt(4))).sqrt();
        ret[0] = b.opposite().add(root).divide(a.multiply(Complex.ofInt(2)));
        ret[1] = b.opposite().subtract(root).divide(a.multiply(Complex.ofInt(2)));
        return ret;
    }

    public Complex linearRoot(Polynomial poly) {
        return poly.getCoefficients()[0].opposite().divide(poly.getCoefficients()[1]);
    }

    private boolean isSmall(Complex complex) {
        if (complex.equals(Complex.ZERO)) return true;
        Fraction sumSquares = complex.getRe().multiply(complex.getRe()).add(complex.getIm().multiply(complex.getIm()));
        return sumSquares.getB().bitLength() - sumSquares.getA().bitLength() > 25;
    }
}
