package com.cosmoloj.math.tabular;

import com.cosmoloj.math.tabular.core.IntTabulars;
import com.cosmoloj.math.tabular.line.DefaultIntVector;
import com.cosmoloj.math.tabular.line.IntVector;
import com.cosmoloj.math.tabular.matrix.DefaultIntMatrix;
import com.cosmoloj.math.tabular.matrix.IntMatrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class DefaultIntVectorTest {

    @Test
    public void testGetDimension() {

        final IntVector instance = new DefaultIntVector(1, 2, 3);
        Assertions.assertEquals(3, instance.getDimension());
    }

    @Test @Disabled
    public void testSet() {

        int i = 0;
        int c = 0;
        DefaultIntVector instance = null;
        instance.set(c, i);
        // TODO review the generated test code and remove the default call to fail.
        Assertions.fail("The test case is a prototype.");
    }

    @Test @Disabled
    public void testGet() {

        int i = 0;
        DefaultIntVector instance = null;
        double expResult = 0.0;
        double result = instance.get(i);
        Assertions.assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        Assertions.fail("The test case is a prototype.");
    }

    @Test @Disabled
    public void testGetMatrix() {

        DefaultIntVector instance = null;
        int[] expResult = null;
        int[] result = instance.getMatrix();
        Assertions.assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        Assertions.fail("The test case is a prototype.");
    }

    @Test @Disabled
    public void testSetMatrix() {

        int[] components = null;
        DefaultIntVector instance = null;
        instance.setMatrix(components);
        // TODO review the generated test code and remove the default call to fail.
        Assertions.fail("The test case is a prototype.");
    }

    @Test
    public void testMult_IntMatrix1D() {

        final IntVector instance1 = new DefaultIntVector(1, 2, 3);
        final IntVector instance2 = new DefaultIntVector(1, 2, 3);

        Assertions.assertEquals(14., instance1.linearForm(instance2));
        Assertions.assertEquals(14., instance2.linearForm(instance1));
    }

    @Test
    public void testMult_IntMatrix2D() {

        // 2X2 I test
        final IntVector instance2 = new DefaultIntVector(1, 2);
        final IntMatrix instance1 = DefaultIntMatrix.rows(2,
                1, 0,
                0, 1);
        Assertions.assertEquals(instance2, new DefaultIntVector(IntTabulars.mult(instance2, instance1)));

        // 3x3 I test
        final IntVector instance20 = new DefaultIntVector(1, 2, 3);
        final IntMatrix instance10 = DefaultIntMatrix.rows(3,
                1, 0, 0,
                0, 1, 0,
                0, 0, 1);
        Assertions.assertEquals(instance20, new DefaultIntVector(IntTabulars.mult(instance20, instance10)));

        // 3x3 diagonal test
        final IntVector instance21 = new DefaultIntVector(1, 2, 3);
        final IntMatrix instance11 = DefaultIntMatrix.rows(3,
                1, 0, 0,
                0, 2, 0,
                0, 0, 3);
        Assertions.assertEquals(new DefaultIntVector(1, 4, 9),
                new DefaultIntVector(IntTabulars.mult(instance21, instance11)));

        // 3x3 test (row)
        final IntVector instance22 = new DefaultIntVector(1, 2, 3);
        final IntMatrix instance12 = DefaultIntMatrix.rows(3,
                1, 1, 0,
                0, 2, 0,
                2, 0, 3);
        Assertions.assertEquals(new DefaultIntVector(7, 5, 9),
                new DefaultIntVector(IntTabulars.mult(instance22, instance12)));

        // 3x3 test (row prepare for colum test)
        final IntVector instance23 = new DefaultIntVector(1, 2, 3);
        final IntMatrix instance13 = DefaultIntMatrix.rows(3,
                1, 0, 2,
                1, 2, 0,
                0, 0, 3);
        Assertions.assertEquals(new DefaultIntVector(3, 4, 11),
                new DefaultIntVector(IntTabulars.mult(instance23, instance13)));

        // 3x3 test (column)
        final IntVector instance24 = new DefaultIntVector(1, 2, 3);
        final IntMatrix instance14 = DefaultIntMatrix.columns(3,
                1, 1, 0,
                0, 2, 0,
                2, 0, 3);
        Assertions.assertEquals(new DefaultIntVector(3, 4, 11),
                new DefaultIntVector(IntTabulars.mult(instance24, instance14)));

        // 4x3 test
        final IntMatrix instance15 = DefaultIntMatrix.rows(3,
                1, 1, 0,
                0, 2, 0,
                2, 0, 3,
                2, 1, 2);
        final IntVector instance25 = new DefaultIntVector(1, 2, 3, 4);
        Assertions.assertEquals(new DefaultIntVector(15, 9, 17),
                new DefaultIntVector(IntTabulars.mult(instance25, instance15)));
    }

}
