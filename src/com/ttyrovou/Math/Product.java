package com.ttyrovou.Functions;

import com.ttyrovou.Numbers.Complex;

public class Product extends Function {

    private Function factor1, factor2;

    public Product(Function factor1, Function factor2) {
        this.factor1 = factor1;
        this.factor2 = factor2;
    }

    @Override
    Function derivative() {
        return new Sum(new Product(factor1.derivative(), factor2), new Product(factor1, factor2.derivative()));
    }

    @Override
    Function indefiniteIntegral() {
        return null;
    }

    @Override
    Complex eval(Complex num) {
        return factor1.eval(num).multiply(factor2.eval(num));
    }
}
