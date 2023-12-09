package com.cosmoloj.format.shp;

/**
 *
 * @author Samuel Andrés
 */
public final class DefaultRecordFactory implements RecordFactory<DefaultShpRecord<?>> {

    private DefaultRecordFactory() {
    }

    public static final RecordFactory INSTANCE = new DefaultRecordFactory();

    @Override
    public DefaultShpRecord<?> get(final int recordNumber, final int recordLength, final int shapeType,
            final double[] bbox, final Object geometry, final double[] measureRange, final double[] measureValues,
            final double[] zRange, final double[] zValues, final int[] partTypes) {
        return new DefaultShpRecord<>(recordNumber, recordLength, shapeType, bbox, geometry, measureRange,
                measureValues, zRange, zValues, partTypes);
    }
}
