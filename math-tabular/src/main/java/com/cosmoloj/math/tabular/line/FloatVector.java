package com.cosmoloj.math.tabular.line;

import com.cosmoloj.math.tabular.FloatTabular;

/**
 *
 * @author Samuel Andrés
 */
public interface FloatVector extends FloatTabular, Vector {

    /**
     * @return <span class="fr">un tableau des valeurs numériques du {@link Vector}</span>
     */
    float[] getMatrix();
}
