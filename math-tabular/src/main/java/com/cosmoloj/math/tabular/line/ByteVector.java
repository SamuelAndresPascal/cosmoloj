package com.cosmoloj.math.tabular.line;

import com.cosmoloj.math.tabular.BooleanTabular;

/**
 *
 * @author Samuel Andrés
 */
public interface ByteVector extends BooleanTabular, Vector {

    /**
     * @return <span class="fr">un tableau des valeurs numériques du {@link Vector}</span>
     */
    byte[] getMatrix();

    /**
     * <div class="fr">Initialisation du {@link Vector} par copie.</div>
     *
     * @param matrix <span class="fr">un tableau des valeurs numériques du {@link Vector}</span>
     */
    void setMatrix(byte[] matrix);

    /**
     * <div class="fr">Réinitialisation du {@link Vector} par référence.</div>
     *
     * @param matrix <span class="fr">tableau de valeurs à référencer</span>
     */
    void resetMatrix(byte[] matrix);

    byte linearForm(ByteVector other);
}
