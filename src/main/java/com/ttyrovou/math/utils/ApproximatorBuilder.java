package com.ttyrovou.math.utils;

import com.ttyrovou.math.numbers.Fraction;

import java.math.BigInteger;

public class ApproximatorBuilder {
    private int maxIterations = 20;
    private BigInteger maxDenominator = BigInteger.ZERO;
    private Fraction epsilon = Fraction.of(1, 100000);

    public ApproximatorBuilder maxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
        return this;
    }

    public ApproximatorBuilder maxDenominator(BigInteger maxDenominator) {
        this.maxDenominator = maxDenominator;
        return this;
    }

    public ApproximatorBuilder maxDenominator(int maxDenominator) {
        this.maxDenominator = BigInteger.valueOf(maxDenominator);
        return this;
    }

    public ApproximatorBuilder epsilon(Fraction epsilon) {
        this.epsilon = epsilon;
        return this;
    }

    public ApproximatorBuilder epsilon(double epsilon) {
        this.epsilon = Fraction.ofDouble(epsilon);
        return this;
    }

    public Approximator build() {
        return new Approximator(maxIterations, maxDenominator, epsilon);
    }
}