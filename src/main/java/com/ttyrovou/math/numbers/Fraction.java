package com.ttyrovou.math.numbers;

import com.ttyrovou.math.utils.NumberFormatMode;
import com.ttyrovou.math.utils.NumberFormatModeBuilder;
import com.ttyrovou.math.utils.NumberUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fraction implements Comparable<Fraction> {
    public static final Fraction ONE = new Fraction(BigInteger.ONE, BigInteger.ONE);
    public static final Fraction ZERO = new Fraction(BigInteger.ZERO, BigInteger.ONE);

    private static final int NUMERATOR_GROUP = 1;
    private static final int DENOMINATOR_GROUP = 3;
    private static final Pattern fractionPattern = Pattern.compile("^([+-]?\\d+)(\\/([+-]?\\d+))?$");
    private static final Pattern decimalPattern = Pattern.compile("^\\d+\\.\\d+$");

    private BigInteger a, b;

    public Fraction(BigInteger a, BigInteger b) {
        if (b.equals(new BigInteger("0"))) {
            throw new ArithmeticException("Fraction cannot have zero as denominator");
        }
        this.a = a;
        this.b = b;
    }

    public Fraction(Fraction other) {
        this.a = other.getA();
        this.b = other.getB();
    }

    public Fraction(BigInteger a) {
        this.a = a;
        this.b = BigInteger.ONE;
    }

    public Fraction(String s) {
        Matcher fractionMatcher = fractionPattern.matcher(s);
        Matcher decimalMatcher = decimalPattern.matcher(s);
        if (fractionMatcher.matches()) {
            this.a = new BigInteger(s.substring(fractionMatcher.start(NUMERATOR_GROUP), fractionMatcher.end(NUMERATOR_GROUP)));
            if (fractionMatcher.start(DENOMINATOR_GROUP) > 0) {
                this.b = new BigInteger(s.substring(fractionMatcher.start(DENOMINATOR_GROUP), fractionMatcher.end(DENOMINATOR_GROUP)));
            } else {
                this.b = BigInteger.ONE;
            }
        } else if (decimalMatcher.matches()) {
            this.a = Fraction.ofDecimalString(s).getA();
            this.b = Fraction.ofDecimalString(s).getB();
        } else {
            throw new NumberFormatException("Fraction not formatted properly.");
        }

        if (b.equals(new BigInteger("0"))) {
            throw new ArithmeticException("Fraction cannot have zero as denominator");
        }
    }

    public static Fraction of(int a, int b) {
        return new Fraction(BigInteger.valueOf(a), BigInteger.valueOf(b));
    }

    public static Fraction ofInt(int a) {
        return new Fraction(BigInteger.valueOf(a), BigInteger.ONE);
    }

    public static Fraction ofDouble(double a) {
        String dec = Double.toString(a);
        return Fraction.ofDecimalString(dec);
    }

    private static Fraction ofDecimalString(String dec) {
        String[] splited = dec.split("\\.");
        BigInteger numerator = new BigInteger(dec.replace(".", ""));
        StringBuilder decString = new StringBuilder("1");
        for (int i = 0; i < splited[1].length(); i++) {
            decString.append('0');
        }
        BigInteger denominator = new BigInteger(decString.toString());
        return new Fraction(numerator, denominator).simplified();
    }

    public Fraction add(Fraction fraction) {
        BigInteger ekp = this.getB().multiply(fraction.getB()).divide(NumberUtils.gcd(this.getB(), fraction.getB()));
        Fraction result = new Fraction(this.getA()
                .multiply(ekp.divide(this.getB()))
                .add(fraction.getA().multiply(ekp.divide(fraction.getB()))), ekp);
        return result.simplified();
    }

    public Fraction subtract(Fraction fraction) {
        return this.add(fraction.opposite());
    }

    public Fraction multiply(Fraction fraction) {
        Fraction result = new Fraction(this.getA().multiply(fraction.getA()),
                this.getB().multiply(fraction.getB()));
        return result.simplified();
    }

    public Fraction divide(Fraction fraction) {
        return this.multiply(fraction.inverse());
    }

    private Fraction simplified() {
        BigInteger gcd = NumberUtils.gcd(getA(), getB());
        return new Fraction(getA().divide(gcd), getB().divide(gcd));
    }

    public Fraction opposite() {
        return new Fraction(getA().multiply(new BigInteger("-1")), getB());
    }

    public Fraction inverse() {
        if (a.equals(new BigInteger("0"))) {
            throw new ArithmeticException("Atttempt to Invert zero");
        }
        return new Fraction(getB(), getA());
    }

    public Fraction abs() {
        return new Fraction(a.abs(), b.abs());
    }

    public Fraction toThe(int a) {
        Fraction returnVal = Fraction.ONE;
        if (a >= 0) {
            for (int i = 0; i < a; i++) {
                returnVal.multiply(this);
            }
            return returnVal;
        } else {
            return this.inverse().toThe(-a);
        }
    }

    public int signum() {
        return a.signum() * b.signum();
    }

    @Override
    public String toString() {
        if (b.equals(new BigInteger("1"))) {
            return a.toString();
        } else {
            return a + "/" + b;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        Fraction other = (Fraction) obj;
        return a.multiply(other.getB()).equals(b.multiply(other.getA()));
    }

    @Override
    public int compareTo(Fraction fraction) {
        return a.multiply(fraction.getB()).subtract(b.multiply(fraction.getA())).intValue();
    }

    public String toLatex(NumberFormatMode formatMode) {
        if (b.equals(new BigInteger("1"))) {
            return a.toString();
        } else if (a.equals(new BigInteger("0"))) {
            return "0";
        } else {
            switch (formatMode.getDecimalMode()) {
                case NumberFormatMode.DECIMAL_FORM:
                    BigDecimal decA = new BigDecimal(a);
                    BigDecimal decB = new BigDecimal(b);
                    MathContext mc = new MathContext(formatMode.getDecimalAccuracy(), RoundingMode.HALF_UP);
                    BigDecimal num = decA.divide(decB, mc);
                    return num.toString();
                case NumberFormatMode.FRACTIONAL_FORM:
                    if (this.signum() == 1) {
                        return "\\frac{" + a.abs().toString() + "}{" + b.abs().toString() + "}";
                    } else {
                        return "-\\frac{" + a.abs().toString() + "}{" + b.abs().toString() + "}";
                    }
                case NumberFormatMode.AUTO:
                    if (Math.max(a.toString().length(), b.toString().length()) > formatMode.getFractionThreshold()) {
                        NumberFormatMode mode = new NumberFormatModeBuilder().withDecimalMode(NumberFormatMode.DECIMAL_FORM)
                                .setDecimalAccuracy(formatMode.getDecimalAccuracy())
                                .build();
                        return this.toLatex(mode);
                    } else {
                        return this.toLatex(new NumberFormatModeBuilder().withDecimalMode(NumberFormatMode.FRACTIONAL_FORM).build());
                    }
                default:
                    throw new IllegalArgumentException("Unknown mode " + formatMode.getDecimalMode());
            }
        }
    }

    public double toDouble() {
        return a.doubleValue() / b.doubleValue();
    }

    public BigInteger getA() {
        return a;
    }

    public BigInteger getB() {
        return b;
    }
}
