package com.cosmoloj.language.common.impl.cache;

/**
 *
 * @author Samuel Andrés
 * @param <T>
 */
public interface TokenCache<T> {

    /**
     * <div class="fr">Ajout d'un élément dans le cache. Cela a pour effet de décaler les éléments précédents et de
     * supprimer l'élément le plus ancien si la capatité du cache est dépassée.</div>
     *
     * @param candidate
     */
    void add(T candidate);

    /**
     * <div class="fr">Récupère l'élément du cache dans l'ordre inverse des ajouts (l'index 0 correspond au dernier
     * élément ajouté).</div>
     *
     * @param order
     * @return
     */
    T get(int order);
}
