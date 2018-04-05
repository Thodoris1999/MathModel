package com.ttyrovou.Math.Functions;

import com.ttyrovou.Numbers.Complex;

public class Composite extends Function {

    private Function outer, inner;

    public Composite(Function outer, Function inner) {
        this.inner = inner;
        this.outer = outer;
    }

    @Override
    public Function derivative() {
        return new Product(inner.derivative(), new Composite(outer.derivative(), inner));
    }

    @Override
    public Function indefiniteIntegral() {
        return null;
    }

    @Override
    public Complex eval(Complex num) {
        return outer.eval(inner.eval(num));
    }
}
