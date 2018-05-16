package com.ttyrovou.math.functions;

import com.ttyrovou.math.numbers.Complex;

import java.util.function.Predicate;

public abstract class Function {

    Predicate<Complex> domain;

    abstract public Function derivative();

    abstract public Function indefiniteIntegral();

    abstract public Complex eval(Complex num);

    @Override
    public String toString() {
        return super.toString();
    }
}
