package com.cosmoloj.math.tabular.matrix;

import com.cosmoloj.math.tabular.FloatTabular;

/**
 *
 * @author Samuel Andrés
 */
public interface FloatMatrix extends FloatTabular, Matrix {

    /**
     * @return <span class="fr">un tableau des valeurs numériques de la {@link Matrix}</span>
     */
    float[][] getMatrix();
}
