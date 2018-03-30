package com.ttyrovou.Functions;

import com.ttyrovou.Numbers.Complex;
import com.ttyrovou.Numbers.Fraction;

public class Polynomial extends Function{

    //index 0 is constant, index one is coefficient of x etc
    private Complex[] coefficients;

    public Polynomial(Complex... coefficients) {
        this.coefficients = coefficients;
    }

    @Override
    Function derivative() {
        Complex[] newCoefficients = new Complex[coefficients.length - 1];
        for (int i = 0; i < newCoefficients.length; i++) {
            newCoefficients[i] = coefficients[i + 1].multiply(Complex.from(i + 1));
        }

        return new Polynomial(newCoefficients);
    }

    @Override
    Function indefiniteIntegral() {
        Complex[] newCoefficients = new Complex[coefficients.length + 1];
        newCoefficients[0] = Complex.ZERO;
        for (int i = 1; i < newCoefficients.length; i++) {
            newCoefficients[i] = coefficients[i - 1].divide(Complex.from(i));
        }

        return new Polynomial(newCoefficients);
    }

    @Override
    Complex eval(Complex num) {
        Complex sum = Complex.ZERO;

        for (int i = 0; i < coefficients.length; i++) {
            sum = sum.add(coefficients[i].toThe(i));
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
                            .append(coefficients[i].toString())
                            .append(")")
                            .append("x^")
                            .append(i);
                } else {
                    builder.append((coefficients[i].getRe().signum() > 0 || coefficients[i].getIm().signum() > 0) ? "+" : "")
                            .append(coefficients[i].toString())
                            .append("x^")
                            .append(i);
                }
            } else if(i == 1) {
                if (!(coefficients[i].getIm().equals(Fraction.ZERO) || coefficients[i].getRe().equals(Fraction.ZERO))) {
                    builder.append("+(")
                            .append(coefficients[i].toString())
                            .append(")")
                            .append("x");
                } else {
                    builder.append((coefficients[i].getRe().signum() > 0 || coefficients[i].getIm().signum() > 0)  ? "+" : "")
                            .append(coefficients[i].toString())
                            .append("x");
                }
            } else if(i == coefficients.length - 1) {
                builder.append("x^")
                        .append(i);
            } else {
                builder.append((coefficients[i].getRe().signum() > 0 ||
                        (coefficients[i].getRe().signum() == 0 || coefficients[i].getIm().signum() >= 0))  ? "+" : "")
                        .append(coefficients[i].toString());
            }
        }
        return builder.toString();
    }
}
