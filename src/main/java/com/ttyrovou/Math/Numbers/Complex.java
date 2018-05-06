package com.ttyrovou.Math.Numbers;

public class Complex {
    public static final Complex ONE = new Complex(Fraction.ONE, Fraction.ZERO);
    public static final Complex ZERO = new Complex(Fraction.ZERO, Fraction.ZERO);

    private Fraction re, im;

    public Complex(Fraction re, Fraction im) {
        this.re = re;
        this.im = im;
    }

    public Complex (String s){
        if(s.contains("i")){ //NON-NLS
            if(s.indexOf("i") != s.length() - 1){
                throw new NumberFormatException("Multiple 'i's or text after 'i'");
            }
            //find complex part separator
            char[] charArray = s.toCharArray();
            int separatorIndex = 0;
            for (int i = charArray.length - 1; i > 0 ; i--) {
                if ((charArray[i] == '+' || charArray[i] == '-') && charArray[i - 1] != '/'){
                    separatorIndex = i;
                    break;
                }
            }
            if(separatorIndex == 0){
                this.re = Fraction.ZERO;
            } else {
                this.re = new Fraction(s.substring(0, separatorIndex));
            }
            this.im = parseImPart(s.substring(separatorIndex));
        }else{
            this.im = Fraction.ZERO;
            this.re = new Fraction(s);
        }
        if(this.im == null || this.re == null){
            throw new NumberFormatException("Complex number not formatted properly.");
        }
    }

    public static Complex from(int a) {
        return new Complex(Fraction.from(a), Fraction.ZERO);
    }

    private Fraction parseImPart(String s) {
        if(s.length() == 1 || s.charAt(s.length() - 2) == '+'){
            return Fraction.ONE;
        }else if(s.charAt(s.length() - 2) == '-'){
            return Fraction.from(-1);
        }else{
            return new Fraction(s.substring(0, s.length() - 1));
        }
    }

    public Complex add(Complex complex){
        return new Complex(this.re.add(complex.getRe()), this.im.add(complex.getIm()));
    }

    public Complex subtract(Complex complex){
        return this.add(complex.opposite());
    }

    public Complex multiply(Complex complex){
        return new Complex(this.getRe().multiply(complex.getRe())
                .subtract(this.getIm().multiply(complex.getIm())),
                this.getRe().multiply(complex.getIm())
                        .add(this.getIm().multiply(complex.getRe())));
    }

    public Complex divide(Complex complex){
        return this.multiply(complex.inverse());
    }

    public Complex opposite(){
        return new Complex(re.opposite(), im.opposite());
    }

    public Complex inverse(){
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

    public boolean equals(Complex complex) {
        return im.equals(complex.getIm()) && re.equals(complex.getRe());
    }

    @Override
    public String toString() {
        if (im.equals(Fraction.ZERO)) {
            return re.toString();
        } else if (re.equals(Fraction.ZERO)) {
            if(im.equals(Fraction.ONE)){
                return "i";
            }else if(im.equals(Fraction.from(-1))){
                return "-i";
            }else if(im.equals(Fraction.ZERO)){
                return "0";
            }
            return im.toString() + "i";
        } else {
            if(im.equals(Fraction.ONE)){
                return re.toString() + "+i";
            }else if(im.equals(Fraction.from(-1))){
                return re.toString() + "-i";
            }
            return re.toString() + (im.toString().startsWith("-") ? "" : "+") + im.toString() + "i";
        }
    }

    public String toLatex(boolean decimal) {
        if(im.equals(Fraction.ZERO)){
            return re.toLatex(decimal);
        } else if (re.equals(Fraction.ZERO)) {
            return imLatex(im, decimal);
        }else{
            return re.toLatex(decimal) + (im.signum() > 0 ? "+" : "") + imLatex(im, decimal);
        }
    }

    public String imLatex (Fraction im, boolean decimal) {
        if (im.equals(Fraction.ONE)) {
            return "i";
        } else if (im.equals(Fraction.from(-1))) {
            return "-i";
        } else {
            return im.toLatex(decimal) + "i";
        }
    }

    public Fraction getRe() { return re; }

    public Fraction getIm() {
        return im;
    }
}
