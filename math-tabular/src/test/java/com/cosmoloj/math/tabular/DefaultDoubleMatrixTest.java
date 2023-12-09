package com.cosmoloj.math.tabular;

import com.cosmoloj.math.tabular.matrix.DefaultDoubleMatrix;
import com.cosmoloj.math.tabular.matrix.DoubleMatrix;
import com.cosmoloj.math.tabular.matrix.Matrix.Dimension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class DefaultDoubleMatrixTest {

    @Test
    public void testGet() {

        final DoubleMatrix rowInstance = DefaultDoubleMatrix.rows(3,
                1, 2, 3,
                4, 5, 6,
                7, 8, 9);

        Assertions.assertEquals(1, rowInstance.get(0, 0));
        Assertions.assertEquals(2, rowInstance.get(0, 1));
        Assertions.assertEquals(3, rowInstance.get(0, 2));
        Assertions.assertEquals(4, rowInstance.get(1, 0));
        Assertions.assertEquals(5, rowInstance.get(1, 1));
        Assertions.assertEquals(6, rowInstance.get(1, 2));
        Assertions.assertEquals(7, rowInstance.get(2, 0));
        Assertions.assertEquals(8, rowInstance.get(2, 1));
        Assertions.assertEquals(9, rowInstance.get(2, 2));


        final DoubleMatrix colInstance = DefaultDoubleMatrix.columns(3,
                1, 2, 3,
                4, 5, 6,
                7, 8, 9);
        Assertions.assertEquals(1, colInstance.get(0, 0));
        Assertions.assertEquals(2, colInstance.get(1, 0));
        Assertions.assertEquals(3, colInstance.get(2, 0));
        Assertions.assertEquals(4, colInstance.get(0, 1));
        Assertions.assertEquals(5, colInstance.get(1, 1));
        Assertions.assertEquals(6, colInstance.get(2, 1));
        Assertions.assertEquals(7, colInstance.get(0, 2));
        Assertions.assertEquals(8, colInstance.get(1, 2));
        Assertions.assertEquals(9, colInstance.get(2, 2));
    }

    @Test
    public void testSetMatrix() {

        final DoubleMatrix rowInstance = DefaultDoubleMatrix.rows(3,
                1, 2, 3,
                4, 5, 6,
                7, 8, 9);

        Assertions.assertEquals(1, rowInstance.get(0, 0));
        Assertions.assertEquals(2, rowInstance.get(0, 1));
        Assertions.assertEquals(3, rowInstance.get(0, 2));
        Assertions.assertEquals(4, rowInstance.get(1, 0));
        Assertions.assertEquals(5, rowInstance.get(1, 1));
        Assertions.assertEquals(6, rowInstance.get(1, 2));
        Assertions.assertEquals(7, rowInstance.get(2, 0));
        Assertions.assertEquals(8, rowInstance.get(2, 1));
        Assertions.assertEquals(9, rowInstance.get(2, 2));


        final DoubleMatrix colInstance = DefaultDoubleMatrix.columns(3,
                1, 2, 3,
                4, 5, 6,
                7, 8, 9);

        Assertions.assertEquals(1, colInstance.get(0, 0));
        Assertions.assertEquals(2, colInstance.get(1, 0));
        Assertions.assertEquals(3, colInstance.get(2, 0));
        Assertions.assertEquals(4, colInstance.get(0, 1));
        Assertions.assertEquals(5, colInstance.get(1, 1));
        Assertions.assertEquals(6, colInstance.get(2, 1));
        Assertions.assertEquals(7, colInstance.get(0, 2));
        Assertions.assertEquals(8, colInstance.get(1, 2));
        Assertions.assertEquals(9, colInstance.get(2, 2));
    }

    @Test
    public void testGetDimension() {

        final DoubleMatrix instance = DefaultDoubleMatrix.rows(2,
                1., 2.,
                3., 4.,
                5., 6.);
        Assertions.assertEquals(3, instance.getDimension(Dimension.ROW));
        Assertions.assertEquals(2, instance.getDimension(Dimension.COLUMN));
    }

}
