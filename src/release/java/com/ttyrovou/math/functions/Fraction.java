package com.ttyrovou.math.functions;

import com.ttyrovou.math.numbers.Complex;

import java.util.function.Predicate;

public class Fraction extends Function {

    private Function numerator, denominator;
    private Predicate<Complex> domain;

    public Fraction(Function numerator, Function denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        this.domain = numerator.domain
                .and(denominator.domain)
                .and((Complex num) -> !denominator.eval(num).equals(Complex.ZERO));
    }

    @Override
    public Function derivative() {
        return new Fraction(new Sum(new Product(numerator.derivative(), denominator),
                new Product(new Constant(Complex.ofInt(-1)), new Product(numerator, denominator.derivative()))), //derivative numerator
                new Product(denominator, denominator)); //derivative denominator
    }

    @Override
    public Function indefiniteIntegral() {
        return null;
    }

    @Override
    public Complex eval(Complex num) {
        if (!domain.test(num)) {
            return null;
        }
        return numerator.eval(num).divide(denominator.eval(num));
    }

    @Override
    public String toString() {
        return numerator.toString() + " / " + denominator.toString();
    }
}
