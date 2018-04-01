package com.ttyrovou.Functions;

import com.ttyrovou.Numbers.Complex;

public class Composite extends Function {

    private Function outer, inner;

    public Composite(Function outer, Function inner) {
        this.inner = inner;
        this.outer = outer;
    }

    @Override
    Function derivative() {
        return new Product(inner.derivative(), new Composite(outer.derivative(), inner));
    }

    @Override
    Function indefiniteIntegral() {
        return null;
    }

    @Override
    Complex eval(Complex num) {
        return outer.eval(inner.eval(num));
    }
}
