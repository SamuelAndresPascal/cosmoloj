package com.cosmoloj.math.tabular;

import com.cosmoloj.math.tabular.core.DoubleTabulars;
import com.cosmoloj.math.tabular.line.DefaultDoubleVector;
import com.cosmoloj.math.tabular.line.DoubleVector;
import com.cosmoloj.math.tabular.matrix.DefaultDoubleMatrix;
import com.cosmoloj.math.tabular.matrix.DoubleMatrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class DefaultDoubleVectorTest {

    @Test
    public void testGetDimension() {

        final DoubleVector instance = new DefaultDoubleVector(1, 2, 3);
        Assertions.assertEquals(3, instance.getDimension());
    }

    @Test @Disabled
    public void testGet() {

        int i = 0;
        DefaultDoubleVector instance = null;
        double expResult = 0.0;
        double result = instance.get(i);
        Assertions.assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        Assertions.fail("The test case is a prototype.");
    }

    @Test @Disabled
    public void testGetMatrix() {

        DefaultDoubleVector instance = null;
        double[] expResult = null;
        double[] result = instance.getMatrix();
        Assertions.assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        Assertions.fail("The test case is a prototype.");
    }

    @Test
    public void testMult_DoubleMatrix1D() {

        final DoubleVector instance1 = new DefaultDoubleVector(1, 2, 3);
        final DoubleVector instance2 = new DefaultDoubleVector(1, 2, 3);

        Assertions.assertEquals(14., DoubleTabulars.mult(instance1, instance2));
        Assertions.assertEquals(14., DoubleTabulars.mult(instance2, instance1));
    }

    @Test
    public void testMult_DoubleMatrix2D() {


        // 2X2 I test
        final DoubleVector instance2 = new DefaultDoubleVector(1, 2);
        final DoubleMatrix instance1 = DefaultDoubleMatrix.rows(2,
                1, 0,
                0, 1);
        Assertions.assertTrue(DoubleTabulars.equal(instance2,
                new DefaultDoubleVector(DoubleTabulars.mult(instance2, instance1))));

        // 3x3 I test
        final DoubleVector instance20 = new DefaultDoubleVector(1, 2, 3);
        final DoubleMatrix instance10 = DefaultDoubleMatrix.rows(3,
                1, 0, 0,
                0, 1, 0,
                0, 0, 1);
        Assertions.assertTrue(DoubleTabulars.equal(instance20,
                new DefaultDoubleVector(DoubleTabulars.mult(instance20, instance10))));

        // 3x3 diagonal test
        final DoubleVector instance21 = new DefaultDoubleVector(1, 2, 3);
        final DoubleMatrix instance11 = DefaultDoubleMatrix.rows(3,
                1, 0, 0,
                0, 2, 0,
                0, 0, 3);
        Assertions.assertTrue(DoubleTabulars.equal(new DefaultDoubleVector(1, 4, 9),
                new DefaultDoubleVector(DoubleTabulars.mult(instance21, instance11))));

        // 3x3 test (row)
        final DoubleVector instance22 = new DefaultDoubleVector(1, 2, 3);
        final DoubleMatrix instance12 = DefaultDoubleMatrix.rows(3,
                1, 1, 0,
                0, 2, 0,
                2, 0, 3);
        Assertions.assertTrue(DoubleTabulars.equal(new DefaultDoubleVector(7, 5, 9),
                new DefaultDoubleVector(DoubleTabulars.mult(instance22, instance12))));

        // 3x3 test (row prepare for colum test)
        final DoubleVector instance23 = new DefaultDoubleVector(1, 2, 3);
        final DoubleMatrix instance13 = DefaultDoubleMatrix.rows(3,
                1, 0, 2,
                1, 2, 0,
                0, 0, 3);
        Assertions.assertTrue(DoubleTabulars.equal(new DefaultDoubleVector(3, 4, 11),
                new DefaultDoubleVector(DoubleTabulars.mult(instance23, instance13))));

        // 3x3 test (column)
        final DoubleVector instance24 = new DefaultDoubleVector(1, 2, 3);
        final DoubleMatrix instance14 = DefaultDoubleMatrix.columns(3,
                1, 1, 0,
                0, 2, 0,
                2, 0, 3);
        Assertions.assertTrue(DoubleTabulars.equal(new DefaultDoubleVector(3, 4, 11),
                new DefaultDoubleVector(DoubleTabulars.mult(instance24, instance14))));

        // 4x3 test
        final DoubleMatrix instance15 = DefaultDoubleMatrix.rows(3,
                1, 1, 0,
                0, 2, 0,
                2, 0, 3,
                2, 1, 2);
        final DoubleVector instance25 = new DefaultDoubleVector(1, 2, 3, 4);
        Assertions.assertTrue(DoubleTabulars.equal(new DefaultDoubleVector(15, 9, 17),
                new DefaultDoubleVector(DoubleTabulars.mult(instance25, instance15))));
    }

}
