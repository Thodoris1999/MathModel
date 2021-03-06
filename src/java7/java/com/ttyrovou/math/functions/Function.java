package com.ttyrovou.math.functions;

import com.ttyrovou.math.numbers.Complex;

public abstract class Function {

    abstract public Function derivative();

    abstract public Function indefiniteIntegral();

    abstract public Complex eval(Complex num);

    @Override
    public String toString() {
        return super.toString();
    }
}
