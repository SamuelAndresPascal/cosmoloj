package com.cosmoloj.math.tabular.matrix;

import com.cosmoloj.math.tabular.Tabular;
import com.cosmoloj.math.tabular.line.Vector;

/**
 * <div class="fr">
 * <p>Une {@link Matrix} est un {@link Tabular} constitué de lignes et de colonnes.
 * </p>
 * <p>
 * Les {@link Matrix} sont utiles de préférence pour les tableaux de plus d'une
 * ligne et de plus d'une colonne.
 * </p>
 * </div>
 *
 * @author Samuel Andrés
 */
public interface Matrix extends Tabular {

    enum Dimension {
        ROW, COLUMN
    }

    /**
     * <span class="fr">Le nombre de dimensions d'un {@link Matrix} au sens du {@link Tabular}
     * est égal à 2.</span>
     */
    int MATRIX_ORDER = 2;

    /**
     *
     * @param dim <span class="fr">dimension pour laquelle on souhaite obtenir le nombre de composantes</span>
     * @return <span class="fr">nombre de composantes de la {@link Matrix}, relativement à la dimension indiquée</span>
     */
    int getDimension(Dimension dim);

    /**
     *
     * @return <span class="fr">la dimension du {@link Vector}, c'est à dire le nombre de lignes
     * (respectivement de colonnes) dans le cas d'un {@link Vector} colonne
     * (respectivement ligne)</span>
     */
    @Override
    default int getDimension() {
        return getDimension(Dimension.ROW) * getDimension(Dimension.COLUMN);
    }

    @Override
    default int getOrder() {
        return MATRIX_ORDER;
    }
}
