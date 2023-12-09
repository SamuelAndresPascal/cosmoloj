package com.cosmoloj.math.tabular;

/**
 *
 * @author Samuel Andrés
 * @param <K> <span class="fr">type de valeurs du tableau</span>
 */
public interface TypedTabular<K> extends Tabular {

    /**
     * @param i <span class="fr">coordonnées de la composante dans le tableau</span>
     * @return  <span class="fr">valeur de la composante</span>
     */
    K get(int... i);

    /**
     * @param c  <span class="fr">valeur de la composante</span>
     * @param i <span class="fr">coordonnées de la composante dans le tableau</span>
     */
    void set(K c, int... i);

    /**
     *
     * @return <span class="fr">corps : type d'éléments du tableau</span>
     */
    Class<K> getField();

}
