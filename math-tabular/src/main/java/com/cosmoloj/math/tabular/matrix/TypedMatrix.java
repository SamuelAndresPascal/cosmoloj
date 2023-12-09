package com.cosmoloj.math.tabular.matrix;

import com.cosmoloj.math.tabular.TypedTabular;

/**
 *
 * @author Samuel Andrés
 * @param <K> <span class="fr">type de valeurs de la matrice</span>
 */
public interface TypedMatrix<K> extends TypedTabular<K>, Matrix {

    /**
     * @return <span class="fr">un tableau des valeurs numériques de la {@link Matrix}</span>
     */
    K[][] getMatrix();
}
