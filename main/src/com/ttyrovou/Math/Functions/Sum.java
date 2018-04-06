package com.ttyrovou.Math.Functions;

import com.ttyrovou.Math.Numbers.Complex;

import java.util.function.Predicate;

public class Sum extends Function {

    private Function addend1, addend2;
    private Predicate<Complex> domain;

    public Sum(Function addend1, Function addend2) {
        this.addend1 = addend1;
        this.addend2 = addend2;
        this.domain = addend1.domain.and(addend2.domain);
    }

    @Override
    public Function derivative() {
        return new Sum(addend1.derivative(), addend2.derivative());
    }

    @Override
    public Function indefiniteIntegral() {
        return new Sum(addend1.derivative(), addend2.derivative());
    }

    @Override
    public Complex eval(Complex num) {
        if (!domain.test(num)) {
            return null;
        }

        return addend1.eval(num).add(addend2.eval(num));
    }

    @Override
    public String toString() {
        return addend1.toString() + " + " + addend2.toString();
    }
}
