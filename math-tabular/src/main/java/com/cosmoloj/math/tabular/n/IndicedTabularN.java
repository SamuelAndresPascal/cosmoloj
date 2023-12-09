package com.cosmoloj.math.tabular.n;

import java.util.Arrays;

/**
 *
 * @author Samuel Andrés
 */
public interface IndicedTabularN extends TabularN {


    /**
     * <div class="fr">Retourne le nombre de dimensions pour l'indice considéré.
     * Retourne 0 si le tableau ne contient pas l'indice fourni en paramètre.</div>
     *
     * @param index <span class="fr">l'indice considéré.</span>
     * @return <span class="fr">le nombre de dimensions pour l'indice considéré</span>
     */
    int getDimension(char index);


    /**
     *
     * <div class="fr">Retourne la position de l'indice dans le tableau.
     * La position va de 0 à (nombre d'indices - 1).
     * Retourne -1 si le tableau ne contient pas l'indice fourni en paramètre.</div>
     *
     * @param index <span class="fr">l'indice recherché.</span>
     * @return <span class="fr">la position de l'indice</span>
     */
    int getPosition(char index);

    /**
     * <div class="fr">Retourne l'indice à la position considérée.
     * La position va de 0 à (nombre d'indices - 1).</div>
     *
     * @param order <span class="fr">position de l'indice</span>
     * @return <span class="fr">indice</span>
     */
    char getIndex(int order);

    /**
     * <div class="fr">Retourne le tableau des indices.</div>
     *
     * @return <span class="fr">le tableau des indices</span>
     */
    char[] getIndexes();

    static NamedIndex getIndex(final char index, final int size) {
        return new NamedIndex(index, size);
    }

    static int tns(final IndicedTabularN t1, final char[] sumIndex) {

        int t1ns = t1.getDimension();
        for (final char c : t1.getIndexes()) {
            if (Arrays.binarySearch(sumIndex, 0, sumIndex.length, c) >= 0) {
                // Si les indices sont sommés, t1 et t2 ont la même dimension sur cette composante
                t1ns /= t1.getDimension(c);
            }
        }
        return t1ns;
    }
}
