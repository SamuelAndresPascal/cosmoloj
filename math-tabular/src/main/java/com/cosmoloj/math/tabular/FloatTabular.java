package com.cosmoloj.math.tabular;

/**
 *
 * @author Samuel Andrés
 */
public interface FloatTabular extends Tabular {

    /**
     * @param i <span class="fr">coordonnées de la composante dans le tableau</span>
     * @return  <span class="fr">valeur de la composante</span>
     */
    float get(int... i);
}
