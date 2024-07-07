package com.cosmoloj.math.operation.projection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class MapProjectionsTest {


    @Test
    public void pole1() {
        Assertions.assertArrayEquals(new double[]{Math.toRadians(-18.9169858), Math.toRadians(3.5880129)},
            MapProjections.pole(Math.toRadians(30.), Math.toRadians(-75.), Math.toRadians(60.), Math.toRadians(-50.)),
            1e-9);
    }
}
