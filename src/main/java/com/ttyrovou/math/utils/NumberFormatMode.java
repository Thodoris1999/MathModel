package com.ttyrovou.math.utils;

public class NumberFormatMode {
    public static final int CARTESIAN_FORM = 0;
    public static final int POLAR_FORM = 1;

    public static final int DECIMAL_FORM = 2;
    public static final int FRACTIONAL_FORM = 3;
    public static final int AUTO = 4;

    public static final int SQUARE_BRACKETS = 5;
    public static final int PARENTHESIS = 6;
    public static final int STRAIGHT_LINES = 7;

    public static final int RADIANS = 8;
    public static final int DEGREES = 9;

    private int fractionThreshold;
    private int decimalAccuracy;
    private int decimalMode;
    private int complexMode;
    private int matrixEnclosures;
    private int angleUnit;

    NumberFormatMode(int fractionThreshold, int decimalAccuracy, int decimalMode, int complexMode, int matrixEnclosures, int angleUnit) {
        this.fractionThreshold = fractionThreshold;
        this.decimalAccuracy = decimalAccuracy;
        this.decimalMode = decimalMode;
        this.complexMode = complexMode;
        this.matrixEnclosures = matrixEnclosures;
        this.angleUnit = angleUnit;
    }

    public int getFractionThreshold() {
        return fractionThreshold;
    }

    public int getDecimalAccuracy() {
        return decimalAccuracy;
    }

    public int getDecimalMode() {
        return decimalMode;
    }

    public int getComplexMode() {
        return complexMode;
    }

    public int getMatrixEnclosures() {
        return matrixEnclosures;
    }

    public int getAngleUnit() {
        return angleUnit;
    }
}
