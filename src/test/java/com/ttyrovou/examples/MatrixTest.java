package com.ttyrovou.examples;

import com.ttyrovou.math.matrix.Eigen;
import com.ttyrovou.math.matrix.Matrix;
import com.ttyrovou.math.numbers.Complex;
import com.ttyrovou.math.numbers.Fraction;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

public class MatrixTest {
    @Test
    public void complexAddition() {
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

    @Test
    public void determinant() {
        Complex[][] co1 = {{Complex.ofInt(5), Complex.I},
                {Complex.ofFraction(Fraction.of(2, 3)), Complex.ofInt(7)}};
        Matrix mat = new Matrix(co1);
        Complex expected = new Complex(Fraction.ofInt(35), Fraction.of(-2, 3));
        long time = System.nanoTime();
        Complex det = mat.det();
        long elapsed = System.nanoTime() - time;
        System.out.println(elapsed);
        Assert.assertEquals(det, expected);
    }

    @Test
    public void rank() {
        Complex[][] co1 = {{Complex.ofInt(5), Complex.I},
                {Complex.ofFraction(Fraction.of(2, 3)), Complex.ofInt(7)}};
        Matrix mat = new Matrix(co1);
        int expected = 2;
        long time = System.nanoTime();
        int rank = mat.rank();
        long elapsed = System.nanoTime() - time;
        System.out.println(elapsed);
        Assert.assertEquals(rank, expected);
    }

    @Test
    public void rref() {
        Complex[][] co1 = {{Complex.ofInt(5), Complex.I},
                {Complex.ofFraction(Fraction.of(2, 3)), Complex.ofInt(7)}};
        Complex[][] exp = {{Complex.ONE, Complex.ZERO},
                {Complex.ZERO, Complex.ONE}};
        Matrix mat = new Matrix(co1);
        Matrix expected = new Matrix(exp);
        long time = System.nanoTime();
        Matrix rref = mat.rref();
        long elapsed = System.nanoTime() - time;
        System.out.println(elapsed);
        Assert.assertEquals(rref, expected);
    }

    @Test
    public void ludecomposition() {
        Complex[][] co1 = {{Complex.ofInt(5), Complex.I},
                {Complex.ofFraction(Fraction.of(2, 3)), Complex.ofInt(7)}};
        Complex[][] exp1 = {{Complex.ONE, Complex.ZERO},
                {Complex.ofFraction(Fraction.of(2, 15)), Complex.ONE}};
        Complex[][] exp2 = {{Complex.ofInt(5), Complex.I},
                {Complex.ZERO, new Complex(Fraction.ofInt(7), Fraction.of(2, -15))}};
        Matrix mat = new Matrix(co1);
        Matrix lu1 = new Matrix(exp1);
        Matrix lu2 = new Matrix(exp2);
        long time = System.nanoTime();
        Matrix[] lu = mat.luDecomposition();
        long elapsed = System.nanoTime() - time;
        System.out.println("LU decomposition: " + elapsed);
        Assert.assertEquals(lu[0], lu1);
        Assert.assertEquals(lu[1], lu2);
    }

    @Test
    public void eigen() {
        Complex[][] co1 = {{Complex.ofInt(1), Complex.ZERO, Complex.ZERO},
                {Complex.ZERO, Complex.ofInt(1), Complex.ZERO},
                {Complex.ofInt(2), Complex.ZERO, Complex.ofInt(1)}};
        Matrix mat = new Matrix(co1);
        long time = System.nanoTime();
        Eigen[] eigens = mat.eigen().getEigens();
        long elapsed = System.nanoTime() - time;
        System.out.println("Eigen: " + elapsed);
        Arrays.stream(eigens).map(Eigen::getEigenvalue).forEach(System.out::println);
        System.out.println(eigens[0].getEigenspace());
        System.out.println(eigens.length);
        System.out.println(eigens[0].getAlgebraicMultiplicity());
    }

    @Test
    public void eigen2() {
        Complex[][] co1 = {{new Complex(Fraction.ofInt(9), Fraction.ofInt(2)), Complex.ofInt(-1)},
                {Complex.ofInt(-1), new Complex(Fraction.ofInt(9), Fraction.ofInt(-4))}};
        Matrix mat = new Matrix(co1);
        long time = System.nanoTime();
        Eigen[] eigens = mat.eigen().getEigens();
        long elapsed = System.nanoTime() - time;
        System.out.println("Eigen2: " + elapsed);
        Arrays.stream(eigens).map(Eigen::getEigenvalue).forEach(System.out::println);
        System.out.println(eigens[0].getEigenspace());
        System.out.println(eigens.length);
        System.out.println(eigens[0].getAlgebraicMultiplicity());
    }

    @Test
    public void linearSystemNoSolutions() {
        Complex[][] noSolMatNums = {{Complex.ofInt(2), Complex.ZERO, Complex.ofInt(3), Complex.ofInt(3)},
                {Complex.ofInt(4), Complex.ofInt(-3), Complex.ofInt(7), Complex.ofInt(5)},
                {Complex.ofInt(8), Complex.ofInt(-9), Complex.ofInt(15), Complex.ofInt(10)}};
        Matrix noSolMat = new Matrix(noSolMatNums);
        LinkedList<LinkedList<Complex>> sol = noSolMat.linearSystemSolution();
        Assert.assertTrue(sol.isEmpty());
    }

    @Test
    public void power() {
        Complex[][] co1 = {{Complex.ofInt(1), Complex.ofInt(2)},
                {Complex.ofInt(3), Complex.ofInt(4)}};
        Complex[][] exp = {{Complex.ofInt(199), Complex.ofInt(290)},
                {Complex.ofInt(435), Complex.ofInt(634)}};
        Matrix mat = new Matrix(co1);
        Matrix expe = new Matrix(exp);
        long time = System.nanoTime();
        Matrix result = mat.power(4);
        long elapsed = System.nanoTime() - time;
        System.out.println("4th power time: " + elapsed);
        Assert.assertEquals(expe, result);
    }
}
