package com.ttyrovou.math.utils;

public class NumberFormatModeBuilder {

    private static final int DEFAULT_FRACTION_THRESHOLD = 7;
    private static final int DEFAULT_DECIMAL_ACCURACY = 5;
    private static final int DEFAULT_DECIMAL_MODE = NumberFormatMode.AUTO;
    private static final int DEFAULT_COMPLEX_MODE = NumberFormatMode.CARTESIAN_FORM;
    private static final int DEFAULT_MATRIX_ENCLOSURES = NumberFormatMode.PARENTHESIS;

    private int fractionThreshold = DEFAULT_FRACTION_THRESHOLD;
    private int decimalAccuracy = DEFAULT_DECIMAL_ACCURACY;
    private int decimalMode = DEFAULT_DECIMAL_MODE;
    private int complexMode = DEFAULT_COMPLEX_MODE;
    private int matrixEnclosures = DEFAULT_MATRIX_ENCLOSURES;

    public NumberFormatModeBuilder withDecimalMode(int mode) {
        this.decimalMode = mode;
        return this;
    }

    public NumberFormatModeBuilder withComplexMode(int mode) {
        this.complexMode = mode;
        return this;
    }

    public NumberFormatModeBuilder withMatrixEnclosures(int style) {
        this.matrixEnclosures = style;
        return this;
    }

    public NumberFormatModeBuilder setFractionThreshold(int fractionThreshold) {
        if (decimalMode != NumberFormatMode.AUTO) {
            throw new IllegalArgumentException("Cannot set fraction size threshold for non auto mode");
        }
        this.fractionThreshold = fractionThreshold;
        return this;
    }

    public NumberFormatModeBuilder setDecimalAccuracy(int decimalAccuracy) {
        if (decimalMode != NumberFormatMode.AUTO && decimalMode != NumberFormatMode.DECIMAL_FORM) {
            throw new IllegalArgumentException("Cannot set decimal accuracy for non auto or non decimal mode");
        }
        this.decimalAccuracy = decimalAccuracy;
        return this;
    }

    public NumberFormatMode build() {
        return new NumberFormatMode(fractionThreshold, decimalAccuracy, decimalMode, complexMode, matrixEnclosures);
    }
}