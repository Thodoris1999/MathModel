package com.ttyrovou.Math.Functions;

import com.ttyrovou.Numbers.Complex;

public class Fraction extends Function {

    private Function numerator, denominator;

    public Fraction(Function numerator, Function denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public Function derivative() {
        return new Fraction(new Sum(new Product(numerator.derivative(), denominator),
                new Product(new Constant(Complex.from(-1)), new Product(numerator, denominator.derivative()))), //derivative numerator
                new Product(denominator, denominator)); //derivative denominator
    }

    @Override
    public Function indefiniteIntegral() {
        return null;
    }

    @Override
    public Complex eval(Complex num) {
        return numerator.eval(num).divide(denominator.eval(num));
    }

    @Override
    public String toString() {
        return numerator.toString() + " / " + denominator.toString();
    }
}
