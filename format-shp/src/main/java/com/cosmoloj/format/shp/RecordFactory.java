package com.cosmoloj.format.shp;

/**
 *
 * @author Samuel Andrés
 * @param <R>
 */
public interface RecordFactory<R extends ShpRecord<?>> {

    R point(int recordNumber, int recordLength, int shapeType, double[] bbox, double[] point,
            double[] measureRange, double[] measureValues, double[] zRange, double[] zValues, int[] partTypes);

    R polyLine(int recordNumber, int recordLength, int shapeType, double[] bbox,
            double[][][] geometry, double[] measureRange, double[] measureValues, double[] zRange, double[] zValues,
            int[] partTypes);

    R polygon(int recordNumber, int recordLength, int shapeType, double[] bbox,
            double[][][] geometry, double[] measureRange, double[] measureValues, double[] zRange, double[] zValues,
            int[] partTypes);

    R multiPoint(int recordNumber, int recordLength, int shapeType, double[] bbox,
            double[][] geometry, double[] measureRange, double[] measureValues, double[] zRange, double[] zValues,
            int[] partTypes);

    R multiPatch(int recordNumber, int recordLength, int shapeType, double[] bbox,
            double[][][] geometry, double[] measureRange, double[] measureValues, double[] zRange, double[] zValues,
            int[] partTypes);

    default R get(final int recordNumber, final int recordLength, final int shapeType, final double[] bbox,
            final Object geometry, final double[] measureRange, final double[] measureValues, final double[] zRange,
            final double[] zValues, final int[] partTypes) {
        return switch (shapeType) {
            case ShapeType.NULL_SHAPE -> null;
            case ShapeType.POINT, ShapeType.POINT_M, ShapeType.POINT_Z ->
                point(recordNumber, recordLength, shapeType, bbox, (double[]) geometry, measureRange, measureValues,
                        zRange, zValues, partTypes);
            case ShapeType.POLY_LINE, ShapeType.POLY_LINE_M, ShapeType.POLY_LINE_Z ->
                polyLine(recordNumber, recordLength, shapeType, bbox, (double[][][]) geometry, measureRange,
                        measureValues, zRange, zValues, partTypes);
            case ShapeType.POLYGON, ShapeType.POLYGON_M, ShapeType.POLYGON_Z ->
                polygon(recordNumber, recordLength, shapeType, bbox, (double[][][]) geometry, measureRange,
                        measureValues, zRange, zValues, partTypes);
            case ShapeType.MULTI_POINT, ShapeType.MULTI_POINT_M, ShapeType.MULTI_POINT_Z ->
                multiPoint(recordNumber, recordLength, shapeType, bbox, (double[][]) geometry, measureRange,
                        measureValues, zRange, zValues, partTypes);
            case ShapeType.MULTI_PATCH ->
                multiPatch(recordNumber, recordLength, shapeType, bbox, (double[][][]) geometry, measureRange,
                        measureValues, zRange, zValues, partTypes);
            default -> throw new IllegalArgumentException("unknown shape type");
        };
    }
}
