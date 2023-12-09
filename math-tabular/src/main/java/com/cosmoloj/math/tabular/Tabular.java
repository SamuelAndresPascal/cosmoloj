package com.cosmoloj.math.tabular;

import com.cosmoloj.math.tabular.n.TabularIterator;

/**
 * <div class="fr">Un {@link Tabular} est une structure constituée de lignes et de colonnes d'entités.</div>
 *
 * @author Samuel Andrés
 */
public interface Tabular extends Iterable<int[]> {

    int getOrder();

    /**
     *
     * @return <span class="fr">le nombre de composantes</span>
     */
    int getDimension();

    /**
     *
     * @return <span class="fr">une copie du tableau des dimensions dans l'ordre des indices</span>
     */
    int[] getDimensions();

    @Override
    default TabularIterator iterator() {
        return new TabularIterator(this);
    }
}
