package com.ttyrovou.Utils;

import java.math.BigInteger;

public class NumberUtils {
    public static BigInteger gcd(BigInteger a, BigInteger b){
        if(a.equals(new BigInteger("0")) || b.equals(new BigInteger("0"))) return a.add(b); // base case
        return gcd(b, mod(a, b));
    }

    public static BigInteger mod(BigInteger a, BigInteger b){
        if(a.compareTo(new BigInteger("0")) < 0 && a.compareTo(new BigInteger("0")) < 0){
            return a.abs().mod(b.abs()).negate();
        }else if(a.compareTo(new BigInteger("0")) < 0){
            return a.abs().mod(b).negate();
        }else if(b.compareTo(new BigInteger("0")) < 0){
            return a.mod(b.abs());
        }else{
            return a.mod(b);
        }
    }
}
