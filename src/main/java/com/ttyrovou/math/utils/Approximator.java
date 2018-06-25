package com.ttyrovou.math.utils;

import com.ttyrovou.math.numbers.Complex;
import com.ttyrovou.math.numbers.Fraction;

import java.math.BigInteger;
import java.util.ArrayList;

public class Approximator {
    private int maxIterations;
    private BigInteger maxDenominator;
    private Fraction epsilon;

    Approximator(int maxIterations, BigInteger maxDenominator, Fraction epsilon) {
        this.maxIterations = maxIterations;
        this.maxDenominator = maxDenominator;
        this.epsilon = epsilon;
    }

    public Complex approximate(Complex c) {
        return new Complex(approximate(c.getRe()), approximate(c.getIm()));
    }

    public Fraction approximate(Fraction f) {
        if (f.equals(Fraction.ZERO)) return Fraction.ZERO;
        if (f.getB().equals(BigInteger.ONE)) return f;

        Fraction remainder = f.subtract(f.floor()).inverse();
        if (remainder.getB().equals(BigInteger.ONE)) return f.floor().add(remainder.inverse());

        ArrayList<Fraction> xs = new ArrayList<>(maxIterations);
        xs.add(f.floor());
        if (approximationEval(xs).subtract(f).abs().compareTo(epsilon) < 0 ||
                (approximationEval(xs).getB().compareTo(maxDenominator) > 0 && !maxDenominator.equals(BigInteger.ZERO))) {
            return approximationEval(xs);
        }

        xs.add(remainder.floor());
        if (approximationEval(xs).subtract(f).abs().compareTo(epsilon) < 0 ||
                (approximationEval(xs).getB().compareTo(maxDenominator) > 0 && !maxDenominator.equals(BigInteger.ZERO)))
            return approximationEval(xs);

        for (int i = 1; i < maxIterations; i++) {
            remainder = remainder.subtract(remainder.floor()).inverse();
            NumberFormatMode nfm = new NumberFormatModeBuilder().withDecimalMode(NumberFormatMode.DECIMAL_FORM).build();
            if (remainder.getB().equals(BigInteger.ONE)) break;
            xs.add(remainder.floor());
            if (approximationEval(xs).subtract(f).abs().compareTo(epsilon) < 0 ||
                    (approximationEval(xs).getB().compareTo(maxDenominator) > 0 && !maxDenominator.equals(BigInteger.ZERO)))
                return approximationEval(xs);
        }
        return approximationEval(xs);
    }

    private Fraction approximationEval(ArrayList<Fraction> xs) {
        Fraction result = xs.get(xs.size() - 1);
        for (int i = xs.size() - 2; i >= 0; i--) {
            result = result.inverse().add(xs.get(i));
        }
        return result;
    }
}
