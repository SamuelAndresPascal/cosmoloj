package com.cosmoloj.math.tabular.matrix;

import com.cosmoloj.math.tabular.DoubleTabular;

/**
 *
 * @author Samuel Andrés
 */
public interface DoubleMatrix extends DoubleTabular, Matrix {

    /**
     * @return <span class="fr">un tableau des valeurs numériques de la {@link Matrix}</span>
     */
    double[][] getMatrix();
}
