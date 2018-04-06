package com.ttyrovou.Math.Functions;

import com.ttyrovou.Math.Numbers.Complex;
import com.ttyrovou.Math.Numbers.Fraction;

import java.util.function.Predicate;

public class Polynomial extends Function{

    //index 0 is constant, index one is coefficient of x etc
    private Complex[] coefficients;
    private Predicate<Complex> domain;

    public Polynomial(Complex... coefficients) {
        this.coefficients = coefficients;
        this.domain = (Complex num) -> true;
    }

    @Override
    public Polynomial derivative() {
        Complex[] newCoefficients = new Complex[coefficients.length - 1];
        for (int i = 0; i < newCoefficients.length; i++) {
            newCoefficients[i] = coefficients[i + 1].multiply(Complex.from(i + 1));
        }

        return new Polynomial(newCoefficients);
    }

    @Override
    public Polynomial indefiniteIntegral() {
        Complex[] newCoefficients = new Complex[coefficients.length + 1];
        newCoefficients[0] = Complex.ZERO;
        for (int i = 1; i < newCoefficients.length; i++) {
            newCoefficients[i] = coefficients[i - 1].divide(Complex.from(i));
        }

        return new Polynomial(newCoefficients);
    }

    @Override
    public Complex eval(Complex num) {
        if (!domain.test(num)) {
            return null;
        }

        Complex sum = Complex.ZERO;
        for (int i = 0; i < coefficients.length; i++) {
            sum = sum.add(coefficients[i].multiply(num.toThe(i)));
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = coefficients.length - 1; i >= 0; i--) {
            if(coefficients[i].equals(Complex.ZERO) && i != coefficients.length - 1){
                continue;
            }
            if (i != 0 && i != coefficients.length - 1 && i != 1) {
                if (!(coefficients[i].getIm().equals(Fraction.ZERO) || coefficients[i].getRe().equals(Fraction.ZERO))) {
                    builder.append("+(")
                            .append(coefficients[i])
                            .append(")")
                            .append("x^")
                            .append(i);
                } else {
                    builder.append((coefficients[i].getRe().signum() > 0 || coefficients[i].getIm().signum() > 0) ? "+" : "")
                            .append(coefficients[i])
                            .append("x^")
                            .append(i);
                }
            } else if(i == 1) {
                if (!(coefficients[i].getIm().equals(Fraction.ZERO) || coefficients[i].getRe().equals(Fraction.ZERO))) {
                    builder.append("+(")
                            .append(coefficients[i])
                            .append(")")
                            .append("x");
                } else {
                    builder.append((coefficients[i].getRe().signum() > 0 || coefficients[i].getIm().signum() > 0)  ? "+" : "")
                            .append(coefficients[i])
                            .append("x");
                }
            } else if(i == coefficients.length - 1) {
                builder.append(coefficients[i])
                        .append("x^")
                        .append(i);
            } else {
                builder.append((coefficients[i].getRe().signum() > 0 ||
                        (coefficients[i].getRe().signum() == 0 || coefficients[i].getIm().signum() >= 0))  ? "+" : "")
                        .append(coefficients[i]);
            }
        }
        return builder.toString();
    }
}
