package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9822Test {

    private final Epsg9822 transform = new Epsg9822(Ellipsoid.CLARKE_1866,
            0., 0., Math.toRadians(29.5), Math.toRadians(45.5), 0., 0.);

    @Test
    public void forward() {
        for (int i = 0; i < RHO.length; i++) {
            Assertions.assertArrayEquals(new double[]{RHO[i], 0.},
                            transform.computePolar(new double[]{Math.toRadians(52. - i), 0.}), 1.);
        }
    }

    @Test
    public void inverse() {
        for (int i = 0; i < RHO.length; i++) {
            Assertions.assertArrayEquals(new double[]{Math.toRadians(52. - i), 0.},
                    transform.inversePolar(new double[]{RHO[i], 0.}), 1e-7);
        }
    }

    private static final double[] RHO = new double[]{
        6713781.,
        6822266.,
        6931335.,
        7040929.,
        7150989.,
        7261460.,
        7372290.,
        //7427824., 45.5
        7483429.,
        7594829.,
        7706445.,
        7818233.,
        7930153.,
        8042164.,
        8154230.,
        8266313.,
        8378379.,
        8490394.,
        8602328.,
        8714149.,
        8825828.,
        8937337.,
        9048649.,
        9159737.,
        // 9215189., 29.5
        9270575.,
        9381141.,
        9491411.,
        9601361.,
        9710969.,
        9820216.,
        9929080.,
        10037541.,
    };
}
