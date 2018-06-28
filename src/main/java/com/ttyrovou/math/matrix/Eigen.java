package com.ttyrovou.math.matrix;

import com.ttyrovou.math.numbers.Complex;

import java.util.LinkedList;

public class Eigen {

    private Complex eigenvalue;
    private LinkedList<LinkedList<Complex>> eigenspace;
    private boolean exact;
    private int algebraicMultiplicity;

    public Eigen(Complex eigenvalue, LinkedList<LinkedList<Complex>> eigenspace, boolean exact) {
        this.eigenvalue = eigenvalue;
        this.eigenspace = eigenspace;
        this.exact = exact;
        this.algebraicMultiplicity = 1;
    }

    public Complex getEigenvalue() {
        return eigenvalue;
    }

    public LinkedList<LinkedList<Complex>> getEigenspace() {
        return eigenspace;
    }

    public boolean isExact() {
        return exact;
    }

    public int getAlgebraicMultiplicity() {
        return algebraicMultiplicity;
    }

    public void incrementAlgebraicMultiplicity() {
        algebraicMultiplicity++;
    }
}
