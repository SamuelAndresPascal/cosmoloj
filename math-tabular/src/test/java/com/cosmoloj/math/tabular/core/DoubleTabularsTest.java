package com.cosmoloj.math.tabular.core;

import com.cosmoloj.math.tabular.line.DefaultDoubleVector;
import com.cosmoloj.math.tabular.line.DoubleVector;
import com.cosmoloj.math.tabular.matrix.DefaultDoubleMatrix;
import com.cosmoloj.math.tabular.matrix.DoubleMatrix;
import com.cosmoloj.math.tabular.matrix.Matrix;
import com.cosmoloj.math.tabular.matrix.Matrix.Dimension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class DoubleTabularsTest {

    @Test
    public void testMult1() {

        final DoubleMatrix first = DefaultDoubleMatrix.rows(3,
                1., 2., 3.,
                4., 5., 6.,
                7., 8., 9.);

        final DoubleMatrix second = DefaultDoubleMatrix.rows(3,
                1., 2., 3.,
                4., 5., 6.,
                7., 8., 9.);

        final DoubleMatrix result = new DefaultDoubleMatrix(DoubleTabulars.mult(first, second));

        final DoubleMatrix expected = DefaultDoubleMatrix.rows(3,
                30., 36., 42.,
                66., 81., 96.,
                102., 126., 150.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testMult2() {

        final DoubleMatrix first = DefaultDoubleMatrix.rows(4,
                1., 2., 3., 5.,
                4., 5., 6., 3.,
                7., 8., 9., 8.);

        final DoubleMatrix second = DefaultDoubleMatrix.rows(3,
                1., 2., 3.,
                4., 5., 6.,
                7., 8., 9.,
                2., 1., 3.);

        final DoubleMatrix result = new DefaultDoubleMatrix(DoubleTabulars.mult(first, second));

        final DoubleMatrix expected = DefaultDoubleMatrix.rows(3,
                40., 41., 57.,
                72., 84., 105.,
                118., 134., 174.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testMult3() {

        final DoubleVector first = new DefaultDoubleVector(1., 2., 3.);

        final DoubleMatrix second = DefaultDoubleMatrix.rows(3,
                1., 2., 3.,
                4., 5., 6.,
                7., 8., 9.);

        final DoubleVector result = new DefaultDoubleVector(DoubleTabulars.mult(first, second));

        final DoubleVector expected = new DefaultDoubleVector(30., 36., 42.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }


    @Test
    public void testMult4() {

        final DoubleVector first = new DefaultDoubleVector(1., 2., 3.);

        final DoubleMatrix second = DefaultDoubleMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final DoubleVector result = new DefaultDoubleVector(DoubleTabulars.mult(first, second));

        final DoubleVector expected = new DefaultDoubleVector(30., 36., 42., 32.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testMult5() {

        final DoubleMatrix first = DefaultDoubleMatrix.rows(3,
                1., 2., 3.,
                4., 5., 6.,
                7., 8., 9.);

        final DoubleVector second = new DefaultDoubleVector(1., 2., 3.);

        final DoubleVector result = new DefaultDoubleVector(DoubleTabulars.mult(first, second));

        final DoubleVector expected = new DefaultDoubleVector(14., 32., 50.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testMult6() {

        final DoubleMatrix first = DefaultDoubleMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final DoubleVector second = new DefaultDoubleVector(1., 2., 3., 4.);

        final DoubleVector result = new DefaultDoubleVector(DoubleTabulars.mult(first, second));

        final DoubleVector expected = new DefaultDoubleVector(30., 52., 74.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testMult7() {

        final DoubleVector first = new DefaultDoubleVector(1., 2., 3.);

        final DoubleVector second = new DefaultDoubleVector(4., 5., 6.);

        final double result = DoubleTabulars.mult(first, second);

        final double expected = 32.;

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testMult8() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            final DoubleVector first = new DefaultDoubleVector(1., 2., 3.);

            final DoubleVector second = new DefaultDoubleVector(4., 5., 6., 3.);

            DoubleTabulars.mult(first, second);
        });
    }

    @Test
    public void testMult9() {

        final DoubleVector first = new DefaultDoubleVector(2., 1.);

        final DoubleVector second = new DefaultDoubleVector(3., 4.);

        final DoubleMatrix result = new DefaultDoubleMatrix(DoubleTabulars.multmn(first, second));

        final DoubleMatrix expected = DefaultDoubleMatrix.rows(2,
                6., 8.,
                3., 4.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testCopy1() {

        final DoubleVector first = new DefaultDoubleVector(2., 1., 3., 4.);

        final DoubleVector result = new DefaultDoubleVector(DoubleTabulars.copy(first));

        final DoubleVector expected = new DefaultDoubleVector(2., 1., 3., 4.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testCopy2() {

        final DoubleMatrix first = DefaultDoubleMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final DoubleMatrix result = new DefaultDoubleMatrix(DoubleTabulars.copy(first));

        final DoubleMatrix expected = DefaultDoubleMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testTranspose1() {

        final DoubleVector first = new DefaultDoubleVector(2., 1., 3., 4.);

        final DoubleVector result = new DefaultDoubleVector(DoubleTabulars.transpose(first));

        final DoubleVector expected = new DefaultDoubleVector(2., 1., 3., 4.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testTranspose2() {

        final DoubleMatrix first = DefaultDoubleMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final DoubleMatrix result = new DefaultDoubleMatrix(DoubleTabulars.transpose(first));

        final DoubleMatrix expected = DefaultDoubleMatrix.columns(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testAdd1() {

        final DoubleVector first = new DefaultDoubleVector(2., 1., 3., 4.);
        final DoubleVector second = new DefaultDoubleVector(3., 1., 3., 4.);

        final DoubleVector result = new DefaultDoubleVector(DoubleTabulars.add(first, second));

        final DoubleVector expected = new DefaultDoubleVector(5., 2., 6., 8.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testAdd2() {

        final DoubleMatrix first = DefaultDoubleMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);
        final DoubleMatrix second = DefaultDoubleMatrix.rows(4,
                6., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final DoubleMatrix result = new DefaultDoubleMatrix(DoubleTabulars.add(first, second));

        final DoubleMatrix expected = DefaultDoubleMatrix.rows(4,
                7., 4., 6., 8.,
                8., 10., 12., 10.,
                14., 16., 18., 12.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testMinus1() {

        final DoubleVector first = new DefaultDoubleVector(2., 1., 3., 4.);
        final DoubleVector second = new DefaultDoubleVector(3., 1., 3., 4.);

        final DoubleVector result = new DefaultDoubleVector(DoubleTabulars.minus(first, second));

        final DoubleVector expected = new DefaultDoubleVector(-1., 0., 0., 0.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testMinus2() {

        final DoubleMatrix first = DefaultDoubleMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);
        final DoubleMatrix second = DefaultDoubleMatrix.rows(4,
                6., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final DoubleMatrix result = new DefaultDoubleMatrix(DoubleTabulars.minus(first, second));

        final DoubleMatrix expected = DefaultDoubleMatrix.rows(4,
                -5., 0., 0., 0.,
                0., 0., 0., 0.,
                0., 0., 0., 0.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testExternal1() {

        final DoubleVector first = new DefaultDoubleVector(2., 1., 3., 4.);

        final DoubleVector result = new DefaultDoubleVector(DoubleTabulars.external(3., first));

        final DoubleVector expected = new DefaultDoubleVector(6., 3., 9., 12.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testExternal2() {

        final DoubleMatrix first = DefaultDoubleMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final DoubleMatrix result = new DefaultDoubleMatrix(DoubleTabulars.external(2., first));

        final DoubleMatrix expected = DefaultDoubleMatrix.rows(4,
                2., 4., 6., 8.,
                8., 10., 12., 10.,
                14., 16., 18., 12.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testValue1() {

        final DoubleMatrix first = DefaultDoubleMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final double result = DoubleTabulars.value(first, 1, 2);

        final double expected = 6.;

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testValue2() {

        final DoubleVector first = new DefaultDoubleVector(1., 2., 3., 4., 9., 6.);

        final double result = DoubleTabulars.value(first, 4);

        final double expected = 9.;

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testLine() {

        final DoubleMatrix first = DefaultDoubleMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final DoubleVector result = new DefaultDoubleVector(DoubleTabulars.line(first, 1));

        final DoubleVector expected = new DefaultDoubleVector(4., 5., 6., 5.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testColumn() {

        final DoubleMatrix first = DefaultDoubleMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final DoubleVector result = new DefaultDoubleVector(DoubleTabulars.column(first, 1));

        final DoubleVector expected = new DefaultDoubleVector(2., 5., 8.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testVector1() {

        final DoubleMatrix first = DefaultDoubleMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final DoubleVector result = new DefaultDoubleVector(DoubleTabulars.vector(first, 1, Dimension.ROW));

        final DoubleVector expected = new DefaultDoubleVector(4., 5., 6., 5.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testVector2() {

        final DoubleMatrix first = DefaultDoubleMatrix.rows(4,
                1., 2., 3., 4.,
                4., 5., 6., 5.,
                7., 8., 9., 6.);

        final DoubleVector result = new DefaultDoubleVector(DoubleTabulars.vector(first, 2, Dimension.COLUMN));

        final DoubleVector expected = new DefaultDoubleVector(3., 6., 9.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testMatrix1() {

        final DoubleVector first = new DefaultDoubleVector(1., 2., 3., 4.);

        final DoubleMatrix result = new DefaultDoubleMatrix(DoubleTabulars.matrix(first, Dimension.ROW));

        final DoubleMatrix expected = DefaultDoubleMatrix.rows(4,
                1., 2., 3., 4.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testMatrix2() {

        final DoubleVector first = new DefaultDoubleVector(1., 2., 3., 4.);

        final DoubleMatrix result = new DefaultDoubleMatrix(DoubleTabulars.matrix(first, Dimension.COLUMN));

        final DoubleMatrix expected = DefaultDoubleMatrix.columns(4,
                1., 2., 3., 4.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testDiagonal1() {

        final DoubleVector first = new DefaultDoubleVector(1., 2., 3., 4.);

        final DoubleMatrix result = new DefaultDoubleMatrix(DoubleTabulars.diagonal(first));

        final DoubleMatrix expected = DefaultDoubleMatrix.rows(4,
                1., 0., 0., 0.,
                0., 2., 0., 0.,
                0., 0., 3., 0.,
                0., 0., 0., 4.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testDiagonal2() {

        final DoubleMatrix result = new DefaultDoubleMatrix(DoubleTabulars.diagonal(3.2, 5));

        final DoubleMatrix expected = DefaultDoubleMatrix.rows(5,
                3.2, 0., 0., 0., 0.,
                0., 3.2, 0., 0., 0.,
                0., 0., 3.2, 0., 0.,
                0., 0., 0., 3.2, 0.,
                0., 0., 0., 0., 3.2);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testDiagonal3() {

        final DoubleMatrix result = new DefaultDoubleMatrix(DoubleTabulars.diagonal(1.2, 5, 7));

        final DoubleMatrix expected = DefaultDoubleMatrix.rows(7,
                1.2, 0., 0., 0., 0., 0., 0.,
                0., 1.2, 0., 0., 0., 0., 0.,
                0., 0., 1.2, 0., 0., 0., 0.,
                0., 0., 0., 1.2, 0., 0., 0.,
                0., 0., 0., 0., 1.2, 0., 0.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testDiagonal4() {

        final DoubleMatrix result = new DefaultDoubleMatrix(DoubleTabulars.diagonal(3.4, 8, 5));

        final DoubleMatrix expected = DefaultDoubleMatrix.rows(5,
                3.4, 0., 0., 0., 0.,
                0., 3.4, 0., 0., 0.,
                0., 0., 3.4, 0., 0.,
                0., 0., 0., 3.4, 0.,
                0., 0., 0., 0., 3.4,
                0., 0., 0., 0., 0.,
                0., 0., 0., 0., 0.,
                0., 0., 0., 0., 0.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testFill1() {

        final DoubleMatrix result = new DefaultDoubleMatrix(DoubleTabulars.fill(3.4, 8, 5));

        final DoubleMatrix expected = DefaultDoubleMatrix.rows(5,
                3.4, 3.4, 3.4, 3.4, 3.4,
                3.4, 3.4, 3.4, 3.4, 3.4,
                3.4, 3.4, 3.4, 3.4, 3.4,
                3.4, 3.4, 3.4, 3.4, 3.4,
                3.4, 3.4, 3.4, 3.4, 3.4,
                3.4, 3.4, 3.4, 3.4, 3.4,
                3.4, 3.4, 3.4, 3.4, 3.4,
                3.4, 3.4, 3.4, 3.4, 3.4);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testFill2() {

        final DoubleMatrix result = new DefaultDoubleMatrix(DoubleTabulars.fill(1.2, 5, 7));

        final DoubleMatrix expected = DefaultDoubleMatrix.rows(7,
                1.2, 1.2, 1.2, 1.2, 1.2, 1.2, 1.2,
                1.2, 1.2, 1.2, 1.2, 1.2, 1.2, 1.2,
                1.2, 1.2, 1.2, 1.2, 1.2, 1.2, 1.2,
                1.2, 1.2, 1.2, 1.2, 1.2, 1.2, 1.2,
                1.2, 1.2, 1.2, 1.2, 1.2, 1.2, 1.2);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testFill3() {

        final DoubleVector result = new DefaultDoubleVector(DoubleTabulars.fill(8.3, 6));

        final DoubleVector expected = new DefaultDoubleVector(8.3, 8.3, 8.3, 8.3, 8.3, 8.3);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void rotation2D() {
        final double[][] rotation2D = DoubleTabulars.rotation2D(Math.PI / 4.);
        Assertions.assertArrayEquals(new double[]{Math.sqrt(2.) / 2., -Math.sqrt(2.) / 2.}, rotation2D[0], 1e-14);
        Assertions.assertArrayEquals(new double[]{Math.sqrt(2.) / 2., Math.sqrt(2.) / 2.}, rotation2D[1], 1e-14);
    }

    @Test
    public void rotation3DXYZ() {
        final double[][] x = DoubleTabulars.rotation3DX(Math.toRadians(20.));
        final double[][] y = DoubleTabulars.rotation3DY(Math.toRadians(30.));
        final double[][] z = DoubleTabulars.rotation3DZ(Math.toRadians(40.));
        final double[][] expected = DoubleTabulars.mult(z, DoubleTabulars.mult(y, x));

        final double[][] xyz = DoubleTabulars.rotation3DXYZ(
                Math.toRadians(20.), Math.toRadians(30.), Math.toRadians(40.));

        Assertions.assertArrayEquals(expected[0], xyz[0]);
        Assertions.assertArrayEquals(expected[1], xyz[1]);
        Assertions.assertArrayEquals(expected[2], xyz[2]);
    }

    @Test
    public void rotation3DXYZApprox() {
        final double[][] x = DoubleTabulars.rotation3DX(Math.toRadians(1.));
        final double[][] y = DoubleTabulars.rotation3DY(Math.toRadians(2.));
        final double[][] z = DoubleTabulars.rotation3DZ(Math.toRadians(3.));
        final double[][] expected = DoubleTabulars.mult(z, DoubleTabulars.mult(y, x));

        final double[][] xyz = DoubleTabulars.rotation3DXYZApprox(
                Math.toRadians(1.), Math.toRadians(2.), Math.toRadians(3.));

        Assertions.assertArrayEquals(expected[0], xyz[0], 1e-2);
        Assertions.assertArrayEquals(expected[1], xyz[1], 1e-2);
        Assertions.assertArrayEquals(expected[2], xyz[2], 1e-2);
    }

    @Test
    public void rotation3DZYX() {
        final double[][] x = DoubleTabulars.rotation3DX(Math.toRadians(20.));
        final double[][] y = DoubleTabulars.rotation3DY(Math.toRadians(30.));
        final double[][] z = DoubleTabulars.rotation3DZ(Math.toRadians(40.));
        final double[][] expected = DoubleTabulars.mult(x, DoubleTabulars.mult(y, z));

        final double[][] zyx = DoubleTabulars.rotation3DZYX(
                Math.toRadians(20.), Math.toRadians(30.), Math.toRadians(40.));

        Assertions.assertArrayEquals(expected[0], zyx[0]);
        Assertions.assertArrayEquals(expected[1], zyx[1]);
        Assertions.assertArrayEquals(expected[2], zyx[2]);
    }

    @Test
    public void rotation3D() {
        final double[][] expected = DoubleTabulars.identity(3);

        final double[][] xyz = DoubleTabulars.rotation3DXYZ(
                Math.toRadians(20.), Math.toRadians(30.), Math.toRadians(40.));
        final double[][] zyx = DoubleTabulars.rotation3DZYX(
                -Math.toRadians(20.), -Math.toRadians(30.), -Math.toRadians(40.));

        final double[][] id = DoubleTabulars.mult(xyz, zyx);

        Assertions.assertArrayEquals(expected[0], id[0], 1e-15);
        Assertions.assertArrayEquals(expected[1], id[1], 1e-15);
        Assertions.assertArrayEquals(expected[2], id[2], 1e-15);
    }

    @Test
    public void rotation3DZYXApprox() {
        final double[][] x = DoubleTabulars.rotation3DX(Math.toRadians(1.));
        final double[][] y = DoubleTabulars.rotation3DY(Math.toRadians(2.));
        final double[][] z = DoubleTabulars.rotation3DZ(Math.toRadians(3.));
        final double[][] expected = DoubleTabulars.mult(x, DoubleTabulars.mult(y, z));

        final double[][] zyx = DoubleTabulars.rotation3DZYXApprox(
                Math.toRadians(1.), Math.toRadians(2.), Math.toRadians(3.));

        Assertions.assertArrayEquals(expected[0], zyx[0], 1e-2);
        Assertions.assertArrayEquals(expected[1], zyx[1], 1e-2);
        Assertions.assertArrayEquals(expected[2], zyx[2], 1e-2);
    }

    @Test
    public void rotation3DApprox() {
        final double[][] expected = DoubleTabulars.identity(3);

        final double[][] xyz = DoubleTabulars.rotation3DXYZApprox(
                Math.toRadians(1.), Math.toRadians(2.), Math.toRadians(3.));
        final double[][] zyx = DoubleTabulars.rotation3DZYXApprox(
                -Math.toRadians(1.), -Math.toRadians(2.), -Math.toRadians(3.));

        final double[][] id = DoubleTabulars.mult(xyz, zyx);

        Assertions.assertArrayEquals(expected[0], id[0], 1e-2);
        Assertions.assertArrayEquals(expected[1], id[1], 1e-2);
        Assertions.assertArrayEquals(expected[2], id[2], 1e-2);
    }

    @Test
    public void testHadamard_kernel() {

        final double[][] first = new double[][]{
            {1., 3., 2.},
            {1., 0., 0.},
            {1., 2., 2.}};
        final double[][] second = new double[][]{
            {0., 0., 2.},
            {7., 5., 0.},
            {2., 1., 1.}};

        final double[][] result = DoubleTabulars.hadamard(first, second);

        final double[][] expected = new double[][]{
            {0., 0., 4.},
            {7., 0., 0.},
            {2., 2., 2.}};

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void testHadamard() {

        final DoubleMatrix first = DefaultDoubleMatrix.rows(3,
                1., 3., 2.,
                1., 0., 0.,
                1., 2., 2.);
        final DoubleMatrix second = DefaultDoubleMatrix.rows(3,
                0., 0., 2.,
                7., 5., 0.,
                2., 1., 1.);

        final DoubleMatrix result = new DefaultDoubleMatrix(DoubleTabulars.hadamard(first, second));

        final DoubleMatrix expected = DefaultDoubleMatrix.rows(3,
                0., 0., 4.,
                7., 0., 0.,
                2., 2., 2.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void testKronecker_kernel() {

        final double[][] first = new double[][]{
                {1., 2.},
                {3., 1.}};
        final double[][] second = new double[][]{
                {0., 3.},
                {2., 1.}};

        final double[][] result = DoubleTabulars.kronecker(first, second);

        final double[][] expected = new double[][]{
            {0., 3., 0., 6.},
            {2., 1., 4., 2.},
            {0., 9., 0., 3.},
            {6., 3., 2., 1.}};

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void testKronecker() {

        final DoubleMatrix first = DefaultDoubleMatrix.rows(2,
                1., 2.,
                3., 1.);
        final DoubleMatrix second = DefaultDoubleMatrix.rows(2,
                0., 3.,
                2., 1.);

        final DoubleMatrix result = new DefaultDoubleMatrix(DoubleTabulars.kronecker(first, second));

        final DoubleMatrix expected = DefaultDoubleMatrix.rows(4,
                0., 3., 0., 6.,
                2., 1., 4., 2.,
                0., 9., 0., 3.,
                6., 3., 2., 1.);

        Assertions.assertTrue(DoubleTabulars.equal(expected, result));
    }

    @Test
    public void determinant22_1() {

        final DoubleMatrix first = DefaultDoubleMatrix.rows(2,
                5., 0.,
                1, -4);

        Assertions.assertEquals(-20., DoubleTabulars.determinant22(first));
    }

    @Test
    public void determinant22_2() {

        final DoubleMatrix first = DefaultDoubleMatrix.rows(2,
                2., 0.,
                -2, -4.);

        Assertions.assertEquals(-8., DoubleTabulars.determinant22(first));
    }

    @Test
    public void determinant22_3() {

        final DoubleMatrix first = DefaultDoubleMatrix.rows(2,
                2,  5,
                -2, 1);

        Assertions.assertEquals(12, DoubleTabulars.determinant22(first));
    }

    @Test
    public void subMatrix_1() {

        final DoubleMatrix a = DefaultDoubleMatrix.rows(3,
                4, -2, 6,
                2, 5, 0,
                -2, 1, -4);

        final DoubleMatrix c = DefaultDoubleMatrix.rows(2,
                5., 0.,
                1, -4);

        Assertions.assertArrayEquals(c.getMatrix(), DoubleTabulars.subMatrix(a.getMatrix(), 0, 0));
    }

    @Test
    public void subMatrix_2() {

        final DoubleMatrix a = DefaultDoubleMatrix.rows(3,
                4, -2, 6,
                2, 5, 0,
                -2, 1, -4);

        final DoubleMatrix c = DefaultDoubleMatrix.rows(2,
                2, 0.,
                -2, -4);

        Assertions.assertArrayEquals(c.getMatrix(), DoubleTabulars.subMatrix(a.getMatrix(), 0, 1));
    }

    @Test
    public void subMatrix_3() {

        final DoubleMatrix a = DefaultDoubleMatrix.rows(3,
                4, -2, 6,
                2, 5, 0,
                -2, 1, -4);

        final DoubleMatrix c = DefaultDoubleMatrix.rows(2,
                2, 5,
                -2, 1);

        Assertions.assertArrayEquals(c.getMatrix(), DoubleTabulars.subMatrix(a.getMatrix(), 0, 2));
    }

    @Test
    public void subMatrix_4() {

        final DoubleMatrix a = DefaultDoubleMatrix.rows(3,
                4, -2, 6,
                2, 5, 0,
                -2, 1, -4);

        final DoubleMatrix c = DefaultDoubleMatrix.rows(2,
                -2, 6,
                1, -4);

        Assertions.assertArrayEquals(c.getMatrix(), DoubleTabulars.subMatrix(a.getMatrix(), 1, 0));
    }

    @Test
    public void subMatrix_5() {

        final DoubleMatrix a = DefaultDoubleMatrix.rows(3,
                4, -2, 6,
                2, 5, 0,
                -2, 1, -4);

        final DoubleMatrix c = DefaultDoubleMatrix.rows(2,
                4, 6,
                -2, -4);

        Assertions.assertArrayEquals(c.getMatrix(), DoubleTabulars.subMatrix(a.getMatrix(), 1, 1));
    }

    @Test
    public void subMatrix_6() {

        final DoubleMatrix a = DefaultDoubleMatrix.rows(3,
                4, -2, 6,
                2, 5, 0,
                -2, 1, -4);

        final DoubleMatrix c = DefaultDoubleMatrix.rows(2,
                4, -2,
                -2, 1);

        Assertions.assertArrayEquals(c.getMatrix(), DoubleTabulars.subMatrix(a.getMatrix(), 1, 2));
    }

    @Test
    public void subMatrix_7() {

        final DoubleMatrix a = DefaultDoubleMatrix.rows(3,
                4, -2, 6,
                2, 5, 0,
                -2, 1, -4);

        final DoubleMatrix c = DefaultDoubleMatrix.rows(2,
                -2, 6,
                5, 0);

        Assertions.assertArrayEquals(c.getMatrix(), DoubleTabulars.subMatrix(a.getMatrix(), 2, 0));
    }

    @Test
    public void subMatrix_8() {

        final DoubleMatrix a = DefaultDoubleMatrix.rows(3,
                4, -2, 6,
                2, 5, 0,
                -2, 1, -4);

        final DoubleMatrix c = DefaultDoubleMatrix.rows(2,
                4, 6,
                2, 0);

        Assertions.assertArrayEquals(c.getMatrix(), DoubleTabulars.subMatrix(a.getMatrix(), 2, 1));
    }

    @Test
    public void subMatrix_9() {

        final DoubleMatrix a = DefaultDoubleMatrix.rows(3,
                4, -2, 6,
                2, 5, 0,
                -2, 1, -4);

        final DoubleMatrix c = DefaultDoubleMatrix.rows(2,
                4, -2,
                2, 5);

        Assertions.assertArrayEquals(c.getMatrix(), DoubleTabulars.subMatrix(a.getMatrix(), 2, 2));
    }

    @Test
    public void cofactor_1() {

        final DoubleMatrix a = DefaultDoubleMatrix.rows(3,
                4, -2, 6,
                2, 5, 0,
                -2, 1, -4);

        Assertions.assertEquals(-20, DoubleTabulars.cofactor(a.getMatrix(), 0, 0, Dimension.ROW));
    }

    @Test
    public void cofactor_2() {

        final DoubleMatrix a = DefaultDoubleMatrix.rows(3,
                4, -2, 6,
                2, 5, 0,
                -2, 1, -4);

        Assertions.assertEquals(8., DoubleTabulars.cofactor(a.getMatrix(), 0, 1, Dimension.ROW));
    }

    @Test
    public void cofactor_3() {

        final DoubleMatrix a = DefaultDoubleMatrix.rows(3,
                4, -2, 6,
                2, 5, 0,
                -2, 1, -4);

        Assertions.assertEquals(12., DoubleTabulars.cofactor(a.getMatrix(), 0, 2, Dimension.ROW));
    }

    @Test
    public void determinantNN_1() {

        final DoubleMatrix a = DefaultDoubleMatrix.rows(3,
                4, -2, 6,
                2, 5, 0,
                -2, 1, -4);

        Assertions.assertEquals(-24, DoubleTabulars.determinantNN(a.getMatrix(), Dimension.ROW));
    }

    @Test
    public void determinantNN_2() {

        final DoubleMatrix a = DefaultDoubleMatrix.rows(3,
                4, -2, 6,
                2, 5, 0,
                -2, 1, -4);

        Assertions.assertEquals(-24, DoubleTabulars.determinantNN(a.getMatrix(), Dimension.COLUMN));
    }

    @Test
    public void inverse22_1() {
        final double[][] m = new double[][]{{1., 0.},
                                            {0., 1.}};

        final double[][] i = DoubleTabulars.inverse22(m);
        Assertions.assertArrayEquals(m[0], i[0], Double.MIN_VALUE);
        Assertions.assertArrayEquals(m[1], i[1], Double.MIN_VALUE);
    }

    @Test
    public void inverse22_2() {
        final double[][] m = new double[][]{{1., 0., 0.},
                                            {0., 1., 0.},
                                            {0., 0., 1.}};

        final double[][] i = DoubleTabulars.inverse33(m, Matrix.Dimension.ROW);
        Assertions.assertArrayEquals(m[0], i[0]);
        Assertions.assertArrayEquals(m[1], i[1]);
        Assertions.assertArrayEquals(m[2], i[2]);
    }

    @Test
    public void inverse22_3() {
        final double[][] m = new double[][]{{1., 0., 0.},
                                            {0., 1., 0.},
                                            {0., 0., 1.}};

        final double[][] i = DoubleTabulars.inverse33(m, Matrix.Dimension.COLUMN);
        Assertions.assertArrayEquals(m[0], i[0]);
        Assertions.assertArrayEquals(m[1], i[1]);
        Assertions.assertArrayEquals(m[2], i[2]);
    }
}
