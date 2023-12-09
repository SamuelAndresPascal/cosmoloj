package com.cosmoloj.math.tabular.core;

import com.cosmoloj.math.tabular.matrix.TypedMatrix;
import com.cosmoloj.math.tabular.line.DefaultDoubleTypedVector;
import com.cosmoloj.math.tabular.line.TypedVector;
import com.cosmoloj.math.tabular.matrix.DefaultDoubleTypedMatrix;
import com.cosmoloj.math.tabular.matrix.Matrix.Dimension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class DoubleTypedTabularsTest {

    @Test
    public void testMult1() {

        final TypedMatrix<Double> first = DefaultDoubleTypedMatrix.rows(3,
                1., 2., 3.,
                4., 5., 6.,
                7., 8., 9.);

        final TypedMatrix<Double> second = DefaultDoubleTypedMatrix.rows(3,
                1., 2., 3.,
                4., 5., 6.,
                7., 8., 9.);

        final TypedMatrix<Double> result = new DefaultDoubleTypedMatrix(DoubleTypedTabulars.mult(first, second));

        final TypedMatrix<Double> expected = DefaultDoubleTypedMatrix.rows(3,
                30., 36., 42.,
                66., 81., 96.,
                102., 126., 150.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testMult2() {

        final TypedMatrix<Double> first = DefaultDoubleTypedMatrix.rows(4,
                1., 2., 3., 5.,
                4., 5., 6., 3.,
                7., 8., 9., 8.);

        final TypedMatrix<Double> second = DefaultDoubleTypedMatrix.rows(3,
                1., 2., 3.,
                4., 5., 6.,
                7., 8., 9.,
                2., 1., 3.);

        final TypedMatrix<Double> result = new DefaultDoubleTypedMatrix(DoubleTypedTabulars.mult(first, second));

        final TypedMatrix<Double> expected = DefaultDoubleTypedMatrix.rows(3,
                40., 41., 57.,
                72., 84., 105.,
                118., 134., 174.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testMult3() {

        final TypedVector<Double> first = new DefaultDoubleTypedVector(1., 2., 3.);

        final TypedMatrix<Double> second = DefaultDoubleTypedMatrix.rows(3,
                1., 2., 3.,
                4., 5., 6.,
                7., 8., 9.);

        final TypedVector<Double> result = new DefaultDoubleTypedVector(DoubleTypedTabulars.mult(first, second));

        final TypedVector<Double> expected = new DefaultDoubleTypedVector(30., 36., 42.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }


    @Test
    public void testMult4() {

        final TypedVector<Double> first = new DefaultDoubleTypedVector(1., 2., 3.);

        final TypedMatrix<Double> second = DefaultDoubleTypedMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final TypedVector<Double> result = new DefaultDoubleTypedVector(DoubleTypedTabulars.mult(first, second));

        final TypedVector<Double> expected = new DefaultDoubleTypedVector(30., 36., 42., 32.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testMult5() {

        final TypedMatrix<Double> first = DefaultDoubleTypedMatrix.rows(3,
                1., 2., 3.,
                4., 5., 6.,
                7., 8., 9.);

        final TypedVector<Double> second = new DefaultDoubleTypedVector(1., 2., 3.);

        final TypedVector<Double> result = new DefaultDoubleTypedVector(DoubleTypedTabulars.mult(first, second));

        final TypedVector<Double> expected = new DefaultDoubleTypedVector(14., 32., 50.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testMult6() {

        final TypedMatrix<Double> first = DefaultDoubleTypedMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final TypedVector<Double> second = new DefaultDoubleTypedVector(1., 2., 3., 4.);

        final TypedVector<Double> result = new DefaultDoubleTypedVector(DoubleTypedTabulars.mult(first, second));

        final TypedVector<Double> expected = new DefaultDoubleTypedVector(30., 52., 74.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testMult7() {

        final TypedVector<Double> first = new DefaultDoubleTypedVector(1., 2., 3.);

        final TypedVector<Double> second = new DefaultDoubleTypedVector(4., 5., 6.);

        final double result = DoubleTypedTabulars.mult(first, second);

        final double expected = 32.;

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testMult8() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
        final TypedVector<Double> first = new DefaultDoubleTypedVector(1., 2., 3.);

        final TypedVector<Double> second = new DefaultDoubleTypedVector(4., 5., 6., 3.);

        DoubleTypedTabulars.mult(first, second);
        });
    }

    @Test
    public void testMult9() {

        final TypedVector<Double> first = new DefaultDoubleTypedVector(2., 1.);

        final TypedVector<Double> second = new DefaultDoubleTypedVector(3., 4.);

        final TypedMatrix<Double> result = new DefaultDoubleTypedMatrix(DoubleTypedTabulars.multmn(first, second));

        final TypedMatrix<Double> expected = DefaultDoubleTypedMatrix.rows(2,
                6., 8.,
                3., 4.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testCopy1() {

        final TypedVector<Double> first = new DefaultDoubleTypedVector(2., 1., 3., 4.);

        final TypedVector<Double> result = new DefaultDoubleTypedVector(DoubleTypedTabulars.copy(first));

        final TypedVector<Double> expected = new DefaultDoubleTypedVector(2., 1., 3., 4.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testCopy2() {

        final TypedMatrix<Double> first = DefaultDoubleTypedMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final TypedMatrix<Double> result = new DefaultDoubleTypedMatrix(DoubleTypedTabulars.copy(first));

        final TypedMatrix<Double> expected = DefaultDoubleTypedMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testTranspose1() {

        final TypedVector<Double> first = new DefaultDoubleTypedVector(2., 1., 3., 4.);

        final TypedVector<Double> result = new DefaultDoubleTypedVector(DoubleTypedTabulars.transpose(first));

        final TypedVector<Double> expected = new DefaultDoubleTypedVector(2., 1., 3., 4.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testTranspose2() {

        final TypedMatrix<Double> first = DefaultDoubleTypedMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final TypedMatrix<Double> result = new DefaultDoubleTypedMatrix(DoubleTypedTabulars.transpose(first));

        final TypedMatrix<Double> expected = DefaultDoubleTypedMatrix.columns(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testAdd1() {

        final TypedVector<Double> first = new DefaultDoubleTypedVector(2., 1., 3., 4.);
        final TypedVector<Double> second = new DefaultDoubleTypedVector(3., 1., 3., 4.);

        final TypedVector<Double> result = new DefaultDoubleTypedVector(DoubleTypedTabulars.add(first, second));

        final TypedVector<Double> expected = new DefaultDoubleTypedVector(5., 2., 6., 8.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testAdd2() {

        final TypedMatrix<Double> first = DefaultDoubleTypedMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);
        final TypedMatrix<Double> second = DefaultDoubleTypedMatrix.rows(4,
                6., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final TypedMatrix<Double> result = new DefaultDoubleTypedMatrix(DoubleTypedTabulars.add(first, second));

        final TypedMatrix<Double> expected = DefaultDoubleTypedMatrix.rows(4,
                7., 4., 6., 8.,
                8., 10., 12., 10.,
                14., 16., 18., 12.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testMinus1() {

        final TypedVector<Double> first = new DefaultDoubleTypedVector(2., 1., 3., 4.);
        final TypedVector<Double> second = new DefaultDoubleTypedVector(3., 1., 3., 4.);

        final TypedVector<Double> result = new DefaultDoubleTypedVector(DoubleTypedTabulars.minus(first, second));

        final TypedVector<Double> expected = new DefaultDoubleTypedVector(-1., 0., 0., 0.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testMinus2() {

        final TypedMatrix<Double> first = DefaultDoubleTypedMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);
        final TypedMatrix<Double> second = DefaultDoubleTypedMatrix.rows(4,
                6., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final TypedMatrix<Double> result = new DefaultDoubleTypedMatrix(DoubleTypedTabulars.minus(first, second));

        final TypedMatrix<Double> expected = DefaultDoubleTypedMatrix.rows(4,
                -5., 0., 0., 0.,
                0., 0., 0., 0.,
                0., 0., 0., 0.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testExternal1() {

        final TypedVector<Double> first = new DefaultDoubleTypedVector(2., 1., 3., 4.);

        final TypedVector<Double> result = new DefaultDoubleTypedVector(DoubleTypedTabulars.external(3., first));

        final TypedVector<Double> expected = new DefaultDoubleTypedVector(6., 3., 9., 12.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testExternal2() {

        final TypedMatrix<Double> first = DefaultDoubleTypedMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final TypedMatrix<Double> result = new DefaultDoubleTypedMatrix(DoubleTypedTabulars.external(2., first));

        final TypedMatrix<Double> expected = DefaultDoubleTypedMatrix.rows(4,
                2., 4., 6., 8.,
                8., 10., 12., 10.,
                14., 16., 18., 12.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testValue1() {

        final TypedMatrix<Double> first = DefaultDoubleTypedMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final double result = DoubleTypedTabulars.value(first, 1, 2);

        final double expected = 6.;

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testValue2() {

        final TypedVector<Double> first = new DefaultDoubleTypedVector(1., 2., 3., 4., 9., 6.);

        final double result = DoubleTypedTabulars.value(first, 4);

        final double expected = 9.;

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testLine() {

        final TypedMatrix<Double> first = DefaultDoubleTypedMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final TypedVector<Double> result = new DefaultDoubleTypedVector(DoubleTypedTabulars.line(first, 1));

        final TypedVector<Double> expected = new DefaultDoubleTypedVector(4., 5., 6., 5.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testColumn() {

        final TypedMatrix<Double> first = DefaultDoubleTypedMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final TypedVector<Double> result = new DefaultDoubleTypedVector(DoubleTypedTabulars.column(first, 1));

        final TypedVector<Double> expected = new DefaultDoubleTypedVector(2., 5., 8.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testVector1() {

        final TypedMatrix<Double> first = DefaultDoubleTypedMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final TypedVector<Double> result = new DefaultDoubleTypedVector(
                DoubleTypedTabulars.vector(first, 1, Dimension.ROW));

        final TypedVector<Double> expected = new DefaultDoubleTypedVector(4., 5., 6., 5.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testVector2() {

        final TypedMatrix<Double> first = DefaultDoubleTypedMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final TypedVector<Double> result = new DefaultDoubleTypedVector(
                DoubleTypedTabulars.vector(first, 2, Dimension.COLUMN));

        final TypedVector<Double> expected = new DefaultDoubleTypedVector(3., 6., 9.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testMatrix1() {

        final TypedVector<Double> first = new DefaultDoubleTypedVector(1., 2., 3., 4.);

        final TypedMatrix<Double> result = new DefaultDoubleTypedMatrix(
                DoubleTypedTabulars.matrix(first, Dimension.ROW));

        final TypedMatrix<Double> expected = DefaultDoubleTypedMatrix.rows(4,
                1., 2., 3., 4.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testMatrix2() {

        final TypedVector<Double> first = new DefaultDoubleTypedVector(1., 2., 3., 4.);

        final TypedMatrix<Double> result = new DefaultDoubleTypedMatrix(
                DoubleTypedTabulars.matrix(first, Dimension.COLUMN));

        final TypedMatrix<Double> expected = DefaultDoubleTypedMatrix.columns(4,
                1., 2., 3., 4.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testDiagonal1() {

        final TypedVector<Double> first = new DefaultDoubleTypedVector(1., 2., 3., 4.);

        final TypedMatrix<Double> result = new DefaultDoubleTypedMatrix(DoubleTypedTabulars.diagonal(first));

        final TypedMatrix<Double> expected = DefaultDoubleTypedMatrix.rows(4,
                1., 0., 0., 0.,
                0., 2., 0., 0.,
                0., 0., 3., 0.,
                0., 0., 0., 4.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testDiagonal2() {

        final TypedMatrix<Double> result = new DefaultDoubleTypedMatrix(DoubleTypedTabulars.diagonal(3.2, 5));

        final TypedMatrix<Double> expected = DefaultDoubleTypedMatrix.rows(5,
                3.2, 0., 0., 0., 0.,
                0., 3.2, 0., 0., 0.,
                0., 0., 3.2, 0., 0.,
                0., 0., 0., 3.2, 0.,
                0., 0., 0., 0., 3.2);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testDiagonal3() {

        final TypedMatrix<Double> result = new DefaultDoubleTypedMatrix(DoubleTypedTabulars.diagonal(1.2, 5, 7));

        final TypedMatrix<Double> expected = DefaultDoubleTypedMatrix.rows(7,
                1.2, 0., 0., 0., 0., 0., 0.,
                0., 1.2, 0., 0., 0., 0., 0.,
                0., 0., 1.2, 0., 0., 0., 0.,
                0., 0., 0., 1.2, 0., 0., 0.,
                0., 0., 0., 0., 1.2, 0., 0.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testDiagonal4() {

        final TypedMatrix<Double> result = new DefaultDoubleTypedMatrix(DoubleTypedTabulars.diagonal(3.4, 8, 5));

        final TypedMatrix<Double> expected = DefaultDoubleTypedMatrix.rows(5,
                3.4, 0., 0., 0., 0.,
                0., 3.4, 0., 0., 0.,
                0., 0., 3.4, 0., 0.,
                0., 0., 0., 3.4, 0.,
                0., 0., 0., 0., 3.4,
                0., 0., 0., 0., 0.,
                0., 0., 0., 0., 0.,
                0., 0., 0., 0., 0.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testFill1() {

        final TypedMatrix<Double> result = new DefaultDoubleTypedMatrix(DoubleTypedTabulars.fill(3.4, 8, 5));

        final TypedMatrix<Double> expected = DefaultDoubleTypedMatrix.rows(5,
                3.4, 3.4, 3.4, 3.4, 3.4,
                3.4, 3.4, 3.4, 3.4, 3.4,
                3.4, 3.4, 3.4, 3.4, 3.4,
                3.4, 3.4, 3.4, 3.4, 3.4,
                3.4, 3.4, 3.4, 3.4, 3.4,
                3.4, 3.4, 3.4, 3.4, 3.4,
                3.4, 3.4, 3.4, 3.4, 3.4,
                3.4, 3.4, 3.4, 3.4, 3.4);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testFill2() {

        final TypedMatrix<Double> result = new DefaultDoubleTypedMatrix(DoubleTypedTabulars.fill(1.2, 5, 7));

        final TypedMatrix<Double> expected = DefaultDoubleTypedMatrix.rows(7,
                1.2, 1.2, 1.2, 1.2, 1.2, 1.2, 1.2,
                1.2, 1.2, 1.2, 1.2, 1.2, 1.2, 1.2,
                1.2, 1.2, 1.2, 1.2, 1.2, 1.2, 1.2,
                1.2, 1.2, 1.2, 1.2, 1.2, 1.2, 1.2,
                1.2, 1.2, 1.2, 1.2, 1.2, 1.2, 1.2);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testFill3() {

        final TypedVector<Double> result = new DefaultDoubleTypedVector(DoubleTypedTabulars.fill(8.3, 6));

        final TypedVector<Double> expected = new DefaultDoubleTypedVector(8.3, 8.3, 8.3, 8.3, 8.3, 8.3);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testHadamard() {

        final TypedMatrix<Double> first = DefaultDoubleTypedMatrix.rows(3,
                1., 3., 2.,
                1., 0., 0.,
                1., 2., 2.);
        final TypedMatrix<Double> second = DefaultDoubleTypedMatrix.rows(3,
                0., 0., 2.,
                7., 5., 0.,
                2., 1., 1.);

        final TypedMatrix<Double> result = new DefaultDoubleTypedMatrix(DoubleTypedTabulars.hadamard(first, second));

        final TypedMatrix<Double> expected = DefaultDoubleTypedMatrix.rows(3,
                0., 0., 4.,
                7., 0., 0.,
                2., 2., 2.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }

    @Test
    public void testKronecker() {

        final TypedMatrix<Double> first = DefaultDoubleTypedMatrix.rows(2,
                1., 2.,
                3., 1.);
        final TypedMatrix<Double> second = DefaultDoubleTypedMatrix.rows(2,
                0., 3.,
                2., 1.);

        final TypedMatrix<Double> result = new DefaultDoubleTypedMatrix(DoubleTypedTabulars.kronecker(first, second));

        final TypedMatrix<Double> expected = DefaultDoubleTypedMatrix.rows(4,
                0., 3., 0., 6.,
                2., 1., 4., 2.,
                0., 9., 0., 3.,
                6., 3., 2., 1.);

        Assertions.assertTrue(TypedTabulars.equal(expected, result));
    }
}
