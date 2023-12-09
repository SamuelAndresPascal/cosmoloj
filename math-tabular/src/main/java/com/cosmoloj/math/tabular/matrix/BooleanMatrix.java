package com.cosmoloj.math.tabular.matrix;

import com.cosmoloj.math.tabular.BooleanTabular;

/**
 *
 * @author Samuel Andrés
 */
public interface BooleanMatrix extends BooleanTabular, Matrix {

    /**
     * @return <span class="fr">un tableau des valeurs numériques de la {@link Matrix}</span>
     */
    boolean[][] getMatrix();

}
