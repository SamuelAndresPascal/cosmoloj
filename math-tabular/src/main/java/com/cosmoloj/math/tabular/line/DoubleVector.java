package com.cosmoloj.math.tabular.line;

import com.cosmoloj.math.tabular.DoubleTabular;

/**
 *
 * @author Samuel Andrés
 */
public interface DoubleVector extends DoubleTabular, Vector {

    /**
     * @return <span class="fr">un tableau des valeurs numériques du {@link Vector}</span>
     */
    double[] getMatrix();
}
