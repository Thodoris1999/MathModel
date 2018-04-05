package com.ttyrovou.Math.Functions;

import com.ttyrovou.Math.Numbers.Complex;

public class Constant extends Function {

    private Complex number;

    public Constant(Complex complex) {
        this.number = complex;
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
        return number;
    }

    @Override
    public String toString() {
        return number.toString();
    }
}
