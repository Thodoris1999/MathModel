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
    public void matrixAddition() {
        Complex[][] co1 = {{Complex.ofInt(5)}, {Complex.ofInt(6)}};
        Complex[][] co2 = {{Complex.ofInt(-2)}, {Complex.ofInt(30)}};
        Complex[][] addCo = {{Complex.ofInt(3)}, {Complex.ofInt(36)}};
        Matrix mat1 = new Matrix(co1);
        Matrix mat2 = new Matrix(co2);
        Matrix expected = new Matrix(addCo);
        long time = System.nanoTime();
        Matrix addMat = mat1.add(mat2);
        long elapsed = System.nanoTime() - time;
        System.out.println(elapsed);
        Assert.assertEquals(addMat, expected);
    }

    @Test
    public void matrixSubtraction() {
        Complex[][] co1 = {{Complex.ofInt(5)}, {Complex.ofInt(6)}};
        Complex[][] co2 = {{Complex.ofInt(-2)}, {Complex.ofInt(30)}};
        Complex[][] subCo = {{Complex.ofInt(7)}, {Complex.ofInt(-24)}};
        Matrix mat1 = new Matrix(co1);
        Matrix mat2 = new Matrix(co2);
        Matrix expected = new Matrix(subCo);
        long time = System.nanoTime();
        Matrix subMat = mat1.subtract(mat2);
        long elapsed = System.nanoTime() - time;
        System.out.println(elapsed);
        Assert.assertEquals(subMat, expected);
    }

    @Test
    public void matrixMultiplication() {
        Complex[][] co1 = {{Complex.ofInt(-2), Complex.ofInt(30)},
                {Complex.ofInt(7), Complex.ofInt(-4)},
                {Complex.ofInt(-9), Complex.ofInt(2)}};
        Complex[][] co2 = {{Complex.ofInt(5)}, {Complex.ofInt(6)}};
        Complex[][] mulCo = {{Complex.ofInt(170)}, {Complex.ofInt(11)}, {Complex.ofInt(-33)}};
        Matrix mat1 = new Matrix(co1);
        Matrix mat2 = new Matrix(co2);
        Matrix expected = new Matrix(mulCo);
        long time = System.nanoTime();
        Matrix mulMat = mat1.multiply(mat2);
        long elapsed = System.nanoTime() - time;
        System.out.println(elapsed);
        Assert.assertEquals(mulMat, expected);
    }

    @Test
    public void scalarMultiplication() {
        Complex[][] co1 = {{Complex.ofInt(-2)}, {Complex.ofInt(30)}};
        Complex scalar = Complex.ofInt(8);
        Complex[][] mulCo = {{Complex.ofInt(-16)}, {Complex.ofInt(240)}};
        Matrix mat1 = new Matrix(co1);
        Matrix expected = new Matrix(mulCo);
        long time = System.nanoTime();
        Matrix mulMat = mat1.multiply(scalar);
        long elapsed = System.nanoTime() - time;
        System.out.println(elapsed);
        Assert.assertEquals(mulMat, expected);
    }
}
