package com.ttyrovou.math.utils;

import com.ttyrovou.math.functions.Polynomial;
import com.ttyrovou.math.matrix.Eigen;

public class EigenDto {

    private Eigen[] eigens;
    private Polynomial charPol;

    public EigenDto(Eigen[] eigens, Polynomial charPol) {
        this.eigens = eigens;
        this.charPol = charPol;
    }

    public Eigen[] getEigens() {
        return eigens;
    }

    public Polynomial getCharPol() {
        return charPol;
    }
}
