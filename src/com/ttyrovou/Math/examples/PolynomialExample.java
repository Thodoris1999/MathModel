package com.ttyrovou.Math.examples;

import com.ttyrovou.Math.Functions.Polynomial;
import com.ttyrovou.Numbers.Complex;

public class PolynomialExample {
    public static void main(String[] args) {
        Polynomial pol = new Polynomial(Complex.from(3), Complex.from(5), Complex.from(2));
        System.out.println("Polynomial:");
        System.out.println(pol);
        Polynomial der = pol.derivative();
        System.out.println("Derivative");
        System.out.println(der);
        Polynomial integ = pol.indefiniteIntegral();
        System.out.println("Integral");
        System.out.println(integ);
    }
}
