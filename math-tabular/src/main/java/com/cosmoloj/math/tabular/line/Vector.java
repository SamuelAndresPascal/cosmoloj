package com.cosmoloj.math.tabular.line;

import com.cosmoloj.math.tabular.Tabular;
import com.cosmoloj.math.tabular.matrix.Matrix;

/**
 * <div class="fr">
 * <p>
 * Un {@link Vector} est un {@link Tabular} constitué d'une ligne ou d'une colonne.
 * </p><p>
 * Les {@link Vector} sont utiles de préférence pour les tableaux d'une seule ligne ou d'une seule colonne.
 * </p><p>
 * Pour les tableaux ayant à la fois plus d'une ligne et plus d'une colonne, se
 * reporter à la classe {@link Matrix}.
 * </p>
 * </div>
 *
 * @author Samuel Andrés
 */
public interface Vector extends Tabular {

    /**
     * <div class="fr">
     * Le nombre de dimensions d'un {@link Vector} au sens du {@link Tabular}
     * est égal à 1.
     * </div>
     */
    int VECTOR_ORDER = 1;

    @Override
    default int getOrder() {
        return VECTOR_ORDER;
    }

    /**
     * <div class="fr">Vérifie que deux vecteurs ont même dimension.</div>
     *
     * @param a <span class="fr">premier vecteur</span>
     * @param b <span class="fr">second vecteur</span>
     * @return <span class="fr">vrai si les deux vecteurs ont la même dimension ; faux sinon</span>
     * @throws NullPointerException <span class="fr">si a est null ou b est null</span>
     * <span class="en">if a is null or b is null</span>
     */
    static boolean sameDimension(final Vector a, final Vector b) {
        return a.getDimension() == b.getDimension();
    }
}
