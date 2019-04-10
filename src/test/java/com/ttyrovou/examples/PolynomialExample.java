package com.ttyrovou.examples;

import com.ttyrovou.math.functions.Polynomial;
import com.ttyrovou.math.matrix.Matrix;
import com.ttyrovou.math.numbers.Complex;
import com.ttyrovou.math.numbers.Fraction;

import java.util.LinkedList;

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

        Complex[][] noSolMatNums = {{Complex.ofInt(2), Complex.ZERO, Complex.ofInt(3), Complex.ofInt(3)},
                {Complex.ofInt(4), Complex.ofInt(-3), Complex.ofInt(7), Complex.ofInt(5)},
                {Complex.ofInt(8), Complex.ofInt(-9), Complex.ofInt(15), Complex.ofInt(10)}};
        Matrix noSolMat = new Matrix(noSolMatNums);
        LinkedList<LinkedList<Complex>> sol = noSolMat.linearSystemSolution();
        System.out.println(sol);

        //TODO: use variable types for composition, sum etc
        //TODO: use correct variable names
        //TODO: use optional for function evaluation and integration
        //TODO: use different name for number and function functions
    }
}
