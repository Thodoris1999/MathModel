package com.ttyrovou.examples;

import com.ttyrovou.math.functions.Polynomial;
import com.ttyrovou.math.numbers.Complex;
import com.ttyrovou.math.numbers.Fraction;
import com.ttyrovou.math.solvers.LaguerreSolver;
import com.ttyrovou.math.utils.NumberFormatMode;
import com.ttyrovou.math.utils.NumberFormatModeBuilder;

public class PolynomialExample {
    public static void main(String[] args) {
        Polynomial pol = new Polynomial(Complex.ofInt(-6), Complex.ofInt(-7), Complex.ofInt(1), Complex.ofInt(2));
        System.out.println("Polynomial");
        System.out.println(pol);
        Polynomial der = pol.derivative();
        System.out.println("Derivative");
        System.out.println(der);
        Polynomial integ = pol.indefiniteIntegral();
        System.out.println("Integral");
        System.out.println(integ);
        System.out.println("Value at 1+i");
        Complex num = new Complex(Fraction.ONE, Fraction.ONE);
        System.out.println(pol.eval(num));

        Complex polar = new Complex("2∠1.6");
        System.out.println(polar.getRe().toDouble());

        LaguerreSolver solver = new LaguerreSolver();
        System.out.println("solution");
        System.out.println(solver.findRoot(pol).orElse(Complex.I).toLatex(new NumberFormatModeBuilder().build()));
        Complex[] allRoots = solver.findAllRoots(pol);
        System.out.println("all solutions of " + pol);
        NumberFormatMode formatMode = new NumberFormatModeBuilder().build();
        for (int i = 0; i < allRoots.length; i++) {
            System.out.println("sol " + allRoots[i].toLatex(formatMode));
        }

        //TODO: use variable types for composition, sum etc
        //TODO: use correct variable names
        //TODO: use optional for function evaluation and integration
        //TODO: use different name for number and function functions
    }
}
