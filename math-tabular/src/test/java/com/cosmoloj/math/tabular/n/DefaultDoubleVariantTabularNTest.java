package com.cosmoloj.math.tabular.n;

import com.cosmoloj.math.tabular.n.VariantTabularN.Variant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class DefaultDoubleVariantTabularNTest {

    private static final IndexFactory IF = IndexFactory.getInstance();

//    @Test @Disabled
//    public void testGetDimensionVariant() {
//
//        int index = 0;
//        DefaultDoubleVariantTabularN instance = null;
//        VariantTabularN.Variant expResult = null;
//        VariantTabularN.Variant result = instance.getDimensionVariant(index);
//        Assertions.assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to Assertions.fail.
//        Assertions.fail("The test case is a prototype.");
//    }

    @Test
    public void testGet() {

        final double[][][] matrix = new double[5][2][4];
        matrix[0][0][0] = 1.4;

        final DoubleVariantTabularN tn = new DefaultDoubleVariantTabularN(
                matrix,
                IF.get('i', 5, Variant.COVARIANT),
                IF.get('j', 2, Variant.COVARIANT),
                IF.get('k', 4, Variant.COVARIANT));

        Assertions.assertEquals(1.4, tn.get(0, 0, 0));
    }

    @Test
    public void testSet() {

        final double[][][] matrix = new double[5][2][4];
        matrix[4][1][2] = Math.E;

        final DoubleVariantTabularN tn = new DefaultDoubleVariantTabularN(
                matrix,
                IF.get('i', 5, Variant.COVARIANT),
                IF.get('j', 2, Variant.COVARIANT),
                IF.get('k', 4, Variant.COVARIANT));

        Assertions.assertEquals(Math.E, tn.get(4, 1, 2));
    }

    @Test
    public void testGetOrder() {

        final double[][][] matrix = new double[5][2][4];

        final DoubleVariantTabularN tn = new DefaultDoubleVariantTabularN(
                matrix,
                IF.get('i', 5, Variant.COVARIANT),
                IF.get('j', 2, Variant.COVARIANT),
                IF.get('k', 4, Variant.COVARIANT));

        Assertions.assertEquals(3, tn.getOrder());
    }

    @Test
    public void testGetDimension() {

        final double[][][] matrix = new double[5][2][4];
        matrix[4][1][2] = Math.E;

        final DoubleVariantTabularN tn = new DefaultDoubleVariantTabularN(
                matrix,
                IF.get('i', 5, Variant.COVARIANT),
                IF.get('j', 2, Variant.COVARIANT),
                IF.get('k', 4, Variant.COVARIANT));

        Assertions.assertEquals(5, tn.getDimension(0));
        Assertions.assertEquals(2, tn.getDimension(1));
        Assertions.assertEquals(4, tn.getDimension(2));
    }

    @Test
    public void testPrepareStructure1() {

        final double[][][][] matrix1 = new double[5][2][2][4];

        final DoubleVariantTabularN t1 = new DefaultDoubleVariantTabularN(
                matrix1,
                IF.get('a', 5, Variant.COVARIANT),
                IF.get('b', 2, Variant.COVARIANT),
                IF.get('j', 2, Variant.CONTRAVARIANT),
                IF.get('k', 4, Variant.COVARIANT));

        final double[][][] matrix2 = new double[5][2][4];

        final DoubleVariantTabularN t2 = new DefaultDoubleVariantTabularN(
                matrix2,
                IF.get('i', 5, Variant.COVARIANT),
                IF.get('j', 2, Variant.COVARIANT),
                IF.get('k', 4, Variant.COVARIANT));


        final VariantIndex[] indexes = DefaultDoubleVariantTabularN.prepareStructure(t1, t2).getKey();

        Assertions.assertEquals(4, indexes.length);

        for (final VariantIndex vi : indexes) {
            switch (vi.getName()) {
                case 'a' -> {
                    Assertions.assertEquals(Variant.COVARIANT, vi.getVariant());
                    Assertions.assertEquals(5, vi.getDimension());
                }
                case 'b' -> {
                    Assertions.assertEquals(Variant.COVARIANT, vi.getVariant());
                    Assertions.assertEquals(2, vi.getDimension());
                }
                case 'k' -> {
                    Assertions.assertEquals(Variant.COVARIANT, vi.getVariant());
                    Assertions.assertEquals(4, vi.getDimension());
                }
                case 'i' -> {
                    Assertions.assertEquals(Variant.COVARIANT, vi.getVariant());
                    Assertions.assertEquals(5, vi.getDimension());
                }
                default -> {
                }
            }
        }

        final NamedIndex[] sumIndexes = DefaultDoubleVariantTabularN.prepareStructure(t1, t2).getValue();

        Assertions.assertEquals(1, sumIndexes.length);

        for (final NamedIndex vi : sumIndexes) {
            if (vi.getName() == 'j') {
                Assertions.assertEquals(2, vi.getDimension());
            }
        }
    }

    @Test
    public void testPrepareStructure2() {

        final double[][][] matrix1 = new double[5][2][4];

        final DoubleVariantTabularN t1 = new DefaultDoubleVariantTabularN(
                matrix1,
                IF.get('i', 5, Variant.COVARIANT),
                IF.get('j', 2, Variant.COVARIANT),
                IF.get('k', 4, Variant.CONTRAVARIANT));

        final double[][][] matrix2 = new double[5][2][4];

        final DoubleVariantTabularN t2 = new DefaultDoubleVariantTabularN(
                matrix2,
                IF.get('i', 5, Variant.COVARIANT),
                IF.get('j', 2, Variant.COVARIANT),
                IF.get('k', 4, Variant.COVARIANT));


        final VariantIndex[] indexes = DefaultDoubleVariantTabularN.prepareStructure(t1, t2).getKey();

        Assertions.assertEquals(2, indexes.length);

        for (final VariantIndex vi : indexes) {
            System.out.println(vi);
            if (vi.getName() == 'i') {
                Assertions.assertEquals(Variant.COVARIANT, vi.getVariant());
                Assertions.assertEquals(5, vi.getDimension());
            } else if (vi.getName() == 'j') {
                Assertions.assertEquals(Variant.COVARIANT, vi.getVariant());
                Assertions.assertEquals(2, vi.getDimension());
            }
        }

        final NamedIndex[] sumIndexes = DefaultDoubleVariantTabularN.prepareStructure(t1, t2).getValue();

        Assertions.assertEquals(1, sumIndexes.length);

        for (final NamedIndex vi : sumIndexes) {
            if (vi.getName() == 'k') {
                Assertions.assertEquals(4, vi.getDimension());
            }
        }
    }

    @Test
    public void testPrepareStructure3() {

        final double[][][] matrix1 = new double[5][2][4];

        final DoubleVariantTabularN t1 = new DefaultDoubleVariantTabularN(
                matrix1,
                IF.get('i', 5, Variant.COVARIANT),
                IF.get('j', 2, Variant.COVARIANT),
                IF.get('k', 4, Variant.COVARIANT));

        final double[][][] matrix2 = new double[5][2][4];

        final DoubleVariantTabularN t2 = new DefaultDoubleVariantTabularN(
                matrix2,
                IF.get('i', 5, Variant.COVARIANT),
                IF.get('j', 2, Variant.COVARIANT),
                IF.get('k', 4, Variant.COVARIANT));

        final VariantIndex[] indexes = DefaultDoubleVariantTabularN.prepareStructure(t1, t2).getKey();

        Assertions.assertEquals(3, indexes.length);

        for (final VariantIndex vi : indexes) {

            switch (vi.getName()) {
                case 'i' -> {
                    Assertions.assertEquals(Variant.COVARIANT, vi.getVariant());
                    Assertions.assertEquals(5, vi.getDimension());
                }
                case 'j' -> {
                    Assertions.assertEquals(Variant.COVARIANT, vi.getVariant());
                    Assertions.assertEquals(2, vi.getDimension());
                }
                case 'k' -> {
                    Assertions.assertEquals(Variant.COVARIANT, vi.getVariant());
                    Assertions.assertEquals(4, vi.getDimension());
                }
                default -> {
                }
            }
        }

        final NamedIndex[] sumIndexes = DefaultDoubleVariantTabularN.prepareStructure(t1, t2).getValue();

        Assertions.assertEquals(0, sumIndexes.length);
    }

    @Test
    public void testGeneralMult1() {

        final double[][] matrix1 = new double[][]{{1., 5., 8., 2.},
                                                {4., 2., 6., 3.}};

        final DoubleVariantTabularN t1 = new DefaultDoubleVariantTabularN(
                matrix1,
                IF.get('i', 2, Variant.COVARIANT),
                IF.get('k', 4, Variant.COVARIANT));

        final double[] matrix2 = new double[]{3., 4., 5.};

        final DoubleVariantTabularN t2 = new DefaultDoubleVariantTabularN(
                matrix2,
                IF.get('j', 3, Variant.COVARIANT));

        final DoubleVariantTabularN generalMult = DefaultDoubleVariantTabularN.generalMult(t1, t2);

        Assertions.assertArrayEquals(new char[]{'i', 'k', 'j'}, generalMult.getIndexes());
        Assertions.assertEquals(3, generalMult.getOrder());

        Assertions.assertEquals(3., generalMult.get(0, 0, 0));
        Assertions.assertEquals(4., generalMult.get(0, 0, 1));
        Assertions.assertEquals(5., generalMult.get(0, 0, 2));
        Assertions.assertEquals(15., generalMult.get(0, 1, 0));
        Assertions.assertEquals(20., generalMult.get(0, 1, 1));
        Assertions.assertEquals(25., generalMult.get(0, 1, 2));
        Assertions.assertEquals(24., generalMult.get(0, 2, 0));
        Assertions.assertEquals(32., generalMult.get(0, 2, 1));
        Assertions.assertEquals(40., generalMult.get(0, 2, 2));
        Assertions.assertEquals(6., generalMult.get(0, 3, 0));
        Assertions.assertEquals(8., generalMult.get(0, 3, 1));
        Assertions.assertEquals(10., generalMult.get(0, 3, 2));
        Assertions.assertEquals(12., generalMult.get(1, 0, 0));
        Assertions.assertEquals(16., generalMult.get(1, 0, 1));
        Assertions.assertEquals(20., generalMult.get(1, 0, 2));
        Assertions.assertEquals(6., generalMult.get(1, 1, 0));
        Assertions.assertEquals(8., generalMult.get(1, 1, 1));
        Assertions.assertEquals(10., generalMult.get(1, 1, 2));
        Assertions.assertEquals(18., generalMult.get(1, 2, 0));
        Assertions.assertEquals(24., generalMult.get(1, 2, 1));
        Assertions.assertEquals(30., generalMult.get(1, 2, 2));
        Assertions.assertEquals(9., generalMult.get(1, 3, 0));
        Assertions.assertEquals(12., generalMult.get(1, 3, 1));
        Assertions.assertEquals(15., generalMult.get(1, 3, 2));
    }

    @Test
    public void testGeneralMult2() {

        final double[][][][] matrix1 = new double[5][2][2][4];
        matrix1[2][1][0][2] = 3.;
        matrix1[2][1][1][2] = 0.;

        final DoubleVariantTabularN t1 = new DefaultDoubleVariantTabularN(
                matrix1,
                IF.get('a', 5, Variant.COVARIANT),
                IF.get('b', 2, Variant.COVARIANT),
                IF.get('j', 2, Variant.CONTRAVARIANT),
                IF.get('k', 4, Variant.COVARIANT));

        final double[][][] matrix2 = new double[5][2][4];
        matrix2[0][0][2] = 2.;
        matrix2[0][1][2] = 7.;

        final DoubleVariantTabularN t2 = new DefaultDoubleVariantTabularN(
                matrix2,
                IF.get('i', 5, Variant.COVARIANT),
                IF.get('j', 2, Variant.COVARIANT),
                IF.get('k', 4, Variant.COVARIANT));

        final DoubleVariantTabularN generalMult = DefaultDoubleVariantTabularN.generalMult(t1, t2);

        Assertions.assertArrayEquals(new char[]{'a', 'b', 'k', 'i'}, generalMult.getIndexes());
        Assertions.assertEquals(4, generalMult.getOrder());
        Assertions.assertEquals(6., generalMult.get(2, 1, 2, 0));
    }

    @Test
    public void testGeneralMult22() {

        final double[][][][] matrix1 = new double[5][2][2][4];
        matrix1[2][1][0][2] = 3.;
        matrix1[2][1][1][2] = 5.;

        final DoubleVariantTabularN t1 = new DefaultDoubleVariantTabularN(
                matrix1,
                IF.get('a', 5, Variant.COVARIANT),
                IF.get('b', 2, Variant.COVARIANT),
                IF.get('j', 2, Variant.CONTRAVARIANT),
                IF.get('k', 4, Variant.COVARIANT));

        final double[][][] matrix2 = new double[5][2][4];
        matrix2[0][0][2] = 2.;
        matrix2[0][1][2] = 7.;

        final DoubleVariantTabularN t2 = new DefaultDoubleVariantTabularN(
                matrix2,
                IF.get('i', 5, Variant.COVARIANT),
                IF.get('j', 2, Variant.COVARIANT),
                IF.get('k', 4, Variant.COVARIANT));

        final DoubleVariantTabularN generalMult = DefaultDoubleVariantTabularN.generalMult(t1, t2);

        Assertions.assertArrayEquals(new char[]{'a', 'b', 'k', 'i'}, generalMult.getIndexes());
        Assertions.assertEquals(4, generalMult.getOrder());
        Assertions.assertEquals(41., generalMult.get(2, 1, 2, 0));
    }

    @Test
    public void testGeneralMult3() {

        final double[] matrix1 = new double[]{5., 9., 4., 5.};

        final DoubleVariantTabularN t1 = new DefaultDoubleVariantTabularN(
                matrix1,
                IF.get('k', 4, Variant.CONTRAVARIANT));

        final double[] matrix2 = new double[]{7., 0., 3., 6.};

        final DoubleVariantTabularN t2 = new DefaultDoubleVariantTabularN(
                matrix2,
                IF.get('k', 4, Variant.COVARIANT));

        final DoubleVariantTabularN generalMult = DefaultDoubleVariantTabularN.generalMult(t1, t2);

        Assertions.assertArrayEquals(new char[]{}, generalMult.getIndexes());
        Assertions.assertEquals(0, generalMult.getOrder());
        Assertions.assertEquals(77., generalMult.get());
    }

    @Test
    public void testGeneralMult4() {

        final double[][][] matrix1 = new double[5][2][4];
        matrix1[4][0][0] = 8.;
        matrix1[4][0][1] = 5.;
        matrix1[4][0][2] = 2.;
        matrix1[4][0][3] = 0.;
        matrix1[0][1][0] = 0.;
        matrix1[0][1][1] = 8.;
        matrix1[0][1][2] = 5.;
        matrix1[0][1][3] = 4.;

        final DoubleVariantTabularN t1 = new DefaultDoubleVariantTabularN(
                matrix1,
                IF.get('i', 5, Variant.COVARIANT),
                IF.get('j', 2, Variant.COVARIANT),
                IF.get('k', 4, Variant.CONTRAVARIANT));

        final double[][][] matrix2 = new double[5][2][4];
        matrix2[4][0][0] = 3.;
        matrix2[4][0][1] = 3.;
        matrix2[4][0][2] = 4.;
        matrix2[4][0][3] = 5.;
        matrix2[0][1][0] = 1.;
        matrix2[0][1][1] = 7.;
        matrix2[0][1][2] = 8.;
        matrix2[0][1][3] = 8.;

        final DoubleVariantTabularN t2 = new DefaultDoubleVariantTabularN(
                matrix2,
                IF.get('i', 5, Variant.COVARIANT),
                IF.get('j', 2, Variant.COVARIANT),
                IF.get('k', 4, Variant.COVARIANT));

        final DoubleVariantTabularN generalMult = DefaultDoubleVariantTabularN.generalMult(t1, t2);

        Assertions.assertEquals(2, generalMult.getOrder());
        Assertions.assertArrayEquals(new char[]{'i', 'j'}, generalMult.getIndexes());
        Assertions.assertEquals(47., generalMult.get(4, 0));
        Assertions.assertEquals(128., generalMult.get(0, 1));
    }

    @Test
    public void testGeneralMult5() {

        final double[][][] matrix1 = new double[5][2][4];
        matrix1[3][1][0] = 8.;
        matrix1[4][1][0] = 1.;

        final DoubleVariantTabularN t1 = new DefaultDoubleVariantTabularN(
                matrix1,
                IF.get('i', 5, Variant.COVARIANT),
                IF.get('j', 2, Variant.COVARIANT),
                IF.get('k', 4, Variant.COVARIANT));

        final double[][][] matrix2 = new double[5][2][4];
        matrix2[3][1][0] = 8.;
        matrix2[4][1][0] = 8.;

        final DoubleVariantTabularN t2 = new DefaultDoubleVariantTabularN(
                matrix2,
                IF.get('i', 5, Variant.COVARIANT),
                IF.get('j', 2, Variant.COVARIANT),
                IF.get('k', 4, Variant.COVARIANT));

        final DoubleVariantTabularN generalMult = DefaultDoubleVariantTabularN.generalMult(t1, t2);

        Assertions.assertEquals(3, generalMult.getOrder());
        Assertions.assertArrayEquals(new char[]{'i', 'j', 'k'}, generalMult.getIndexes());
        Assertions.assertEquals(64., generalMult.get(3, 1, 0));
        Assertions.assertEquals(8., generalMult.get(4, 1, 0));
    }
}
