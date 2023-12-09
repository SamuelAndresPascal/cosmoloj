package com.cosmoloj.math.tabular.line;

import com.cosmoloj.math.tabular.IntTabular;

/**
 *
 * @author Samuel Andrés
 */
public interface IntVector extends IntTabular, Vector {

    /**
     * @return <span class="fr">un tableau des valeurs numériques du {@link Vector}</span>
     */
    int[] getMatrix();

    /**
     * <div class="fr">Initialisation du {@link Vector} par copie.</div>
     *
     * @param matrix <span class="fr">un tableau des valeurs numériques du {@link Vector}</span>
     */
    void setMatrix(int[] matrix);

    /**
     * <div class="fr">Réinitialisation du {@link Vector} par référence.</div>
     *
     * @param matrix <span class="fr">tableau de valeurs à référencer</span>
     */
    void resetMatrix(int[] matrix);

    int linearForm(IntVector other);
}
