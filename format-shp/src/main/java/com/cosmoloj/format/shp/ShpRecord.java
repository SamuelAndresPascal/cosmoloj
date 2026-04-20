package com.cosmoloj.format.shp;

/**
 * <div class="fr">Représentation d'un enregistrement, incluant en-tête, type de forme et données géométriques.</div>
 *
 * @author Samuel Andrés
 * @param <G> <span class="fr">type de données géométriques dans la représentation lue dans le fichier</span>
 */
public interface ShpRecord<G> {

    /**
     * <div class="fr">Numéro d'enregistrement.</div>
     *
     * @return <span class="fr">numéro d'enregistrement</span>
     */
    int recordNumber();

    /**
     * <div class="fr">Taille de l'enregistrement en nombre de mots de 16 bits.</div>
     *
     * @return <span class="fr">taille de l'enregistrement</span>
     */
    int recordLength();

    /**
     * <div class="fr">Type de forme.</div>
     *
     * @return <span class="fr">code du type de forme</span>
     * @see ShapeType
     * @see ShapeCode
     */
    int shapeType();

    /**
     * <div class="fr">Données géométriques brutes telles que lues dans le fichier, structurées en fonction du type de
     * forme.</div>
     *
     * @return <span class="fr">données géométriques brutes</span>
     */
    G geometry();

    /**
     * <div class="fr">Emprise spatiale des données géométriques.</div>
     *
     * @return <span class="fr">emprise spatiale des données géométriques</span>
     */
    double[] bbox();

    /**
     * <div class="fr">Intervalle des valeurs de mesure.</div>
     *
     * @return <span class="fr">intervalle des valeurs de mesure</span>
     */
    double[] measureRange();

    /**
     * <div class="fr">Valeurs de mesure.</div>
     *
     * @return <span class="fr">valeurs de mesure</span>
     */
    double[] measureValues();

    /**
     * <div class="fr">Intervalle des valeurs de la troisième dimension spatiale.</div>
     *
     * @return <span class="fr">intervalle des valeurs de la troisième dimension spatiale</span>
     */
    double[] zRange();

    /**
     * <div class="fr">Valeurs de la troisième dimension spatiale.</div>
     *
     * @return <span class="fr">valeurs de la troisième dimension spatiale</span>
     */
    double[] zValues();

    /**
     * <div class="fr">Types des parties d'un Multipatch.</div>
     *
     * @return <span class="fr">types des parties d'un Multipatch</span>
     */
    int[] partTypes();
}
