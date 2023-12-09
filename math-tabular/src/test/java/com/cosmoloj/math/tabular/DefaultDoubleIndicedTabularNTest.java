package com.cosmoloj.math.tabular;

import com.cosmoloj.math.tabular.n.DefaultDoubleIndicedTabularN;
import com.cosmoloj.math.tabular.n.DoubleIndicedTabularN;
import com.cosmoloj.math.tabular.n.IndicedTabularN;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class DefaultDoubleIndicedTabularNTest {

    @Test
    public void testGet() {

        final double[][][] matrix = new double[5][2][4];
        matrix[0][0][0] = 1.4;

        final DoubleIndicedTabularN tn = new DefaultDoubleIndicedTabularN(
                matrix,
                IndicedTabularN.getIndex('i', 5),
                IndicedTabularN.getIndex('j', 2),
                IndicedTabularN.getIndex('k', 4));

        Assertions.assertEquals(1.4, tn.get(0, 0, 0));
    }

    @Test
    public void testGet1() {

        final double[][][] matrix = new double[5][2][4];
        matrix[0][0][0] = 1.4;

        final DoubleIndicedTabularN tn = new DefaultDoubleIndicedTabularN(
                matrix,
                IndicedTabularN.getIndex('i', 5),
                IndicedTabularN.getIndex('j', 2),
                IndicedTabularN.getIndex('k', 4));

        Assertions.assertEquals(1.4, tn.get(
                IndicedTabularN.getIndex('k', 0),
                IndicedTabularN.getIndex('i', 0),
                IndicedTabularN.getIndex('j', 0)));
    }

    @Test
    public void testSet() {

        final double[][][] matrix = new double[5][2][4];
        matrix[4][1][2] = Math.E;

        final DoubleIndicedTabularN tn = new DefaultDoubleIndicedTabularN(
                matrix,
                IndicedTabularN.getIndex('i', 5),
                IndicedTabularN.getIndex('j', 2),
                IndicedTabularN.getIndex('k', 4));

        Assertions.assertEquals(Math.E, tn.get(4, 1, 2));
    }

    @Test
    public void testSet1() {

        final double[][][] matrix = new double[5][2][4];
        matrix[4][1][2] = Math.E;

        final DoubleIndicedTabularN tn = new DefaultDoubleIndicedTabularN(
                matrix,
                IndicedTabularN.getIndex('i', 5),
                IndicedTabularN.getIndex('j', 2),
                IndicedTabularN.getIndex('k', 4));

        Assertions.assertEquals(Math.E, tn.get(
                IndicedTabularN.getIndex('i', 4),
                IndicedTabularN.getIndex('k', 2),
                IndicedTabularN.getIndex('j', 1)));
    }

    @Test
    public void testGetOrder() {

        final double[][][] matrix = new double[5][2][4];

        final DoubleIndicedTabularN tn = new DefaultDoubleIndicedTabularN(
                matrix,
                IndicedTabularN.getIndex('i', 5),
                IndicedTabularN.getIndex('j', 2),
                IndicedTabularN.getIndex('k', 4));

        Assertions.assertEquals(3, tn.getOrder());
    }

    @Test
    public void testGetDimension() {

        final double[][][] matrix = new double[5][2][4];

        final DoubleIndicedTabularN tn = new DefaultDoubleIndicedTabularN(
                matrix,
                IndicedTabularN.getIndex('i', 5),
                IndicedTabularN.getIndex('j', 2),
                IndicedTabularN.getIndex('k', 4));

        Assertions.assertEquals(5, tn.getDimension(0));
        Assertions.assertEquals(2, tn.getDimension(1));
        Assertions.assertEquals(4, tn.getDimension(2));
    }

    @Test
    public void testGetDimension1() {

        final double[][][] matrix = new double[5][2][4];

        final DoubleIndicedTabularN tn = new DefaultDoubleIndicedTabularN(
                matrix,
                IndicedTabularN.getIndex('i', 5),
                IndicedTabularN.getIndex('j', 2),
                IndicedTabularN.getIndex('k', 4));

        Assertions.assertEquals(5, tn.getDimension('i'));
        Assertions.assertEquals(2, tn.getDimension('j'));
        Assertions.assertEquals(4, tn.getDimension('k'));
    }
}
