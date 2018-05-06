package com.ttyrovou.Math.Functions;

import com.ttyrovou.Math.Numbers.Complex;

import java.util.function.Predicate;

public class Composite extends Function {

    private Function outer, inner;
    private Predicate<Complex> domain;

    public Composite(Function outer, Function inner) {
        this.inner = inner;
        this.outer = outer;
        this.domain = inner.domain.and((Complex num) -> outer.domain.test(inner.eval(num)));
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
        if (!domain.test(num)) {
            return null;
        }

        return outer.eval(inner.eval(num));
    }
}
