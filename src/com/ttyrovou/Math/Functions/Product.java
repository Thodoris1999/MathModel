package com.ttyrovou.Math.Functions;

import com.ttyrovou.Numbers.Complex;

public class Product extends Function {

    private Function factor1, factor2;

    public Product(Function factor1, Function factor2) {
        this.factor1 = factor1;
        this.factor2 = factor2;
    }

    @Override
    public Function derivative() {
        return new Sum(new Product(factor1.derivative(), factor2), new Product(factor1, factor2.derivative()));
    }

    @Override
    public Function indefiniteIntegral() {
        return null;
    }

    @Override
    public Complex eval(Complex num) {
        return factor1.eval(num).multiply(factor2.eval(num));
    }

    @Override
    public String toString() {
        return factor1.toString() + " * " + factor2.toString();
    }
}
