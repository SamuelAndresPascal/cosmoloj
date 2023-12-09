package com.cosmoloj.format.shp;

/**
 *
 * @author Samuel Andrés
 * @param <R>
 */
public interface RecordFactory<R extends ShpRecord<?>> {

    R  get(int recordNumber, int recordLength, int shapeType, double[] bbox, Object geometry, double[] measureRange,
            double[] measureValues, double[] zRange, double[] zValues, int[] partTypes);
}
