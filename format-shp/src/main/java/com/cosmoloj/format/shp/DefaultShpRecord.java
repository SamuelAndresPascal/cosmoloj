package com.cosmoloj.format.shp;

import java.util.Arrays;

/**
 * <div class="fr">Implémentation par défaut de la représentation d'un enregistrement, incluant en-tête, type de forme
 * et données géométriques.</div>
 *
 * @author Samuel Andrés
 * @param <G> <span class="fr">type de données géométriques dans la représentation lue dans le fichier</span>
 */
public class DefaultShpRecord<G> implements ShpRecord<G> {

    private final int recordNumber;
    private final int recordLength;
    private final int shapeType;
    private final double[] bbox;
    private final G geometry;
    private final double[] measureRange;
    private final double[] measureValues;
    private final double[] zRange;
    private final double[] zValues;
    private final int[] partTypes;

    public DefaultShpRecord(final int recordNumber, final int recordLength, final int shapeType, final double[] bbox,
            final G geometry, final double[] measureRange, final double[] measureValues, final double[] zRange,
            final double[] zValues, final int[] partTypes) {
        this.recordNumber = recordNumber;
        this.recordLength = recordLength;
        this.shapeType = shapeType;
        this.bbox = bbox;
        this.geometry = geometry;
        this.measureRange = measureRange;
        this.measureValues = measureValues;
        this.zRange = zRange;
        this.zValues = zValues;
        this.partTypes = partTypes;
    }

    @Override
    public int getRecordNumber() {
        return recordNumber;
    }

    @Override
    public int getRecordLength() {
        return recordLength;
    }

    @Override
    public int getShapeType() {
        return shapeType;
    }

    @Override
    public G getGeometry() {
        return geometry;
    }

    @Override
    public double[] getBbox() {
        return bbox;
    }

    @Override
    public double[] getMeasureRange() {
        return measureRange;
    }

    @Override
    public double[] getMeasureValues() {
        return measureValues;
    }

    @Override
    public double[] getZRange() {
        return zRange;
    }

    @Override
    public double[] getZValues() {
        return zValues;
    }

    @Override
    public int[] getPartTypes() {
        return partTypes;
    }

    @Override
    public String toString() {
        return "ShpRecord{" + "recordNumber=" + recordNumber + ", recordLength=" + recordLength
                + ", shapeType=" + shapeType + ", bbox=" + Arrays.toString(bbox) + ", geometry=" + geometry
                + ", measureRange=" + Arrays.toString(measureRange)
                + ", measureValues=" + Arrays.toString(measureValues)
                + ", zRange=" + Arrays.toString(zRange) + ", zValues=" + Arrays.toString(zValues)
                + ", partTypes=" + Arrays.toString(partTypes) + '}';
    }
}
