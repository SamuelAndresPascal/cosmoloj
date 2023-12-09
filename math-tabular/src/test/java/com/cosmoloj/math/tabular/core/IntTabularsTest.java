package com.cosmoloj.math.tabular.core;

import com.cosmoloj.math.tabular.line.DefaultIntVector;
import com.cosmoloj.math.tabular.line.IntVector;
import com.cosmoloj.math.tabular.matrix.IntMatrix;
import com.cosmoloj.math.tabular.matrix.DefaultIntMatrix;
import com.cosmoloj.math.tabular.matrix.Matrix.Dimension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class IntTabularsTest {

    @Test
    public void testMult1() {

        final IntMatrix first = DefaultIntMatrix.rows(3,
                1, 2, 3,
                4, 5, 6,
                7, 8, 9);

        final IntMatrix second = DefaultIntMatrix.rows(3,
                1, 2, 3,
                4, 5, 6,
                7, 8, 9);

        final IntMatrix result = new DefaultIntMatrix(IntTabulars.mult(first, second));

        final IntMatrix expected = DefaultIntMatrix.rows(3,
                30, 36, 42,
                66, 81, 96,
                102, 126, 150);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testMult2() {

        final IntMatrix first = DefaultIntMatrix.rows(4,
                1, 2, 3, 5,
                4, 5, 6, 3,
                7, 8, 9, 8);

        final IntMatrix second = DefaultIntMatrix.rows(3,
                1, 2, 3,
                4, 5, 6,
                7, 8, 9,
                2, 1, 3);

        final IntMatrix result = new DefaultIntMatrix(IntTabulars.mult(first, second));

        final IntMatrix expected = DefaultIntMatrix.rows(3,
                40, 41, 57,
                72, 84, 105,
                118, 134, 174);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testMult3() {

        final IntVector first = new DefaultIntVector(1, 2, 3);

        final IntMatrix second = DefaultIntMatrix.rows(3,
                1, 2, 3,
                4, 5, 6,
                7, 8, 9);

        final IntVector result = new DefaultIntVector(IntTabulars.mult(first, second));

        final IntVector expected = new DefaultIntVector(30, 36, 42);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }


    @Test
    public void testMult4() {

        final IntVector first = new DefaultIntVector(1, 2, 3);

        final IntMatrix second = DefaultIntMatrix.rows(4,
                1, 2, 3, 4,
                4, 5, 6, 5,
                7, 8, 9, 6);

        final IntVector result = new DefaultIntVector(IntTabulars.mult(first, second));

        final IntVector expected = new DefaultIntVector(30, 36, 42, 32);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testMult5() {

        final IntMatrix first = DefaultIntMatrix.rows(3,
                1, 2, 3,
                4, 5, 6,
                7, 8, 9);

        final IntVector second = new DefaultIntVector(1, 2, 3);

        final IntVector result = new DefaultIntVector(IntTabulars.mult(first, second));

        final IntVector expected = new DefaultIntVector(14, 32, 50);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testMult6() {

        final IntMatrix first = DefaultIntMatrix.rows(4,
                1, 2, 3, 4,
                4, 5, 6, 5,
                7, 8, 9, 6);

        final IntVector second = new DefaultIntVector(1, 2, 3, 4);

        final IntVector result = new DefaultIntVector(IntTabulars.mult(first, second));

        final IntVector expected = new DefaultIntVector(30, 52, 74);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testMult7() {

        final IntVector first = new DefaultIntVector(1, 2, 3);

        final IntVector second = new DefaultIntVector(4, 5, 6);

        final int result = IntTabulars.mult(first, second);

        final int expected = 32;

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testMult8() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
        final IntVector first = new DefaultIntVector(1, 2, 3);

        final IntVector second = new DefaultIntVector(4, 5, 6, 3);

        IntTabulars.mult(first, second);
        });
    }

    @Test
    public void testMult9() {

        final IntVector first = new DefaultIntVector(2, 1);

        final IntVector second = new DefaultIntVector(3, 4);

        final IntMatrix result = new DefaultIntMatrix(IntTabulars.multmn(first, second));

        final IntMatrix expected = DefaultIntMatrix.rows(2,
                6, 8,
                3, 4);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testCopy1() {

        final IntVector first = new DefaultIntVector(2, 1, 3, 4);

        final IntVector result = new DefaultIntVector(IntTabulars.copy(first));

        final IntVector expected = new DefaultIntVector(2, 1, 3, 4);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testCopy2() {

        final IntMatrix first = DefaultIntMatrix.rows(4,
                1, 2, 3, 4,
                4, 5, 6, 5,
                7, 8, 9, 6);

        final IntMatrix result = new DefaultIntMatrix(IntTabulars.copy(first));

        final IntMatrix expected = DefaultIntMatrix.rows(4,
                1, 2, 3, 4,
                4, 5, 6, 5,
                7, 8, 9, 6);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testTranspose1() {

        final IntVector first = new DefaultIntVector(2, 1, 3, 4);

        final IntVector result = new DefaultIntVector(IntTabulars.transpose(first));

        final IntVector expected = new DefaultIntVector(2, 1, 3, 4);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testTranspose2() {

        final IntMatrix first = DefaultIntMatrix.rows(4,
                1, 2, 3, 4,
                4, 5, 6, 5,
                7, 8, 9, 6);

        final IntMatrix result = new DefaultIntMatrix(IntTabulars.transpose(first));

        final IntMatrix expected = DefaultIntMatrix.columns(4,
                1, 2, 3, 4,
                4, 5, 6, 5,
                7, 8, 9, 6);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testAdd1() {

        final IntVector first = new DefaultIntVector(2, 1, 3, 4);
        final IntVector second = new DefaultIntVector(3, 1, 3, 4);

        final IntVector result = new DefaultIntVector(IntTabulars.add(first, second));

        final IntVector expected = new DefaultIntVector(5, 2, 6, 8);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testAdd2() {

        final IntMatrix first = DefaultIntMatrix.rows(4,
                1, 2, 3, 4,
                4, 5, 6, 5,
                7, 8, 9, 6);
        final IntMatrix second = DefaultIntMatrix.rows(4,
                6, 2, 3, 4,
                4, 5, 6, 5,
                7, 8, 9, 6);

        final IntMatrix result = new DefaultIntMatrix(IntTabulars.add(first, second));

        final IntMatrix expected = DefaultIntMatrix.rows(4,
                7, 4, 6, 8,
                8, 10, 12, 10,
                14, 16, 18, 12);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testMinus1() {

        final IntVector first = new DefaultIntVector(2, 1, 3, 4);
        final IntVector second = new DefaultIntVector(3, 1, 3, 4);

        final IntVector result = new DefaultIntVector(IntTabulars.minus(first, second));

        final IntVector expected = new DefaultIntVector(-1, 0, 0, 0);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testMinus2() {

        final IntMatrix first = DefaultIntMatrix.rows(4,
                1, 2, 3, 4,
                4, 5, 6, 5,
                7, 8, 9, 6);
        final IntMatrix second = DefaultIntMatrix.rows(4,
                6, 2, 3, 4,
                4, 5, 6, 5,
                7, 8, 9, 6);

        final IntMatrix result = new DefaultIntMatrix(IntTabulars.minus(first, second));

        final IntMatrix expected = DefaultIntMatrix.rows(4,
                -5, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testExternal1() {

        final IntVector first = new DefaultIntVector(2, 1, 3, 4);

        final IntVector result = new DefaultIntVector(IntTabulars.external(3, first));

        final IntVector expected = new DefaultIntVector(6, 3, 9, 12);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testExternal2() {

        final IntMatrix first = DefaultIntMatrix.rows(4,
                1, 2, 3, 4,
                4, 5, 6, 5,
                7, 8, 9, 6);

        final IntMatrix result = new DefaultIntMatrix(IntTabulars.external(2, first));

        final IntMatrix expected = DefaultIntMatrix.rows(4,
                2, 4, 6, 8,
                8, 10, 12, 10,
                14, 16, 18, 12);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testValue1() {

        final IntMatrix first = DefaultIntMatrix.rows(4,
                1, 2, 3, 4,
                4, 5, 6, 5,
                7, 8, 9, 6);

        final int result = IntTabulars.value(first, 1, 2);

        final int expected = 6;

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testValue2() {

        final IntVector first = new DefaultIntVector(1, 2, 3, 4, 9, 6);

        final int result = IntTabulars.value(first, 4);

        final int expected = 9;

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testLine() {

        final IntMatrix first = DefaultIntMatrix.rows(4,
                1, 2, 3, 4,
                4, 5, 6, 5,
                7, 8, 9, 6);

        final IntVector result = new DefaultIntVector(IntTabulars.line(first, 1));

        final IntVector expected = new DefaultIntVector(4, 5, 6, 5);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testColumn() {

        final IntMatrix first = DefaultIntMatrix.rows(4,
                1, 2, 3, 4,
                4, 5, 6, 5,
                7, 8, 9, 6);

        final IntVector result = new DefaultIntVector(IntTabulars.column(first, 1));

        final IntVector expected = new DefaultIntVector(2, 5, 8);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testVector1() {

        final IntMatrix first = DefaultIntMatrix.rows(4,
                1, 2, 3, 4,
                4, 5, 6, 5,
                7, 8, 9, 6);

        final IntVector result = new DefaultIntVector(IntTabulars.vector(first, 1, Dimension.ROW));

        final IntVector expected = new DefaultIntVector(4, 5, 6, 5);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testVector2() {

        final IntMatrix first = DefaultIntMatrix.rows(4,
                1, 2, 3, 4,
                4, 5, 6, 5,
                7, 8, 9, 6);

        final IntVector result = new DefaultIntVector(IntTabulars.vector(first, 2, Dimension.COLUMN));

        final IntVector expected = new DefaultIntVector(3, 6, 9);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testMatrix1() {

        final IntVector first = new DefaultIntVector(1, 2, 3, 4);

        final IntMatrix result = new DefaultIntMatrix(IntTabulars.matrix(first, Dimension.ROW));

        final IntMatrix expected = DefaultIntMatrix.rows(4,
                1, 2, 3, 4);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testMatrix2() {

        final IntVector first = new DefaultIntVector(1, 2, 3, 4);

        final IntMatrix result = new DefaultIntMatrix(IntTabulars.matrix(first, Dimension.COLUMN));

        final IntMatrix expected = DefaultIntMatrix.columns(4,
                1, 2, 3, 4);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testDiagonal1() {

        final IntVector first = new DefaultIntVector(1, 2, 3, 4);

        final IntMatrix result = new DefaultIntMatrix(IntTabulars.diagonal(first));

        final IntMatrix expected = DefaultIntMatrix.rows(4,
                1, 0, 0, 0,
                0, 2, 0, 0,
                0, 0, 3, 0,
                0, 0, 0, 4);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testDiagonal2() {

        final IntMatrix result = new DefaultIntMatrix(IntTabulars.diagonal(32, 5));

        final IntMatrix expected = DefaultIntMatrix.rows(5,
                32, 0, 0, 0, 0,
                0, 32, 0, 0, 0,
                0, 0, 32, 0, 0,
                0, 0, 0, 32, 0,
                0, 0, 0, 0, 32);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testDiagonal3() {

        final IntMatrix result = new DefaultIntMatrix(IntTabulars.diagonal(12, 5, 7));

        final IntMatrix expected = DefaultIntMatrix.rows(7,
                12, 0, 0, 0, 0, 0, 0,
                0, 12, 0, 0, 0, 0, 0,
                0, 0, 12, 0, 0, 0, 0,
                0, 0, 0, 12, 0, 0, 0,
                0, 0, 0, 0, 12, 0, 0);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testDiagonal4() {

        final IntMatrix result = new DefaultIntMatrix(IntTabulars.diagonal(34, 8, 5));

        final IntMatrix expected = DefaultIntMatrix.rows(5,
                34, 0, 0, 0, 0,
                0, 34, 0, 0, 0,
                0, 0, 34, 0, 0,
                0, 0, 0, 34, 0,
                0, 0, 0, 0, 34,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testFill1() {

        final IntMatrix result = new DefaultIntMatrix(IntTabulars.fill(7, 8, 5));

        final IntMatrix expected = DefaultIntMatrix.rows(5,
                7, 7, 7, 7, 7,
                7, 7, 7, 7, 7,
                7, 7, 7, 7, 7,
                7, 7, 7, 7, 7,
                7, 7, 7, 7, 7,
                7, 7, 7, 7, 7,
                7, 7, 7, 7, 7,
                7, 7, 7, 7, 7);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testFill2() {

        final IntMatrix result = new DefaultIntMatrix(IntTabulars.fill(38, 5, 7));

        final IntMatrix expected = DefaultIntMatrix.rows(7,
                38, 38, 38, 38, 38, 38, 38,
                38, 38, 38, 38, 38, 38, 38,
                38, 38, 38, 38, 38, 38, 38,
                38, 38, 38, 38, 38, 38, 38,
                38, 38, 38, 38, 38, 38, 38);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testFill3() {

        final IntVector result = new DefaultIntVector(IntTabulars.fill(54, 6));

        final IntVector expected = new DefaultIntVector(54, 54, 54, 54, 54, 54);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testHadamard() {

        final IntMatrix first = DefaultIntMatrix.rows(3,
                1, 3, 2,
                1, 0, 0,
                1, 2, 2);
        final IntMatrix second = DefaultIntMatrix.rows(3,
                0, 0, 2,
                7, 5, 0,
                2, 1, 1);

        final IntMatrix result = new DefaultIntMatrix(IntTabulars.hadamard(first, second));

        final IntMatrix expected = DefaultIntMatrix.rows(3,
                0, 0, 4,
                7, 0, 0,
                2, 2, 2);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }

    @Test
    public void testKronecker() {

        final IntMatrix first = DefaultIntMatrix.rows(2,
                1, 2,
                3, 1);
        final IntMatrix second = DefaultIntMatrix.rows(2,
                0, 3,
                2, 1);

        final IntMatrix result = new DefaultIntMatrix(IntTabulars.kronecker(first, second));

        final IntMatrix expected = DefaultIntMatrix.rows(4,
                0, 3, 0, 6,
                2, 1, 4, 2,
                0, 9, 0, 3,
                6, 3, 2, 1);

        Assertions.assertTrue(IntTabulars.equal(expected, result));
    }
}
