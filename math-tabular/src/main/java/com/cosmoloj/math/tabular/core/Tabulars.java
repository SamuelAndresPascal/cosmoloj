package com.cosmoloj.math.tabular.core;

/**
 *
 * @author Samuel Andrés
 */
public final class Tabulars {

    private Tabulars() {
    }

    public static int kronecker(final int i, final int j) {
        return i == j ? 1 : 0;
    }
}
