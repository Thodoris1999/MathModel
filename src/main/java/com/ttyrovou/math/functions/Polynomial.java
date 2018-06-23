package com.ttyrovou.math.functions;

import com.ttyrovou.math.numbers.Complex;
import com.ttyrovou.math.numbers.Fraction;

import java.util.function.Predicate;

public class Polynomial extends Function{

    //index 0 is constant, index one is coefficient of x etc
    private Complex[] coefficients;
    private Predicate<Complex> domain;

    public Polynomial(Complex... coefficients) {
        this.coefficients = coefficients;
        this.domain = (Complex num) -> true;
    }

    public int degree() {
        return coefficients.length - 1;
    }

    @Override
    public Polynomial derivative() {
        Complex[] newCoefficients = new Complex[coefficients.length - 1];
        for (int i = 0; i < newCoefficients.length; i++) {
            newCoefficients[i] = coefficients[i + 1].multiply(Complex.ofInt(i + 1));
        }

        return new Polynomial(newCoefficients);
    }

    @Override
    public Polynomial indefiniteIntegral() {
        Complex[] newCoefficients = new Complex[coefficients.length + 1];
        newCoefficients[0] = Complex.ZERO;
        for (int i = 1; i < newCoefficients.length; i++) {
            newCoefficients[i] = coefficients[i - 1].divide(Complex.ofInt(i));
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

    public Polynomial opposite() {
        Complex[] newCoefficients = new Complex[this.degree() + 1];
        for (int i = 0; i < newCoefficients.length; i++) {
            newCoefficients[i] = this.coefficients[i].opposite();
        }
        return new Polynomial(newCoefficients);
    }

    public Polynomial add(Polynomial poly) {
        int diff = this.degree() - poly.degree();
        Complex[] newCoefficients = new Complex[diff > 0 ? this.degree() + 1 : poly.degree() + 1];
        for (int i = 0; i < ((diff > 0) ? poly.degree() + 1 : this.degree() + 1); i++) {
            newCoefficients[i] = this.coefficients[i].add(poly.coefficients[i]);
        }
        for (int i = ((diff > 0) ? poly.degree() + 1 : this.degree() + 1); i < ((diff > 0) ? this.degree() + 1 : poly.degree() + 1); i++) {
            newCoefficients[i] = diff > 0 ? this.coefficients[i] : poly.coefficients[i];
        }
        int numFrontZeros = 0;
        for (int i = newCoefficients.length - 1; i >= 0; i--) {
            if (!newCoefficients[i].equals(Complex.ZERO)) break;
            numFrontZeros++;
        }
        Complex[] newCoefficientsNoFrontZeros = new Complex[newCoefficients.length - numFrontZeros];
        System.arraycopy(newCoefficients, 0, newCoefficientsNoFrontZeros, 0, newCoefficientsNoFrontZeros.length);
        return new Polynomial(newCoefficientsNoFrontZeros);
    }

    public Polynomial subtract(Polynomial poly) {
        return this.add(poly.opposite());
    }

    public Polynomial multiply(Polynomial poly) {
        Complex[] newCoefficients = new Complex[poly.degree() + this.degree() + 1];
        for (int i = 0; i < newCoefficients.length; i++) {
            newCoefficients[i] = Complex.ZERO;
        }
        for (int i = 0; i < poly.degree() + 1; i++) {
            for (int j = 0; j < this.degree() + 1; j++) {
                newCoefficients[i + j] = newCoefficients[i + j].add(poly.coefficients[i].multiply(this.coefficients[j]));
            }
        }
        return new Polynomial(newCoefficients);
    }

    public Polynomial divide(Polynomial poly) {
        Polynomial res = new Polynomial(Complex.ZERO);
        Polynomial dividor = this;
        while (dividor.degree() >= poly.degree()) {
            int resExp = dividor.degree() - poly.degree();
            Complex coefficient = dividor.coefficients[dividor.degree()].divide(poly.coefficients[poly.degree()]);
            Complex[] temp = new Complex[resExp + 1];
            for (int j = 0; j < temp.length; j++) {
                temp[j] = j == resExp ? coefficient : Complex.ZERO;
            }
            Polynomial tempPol = new Polynomial(temp);
            res = res.add(tempPol);
            dividor = dividor.subtract(poly.multiply(tempPol));
        }
        return res;
    }

    public Complex[] getCoefficients() {
        return coefficients;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        
        Polynomial other = (Polynomial) obj;
        if (this.coefficients.length != other.coefficients.length) return false;
        for (int i = 0; i < coefficients.length; i++) {
            if (!this.coefficients[i].equals(other.coefficients[i])) return false;
        }
        return true;
    }
}
