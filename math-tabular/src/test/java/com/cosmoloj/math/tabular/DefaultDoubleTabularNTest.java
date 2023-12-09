package com.cosmoloj.math.tabular;

import com.cosmoloj.math.tabular.n.DefaultDoubleTabularN;
import com.cosmoloj.math.tabular.n.DoubleTabularN;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class DefaultDoubleTabularNTest {

    @Test
    public void get1() {

        final double[][][] matrix = new double[5][2][4];
        matrix[0][0][0] = 1.4;

        final DoubleTabularN tn = new DefaultDoubleTabularN(matrix, 5, 2, 4);

        Assertions.assertEquals(1.4, tn.get(0, 0, 0));
    }

    @Test
    public void get2() {

        final double[][][] matrix = new double[5][2][4];
        matrix[4][1][2] = Math.E;

        final DoubleTabularN tn = new DefaultDoubleTabularN(matrix, 5, 2, 4);

        Assertions.assertEquals(Math.E, tn.get(4, 1, 2));
    }

    @Test
    public void getOrder() {

        final double[][][] matrix = new double[5][2][4];

        final DoubleTabularN tn = new DefaultDoubleTabularN(matrix, 5, 2, 4);

        Assertions.assertEquals(3, tn.getOrder());
    }

    @Test
    public void getDimension() {

        final double[][][] matrix = new double[5][2][4];
        matrix[4][1][2] = Math.E;

        final DoubleTabularN tn = new DefaultDoubleTabularN(matrix, 5, 2, 4);

        Assertions.assertEquals(5, tn.getDimension(0));
        Assertions.assertEquals(2, tn.getDimension(1));
        Assertions.assertEquals(4, tn.getDimension(2));
    }
}
