package com.ttyrovou.Numbers;

import com.ttyrovou.Utils.NumberUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class Fraction {
    public static final Fraction ONE = new Fraction(BigInteger.ONE, BigInteger.ONE);
    public static final Fraction ZERO = new Fraction(BigInteger.ZERO, BigInteger.ONE);

    private BigInteger a, b;

    public Fraction(BigInteger a, BigInteger b) {
        if (b.equals(new BigInteger("0"))) {
            throw new ArithmeticException("Fraction cannot have zero as denominator");
        }
        this.a = a;
        this.b = b;
    }

    public Fraction(BigInteger a) {
        this.a = a;
        this.b = BigInteger.ONE;
    }

    public Fraction(String s){
        if(s.contains("/")){
            String[] splited = s.split("/");
            this.a = new BigInteger(splited[0]);
            this.b = new BigInteger(splited[1]);
        }else if(s.contains(".")){
            String[] splited = s.split("\\.");
            if(splited.length > 2){
                throw new NumberFormatException("Decimal containing \'.\' multiple times.");
            }
            this.a = new BigInteger(s.replace(".", ""));
            this.b = new BigInteger(Integer.toString((int) Math.pow(10, splited[1].length())));
            this.a = this.simplified().getA();
            this.b = this.simplified().getB();
        }else if(s.contains(",")){
            String[] splited = s.split(",");
            if(splited.length > 2){
                throw new NumberFormatException("Decimal containing \',\' multiple times.");
            }
            this.a = new BigInteger(s.replace(",", ""));
            this.b = new BigInteger(Integer.toString((int) Math.pow(10, splited[1].length())));
            this.a = this.simplified().getA();
            this.b = this.simplified().getB();
        } else{
            this.a = new BigInteger(s);
            this.b = new BigInteger("1");
        }

        if (b.equals(new BigInteger("0"))) {
            throw new ArithmeticException("Fraction cannot have zero as denominator");
        }
        if(this.a == null || this.b == null){
            throw new NumberFormatException("Fraction not formatted correctly.");
        }
    }

    public static Fraction from(int a) {
        return new Fraction(BigInteger.valueOf(a), BigInteger.ONE);
    }

    public BigInteger getA() {
        return a;
    }

    public BigInteger getB() {
        return b;
    }

    public Fraction add(Fraction fraction){
        BigInteger ekp = this.getB().multiply(fraction.getB()).divide(NumberUtils.gcd(this.getB(), fraction.getB()));
        Fraction result = new Fraction(this.getA()
                .multiply(ekp.divide(this.getB()))
                .add(fraction.getA().multiply(ekp.divide(fraction.getB()))), ekp);
        return result.simplified();
    }

    public Fraction subtract(Fraction fraction){
        return this.add(fraction.opposite());
    }

    public Fraction multiply(Fraction fraction){
        Fraction result = new Fraction(this.getA().multiply(fraction.getA()),
                this.getB().multiply(fraction.getB()));
        return result.simplified();
    }

    public Fraction divide(Fraction fraction){
        return this.multiply(fraction.inverse());
    }

    private Fraction simplified() {
        BigInteger gcd = NumberUtils.gcd(getA(), getB());
        return new Fraction(getA().divide(gcd), getB().divide(gcd));
    }

    public Fraction opposite(){
        return new Fraction(getA().multiply(new BigInteger("-1")), getB());
    }

    public Fraction inverse(){
        if(a.equals(new BigInteger("0"))){
            throw new ArithmeticException("Atttempt to Invert zero");
        }
        return new Fraction(getB(), getA());
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
        if(b.equals(new BigInteger("1"))){
            return a.toString();
        } else {
            return a + "/" + b;
        }
    }

    public boolean equals(Fraction fraction) {
        if(a.equals(new BigInteger("0")) && fraction.getA().equals(new BigInteger("0"))) {
            return true;
        } else {
            Fraction thisSimplified = this.simplified();
            Fraction fractionSimplified = fraction.simplified();
            return thisSimplified.getB().equals(fractionSimplified.getB()) &&
                    thisSimplified.getA().equals(fractionSimplified.getA());
        }
    }

    public String toLatex(boolean decimal) {
        if(b.equals(new BigInteger("1"))){
            return a.toString();
        } else if (a.equals(new BigInteger("0"))){
            return "0";
        }else{
            if(decimal){
                BigDecimal decA = new BigDecimal(a);
                BigDecimal decB = new BigDecimal(b);
                MathContext mc = new MathContext(5, RoundingMode.HALF_UP);
                BigDecimal num = decA.divide(decB, mc);
                return num.toString();
            }else {
                if (this.signum() == 1) {
                    return "\\frac{" + a.abs().toString() + "}{" + b.abs().toString() + "}";
                } else {
                    return "-\\frac{" + a.abs().toString() + "}{" + b.abs().toString() + "}";
                }
            }
        }
    }
}
