package com.cosmoloj.math.tabular.core;

import com.cosmoloj.math.tabular.line.BooleanVector;
import com.cosmoloj.math.tabular.line.DefaultBooleanVector;
import com.cosmoloj.math.tabular.matrix.DefaultBooleanMatrix;
import com.cosmoloj.math.tabular.matrix.BooleanMatrix;
import com.cosmoloj.math.tabular.matrix.Matrix.Dimension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class BooleanTabularsTest {

    @Test
    public void testMult1() {

        final BooleanMatrix first = DefaultBooleanMatrix.rows(3,
                1, 0, 0,
                0, 0, 0,
                0, 0, 1);

        final BooleanMatrix second = DefaultBooleanMatrix.rows(3,
                1, 1, 0,
                0, 0, 1,
                0, 1, 1);

        final BooleanMatrix result = new DefaultBooleanMatrix(BooleanTabulars.mult(first, second));

        final BooleanMatrix expected = DefaultBooleanMatrix.rows(3,
                1, 1, 0,
                0, 0, 0,
                0, 1, 1);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testMult2() {

        final BooleanMatrix first = DefaultBooleanMatrix.rows(4,
                1, 0, 0, 1,
                0, 1, 1, 0,
                1, 1, 0, 0);

        final BooleanMatrix second = DefaultBooleanMatrix.rows(3,
                1, 0, 0,
                1, 0, 0,
                1, 1, 1,
                1, 1, 0);

        final BooleanMatrix result = new DefaultBooleanMatrix(BooleanTabulars.mult(first, second));

        final BooleanMatrix expected = DefaultBooleanMatrix.rows(3,
                1, 1, 0,
                1, 1, 1,
                1, 0, 0);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testMult3() {

        final BooleanVector first = new DefaultBooleanVector(1, 1, 0);

        final BooleanMatrix second = DefaultBooleanMatrix.rows(3,
                1, 1, 1,
                0, 0, 0,
                0, 0, 0);

        final BooleanVector result = new DefaultBooleanVector(BooleanTabulars.mult(first, second));

        final BooleanVector expected = new DefaultBooleanVector(1, 1, 1);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }


    @Test
    public void testMult4() {

        final BooleanVector first = new DefaultBooleanVector(1, 0, 1);

        final BooleanMatrix second = DefaultBooleanMatrix.rows(4,
                1, 0, 0, 0,
                1, 0, 1, 1,
                0, 1, 0, 1);

        final BooleanVector result = new DefaultBooleanVector(BooleanTabulars.mult(first, second));

        final BooleanVector expected = new DefaultBooleanVector(1, 1, 0, 1);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testMult5() {

        final BooleanMatrix first = DefaultBooleanMatrix.rows(3,
                1, 1, 0,
                0, 0, 0,
                1, 0, 0);

        final BooleanVector second = new DefaultBooleanVector(1, 1, 0);

        final BooleanVector result = new DefaultBooleanVector(BooleanTabulars.mult(first, second));

        final BooleanVector expected = new DefaultBooleanVector(1, 0, 1);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testMult6() {

        final BooleanMatrix first = DefaultBooleanMatrix.rows(4,
                1, 1, 0, 1,
                1, 1, 0, 1,
                0, 1, 0, 1);

        final BooleanVector second = new DefaultBooleanVector(1, 1, 1, 0);

        final BooleanVector result = new DefaultBooleanVector(BooleanTabulars.mult(first, second));

        final BooleanVector expected = new DefaultBooleanVector(1, 1, 1);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testMult7() {

        final BooleanVector first = new DefaultBooleanVector(1, 1, 0);

        final BooleanVector second = new DefaultBooleanVector(0, 1, 0);

        final boolean result = BooleanTabulars.mult(first, second);

        final boolean expected = true;

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testMult8() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
        final BooleanVector first = new DefaultBooleanVector(1, 1, 1);

        final BooleanVector second = new DefaultBooleanVector(1, 0, 1, 1);

        BooleanTabulars.mult(first, second);
        });
    }

    @Test
    public void testMult9() {

        final BooleanVector first = new DefaultBooleanVector(1, 1);

        final BooleanVector second = new DefaultBooleanVector(1, 0);

        final BooleanMatrix result = new DefaultBooleanMatrix(BooleanTabulars.multmn(first, second));

        final BooleanMatrix expected = DefaultBooleanMatrix.rows(2,
                1, 0,
                1, 0);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testCopy1() {

        final BooleanVector first = new DefaultBooleanVector(1, 1, 0, 1);

        final BooleanVector result = new DefaultBooleanVector(BooleanTabulars.copy(first));

        final BooleanVector expected = new DefaultBooleanVector(1, 1, 0, 1);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testCopy2() {

        final BooleanMatrix first = DefaultBooleanMatrix.rows(4,
                1, 1, 1, 1,
                0, 0, 1, 0,
                1, 1, 0, 0);

        final BooleanMatrix result = new DefaultBooleanMatrix(BooleanTabulars.copy(first));

        final BooleanMatrix expected = DefaultBooleanMatrix.rows(4,
                1, 1, 1, 1,
                0, 0, 1, 0,
                1, 1, 0, 0);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testTranspose1() {

        final BooleanVector first = new DefaultBooleanVector(1, 1, 1, 0);

        final BooleanVector result = new DefaultBooleanVector(BooleanTabulars.transpose(first));

        final BooleanVector expected = new DefaultBooleanVector(1, 1, 1, 0);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testTranspose2() {

        final BooleanMatrix first = DefaultBooleanMatrix.rows(4,
                1, 1, 1, 1,
                0, 0, 1, 0,
                1, 1, 0, 0);

        final BooleanMatrix result = new DefaultBooleanMatrix(BooleanTabulars.transpose(first));

        final BooleanMatrix expected = DefaultBooleanMatrix.columns(4,
                1, 1, 1, 1,
                0, 0, 1, 0,
                1, 1, 0, 0);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testAdd1() {

        final BooleanVector first = new DefaultBooleanVector(1, 1, 0, 1);
        final BooleanVector second = new DefaultBooleanVector(0, 0, 1, 1);

        final BooleanVector result = new DefaultBooleanVector(BooleanTabulars.add(first, second));

        final BooleanVector expected = new DefaultBooleanVector(1, 1, 1, 1);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testAdd2() {

        final BooleanMatrix first = DefaultBooleanMatrix.rows(4,
                1, 1, 1, 1,
                0, 0, 1, 0,
                1, 1, 0, 0);
        final BooleanMatrix second = DefaultBooleanMatrix.rows(4,
                0, 0, 1, 1,
                1, 0, 1, 0,
                0, 1, 0, 0);

        final BooleanMatrix result = new DefaultBooleanMatrix(BooleanTabulars.add(first, second));

        final BooleanMatrix expected = DefaultBooleanMatrix.rows(4,
                1, 1, 1, 1,
                1, 0, 1, 0,
                1, 1, 0, 0);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testExternal1() {

        final BooleanVector first = new DefaultBooleanVector(1, 1, 0, 1);

        final BooleanVector result = new DefaultBooleanVector(BooleanTabulars.external(true, first));

        final BooleanVector expected = new DefaultBooleanVector(1, 1, 0, 1);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testExternal2() {

        final BooleanMatrix first = DefaultBooleanMatrix.rows(4,
                0, 0, 1, 1,
                1, 0, 1, 0,
                0, 1, 0, 0);

        final BooleanMatrix result = new DefaultBooleanMatrix(BooleanTabulars.external(false, first));

        final BooleanMatrix expected = DefaultBooleanMatrix.rows(4,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testValue1() {

        final BooleanMatrix first = DefaultBooleanMatrix.rows(4,
                0, 0, 1, 1,
                1, 0, 1, 0,
                0, 1, 0, 0);

        final boolean result = BooleanTabulars.value(first, 1, 2);

        final boolean expected = true;

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testValue2() {

        final BooleanVector first = new DefaultBooleanVector(1, 1, 0, 1, 0, 1);

        final boolean result = BooleanTabulars.value(first, 4);

        final boolean expected = false;

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testLine() {

        final BooleanMatrix first = DefaultBooleanMatrix.rows(4,
                0, 0, 1, 1,
                1, 0, 1, 0,
                0, 1, 0, 0);

        final BooleanVector result = new DefaultBooleanVector(BooleanTabulars.line(first, 1));

        final BooleanVector expected = new DefaultBooleanVector(1, 0, 1, 0);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testColumn() {

        final BooleanMatrix first = DefaultBooleanMatrix.rows(4,
                0, 0, 1, 1,
                1, 0, 1, 0,
                0, 1, 0, 0);

        final BooleanVector result = new DefaultBooleanVector(BooleanTabulars.column(first, 1));

        final BooleanVector expected = new DefaultBooleanVector(0, 0, 1);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testVector1() {

        final BooleanMatrix first = DefaultBooleanMatrix.rows(4,
                0, 0, 1, 1,
                1, 0, 1, 0,
                0, 1, 0, 0);

        final BooleanVector result = new DefaultBooleanVector(BooleanTabulars.vector(first, 1, Dimension.ROW));

        final BooleanVector expected = new DefaultBooleanVector(1, 0, 1, 0);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testVector2() {

        final BooleanMatrix first = DefaultBooleanMatrix.rows(4,
                0, 0, 1, 1,
                1, 0, 1, 0,
                0, 1, 0, 0);

        final BooleanVector result = new DefaultBooleanVector(BooleanTabulars.vector(first, 2, Dimension.COLUMN));

        final BooleanVector expected = new DefaultBooleanVector(1, 1, 0);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testMatrix1() {

        final BooleanVector first = new DefaultBooleanVector(1, 0, 1, 1);

        final BooleanMatrix result = new DefaultBooleanMatrix(BooleanTabulars.matrix(first, Dimension.ROW));

        final BooleanMatrix expected = DefaultBooleanMatrix.rows(4,
                1, 0, 1, 1);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testMatrix2() {

        final BooleanVector first = new DefaultBooleanVector(1, 1, 1, 0);

        final BooleanMatrix result = new DefaultBooleanMatrix(BooleanTabulars.matrix(first, Dimension.COLUMN));

        final BooleanMatrix expected = DefaultBooleanMatrix.columns(4,
                1, 1, 1, 0);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testDiagonal1() {

        final BooleanVector first = new DefaultBooleanVector(0, 1, 1, 1);

        final BooleanMatrix result = new DefaultBooleanMatrix(BooleanTabulars.diagonal(first));

        final BooleanMatrix expected = DefaultBooleanMatrix.rows(4,
                0, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testDiagonal2() {

        final BooleanMatrix result = new DefaultBooleanMatrix(BooleanTabulars.diagonal(true, 5));

        final BooleanMatrix expected = DefaultBooleanMatrix.rows(5,
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
                0, 0, 0, 0, 1);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testDiagonal3() {

        final BooleanMatrix result = new DefaultBooleanMatrix(BooleanTabulars.diagonal(true, 5, 7));

        final BooleanMatrix expected = DefaultBooleanMatrix.rows(7,
                1, 0, 0, 0, 0, 0, 0,
                0, 1, 0, 0, 0, 0, 0,
                0, 0, 1, 0, 0, 0, 0,
                0, 0, 0, 1, 0, 0, 0,
                0, 0, 0, 0, 1, 0, 0);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testDiagonal4() {

        final BooleanMatrix result = new DefaultBooleanMatrix(BooleanTabulars.diagonal(true, 8, 5));

        final BooleanMatrix expected = DefaultBooleanMatrix.rows(5,
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
                0, 0, 0, 0, 1,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testFill1() {

        final BooleanMatrix result = new DefaultBooleanMatrix(BooleanTabulars.fill(true, 8, 5));

        final BooleanMatrix expected = DefaultBooleanMatrix.rows(5,
                1, 1, 1, 1, 1,
                1, 1, 1, 1, 1,
                1, 1, 1, 1, 1,
                1, 1, 1, 1, 1,
                1, 1, 1, 1, 1,
                1, 1, 1, 1, 1,
                1, 1, 1, 1, 1,
                1, 1, 1, 1, 1);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testFill2() {

        final BooleanMatrix result = new DefaultBooleanMatrix(BooleanTabulars.fill(false, 5, 7));

        final BooleanMatrix expected = DefaultBooleanMatrix.rows(7,
                false, false, false, false, false, false, false,
                false, false, false, false, false, false, false,
                false, false, false, false, false, false, false,
                false, false, false, false, false, false, false,
                false, false, false, false, false, false, false);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testFill3() {

        final BooleanVector result = new DefaultBooleanVector(BooleanTabulars.fill(true, 6));

        final BooleanVector expected = new DefaultBooleanVector(true, true, true, true, true, true);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testHadamard() {

        final BooleanMatrix first = DefaultBooleanMatrix.rows(3,
                1, 1, 1,
                1, 0, 0,
                1, 1, 0);
        final BooleanMatrix second = DefaultBooleanMatrix.rows(3,
                0, 0, 1,
                1, 1, 0,
                1, 1, 1);

        final BooleanMatrix result = new DefaultBooleanMatrix(BooleanTabulars.hadamard(first, second));

        final BooleanMatrix expected = DefaultBooleanMatrix.rows(3,
                0, 0, 1,
                1, 0, 0,
                1, 1, 0);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }

    @Test
    public void testKronecker() {

        final BooleanMatrix first = DefaultBooleanMatrix.rows(2,
                1, 0,
                1, 1);
        final BooleanMatrix second = DefaultBooleanMatrix.rows(2,
                0, 1,
                1, 1);

        final BooleanMatrix result = new DefaultBooleanMatrix(BooleanTabulars.kronecker(first, second));

        final BooleanMatrix expected = DefaultBooleanMatrix.rows(4,
                0, 1, 0, 0,
                1, 1, 0, 0,
                0, 1, 0, 1,
                1, 1, 1, 1);

        Assertions.assertTrue(BooleanTabulars.equal(expected, result));
    }
}
