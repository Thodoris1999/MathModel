package com.ttyrovou.math.functions;

import com.ttyrovou.math.numbers.Complex;

import java.util.function.Predicate;

public class Constant extends Function {

    private Complex number;
    private Predicate<Complex> domain;

    public Constant(Complex complex) {
        this.number = complex;
        this.domain = (Complex num) -> true;
    }

    @Override
    public Function derivative() {
        return new Constant(Complex.ZERO);
    }

    @Override
    public Function indefiniteIntegral() {
        return new Polynomial(Complex.ZERO, number);
    }

    @Override
    public Complex eval(Complex num) {
        if (!domain.test(num)) {
            return null;
        }

        return number;
    }

    @Override
    public String toString() {
        return number.toString();
    }
}
