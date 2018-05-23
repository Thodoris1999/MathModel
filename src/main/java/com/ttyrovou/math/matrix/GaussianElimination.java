package com.ttyrovou.math.matrix;

import com.ttyrovou.math.numbers.Complex;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GaussianElimination {
    private List<Integer> pivotIndices;
    private Matrix matrix;
    private Matrix reducedRowEchelon;
    private Matrix rowEchelon;
    private int pivotCount;
    private boolean isSquare;
    private boolean isInvertible;
    private boolean isDecomposable;
    private boolean rowEchelonPerformed;
    private boolean reducedRowEchelonPerformed;
    private Matrix lower;
    private Matrix inverse;

    public GaussianElimination(Matrix matrix) {
        this.matrix = matrix;
        isSquare = matrix.getColCount() == matrix.getRowCount();
        rowEchelonPerformed = false;
        reducedRowEchelonPerformed = false;
    }

    private void reducedRowEchelon() {
        pivotIndices = new ArrayList<>();
        isInvertible = false;
        if (isSquare) {
            inverse = Matrix.fromUnit(matrix.getRowCount());
            isInvertible = true;
        }

        reducedRowEchelon = new Matrix(matrix);
        pivotCount = 0;
        for (int i = 0; i < reducedRowEchelon.getColCount(); i++) {
            int pivotIndex = -1;
            //find a non zero element in jth row
            for (int j = pivotCount; j < reducedRowEchelon.getRowCount(); j++) {
                if (!reducedRowEchelon.get(j, i).equals(Complex.ZERO)) {
                    pivotIndex = j;
                    pivotIndices.add(i);
                    pivotCount++;
                    break;
                }
            }

            if (pivotIndex != -1) {
                //bring pivot row to ith row
                if (pivotIndex != pivotCount - 1) {
                    reducedRowEchelon.swapRows(pivotCount - 1, pivotIndex);
                    if (isSquare) {
                        //do the same to identity
                        if (isInvertible) {
                            inverse.swapRows(pivotCount - 1, pivotIndex);
                        }
                    }
                    pivotIndex = pivotCount - 1;
                }
                //scale pivot row and do the same for identity
                Complex scalar = reducedRowEchelon.get(pivotIndex, i).inverse();
                reducedRowEchelon.scaleRow(pivotIndex, scalar);
                if (isSquare) {
                    //do the same for identity
                    if (isInvertible) {
                        inverse.scaleRow(pivotIndex, scalar);
                    }
                }

                for (int j = 0; j < reducedRowEchelon.getRowCount(); j++) {
                    //make all other elements zero
                    if (j != pivotIndex && !reducedRowEchelon.get(j, i).equals(Complex.ZERO)) {
                        scalar = reducedRowEchelon.get(j, i).opposite();
                        reducedRowEchelon.addScaled(pivotIndex, j, scalar);
                        if (isSquare) {
                            //do the same for identity
                            if (isInvertible) {
                                inverse.addScaled(pivotIndex, j, scalar);
                            }
                        }
                    }
                }
            } else {
                isInvertible = false;
            }
        }
        reducedRowEchelonPerformed = true;
    }

    private void rowEchelon() {
        isSquare = matrix.getColCount() == matrix.getRowCount();
        isDecomposable = false;
        if (isSquare) {
            isDecomposable = true;
            lower = Matrix.fromUnit(matrix.getRowCount());
        }
        rowEchelon = new Matrix(matrix);
        pivotCount = 0;
        for (int i = 0; i < rowEchelon.getColCount(); i++) {
            int pivotIndex = -1;

            //find a non zero element in jth row
            for (int j = pivotCount; j < rowEchelon.getRowCount(); j++) {
                if (!rowEchelon.get(j, i).equals(Complex.ZERO)) {
                    pivotIndex = j;
                    pivotCount++;
                    break;
                }
            }

            if (pivotIndex != -1) {
                //bring pivot row to first row
                if (pivotIndex != pivotCount - 1) {
                    rowEchelon.swapRows(pivotCount - 1, pivotIndex);
                    isDecomposable = false;
                    pivotIndex = pivotCount - 1;
                }

                for (int j = pivotIndex + 1; j < rowEchelon.getRowCount(); j++) {
                    //make all other elements zero
                    Complex scalar = rowEchelon.get(j, i).divide(rowEchelon.get(pivotIndex, i)).opposite();
                    rowEchelon.addScaled(pivotIndex, j, scalar);
                    if (isDecomposable) {
                        lower.set(j, i, scalar.opposite());
                    }
                }
            }
        }
        rowEchelonPerformed = true;
    }

    public Optional<Complex> getDet() {
        if (!rowEchelonPerformed) {
            rowEchelon();
        }
        if (isDecomposable) {
            Complex result = Complex.ONE;
            for (int i = 0; i < rowEchelon.getRowCount(); i++) {
                result = result.multiply(rowEchelon.get(i, i));
            }
            return Optional.of(result);
        } else {
            return Optional.empty();
        }
    }

    public int getPivotCount() {
        if (!rowEchelonPerformed) {
            rowEchelon();
        }
        return pivotCount;
    }

    public List<Integer> getPivotIndices() {
        if (!reducedRowEchelonPerformed) {
            reducedRowEchelon();
        }
        return pivotIndices;
    }

    public Matrix getLower() {
        if (!rowEchelonPerformed) {
            rowEchelon();
        }
        if (isDecomposable) {
            return lower;
        } else {
            throw new UnsupportedOperationException("Matrix is not LU decomposable");
        }
    }

    public Matrix getInverse() {
        if (!reducedRowEchelonPerformed) {
            reducedRowEchelon();
        }
        if (!isInvertible) {
            throw new UnsupportedOperationException("Matrix is not invertible");
        }
        return inverse;
    }

    public Matrix getRowEchelon() {
        if (!rowEchelonPerformed) {
            rowEchelon();
        }
        return rowEchelon;
    }

    public Matrix getReducedRowEchelon() {
        if (!reducedRowEchelonPerformed) {
            reducedRowEchelon();
        }
        return reducedRowEchelon;
    }

    public boolean isInvertible() {
        if (!reducedRowEchelonPerformed) {
            reducedRowEchelon();
        }
        return isInvertible;
    }

    public boolean isSquare() {
        return isSquare;
    }

    public boolean isDecomposable() {
        if (!rowEchelonPerformed) {
            rowEchelon();
        }
        return isDecomposable;
    }
}
