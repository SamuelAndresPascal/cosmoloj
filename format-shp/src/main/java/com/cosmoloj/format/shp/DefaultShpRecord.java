package com.cosmoloj.format.shp;

import java.util.Arrays;
import java.util.Objects;

/**
 * <div class="fr">Implémentation par défaut de la représentation d'un enregistrement, incluant en-tête, type de forme
 * et données géométriques.</div>
 *
 * @author Samuel Andrés
 * @param <G> <span class="fr">type de données géométriques dans la représentation lue dans le fichier</span>
 */
public record DefaultShpRecord<G>(
    int recordNumber,
    int recordLength,
    int shapeType,
    double[] bbox,
    G geometry,
    double[] measureRange,
    double[] measureValues,
    double[] zRange,
    double[] zValues,
    int[] partTypes) implements ShpRecord<G> {

    @Override
    public String toString() {
        return "ShpRecord{" + "recordNumber=" + recordNumber + ", recordLength=" + recordLength
                + ", shapeType=" + shapeType + ", bbox=" + Arrays.toString(bbox) + ", geometry=" + geometry
                + ", measureRange=" + Arrays.toString(measureRange)
                + ", measureValues=" + Arrays.toString(measureValues)
                + ", zRange=" + Arrays.toString(zRange) + ", zValues=" + Arrays.toString(zValues)
                + ", partTypes=" + Arrays.toString(partTypes) + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.recordNumber;
        hash = 17 * hash + this.recordLength;
        hash = 17 * hash + this.shapeType;
        hash = 17 * hash + Arrays.hashCode(this.bbox);
        hash = 17 * hash + Objects.hashCode(this.geometry);
        hash = 17 * hash + Arrays.hashCode(this.measureRange);
        hash = 17 * hash + Arrays.hashCode(this.measureValues);
        hash = 17 * hash + Arrays.hashCode(this.zRange);
        hash = 17 * hash + Arrays.hashCode(this.zValues);
        hash = 17 * hash + Arrays.hashCode(this.partTypes);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DefaultShpRecord<?> other = (DefaultShpRecord<?>) obj;
        if (this.recordNumber != other.recordNumber) {
            return false;
        }
        if (this.recordLength != other.recordLength) {
            return false;
        }
        if (this.shapeType != other.shapeType) {
            return false;
        }
        if (!Arrays.equals(this.bbox, other.bbox)) {
            return false;
        }
        if (!Objects.equals(this.geometry, other.geometry)) {
            return false;
        }
        if (!Arrays.equals(this.measureRange, other.measureRange)) {
            return false;
        }
        if (!Arrays.equals(this.measureValues, other.measureValues)) {
            return false;
        }
        if (!Arrays.equals(this.zRange, other.zRange)) {
            return false;
        }
        if (!Arrays.equals(this.zValues, other.zValues)) {
            return false;
        }
        return Arrays.equals(this.partTypes, other.partTypes);
    }
}
