package com.cosmoloj.math.tabular;

import com.cosmoloj.math.tabular.line.DefaultDoubleVector;
import com.cosmoloj.math.tabular.line.Vector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class VectorTest {

    @Test
    public void testMATRIX_DIMENSION() {

        Assertions.assertEquals(1, Vector.VECTOR_ORDER);
    }

    @Test
    public void testSameDimension_DoubleMatrix1D_DoubleMatrix1D() {

        final Vector first = new DefaultDoubleVector(1., 2., 3.);
        final Vector second = new DefaultDoubleVector(4., 5., 6.);
        Assertions.assertTrue(Vector.sameDimension(first, second));

        final Vector third = new DefaultDoubleVector(4., 5., 6., 7.);
        Assertions.assertFalse(Vector.sameDimension(first, third));
        Assertions.assertFalse(Vector.sameDimension(third, second));
    }
}
