package com.ttyrovou.Functions;

import com.ttyrovou.Numbers.Complex;

public abstract class Function {

    abstract Function derivative();

    abstract Function indefiniteIntegral();

    abstract Complex eval(Complex num);

    @Override
    public String toString() {
        return super.toString();
    }
}
