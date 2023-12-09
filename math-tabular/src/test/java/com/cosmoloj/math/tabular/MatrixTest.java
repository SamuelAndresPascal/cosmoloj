package com.cosmoloj.math.tabular;

import com.cosmoloj.math.tabular.matrix.Matrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class MatrixTest {

    /**
     * Test of getMatrixDimension method, of class AbstractDoubleMatrix1D.
     */
    @Test
    public void testMATRIX_DIMENSION() {
        System.out.println("getMatrixDimension");
        Assertions.assertEquals(2, Matrix.MATRIX_ORDER);
    }
}
