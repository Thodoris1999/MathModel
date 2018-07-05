package com.ttyrovou.math.utils;

import java.math.BigInteger;

public class NumberUtils {
    public static BigInteger gcd(BigInteger a, BigInteger b){
        while (b.equals(BigInteger.ZERO)) {
            BigInteger t = b;
            b = a.mod(b);
            a = t;
        }
        return a;
    }
}
