package com.ttyrovou.Functions;

import com.ttyrovou.Numbers.Complex;

public class Constant extends Function {

    private Complex number;

    public Constant(Complex complex) {
        this.number = complex;
    }

    @Override
    Function derivative() {
        return new Constant(Complex.ZERO);
    }

    @Override
    Function indefiniteIntegral() {
        return new Polynomial(Complex.ZERO, number);
    }

    @Override
    Complex eval(Complex num) {
        return number;
    }

    @Override
    public String toString() {
        return number.toString();
    }
}
