package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.surface.Spheroid;
import com.cosmoloj.util.bib.Page;
import com.cosmoloj.util.bib.Reference;
import com.cosmoloj.util.bib.SectionReference;
import com.cosmoloj.util.bib.SectionReferenceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.MAP_PROJECTIONS)
public class Epsg1027Test {

    private final Epsg1027 transform = new Epsg1027(Spheroid.unit(), 0., 0., 0., 0.);

    @Test
    public void forward() {

        for (int i = 0; i < RESULTS.length; i++) {
            for (int j = 0; j < RESULTS[i].length; j++) {
                Assertions.assertArrayEquals(RESULTS[i][j],
                        transform.compute(new double[]{Math.toRadians(90. - 10. * i), Math.toRadians(10. * j)}), 1e-5);
            }
        }
    }

    @Test
    public void inverse() {

        // pour la première ligne on ne vérifie que la première valeur car les x/y sont identiques pour différentes
        // valeurs phi/lambda qu'on ne peut dont pas distinguer.
        double[] result = transform.inverse(RESULTS[0][0]);
        Assertions.assertArrayEquals(new double[]{90., 0.},
                new double[]{Math.toDegrees(result[0]), Math.toDegrees(result[1])}, 1e-2, "i=0;j=0");

        for (int i = 1; i < RESULTS.length; i++) {
            for (int j = 0; j < RESULTS[i].length; j++) {
                result = transform.inverse(RESULTS[i][j]);
                Assertions.assertArrayEquals(new double[]{90. - 10. * i, 10. * j},
                        new double[]{Math.toDegrees(result[0]), Math.toDegrees(result[1])}, 1e-2, "i=" + i + ";j=" + j);
            }
        }
    }

    @SectionReference(type = SectionReferenceType.TABLE, number = 28)
    @Page(188)
    private static final double[][][] RESULTS = {
        {{0., 1.41421}, {0., 1.41421}, {0., 1.41421}, {0., 1.41421}, {0., 1.41421},
        {0., 1.41421}, {0., 1.41421}, {0., 1.41421}, {0., 1.41421}, {0., 1.41421}},
        {{0., 1.28558}, {0.03941, 1.28702}, {0.07788, 1.29135}, {0.11448, 1.29851}, {0.14830, 1.30842},
        {0.17843, 1.32096}, {0.20400, 1.33594}, {0.22420, 1.35313}, {0.23828, 1.37219}, {0.24558, 1.39273}},
        {{0., 1.14715}, {0.07264, 1.14938}, {0.14391, 1.15607}, {0.21242, 1.16725}, {0.27676, 1.18296},
        {0.33548, 1.20323}, {0.38709, 1.22806}, {0.43006, 1.25741}, {0.46280, 1.29114}, {0.48369, 1.32893}},
        {{0., 1.00000}, {0.10051, 1.00254}, {0.19948, 1.01021}, {0.29535, 1.02311}, {0.38649, 1.04143},
        {0.47122, 1.06544}, {0.54772, 1.09545}, {0.61403, 1.13179}, {0.66797, 1.17481}, {0.70711, 1.22474}},
        {{0., 0.84524}, {0.12353, 0.84776}, {0.24549, 0.85539}, {0.36430, 0.86830}, {0.47831, 0.88680},
        {0.58579, 0.91132}, {0.68485, 0.94244}, {0.77342, 0.98088}, {0.84909, 1.02752}, {0.90904, 1.08335}},
        {{0., 0.68404}, {0.14203, 0.68631}, {0.28254, 0.69317}, {0.41999, 0.70483}, {0.55281, 0.72164},
        {0.67933, 0.74411}, {0.79778, 0.77298}, {0.90620, 0.80919}, {1.00231, 0.85401}, {1.08335, 0.90904}},
        {{0., 0.51764}, {0.15624, 0.51947}, {0.31103, 0.52504}, {0.46291, 0.53452}, {0.61040, 0.54826},
        {0.75197, 0.56674}, {0.88604, 0.59069}, {1.01087, 0.62108}, {1.12454, 0.65927}, {1.22474, 0.70711}},
        {{0., 0.34730}, {0.16631, 0.34858}, {0.33123, 0.35248}, {0.49337, 0.35915}, {0.65136, 0.36883},
        {0.80380, 0.38191}, {0.94928, 0.39896}, {1.08635, 0.42078}, {1.21347, 0.44848}, {1.32893, 0.48369}},
        {{0., 0.17431}, {0.17231, 0.17497}, {0.34329, 0.17698}, {0.51158, 0.18041}, {0.67588, 0.18540},
        {0.83488, 0.19217}, {0.98731, 0.20102}, {1.13192, 0.21240}, {1.26747, 0.22694}, {1.39273, 0.24558}},
        {{0., 0.00000}, {0.17431, 0.00000}, {0.34730, 0.00000}, {0.51764, 0.00000}, {0.68404, 0.00000},
        {0.84524, 0.}, {1.00000, 0.}, {1.14715, 0.}, {1.28558, 0.}, {1.41421, 0.}}
    };
}
