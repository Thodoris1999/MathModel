package com.ttyrovou.Math.Utils;

public class NumberFormatModeBuilder {

    private static final int DEFAULT_FRACTION_THRESHOLD = 7;
    private static final int DEFAULT_DECIMAL_ACCURACY = 5;
    private static final int DEFAULT_DECIMAL_MODE = NumberFormatMode.AUTO;
    private static final int DEFAULT_COMPLEX_MODE = NumberFormatMode.CARTESIAN_FORM;

    private int fractionThreshold = DEFAULT_FRACTION_THRESHOLD;
    private int decimalAccuracy = DEFAULT_DECIMAL_ACCURACY;
    private int decimalMode = DEFAULT_DECIMAL_MODE;
    private int complexMode = DEFAULT_COMPLEX_MODE;

    public NumberFormatModeBuilder withDecimalMode(int mode) {
        this.decimalMode = mode;
        return this;
    }

    public NumberFormatModeBuilder withComplexMode(int mode) {
        this.complexMode = mode;
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
        if (decimalMode != NumberFormatMode.AUTO) {
            throw new IllegalArgumentException("Cannot set decimal accuracy for non auto mode");
        }
        this.decimalAccuracy = decimalAccuracy;
        return this;
    }

    public NumberFormatMode build() {
        return new NumberFormatMode(fractionThreshold, decimalAccuracy, decimalMode, complexMode);
    }
}