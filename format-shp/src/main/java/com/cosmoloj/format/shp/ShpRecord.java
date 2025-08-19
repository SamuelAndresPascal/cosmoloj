package com.cosmoloj.format.shp;

import java.util.Arrays;
import java.util.Objects;

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
    int getRecordNumber();

    /**
     * <div class="fr">Taille de l'enregistrement en nombre de mots de 16 bits.</div>
     *
     * @return <span class="fr">taille de l'enregistrement</span>
     */
    int getRecordLength();

    /**
     * <div class="fr">Type de forme.</div>
     *
     * @return <span class="fr">code du type de forme</span>
     * @see ShapeType
     * @see ShapeCode
     */
    int getShapeType();

    /**
     * <div class="fr">Données géométriques brutes telles que lues dans le fichier, structurées en fonction du type de
     * forme.</div>
     *
     * @return <span class="fr">données géométriques brutes</span>
     */
    G getGeometry();

    /**
     * <div class="fr">Emprise spatiale des données géométriques.</div>
     *
     * @return <span class="fr">emprise spatiale des données géométriques</span>
     */
    double[] getBbox();

    /**
     * <div class="fr">Intervalle des valeurs de mesure.</div>
     *
     * @return <span class="fr">intervalle des valeurs de mesure</span>
     */
    double[] getMeasureRange();

    /**
     * <div class="fr">Valeurs de mesure.</div>
     *
     * @return <span class="fr">valeurs de mesure</span>
     */
    double[] getMeasureValues();

    /**
     * <div class="fr">Intervalle des valeurs de la troisième dimension spatiale.</div>
     *
     * @return <span class="fr">intervalle des valeurs de la troisième dimension spatiale</span>
     */
    double[] getZRange();

    /**
     * <div class="fr">Valeurs de la troisième dimension spatiale.</div>
     *
     * @return <span class="fr">valeurs de la troisième dimension spatiale</span>
     */
    double[] getZValues();

    /**
     * <div class="fr">Types des parties d'un Multipatch.</div>
     *
     * @return <span class="fr">types des parties d'un Multipatch</span>
     */
    int[] getPartTypes();

    /**
     * <div class="fr">Vérifie l'égalité des enregistrements du point de vue des données.</div>
     *
     * @param other
     * @return
     */
    default boolean equalData(final ShpRecord<?> other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (this.getRecordNumber() != other.getRecordNumber()) {
            return false;
        }
        if (this.getRecordLength() != other.getRecordLength()) {
            return false;
        }
        if (this.getShapeType() != other.getShapeType()) {
            return false;
        }
        if (!Arrays.equals(this.getBbox(), other.getBbox())) {
            return false;
        }
        if (!Objects.equals(this.getGeometry(), other.getGeometry())) {
            return false;
        }
        if (!Arrays.equals(this.getMeasureRange(), other.getMeasureRange())) {
            return false;
        }
        if (!Arrays.equals(this.getMeasureValues(), other.getMeasureValues())) {
            return false;
        }
        if (!Arrays.equals(this.getZRange(), other.getZRange())) {
            return false;
        }
        if (!Arrays.equals(this.getZValues(), other.getZValues())) {
            return false;
        }
        return Arrays.equals(this.getPartTypes(), other.getPartTypes());
    }
}
