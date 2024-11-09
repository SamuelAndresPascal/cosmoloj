package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.surface.Spheroid;
import com.cosmoloj.util.bib.Page;
import com.cosmoloj.util.bib.Cite;
import com.cosmoloj.util.bib.SectionReference;
import com.cosmoloj.util.bib.SectionReferenceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.MAP_PROJECTIONS)
@SectionReference(type = SectionReferenceType.APPENDIX, id = "A")
@Page(278)
public class CylindricalEqualAreaSpheroidTest {

    private final double phiNormal = Math.toRadians(35.);
    private final double lambdaNormal = Math.toRadians(80.);
    private final double eastingNormal = 2.3428242;
    private final double northingNormal = 0.6623090;

    private final CylindricalEqualAreaSpheroid.Normal normal = new CylindricalEqualAreaSpheroid.Normal(
            Spheroid.unit(), Math.toRadians(30.), Math.toRadians(-75.));

    @Test
    public void forwardNormalSpherical() {

        Assertions.assertArrayEquals(new double[]{eastingNormal, northingNormal},
                normal.compute(new double[]{phiNormal, lambdaNormal}), 1e-7);
    }

    @Test
    public void inverseNormalSpherical() {

        Assertions.assertArrayEquals(new double[]{phiNormal, lambdaNormal},
                normal.inverse(new double[]{eastingNormal, northingNormal}), 1e-7);
    }

    private final double phiTransverse = Math.toRadians(25.);
    private final double lambdaTransverse = Math.toRadians(-90.);
    private final double eastingTransverse = -0.2393569;
    private final double northingTransverse = 0.7828478;

    private final CylindricalEqualAreaSpheroid.Transverse transverse = new CylindricalEqualAreaSpheroid.Transverse(
            Spheroid.unit(), 0.98, Math.toRadians(-20), Math.toRadians(-75.));

    @Test
    public void forwardTransverseSpherical() {

        Assertions.assertArrayEquals(new double[]{eastingTransverse, northingTransverse},
                transverse.compute(new double[]{phiTransverse, lambdaTransverse}), 1e-7);
    }

    @Test
    public void inverseTransverseSpherical() {

        Assertions.assertArrayEquals(new double[]{phiTransverse, lambdaTransverse},
                transverse.inverse(new double[]{eastingTransverse, northingTransverse}), 1e-7);
    }

    private final double phiOblique = Math.toRadians(-30.);
    private final double lambdaOblique = Math.toRadians(-100.);
    private final double eastingOblique = 3.6368646;
    private final double northingOblique = -0.0309947;

    private final double phi1 = Math.toRadians(30.);
    private final double lambda1 = Math.toRadians(-75.);
    private final double phi2 = Math.toRadians(60.);
    private final double lambda2 = Math.toRadians(-50.);

    private final double phip = Math.toRadians(-18.9169858);
    private final double lambdap = Math.toRadians(3.5880129);

    @Test
    public void pole1() {
        Assertions.assertArrayEquals(new double[]{phip, lambdap},
                MapProjections.pole(phi1, lambda1, phi2, lambda2), 1e-9);
    }

    private final CylindricalEqualAreaSpheroid.Oblique oblique = new CylindricalEqualAreaSpheroid.Oblique(
            Spheroid.unit(), 0.98, phip, lambdap);

    @Test
    public void forwardObliqueSpherical() {

        Assertions.assertArrayEquals(new double[]{eastingOblique, northingOblique},
                oblique.compute(new double[]{phiOblique, lambdaOblique}), 1e-7);
    }

    @Test
    public void inverseObliqueSpherical() {

        Assertions.assertArrayEquals(new double[]{phiOblique, lambdaOblique + 2. * Math.PI},
                oblique.inverse(new double[]{eastingOblique, northingOblique}), 1e-7);
    }

}
