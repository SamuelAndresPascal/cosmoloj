package com.cosmoloj.format.gr3df97a;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author Samuel Andrés
 */
public class GridGr3df97aTest {

    private final GridGr3df97a read;

    public GridGr3df97aTest() throws IOException {
        this.read = GridGr3df97a.read();
    }

    @Test // non-régression
    public void get() throws IOException {

        Assertions.assertEquals(111, read.getRows());
        Assertions.assertEquals(156, read.getCols());

        Assertions.assertArrayEquals(new double[]{-166.017, -65.936, 317.157}, read.apply(new double[]{-5.4, 41.7}));
        Assertions.assertArrayEquals(new double[]{-166.162, -65.786, 317.344}, read.apply(new double[]{-5.4, 41.8}));
        Assertions.assertArrayEquals(new double[]{-166.04, -66.034, 317.161}, read.apply(new double[]{-5.5, 41.7}));
        Assertions.assertArrayEquals(new double[]{-166.188, -65.882, 317.353}, read.apply(new double[]{-5.5, 41.8}));
        Assertions.assertArrayEquals(
                new double[]{-166.15789199999998, -65.910472, 317.31444}, read.apply(new double[]{-5.498, 41.78}));
    }
}
