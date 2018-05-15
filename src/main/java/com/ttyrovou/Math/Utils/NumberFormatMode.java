package com.ttyrovou.Math.Utils;

public class NumberFormatMode {
    public static final int CARTESIAN_FORM = 0;
    public static final int POLAR_FORM = 1;

    public static final int DECIMAL_FORM = 2;
    public static final int FRACTIONAL_FORM = 3;
    public static final int AUTO = 4;

    private int fractionThreshold;
    private int decimalAccuracy;
    private int decimalMode;
    private int complexMode;

    NumberFormatMode(int fractionThreshold, int decimalAccuracy, int decimalMode, int complexMode) {
        this.fractionThreshold = fractionThreshold;
        this.decimalAccuracy = decimalAccuracy;
        this.decimalMode = decimalMode;
        this.complexMode = complexMode;
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
}
