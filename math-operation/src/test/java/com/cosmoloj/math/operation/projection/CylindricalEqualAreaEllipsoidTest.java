package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Page;
import com.cosmoloj.util.bib.Cite;
import com.cosmoloj.util.bib.SectionReference;
import com.cosmoloj.util.bib.SectionReferenceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.MAP_PROJECTIONS)
@SectionReference(type = SectionReferenceType.APPENDIX, id = "A")
@Page(281)
public class CylindricalEqualAreaEllipsoidTest {

    private static final Ellipsoid ELLIPSOID = Ellipsoid.CLARKE_1866;

    private final double phiNormal = Math.toRadians(5.);
    private final double lambdaNormal = Math.toRadians(-78.);
    private final double eastingNormal = -332699.8;
    private final double northingNormal = 554248.5;

    private final CylindricalEqualAreaEllipsoid.Normal normal = new CylindricalEqualAreaEllipsoid.Normal(
            ELLIPSOID, Math.toRadians(5.), Math.toRadians(-75.));

    @Test
    public void forwardNormalEllipsoidal() {

        Assertions.assertArrayEquals(new double[]{eastingNormal, northingNormal},
                normal.compute(new double[]{phiNormal, lambdaNormal}), 1e-1);
    }

    @Test
    public void inverseNormalEllipsoidal() {

        Assertions.assertArrayEquals(new double[]{phiNormal, lambdaNormal},
                normal.inverse(new double[]{eastingNormal, northingNormal}), 1e-3);
    }

    private final double phiTransverse = Math.toRadians(40.);
    private final double lambdaTransverse = Math.toRadians(-83.);
    private final double eastingTransverse = -687825.8;
    private final double northingTransverse = 1128646.2;

    private final CylindricalEqualAreaEllipsoid.Transverse transverse = new CylindricalEqualAreaEllipsoid.Transverse(
            ELLIPSOID, 0.99, Math.toRadians(30.), Math.toRadians(-75.));

    @Test
    public void forwardTransverseEllipsoidal() {

        Assertions.assertArrayEquals(new double[]{eastingTransverse, northingTransverse},
                transverse.compute(new double[]{phiTransverse, lambdaTransverse}), 1e-1);
    }

    @Test
    public void inverseTransverseEllipsoidal() {

        Assertions.assertArrayEquals(new double[]{phiTransverse, lambdaTransverse},
                transverse.inverse(new double[]{eastingTransverse, northingTransverse}), 1e-3);
    }

    private final double phiOblique = Math.toRadians(42.);
    private final double lambdaOblique = Math.toRadians(-77.);
    private final double eastingOblique = 12_582_246.4;
    private final double northingOblique = 1_207_233.0;

    private final double betap = Math.toRadians(55.5374608);
    private final double phip = Math.toRadians(55.6583959);
    private final double lambdap = Math.toRadians(71.8693268);

    @Test
    public void pole() {

        final double phi1 = Math.toRadians(30.);
        final double lambda1 = Math.toRadians(-75.);
        final double phi2 = Math.toRadians(40.);
        final double lambda2 = Math.toRadians(-80.);

        final double beta1 = ELLIPSOID.betaPhi(phi1);
        final double beta2 = ELLIPSOID.betaPhi(phi2);

        Assertions.assertEquals(Math.toRadians(29.8877623), beta1, 1e-8);
        Assertions.assertEquals(Math.toRadians(39.8722878), beta2, 1e-8);

        final double fixBetap = Math.toRadians(18.0472981);
        final double fixLambdap = Math.toRadians(25.7934757);
        Assertions.assertArrayEquals(new double[]{fixBetap, fixLambdap},
                MapProjections.pole(beta1, lambda1, beta2, lambda2), 1e-8);

        final double q = ELLIPSOID.qp() * Math.sin(betap);
        Assertions.assertEquals(phip, ELLIPSOID.new PhiIterator(1e-9, q).loop(), 1e-7);
    }

    private final CylindricalEqualAreaEllipsoid.Oblique obliqueEllipsoidal = new CylindricalEqualAreaEllipsoid.Oblique(
            ELLIPSOID, 1., phip, lambdap);

    @Test
    public void forwardObliqueEllipsoidal() {

        Assertions.assertArrayEquals(new double[]{eastingOblique, northingOblique},
                obliqueEllipsoidal.compute(new double[]{phiOblique, lambdaOblique}), 1e-1);
    }

    @Test
    public void inverseObliqueEllipsoidal() {

        Assertions.assertArrayEquals(new double[]{phiOblique, lambdaOblique},
                obliqueEllipsoidal.inverse(new double[]{eastingOblique, northingOblique}), 1e-8);
    }

    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.TABLE, number = 13)
    @Page(83)
    private static final double[][] POLE_CLARKE_1866 = new double[][]{
        {0.9983056818, -0.0002822502, -0.0000001158}, // 0
        {0.9984181201, -0.0002633856, -0.0000001008}, // 15
        {0.9987260769, -0.0002118145, -0.0000000652}, // 30
        {0.9991485842, -0.0001412929, -0.0000000290}, // 45
        {0.9995732199, -0.0000706875, -0.0000000073}, // 60
        {0.9998854334, -0.0000189486, -0.0000000005}, // 75
        {1.0, 0.0, 0.0} // 90
    };

    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.TABLE, number = 13)
    @Page(83)
    private static final double[][] POLE_INTERNATIONAL = new double[][]{
        {0.9983172080, -0.0002803311, -0.0000001142}, // 0
        {0.9984288886, -0.0002615944, -0.0000000995}, // 15
        {0.9987347648, -0.0002103733, -0.0000000644}, // 30
        {0.9991544051, -0.0001403310, -0.0000000287}, // 45
        {0.9995761449, -0.0000702060, -0.0000000072}, // 60
        {0.9998862200, -0.0000188195, -0.0000000005}, // 75
        {1.0, 0.0, 0.0} // 90
    };

    @ParameterizedTest
    @ValueSource(ints = {0, 15, 30, 45, 60, 75, 90})
    public void coeffsClarke(final int latitude) {
        final CylindricalEqualAreaEllipsoid.Oblique proj = new CylindricalEqualAreaEllipsoid.Oblique(
                ELLIPSOID, 1., Math.toRadians(latitude), 0.);
        Assertions.assertArrayEquals(POLE_CLARKE_1866[latitude / 15], proj.poleCoefs(), 1e-5);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 15, 30, 45, 60, 75, 90})
    public void coeffsInternational(final int latitude) {
        final CylindricalEqualAreaEllipsoid.Oblique proj = new CylindricalEqualAreaEllipsoid.Oblique(
                Ellipsoid.INTERNATIONAL, 1., Math.toRadians(latitude), 0.);
        Assertions.assertArrayEquals(POLE_INTERNATIONAL[latitude / 15], proj.poleCoefs(), 1e-5);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 15, 30, 45, 60, 75, 90})
    public void coeffsOtherEllipsoid_clarkeEquivalent(final int latitude) {
        final CylindricalEqualAreaEllipsoid.Oblique proj = new CylindricalEqualAreaEllipsoid.Oblique(
                Ellipsoid.ofSemiMinorAxis(6_378_206.4, 6_356_583.8), 1., Math.toRadians(latitude), 0.);
        Assertions.assertArrayEquals(POLE_CLARKE_1866[latitude / 15], proj.poleCoefs(), 1e-5);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 15, 30, 45, 60, 75, 90})
    public void coeffsOtherEllipsoid_internationalEquivalent(final int latitude) {
        final CylindricalEqualAreaEllipsoid.Oblique proj = new CylindricalEqualAreaEllipsoid.Oblique(
                Ellipsoid.ofInverseFlattening(6_378_388., 297.), 1., Math.toRadians(latitude), 0.);
        Assertions.assertArrayEquals(POLE_INTERNATIONAL[latitude / 15], proj.poleCoefs(), 1e-5);
    }

    @Test
    public void integrationClarke1866() {
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[0][0],
                CylindricalEqualAreaEllipsoid.Oblique.b(Ellipsoid.CLARKE_1866), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[0][1],
                CylindricalEqualAreaEllipsoid.Oblique.an(Ellipsoid.CLARKE_1866, 2), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[0][2],
                CylindricalEqualAreaEllipsoid.Oblique.an(Ellipsoid.CLARKE_1866, 4), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[0][3],
                CylindricalEqualAreaEllipsoid.Oblique.an(Ellipsoid.CLARKE_1866, 6), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[0][4],
                CylindricalEqualAreaEllipsoid.Oblique.bn(Ellipsoid.CLARKE_1866, 2), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[0][5],
                CylindricalEqualAreaEllipsoid.Oblique.anm(Ellipsoid.CLARKE_1866, 2, 2), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[0][6],
                CylindricalEqualAreaEllipsoid.Oblique.anm(Ellipsoid.CLARKE_1866, 2, 4), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[0][7],
                CylindricalEqualAreaEllipsoid.Oblique.anm(Ellipsoid.CLARKE_1866, 2, 6), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[0][8],
                CylindricalEqualAreaEllipsoid.Oblique.bn(Ellipsoid.CLARKE_1866, 4), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[0][9],
                CylindricalEqualAreaEllipsoid.Oblique.anm(Ellipsoid.CLARKE_1866, 4, 2), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[0][10],
                CylindricalEqualAreaEllipsoid.Oblique.anm(Ellipsoid.CLARKE_1866, 4, 4), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[0][11],
                CylindricalEqualAreaEllipsoid.Oblique.anm(Ellipsoid.CLARKE_1866, 4, 6), 1e-10);
    }

    @Test
    public void integrationInternational() {
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[1][0],
                CylindricalEqualAreaEllipsoid.Oblique.b(Ellipsoid.INTERNATIONAL), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[1][1],
                CylindricalEqualAreaEllipsoid.Oblique.an(Ellipsoid.INTERNATIONAL, 2), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[1][2],
                CylindricalEqualAreaEllipsoid.Oblique.an(Ellipsoid.INTERNATIONAL, 4), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[1][3],
                CylindricalEqualAreaEllipsoid.Oblique.an(Ellipsoid.INTERNATIONAL, 6), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[1][4],
                CylindricalEqualAreaEllipsoid.Oblique.bn(Ellipsoid.INTERNATIONAL, 2), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[1][5],
                CylindricalEqualAreaEllipsoid.Oblique.anm(Ellipsoid.INTERNATIONAL, 2, 2), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[1][6],
                CylindricalEqualAreaEllipsoid.Oblique.anm(Ellipsoid.INTERNATIONAL, 2, 4), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[1][7],
                CylindricalEqualAreaEllipsoid.Oblique.anm(Ellipsoid.INTERNATIONAL, 2, 6), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[1][8],
                CylindricalEqualAreaEllipsoid.Oblique.bn(Ellipsoid.INTERNATIONAL, 4), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[1][9],
                CylindricalEqualAreaEllipsoid.Oblique.anm(Ellipsoid.INTERNATIONAL, 4, 2), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[1][10],
                CylindricalEqualAreaEllipsoid.Oblique.anm(Ellipsoid.INTERNATIONAL, 4, 4), 1e-10);
        Assertions.assertEquals(CylindricalEqualAreaEllipsoid.GENERAL[1][11],
                CylindricalEqualAreaEllipsoid.Oblique.anm(Ellipsoid.INTERNATIONAL, 4, 6), 1e-10);
    }
}
