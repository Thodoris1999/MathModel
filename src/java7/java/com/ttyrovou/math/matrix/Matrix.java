package com.ttyrovou.math.matrix;

import com.ttyrovou.math.numbers.Complex;
import com.ttyrovou.math.utils.NumberFormatMode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Matrix {
    private LinkedList<LinkedList<Complex>> elements;

    public Matrix(LinkedList<LinkedList<Complex>> elements) {
        this.elements = elements;
    }
    
    public Matrix(Complex[][] elements) {
        int size = elements[0].length;
        for (int i = 1; i < elements.length; i++) {
            if (elements[i].length != size) {
                throw new IllegalArgumentException("Array not convertible to matrix!");
            }
        }
        this.elements = new LinkedList<>();
        for (int i = 0; i < elements.length; i++) {
            this.elements.add(new LinkedList<>());
            for (int j = 0; j < elements[0].length; j++) {
                this.elements.get(i).add(elements[i][j]);
            }
        }
    }

    public Matrix(int rowCount, int colCount) {
        this.elements = new LinkedList<>();

        for (int i = 0; i < rowCount; i++) {
            this.elements.add(new LinkedList<>());
            for (int j = 0; j < colCount; j++) {
                this.elements.get(i).add(Complex.ZERO);
            }
        }
    }

    public static Matrix fromZero(int size) {
        LinkedList<LinkedList<Complex>> elements = new LinkedList<>();

        for (int i = 0; i < size; i++) {
            elements.add(new LinkedList<>());
            for (int j = 0; j < size; j++) {
                elements.get(i).add(Complex.ZERO);
            }
        }
        return new Matrix(elements);
    }

    public static Matrix fromUnit(int size) {
        LinkedList<LinkedList<Complex>> elements = new LinkedList<>();

        for (int i = 0; i < size; i++) {
            elements.add(new LinkedList<>());
            for (int j = 0; j < size; j++) {
                elements.get(i).add((i == j) ? Complex.ONE : Complex.ZERO);
            }
        }
        return new Matrix(elements);
    }

    public Matrix(String[] strings, int rowCount, int colCount) {
        this.elements = new LinkedList<>();
        for (int i = 0; i < rowCount; i++) {
            this.elements.add(new LinkedList<>());
            for (int j = 0; j < colCount; j++) {
                this.elements.get(i).add(new Complex(strings[i * colCount + j]));
            }
        }
    }

    public Matrix(Matrix other) {
        LinkedList<LinkedList<Complex>> newElements = new LinkedList<>();
        for (int i = 0; i < other.elements.size(); i++) {
            newElements.add(new LinkedList<>());
            for (int j = 0; j < other.elements.get(0).size(); j++) {
                newElements.get(i).add(other.get(i, j));
            }
        }
        this.elements = newElements;
    }

    public Matrix add(Matrix matrix) {
        if (this.getColCount() == matrix.getColCount() && this.getRowCount() == matrix.getRowCount()) {
            Matrix result = new Matrix(this.getRowCount(), this.getColCount());
            for (int i = 0; i < getRowCount(); i++) {
                for (int j = 0; j < getColCount(); j++) {
                    result.set(i, j, this.get(i, j).add(matrix.get(i, j)));
                }
            }
            return result;
        } else {
            throw new UnsupportedOperationException("Atteampting to add matrices of non equal dimensions");
        }
    }

    public Matrix subtract(Matrix matrix) {
        if (this.getColCount() == matrix.getColCount() && this.getRowCount() == matrix.getRowCount()) {
            Matrix result = new Matrix(this.getRowCount(), this.getColCount());
            for (int i = 0; i < getRowCount(); i++) {
                for (int j = 0; j < getColCount(); j++) {
                    result.set(i, j, this.get(i, j).subtract(matrix.get(i, j)));
                }
            }
            return result;
        } else {
            throw new UnsupportedOperationException("Atteampting to subtract matrices of non equal dimensions");
        }
    }

    public Matrix multiply(Matrix matrix) {
        if (this.getColCount() == matrix.getRowCount()) {
            Matrix result = new Matrix(this.getRowCount(), matrix.getColCount());
            for (int i = 0; i < result.getRowCount(); i++) {
                for (int j = 0; j < matrix.getColCount(); j++) {
                    Complex sum = Complex.ZERO;
                    for (int k = 0; k < this.getColCount(); k++) {
                        sum = sum.add(this.get(i, k).multiply(matrix.get(k, j)));
                    }
                    result.set(i, j, sum);
                }
            }
            return result;
        } else {
            throw new UnsupportedOperationException("The first matrix's column count must be equal to the second's row count");
        }
    }

    public Matrix multiply(Complex scalar) {
        Matrix result = new Matrix(this);
        for (int i = 0; i < getRowCount(); i++) {
            result.scaleRow(i, scalar);
        }
        return result;
    }

    public Complex det() {
        System.out.print("got throu java7");
        if (this.getColCount() == this.getRowCount()) {
            GaussianElimination elimination = new GaussianElimination(this);
            Complex maybeDet = elimination.getDet();
            if (maybeDet != null) {
                return maybeDet;
            } else {
                return slowSquareDet();
            }
        } else {
            throw new UnsupportedOperationException("Attempting to get the determinant of a non square matrix");
        }
    }

    public Complex slowSquareDet() {
        if (this.getRowCount() == 1) {
            return this.get(0, 0);
        } else {
            Complex result = Complex.ZERO;
            for (int i = 0; i < this.getColCount(); i++) {
                if (i % 2 == 0) {
                    result = result.add(this.get(0, i).multiply(this.removeRow(0).removeColumn(i).det()));
                } else {
                    result = result.subtract(this.get(0, i).multiply(this.removeRow(0).removeColumn(i).det()));
                }
            }
            return result;
        }
    }

    public int rank() {
        GaussianElimination elimination = new GaussianElimination(this);
        return elimination.getPivotCount();
    }

    public Matrix rref() {
        GaussianElimination elimination = new GaussianElimination(this);
        return elimination.getReducedRowEchelon();
    }

    public Matrix[] LUDecomposition() {
        GaussianElimination elimination = new GaussianElimination(this);
        elimination.getRowEchelon();
        if (elimination.isDecomposable()) {
            Matrix[] lu = new Matrix[2];
            lu[0] = elimination.getLower();
            lu[1] = elimination.getRowEchelon();
            return lu;
        } else {
            throw new UnsupportedOperationException("Failed to decompose matrix");
        }
    }

    public LinkedList<LinkedList<Complex>> linearSystemSolution() {
        LinkedList<LinkedList<Complex>> sol = new LinkedList<>();

        GaussianElimination elimination = new GaussianElimination(this);
        Matrix rref = elimination.getReducedRowEchelon();
        List<Integer> pivotIndices = elimination.getPivotIndices();
        if (pivotIndices.contains(rref.getColCount())) return sol;

        //loop through all non pivot columns
        for (int i = 0; i < rref.getColCount(); i++) {
            if (pivotIndices.contains(i)) {
                continue;
            }
            LinkedList<Complex> tempVector = new LinkedList<>();
            if (i == rref.getColCount() - 1) {
                //constant vector
                for (int j = 0; j < rref.getColCount() - 1; j++) {
                    if (j < pivotIndices.size()) {
                        tempVector.add(rref.get(j, i));
                    } else {
                        tempVector.add(Complex.ZERO);
                    }
                }
                sol.add(0, tempVector);
            } else {
                //variable vector
                for (int j = 0; j < rref.getColCount() - 1; j++) {
                    if (j == i) {
                        tempVector.add(Complex.ONE);
                    } else if (j < pivotIndices.size()) {
                        tempVector.add(rref.get(j, i).opposite());
                    } else {
                        tempVector.add(Complex.ZERO);
                    }
                }
                sol.add(tempVector);
            }
        }
        return sol;
    }

    public LinkedList<LinkedList<Complex>> getImage() {
        LinkedList<LinkedList<Complex>> result = new LinkedList<>();
        Matrix clone = new Matrix(this);
        Matrix transpose = clone.transpose();

        GaussianElimination elimination = new GaussianElimination(transpose);
        Matrix rref = elimination.getReducedRowEchelon();
        int pivotCount = elimination.getPivotCount();

        for (int i = 0; i < pivotCount; i++) {
            result.add(rref.getRow(i));
        }
        return result;
    }

    public LinkedList<LinkedList<Complex>> getNullspace() {
        return this.addColumn(getColCount()).linearSystemSolution();
    }

    public Matrix inverse() {
        GaussianElimination elimination = new GaussianElimination(this);
        return elimination.getInverse();
    }

    public Complex trace() {
        if (getColCount() != getRowCount()) {
            throw new UnsupportedOperationException("Trace of non square matrices is not defined");
        }

        Complex sum = Complex.ZERO;
        for (int i = 0; i < getRowCount(); i++) {
            sum = sum.add(this.get(i, i));
        }
        return sum;
    }

    public Complex[] getCharacteristicPolynomial() {
        if (getColCount() != getRowCount()) {
            throw new UnsupportedOperationException("Attempting to calculate characteristic polynomial of non square matrix");
        }
        Complex[] coefficients = new Complex[getColCount() + 1];
        Matrix prev = Matrix.fromZero(getColCount());
        coefficients[getColCount()] = Complex.ONE;
        Matrix cur;
        for (int i = 1; i < getColCount() + 1; i++) {
            cur = this.multiply(prev)
                    .add(Matrix.fromUnit(getColCount()).multiply(coefficients[getColCount() + 1 - i]));
            coefficients[getColCount() - i] = this.multiply(cur)
                    .trace()
                    .divide(Complex.ofInt(i))
                    .opposite();
            prev = cur;
        }
        return coefficients;
    }

    public Matrix transpose() {
        Matrix result = new Matrix(this.getColCount(), this.getRowCount());
        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.getColCount(); j++) {
                result.set(j, i, this.get(i, j));
            }
        }
        return result;
    }

    public void swapRows(int rowIndex1, int rowIndex2) {
        Collections.swap(this.elements, rowIndex1, rowIndex2);
    }

    public void scaleRow(int rowIndex, Complex scalar) {
        for (int i = 0; i < getColCount(); i++) {
            this.set(rowIndex, i, scalar.multiply(this.get(rowIndex, i)));
        }
    }

    public void addScaled(int fromIndex, int toIndex, Complex scalar) {
        for (int i = 0; i < getColCount(); i++) {
            this.set(toIndex, i, this.get(toIndex, i).add(scalar.multiply(this.get(fromIndex, i))));
        }
    }

    public Matrix removeRow(int index) {
        Matrix clone = new Matrix(this);
        clone.elements.remove(index);
        return clone;
    }

    public Matrix removeColumn(int index) {
        Matrix clone = new Matrix(this);
        for (int i = 0; i < getRowCount(); i++) {
            clone.elements.get(i).remove(index);
        }
        return clone;
    }

    public Matrix addColumn(int index) {
        Matrix clone = new Matrix(this);
        for (int i = 0; i < getRowCount(); i++) {
            clone.elements.get(i).add(index, Complex.ZERO);
        }
        return clone;
    }

    public LinkedList<Complex> getColumn(int index) {
        LinkedList<Complex> returnCol = new LinkedList<>();
        for (int i = 0; i < getRowCount(); i++) {
            returnCol.add(this.get(i, index));
        }

        return returnCol;
    }

    public LinkedList<Complex> getRow(int index) {
        return elements.get(index);
    }

    public Complex get(int row, int col) {
        return elements.get(row).get(col);
    }

    public void set(int row, int col, Complex number) {
        elements.get(row).set(col, number);
    }

    public int getColCount() {
        return elements.get(0).size();
    }

    public int getRowCount() {
        return elements.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        Matrix other = (Matrix) obj;
        if (this.getRowCount()!= other.getRowCount() || this.getColCount() != other.getColCount()) return false;
        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.getColCount(); j++) {
                if (!this.get(i, j).equals(other.get(i, j))) return false;
            }
        }
        return true;
    }

    public String toLatex(NumberFormatMode formatMode) {
        StringBuilder returnString = new StringBuilder();
        returnString.append("\\left");
        switch (formatMode.getMatrixEnclosures()) {
            case NumberFormatMode.PARENTHESIS:
                returnString.append("(");
                break;
            case NumberFormatMode.SQUARE_BRACKETS:
                returnString.append("[");
                break;
            case NumberFormatMode.STRAIGHT_LINES:
                returnString.append("|");
                break;
            default:
                throw new IllegalArgumentException("Unknown matrix enclosure style");
        }
        returnString.append("\\begin{matrix}\n "); //NON-NLS NON-NLS
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColCount(); j++) {
                if (j == getColCount() - 1 && i != getRowCount() - 1) {
                    returnString.append(this.get(i, j).toLatex(formatMode)).append("\\\\");
                } else if (j == getColCount() - 1) {
                    returnString.append(this.get(i, j).toLatex(formatMode));
                } else {
                    returnString.append(this.get(i, j).toLatex(formatMode)).append(" &");
                }
            }
        }
        returnString.append(" \\end{matrix}\\right");
        switch (formatMode.getMatrixEnclosures()) {
            case NumberFormatMode.PARENTHESIS:
                returnString.append(")");
                break;
            case NumberFormatMode.SQUARE_BRACKETS:
                returnString.append("]");
                break;
            case NumberFormatMode.STRAIGHT_LINES:
                returnString.append("|");
                break;
            default:
                throw new IllegalArgumentException("Unknown matrix enclosure style");
        }
        return returnString.toString();
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (List<Complex> row : elements) {
            for (Complex element : row) {
                string.append(element.toString()).append(" ");
            }
            string.append("\n");
        }
        return string.toString();
    }
}
