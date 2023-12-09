package com.cosmoloj.math.tabular.line;

import com.cosmoloj.math.tabular.TypedTabular;

/**
 *
 * @author Samuel Andrés
 * @param <K> <span class="fr">type de valeurs du vecteur</span>
 */
public interface TypedVector<K> extends TypedTabular<K>, Vector {

    /**
     * @return <span class="fr">un tableau des valeurs numériques du {@link Vector}</span>
     */
    K[] getMatrix();

    K linearForm(TypedVector<K> other);
}
