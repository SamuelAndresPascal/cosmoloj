package com.cosmoloj.math.tabular;

/**
 *
 * @author Samuel Andrés
 */
public interface BooleanTabular extends Tabular {

    /**
     * @param i <span class="fr">coordonnées de la composante dans le tableau</span>
     * @return  <span class="fr">valeur de la composante</span>
     */
    boolean get(int... i);

    /**
     * @param c  <span class="fr">valeur de la composante</span>
     * @param i <span class="fr">coordonnées de la composante dans le tableau</span>
     */
    void set(boolean c, int... i);

}
