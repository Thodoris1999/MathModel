package com.ttyrovou.math.numbers;

import com.ttyrovou.math.utils.NumberFormatMode;

public class Complex {
    public static final Complex ONE = new Complex(Fraction.ONE, Fraction.ZERO);
    public static final Complex ZERO = new Complex(Fraction.ZERO, Fraction.ZERO);
    public static final Complex I = new Complex(Fraction.ZERO, Fraction.ONE);

    private Fraction re, im;

    public Complex(Fraction re, Fraction im) {
        this.re = re;
        this.im = im;
    }

    public Complex(String s) {
        if (s.contains("i")) { //NON-NLS
            if (s.indexOf("i") != s.length() - 1) {
                throw new NumberFormatException("Multiple 'i's or text after 'i'");
            }
            //find complex part separator
            char[] charArray = s.toCharArray();
            int separatorIndex = 0;
            for (int i = charArray.length - 1; i > 0; i--) {
                if ((charArray[i] == '+' || charArray[i] == '-') && charArray[i - 1] != '/') {
                    separatorIndex = i;
                    break;
                }
            }
            if (separatorIndex == 0) {
                this.re = Fraction.ZERO;
            } else {
                this.re = new Fraction(s.substring(0, separatorIndex));
            }
            this.im = parseImPart(s.substring(separatorIndex));
        } else if (s.contains("∠")) {
            String[] splited = s.split("∠");
            if (splited.length == 2) {
                Fraction radius = new Fraction(splited[0]);
                Fraction angle = new Fraction(splited[1]);
                System.out.println(Math.cos(angle.toDouble()));
                this.re = radius.multiply(Fraction.ofDouble(Math.cos(angle.toDouble())));
                this.im = radius.multiply(Fraction.ofDouble(Math.sin(angle.toDouble())));
            } else {
                throw new NumberFormatException("Wrong use of \"∠\"");
            }
        } else {
            this.im = Fraction.ZERO;
            this.re = new Fraction(s);
        }
    }

    public static Complex ofPolar(Fraction radius, Fraction angle) {
        Fraction real = radius.multiply(Fraction.ofDouble(Math.cos(angle.toDouble())));
        Fraction imag = radius.multiply(Fraction.ofDouble(Math.sin(angle.toDouble())));
        return new Complex(real, imag);
    }

    public static Complex ofFraction(Fraction fraction) {
        return new Complex(fraction, Fraction.ZERO);
    }

    public static Complex ofInt(int a) {
        return new Complex(Fraction.ofInt(a), Fraction.ZERO);
    }

    private Fraction parseImPart(String s) {
        if (s.length() == 1 || s.charAt(s.length() - 2) == '+') {
            return Fraction.ONE;
        } else if (s.charAt(s.length() - 2) == '-') {
            return Fraction.ofInt(-1);
        } else {
            return new Fraction(s.substring(0, s.length() - 1));
        }
    }

    public Complex add(Complex complex) {
        return new Complex(this.re.add(complex.getRe()), this.im.add(complex.getIm()));
    }

    public Complex subtract(Complex complex) {
        return this.add(complex.opposite());
    }

    public Complex multiply(Complex complex) {
        return new Complex(this.getRe().multiply(complex.getRe())
                .subtract(this.getIm().multiply(complex.getIm())),
                this.getRe().multiply(complex.getIm())
                        .add(this.getIm().multiply(complex.getRe())));
    }

    public Complex divide(Complex complex) {
        return this.multiply(complex.inverse());
    }

    public Complex opposite() {
        return new Complex(re.opposite(), im.opposite());
    }

    public Complex inverse() {
        Fraction realPart = re.divide(re.multiply(re).add(im.multiply(im)));
        Fraction imPart = im.divide(re.multiply(re).add(im.multiply(im))).opposite();
        return new Complex(realPart, imPart);
    }

    public Complex toThe(int a) {
        Complex returnVal = Complex.ONE;
        if (a >= 0) {
            for (int i = 0; i < a; i++) {
                returnVal = returnVal.multiply(this);
            }
            return returnVal;
        } else {
            return this.inverse().toThe(-a);
        }
    }

    public Complex sqrt() {
        if (this.im.equals(Fraction.ZERO)) return new Complex(this.re.sqrt(), Fraction.ZERO);
        Fraction real = this.abs().add(re).divide(Fraction.ofInt(2)).sqrt();
        Fraction imag = this.abs().subtract(re).divide(Fraction.ofInt(2)).sqrt().multiply(Fraction.ofInt(im.signum()));
        return new Complex(real, imag);
    }

    public Complex approximate() {
        return approximate(20);
    }

    public Complex approximate(int maxIterations) {
        return new Complex(re.approximate(maxIterations), im.approximate(maxIterations));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        Complex other = (Complex) obj;
        return im.equals(other.getIm()) && re.equals(other.getRe());
    }

    @Override
    public String toString() {
        if (im.equals(Fraction.ZERO)) {
            return re.toString();
        } else if (re.equals(Fraction.ZERO)) {
            if (im.equals(Fraction.ONE)) {
                return "i";
            } else if (im.equals(Fraction.ofInt(-1))) {
                return "-i";
            } else if (im.equals(Fraction.ZERO)) {
                return "0";
            }
            return im.toString() + "i";
        } else {
            if (im.equals(Fraction.ONE)) {
                return re.toString() + "+i";
            } else if (im.equals(Fraction.ofInt(-1))) {
                return re.toString() + "-i";
            }
            return re.toString() + (im.toString().startsWith("-") ? "" : "+") + im.toString() + "i";
        }
    }

    public String toLatex(NumberFormatMode formatMode) {
        switch (formatMode.getComplexMode()) {
            case NumberFormatMode.CARTESIAN_FORM:
                if (im.equals(Fraction.ZERO)) {
                    return re.toLatex(formatMode);
                } else if (re.equals(Fraction.ZERO)) {
                    return imLatex(im, formatMode);
                } else {
                    return re.toLatex(formatMode) + (im.signum() > 0 ? "+" : "") + imLatex(im, formatMode);
                }
            case NumberFormatMode.POLAR_FORM:
                if (getAngle().equals(Fraction.ZERO)) {
                    return re.toLatex(formatMode);
                }
                return abs().toLatex(formatMode) + "∠" + getAngle().toLatex(formatMode);
            default:
                throw new IllegalArgumentException("Unknown mode " + formatMode.getComplexMode());
        }
    }

    private String imLatex(Fraction im, NumberFormatMode formatMode) {
        if (im.equals(Fraction.ONE)) {
            return "i";
        } else if (im.equals(Fraction.ofInt(-1))) {
            return "-i";
        } else {
            return im.toLatex(formatMode) + "i";
        }
    }

    public Fraction getRe() {
        return re;
    }

    public Fraction getIm() {
        return im;
    }

    public Fraction abs() {
        return re.multiply(re).add(im.multiply(im)).sqrt();
    }

    public Fraction getAngle() {
        if (re.signum() > 0) {
            return Fraction.ofDouble(Math.atan(im.divide(re).toDouble()));
        } else if (re.signum() < 0 && im.signum() >= 0) {
            return Fraction.ofDouble(Math.atan(im.divide(re).toDouble()) + Math.PI);
        } else if (re.signum() < 0 && im.signum() < 0) {
            return Fraction.ofDouble(Math.atan(im.divide(re).toDouble()) - Math.PI);
        } else if (re.signum() == 0 && im.signum() > 0) {
            return Fraction.ofDouble(Math.PI / 2);
        } else if (re.signum() == 0 && im.signum() < 0) {
            return Fraction.ofDouble(-Math.PI / 2);
        } else {
            return Fraction.ZERO;
        }
    }
}
