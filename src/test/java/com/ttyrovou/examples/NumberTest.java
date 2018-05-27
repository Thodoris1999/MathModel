package com.ttyrovou.examples;

import com.ttyrovou.math.matrix.Matrix;
import com.ttyrovou.math.numbers.Complex;
import com.ttyrovou.math.numbers.Fraction;
import org.junit.Assert;
import org.junit.Test;

public class NumberTest {
    @Test
    public void copmlexAddition() {
        Complex num1 = new Complex(Fraction.ofDouble(3.2), Fraction.ofDouble(-1.1));
        Complex num2 = new Complex(Fraction.ofDouble(5.3), Fraction.ofDouble(2.9));
        Complex sum = num1.add(num2);
        Assert.assertEquals(sum, new Complex(Fraction.ofDouble(8.5), Fraction.ofDouble(1.8)));
    }

    @Test
    public void MatrixAddition() {
        Complex[][] co1 = {{Complex.ofInt(5)}, {Complex.ofInt(6)}};
        Complex[][] co2 = {{Complex.ofInt(-2)}, {Complex.ofInt(30)}};
        Complex[][] addCo = {{Complex.ofInt(3)}, {Complex.ofInt(36)}};
        Matrix mat1 = new Matrix(co1);
        Matrix mat2 = new Matrix(co2);
        Matrix expected = new Matrix(addCo);
        Matrix addMat = mat1.add(mat2);
        Assert.assertEquals(addMat, expected);
    }
}
