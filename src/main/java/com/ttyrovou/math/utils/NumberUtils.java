package com.ttyrovou.math.utils;

import java.math.BigInteger;

public class NumberUtils {
    public static BigInteger gcd(BigInteger a, BigInteger b){
        if(b.signum() == 0) return a; // base case
        return gcd(b, a.remainder(b));
    }
}
