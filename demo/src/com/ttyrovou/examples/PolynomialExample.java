package com.ttyrovou.examples;

import com.ttyrovou.Math.Functions.Polynomial;
import com.ttyrovou.Math.Numbers.Complex;
import com.ttyrovou.Math.Numbers.Fraction;

public class PolynomialExample {
    public static void main(String[] args) {
        Polynomial pol = new Polynomial(Complex.from(3), Complex.from(5), Complex.from(2));
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

        //TODO: use variable types for composition, sum etc
        //TODO: use correct variable names
        //TODO: use optional for function evaluation and integration
        //TODO: use different name for number and function functions
    }
}
