package com.cosmoloj.format.gr3df97a.ntg88.v1_0;

/**
 *
 * @author Samuel Andrés
 */
public interface Line {

    double getLongitude();

    double getLatitude();

    double getTx();

    double getTy();

    double getTz();

    String getPrecisionCode();

    char getF50();

    int getN();

    default double[] getT() {
        return new double[]{getTx(), getTy(), getTz()};
    }
}
