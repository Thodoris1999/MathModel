package com.ttyrovou.math.matrix;

import com.ttyrovou.math.numbers.Complex;

import java.util.LinkedList;

public class Eigen {

    private Complex eigenValue;
    private LinkedList<LinkedList<Complex>> eigenspace;
    private boolean exact;

    public Eigen(Complex eigenValue, LinkedList<LinkedList<Complex>> eigenspace, boolean exact) {
        this.eigenValue = eigenValue;
        this.eigenspace = eigenspace;
        this.exact = exact;
    }

    public Complex getEigenValue() {
        return eigenValue;
    }

    public LinkedList<LinkedList<Complex>> getEigenspace() {
        return eigenspace;
    }

    public boolean isExact() {
        return exact;
    }
}
