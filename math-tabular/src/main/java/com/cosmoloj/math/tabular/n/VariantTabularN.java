package com.cosmoloj.math.tabular.n;

/**
 *
 * @author Samuel Andrés
 */
public interface VariantTabularN extends IndicedTabularN {

    enum Variant {
        CONTRAVARIANT, COVARIANT
    }

    /**
     * <div class="fr">Indique pour un index donné s'il est variant ou covariant.</div>
     *
     * @param index <span class="fr">index</span>
     * @return <span class="fr">la variance de l'index</span>
     */
    Variant getDimensionVariant(int index);
}
