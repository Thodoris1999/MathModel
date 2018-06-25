package com.ttyrovou.math.functions;

import com.ttyrovou.math.numbers.Complex;

import java.util.function.Predicate;

public class Product extends Function {

    private Function factor1, factor2;
    private Predicate<Complex> domain;

    public Product(Function factor1, Function factor2) {
        this.factor1 = factor1;
        this.factor2 = factor2;
        this.domain = factor1.domain.and(factor2.domain);
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
