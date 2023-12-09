package com.cosmoloj.math.tabular.n;

import com.cosmoloj.math.tabular.Tabular;

/**
 *
 * @author Samuel Andrés
 */
public interface TabularN extends Tabular {

    /**
     * <div class="fr">Donne la dimension selon l'indice dont l'ordre est fourni en paramètre.</div>
     *
     * @param index <span class="fr">ordre</span>
     * @return <span class="fr">la dimension selon l'indice dont l'ordre est fourni en paramètre</span>
     */
    int getDimension(int index);
}
