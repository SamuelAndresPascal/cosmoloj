package com.cosmoloj.format.shp;

/**
 *
 * @author Samuel Andrés
 */
public final class ShapeType {

    private ShapeType() {
    }

    public static final int NULL_SHAPE = 0;

    public static final int POINT = 1;
    public static final int POLY_LINE = 3;
    public static final int POLYGON = 5;
    public static final int MULTI_POINT = 8;

    public static final int POINT_Z = 11;
    public static final int POLY_LINE_Z = 13;
    public static final int POLYGON_Z = 15;
    public static final int MULTI_POINT_Z = 18;

    public static final int POINT_M = 21;
    public static final int POLY_LINE_M = 23;
    public static final int POLYGON_M = 25;
    public static final int MULTI_POINT_M = 28;

    public static final int MULTI_PATCH = 31;
}
