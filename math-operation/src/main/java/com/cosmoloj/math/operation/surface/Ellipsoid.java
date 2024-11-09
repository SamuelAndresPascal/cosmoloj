package com.cosmoloj.math.operation.surface;

import com.cosmoloj.math.operation.trials.AbstractInitTrials;
import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.util.bib.Page;
import com.cosmoloj.util.bib.Cite;
import com.cosmoloj.util.bib.SectionReference;
import com.cosmoloj.util.bib.SectionReferenceType;
import com.cosmoloj.math.operation.trials.AbsoluteDifferenceDoubleTrials;

/**
 *
 * @author Samuel Andrés
 */
public final class Ellipsoid implements Surface {

    private enum Parameter {
        SEMI_MINOR_AXIS, INVERSE_FLATTENING, FLATTENING, ECCENTRICITY;
    }

    private final double a;
    private final double b;
    private final double inverseFlattening;
    private final double f;
    private final double e;
    private final double e2;
    private final double secondEccentricity;
    private final double e1;
    private final double e12;
    private final double ra;
    private final double mp;
    private final double mod;
    private final double qp;
    private final double rq;

    private Ellipsoid(final double a, final double secondParameter, final Parameter p) {
        this.a = a;
        switch (p) {
            case SEMI_MINOR_AXIS -> {
                this.b = secondParameter;
                this.inverseFlattening = a / (a - b);
                this.f = 1. / inverseFlattening;
                this.e = Math.sqrt(f * (2. - f));
            }
            case INVERSE_FLATTENING -> {
                this.inverseFlattening = secondParameter;
                this.f = 1. / inverseFlattening;
                this.b = a * (1. - f);
                this.e = Math.sqrt(f * (2. - f));
            }
            case FLATTENING -> {
                this.f = secondParameter;
                this.inverseFlattening = 1. / f;
                this.b = a * (1. - f);
                this.e = Math.sqrt(f * (2. - f));
            }
            case ECCENTRICITY -> {
                this.e = secondParameter;
                this.b = a * Math.sqrt(1. - e * e);
                this.inverseFlattening = a / (a - b);
                this.f = 1. / inverseFlattening;
            }
            default -> throw new IllegalArgumentException();
        }
        this.e2 = e * e;
        this.secondEccentricity = Math.sqrt(e2 / (1. - e2));
        this.e1 =  (1. - Math.sqrt(1. - e2)) / (1. + Math.sqrt(1. - e2));
        this.e12 = e1 * e1;
        this.ra = a * Math.sqrt((1. - (1. - e2) / (2. * e)) * Math.log((1. - e) / (1. + e)));
        this.mp = m(Math.PI / 2.);
        this.mod = a - Math.floor(a / b) * b;
        this.qp = q(Math.PI / 2.);
        this.rq = a * Math.sqrt(qp / 2.);
    }

    /**
     * @return <span class="en">semi-major axis</span>
     */
    public double a() {
        return a;
    }

    /**
     * @return <span class="en">semi-minor axis</span>
     */
    public double b() {
        return b;
    }

    public double getInverseFlattening() {
        return inverseFlattening;
    }

    /**
     * @return <span class="en">flattening</span>
     */
    public double f() {
        return f;
    }

    /**
     * @return <span class="en">eccentricity</span>
     */
    public double e() {
        return e;
    }

    /**
     * @return <span class="en">square eccentricity</span>
     */
    public double e2() {
        return e2;
    }

    /**
     * @return <span class="en">second eccentricity</span>
     */
    public double secondE() {
        return secondEccentricity;
    }

    public double mod() {
        return mod;
    }

    /**
     * rho
     * @param phi
     * @return <span class="en">meridian curvature radius</span>
     */
    public double rho(final double phi) {
        final double esinphi2 = eSin(phi);
        return a * (1. - e2) / (esinphi2 * Math.sqrt(esinphi2));
    }

    /**
     * @param phi
     * @return
     */
    public double eSin(final double phi) {
        final double sinphi = Math.sin(phi);
        return 1. - e2 * sinphi * sinphi;
    }

    /**
     * @param phi
     * @return
     */
    public double eSinSqrt(final double phi) {
        return Math.sqrt(eSin(phi));
    }

    /**
     * @param phi
     * @return <span class="en">prime vertical curvature radius</span>
     */
    public double nu(final double phi) {
        return a / eSinSqrt(phi);
    }

    /**
     * @return <span class="en">authalic sphere radius</span>
     */
    public double ra() {
        return ra;
    }

    public double qp() {
        return qp;
    }

    public double rq() {
        return rq;
    }

    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.FORMULA, id = "3-11")
    public double betaPhi(final double phi) {
        return betaQ(q(phi));
    }

    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.FORMULA, id = "3-11")
    public double betaQ(final double q) {
        return Math.asin(q / qp);
    }

    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.FORMULA, id = "3-12")
    public double q(final double phi) {
        final double sinphi = Math.sin(phi);
        return (1. - e2)
                * (sinphi / (1. - e2 * sinphi * sinphi)
                - (1. / (2. * e)) * Math.log((1. - e * sinphi) / (1. + e * sinphi)));
    }

    /**
     * <div class="en">Approximate latitude from authalic latitude using a series.</div>
     *
     * @param betap <span class="en">authalic latitude</span>
     * @return <span class="en">latitude</span>
     */
    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.FORMULA, id = "3-18")
    public double phi(final double betap) {
        return betap + e2 * ((1. / 3. + e2 * (31. / 180. + e2 * 517. / 5040.)) * Math.sin(2. * betap)
                    + e2 * ((23. / 360. + e2 * 251. / 3780.) * Math.sin(4. * betap)
                    + e2 * 761. / 45360. * Math.sin(6. * betap)));
    }

    /**
     * @param phi
     * @return <span class="en">conformal sphere radius</span>
     */
    public double rc(final double phi) {
        return Math.sqrt(1 - e2) * nu(phi);
    }

    /**
     * @param m
     * @return <span class="en">rectifying latitude</span>
     */
    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.FORMULA, id = "3-20")
    public double rectifyingLatitude(final double m) {
        return m / mp() * (Math.PI / 2.);
    }

    /**
     * <div class="en">Value of m(phi) / phi when phi = PI/2.</div>
     * @return
     */
    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.FORMULA, id = "3-21")
    private double mp() {
        return mp;
    }

    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.FORMULA, id = "3-24")
    public double e1() {
        return e1;
    }

    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.FORMULA, id = "3-26")
    public double phi1(final double mu) {
        return mu + e1 * ((3. / 2. - e12 * 27. / 32.) * Math.sin(2. * mu)
                + e1 * ((21. / 16. - e12 * 55. / 32.) * Math.sin(4. * mu)
                + e1 * (151. / 96. * Math.sin(6. * mu)
                + e1 * 1097. / 512. * Math.sin(8. * mu))));
    }

    /**
     * <div class="en">Distance along the meridian from the equator to latitude phi.</div>
     *
     * @param phi
     * @return
     */
    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.FORMULA, id = "3-21")
    public double m(final double phi) {
        return a * ((1. - e2 * (1. / 4. + e2 * (3. / 64. + e2 * 5. / 256.))) * phi
                + e2 * (-(3. / 8. + e2 * (3. / 32. + e2 * 45. / 1024.)) * Math.sin(2. * phi)
                + e2 * ((15. / 256. + e2 * 45. / 1024.) * Math.sin(4. * phi)
                + e2 * -35. / 3072. * Math.sin(6. * phi))));
    }

    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.FORMULA, id = "7-19")
    public double mu(final double m) {
        return m / (a * (1. - e2 * (1. / 4. + e2 * (3. / 64. + e2 * (5. / 256.)))));
    }

    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.FORMULA, id = "18-17")
    public double mp(final double phi) {
        return (1. - e2 * (1. / 4. + e2 * (3. / 64. + e2 * 5. / 256.)))
                + 2. * e2 * (-(3. / 8. + e2 * (3. / 32. + e2 * 45. / 1024.)) * Math.cos(2. * phi)
                + 4. * e2 * ((15. / 256. + e2 * 45. / 1024.) * Math.cos(4. * phi)
                + 6. * e2 * -35. / 3072. * Math.cos(6. * phi)));
    }

    public static Ellipsoid ofSemiMinorAxis(final double a, final double b) {
        return new Ellipsoid(a, b, Parameter.SEMI_MINOR_AXIS);
    }

    public static Ellipsoid ofInverseFlattening(final double a, final double invf) {
        return new Ellipsoid(a, invf, Parameter.INVERSE_FLATTENING);
    }

    public static Ellipsoid ofFlattening(final double a, final double f) {
        return new Ellipsoid(a, f, Parameter.FLATTENING);
    }

    public static Ellipsoid ofEccentricity(final double a, final double e) {
        return new Ellipsoid(a, e, Parameter.ECCENTRICITY);
    }

    public static Ellipsoid ofSquareEccentricity(final double a, final double e2) {
        return new Ellipsoid(a, Math.sqrt(e2), Parameter.ECCENTRICITY);
    }

    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.FORMULA, id = "3-16")
    public final class PhiIterator extends AbstractInitTrials implements AbsoluteDifferenceDoubleTrials {

        private final double q;
        private final double precision;

        public PhiIterator(final double precision, final double q) {
            super(Math.asin(q / 2.));
            this.precision = precision;
            this.q = q;
        }

        @Override
        public double precision() {
            return precision;
        }

        @Override
        public double trial(final double phi) {
            final double sinphi = Math.sin(phi);
            final double esinphi = e * sinphi;
            final double esinphi21 = 1. - esinphi * esinphi;

            return phi
                    + esinphi21 * esinphi21 / (2. * Math.cos(phi))
                    * (q / (1. - e2) - sinphi / esinphi21 + 1. / (2. * e) * Math.log((1. - esinphi) / (1. + esinphi)));
        }
    }

    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.TABLE, number = 1)
    @Page(12)
    public static final Ellipsoid GRS_80 = ofInverseFlattening(6_378_137., 298.257);

    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.TABLE, number = 1)
    @Page(12)
    public static final Ellipsoid WGS_72 = ofInverseFlattening(6_378_135., 298.26);

    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.TABLE, number = 1)
    @Page(12)
    public static final Ellipsoid AUSTRALIAN = ofInverseFlattening(6_378_160., 298.25);

    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.TABLE, number = 1)
    @Page(12)
    public static final Ellipsoid KRASOVSKY = ofInverseFlattening(6_378_245., 298.3);

    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.TABLE, number = 1)
    @Page(12)
    public static final Ellipsoid INTERNATIONAL = ofInverseFlattening(6_378_388., 297.);

    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.TABLE, number = 1)
    @Page(12)
    public static final Ellipsoid HAYFORD = ofInverseFlattening(6_378_388., 297.);

    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.TABLE, number = 1)
    @Page(12)
    public static final Ellipsoid CLARKE_1880 = ofInverseFlattening(6_378_249.1, 293.46);

    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.TABLE, number = 1)
    @Page(12)
    public static final Ellipsoid CLARKE_1866 = ofSemiMinorAxis(6_378_206.4, 6_356_583.8);

    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.TABLE, number = 1)
    @Page(12)
    public static final Ellipsoid AIRY = ofSemiMinorAxis(6_377_563.4, 6_356_256.9);

    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.TABLE, number = 1)
    @Page(12)
    public static final Ellipsoid BESSEL = ofSemiMinorAxis(6_377_397.2, 6_356_079.0);

    @Cite(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.TABLE, number = 1)
    @Page(12)
    public static final Ellipsoid EVEREST = ofSemiMinorAxis(6_377_276.3, 6_356_075.4);
}
