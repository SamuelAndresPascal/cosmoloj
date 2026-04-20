package com.cosmoloj.format.shp;

/**
 *
 * @author Samuel Andrés
 */
public final class DefaultRecordFactory implements RecordFactory<ShpRecord<?>> {

    private DefaultRecordFactory() {
    }

    public static final RecordFactory<ShpRecord<?>> INSTANCE = new DefaultRecordFactory();


    @Override
    public ShpRecord<double[]> point(final int recordNumber, final int recordLength, final int shapeType,
            final double[] bbox, final double[] geometry, final double[] measureRange, final double[] measureValues,
            final double[] zRange, final double[] zValues, final int[] partTypes) {
        return new DefaultShpRecord<>(recordNumber, recordLength, shapeType, bbox, geometry, measureRange,
                measureValues, zRange, zValues, partTypes);
    }

    @Override
    public ShpRecord<double[][]> multiPoint(final int recordNumber, final int recordLength, final int shapeType,
            final double[] bbox, final double[][] geometry, final double[] measureRange, final double[] measureValues,
            final double[] zRange, final double[] zValues, final int[] partTypes) {
        return new DefaultShpRecord<>(recordNumber, recordLength, shapeType, bbox, geometry, measureRange,
                measureValues, zRange, zValues, partTypes);
    }

    @Override
    public ShpRecord<double[][][]> polyLine(final int recordNumber, final int recordLength, final int shapeType,
            final double[] bbox, final double[][][] geometry, final double[] measureRange, final double[] measureValues,
            final double[] zRange, final double[] zValues, final int[] partTypes) {
        return new DefaultShpRecord<>(recordNumber, recordLength, shapeType, bbox, geometry, measureRange,
                measureValues, zRange, zValues, partTypes);
    }

    @Override
    public ShpRecord<double[][][]> polygon(final int recordNumber, final int recordLength, final int shapeType,
            final double[] bbox, final double[][][] geometry, final double[] measureRange, final double[] measureValues,
            final double[] zRange, final double[] zValues, final int[] partTypes) {
        return new DefaultShpRecord<>(recordNumber, recordLength, shapeType, bbox, geometry, measureRange,
                measureValues, zRange, zValues, partTypes);
    }

    @Override
    public ShpRecord<double[][][]> multiPatch(final int recordNumber, final int recordLength, final int shapeType,
            final double[] bbox, final double[][][] geometry, final double[] measureRange, final double[] measureValues,
            final double[] zRange, final double[] zValues, final int[] partTypes) {
        return new DefaultShpRecord<>(recordNumber, recordLength, shapeType, bbox, geometry, measureRange,
                measureValues, zRange, zValues, partTypes);
    }
}
