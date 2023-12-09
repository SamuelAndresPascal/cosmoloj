package com.cosmoloj.math.tabular.matrix;

import com.cosmoloj.math.tabular.IntTabular;

/**
 *
 * @author Samuel Andrés
 */
public interface IntMatrix extends IntTabular, Matrix {

    /**
     * @return <span class="fr">un tableau des valeurs numériques de la {@link Matrix}</span>
     */
    int[][] getMatrix();
}
