package com.cosmoloj.math.operation.projection;

import com.cosmoloj.util.bib.Reference;
import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.util.bib.SectionReference;
import com.cosmoloj.util.bib.SectionReferenceType;

/**
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.MAP_PROJECTIONS)
public final class MapProjections {

    private MapProjections() {
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "5-3")
    public static double cosC_5_3(final double cosPhi1, final double sinPhi1, final double lambda0,
            final double phi, final double lambda) {
        return sinPhi1 * Math.sin(phi) + cosPhi1 * Math.cos(phi) * Math.cos(lambda - lambda0);
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "5-3a")
    public static double sinC_5_3a(final double cosPhi1, final double phi1, final double lambda0,
            final double phi, final double lambda) {
        final double sinphi = Math.sin(phi - phi1);
        final double sinlambda = Math.sin(lambda - lambda0);
        return Math.sqrt(sinphi * sinphi / 2. + cosPhi1 * Math.cos(phi) * sinlambda * sinlambda / 2.);
    }

    /**
     *
     * @param phi
     * @param lambda
     * @param a
     * @param b
     * @param lambda0
     * @return
     */
    @SectionReference(type = SectionReferenceType.FORMULA, id = {"5-9", "5-10b"})
    public static double[] inverseShiftTransform(final double phi, final double lambda, final double a,
            final double b, final double lambda0) {

        final double sina = Math.sin(a);
        final double sinfi = Math.sin(phi);
        final double cosa = Math.cos(a);
        final double cosfi = Math.cos(phi);
        final double cosl = Math.cos(lambda - b);
        final double sinl = Math.sin(lambda - lambda0);

        return new double[]{Math.asin(sina * sinfi + cosa * cosfi * cosl),
            Math.atan2(cosfi * sinl, (sina * cosfi * cosl - cosa * sinfi)) + lambda0};
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "7-1")
    public static double x_7_1(final double radius, final double cosPhi1, final double lambda0, final double lon) {
        return radius * (lon - lambda0) * cosPhi1;
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "7-2")
    public static double y_7_2(final double radius, final double cosPhi1, final double lat) {
        return radius * Math.log(Math.tan(Math.PI / 4. + lat / 2.)) * cosPhi1;
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "7-3")
    public static double y_7_3(final double radius, final double cosPhi1, final double lat) {
        return radius / 2. * (Math.log((1. + Math.sin(lat)) / (1. - Math.sin(lat)))) * cosPhi1;
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "7-4")
    public static double phi_7_4(final double radius, final double cosPhi1, final double y) {
        return (Math.PI / 2. - 2 * Math.atan(Math.exp(-y / radius))) / cosPhi1;
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "7-4a")
    public static double phi_7_4a(final double radius, final double cosPhi1, final double y) {
        return Math.atan(Math.sinh(y / radius)) / cosPhi1;
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "7-5")
    public static double lambda_7_5(final double radius, final double cosPhi1, final double lambda0, final double x) {
        return (x / radius + lambda0) / cosPhi1;
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = {"9-1", "9-2"})
    public static double[] pole(final double phi1, final double lambda1, final double phi2, final double lambda2) {
        final double cosphi1 = Math.cos(phi1);
        final double sinphi1 = Math.sin(phi1);
        final double cosphi2 = Math.cos(phi2);
        final double sinphi2 = Math.sin(phi2);
        final double lambdap = Math.atan2(cosphi1 * sinphi2 * Math.cos(lambda1) - sinphi1 * cosphi2 * Math.cos(lambda2),
                sinphi1 * cosphi2 * Math.sin(lambda2) - cosphi1 * sinphi2 * Math.sin(lambda1));
        return new double[]{
            Math.atan(-Math.cos(lambdap - lambda1) / Math.tan(phi1)), // do not use artan2()
            lambdap
        };
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = {"9-7", "9-8"})
    public static double[] pole(final double phiz, final double lambdaz, final double gamma) {
        return new double[]{
            Math.atan(Math.cos(phiz) * Math.sin(gamma)),
            Math.atan2(Math.cos(gamma), Math.sin(phiz) * Math.sin(gamma)) + lambdaz
        };
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "20-3")
    public static double x_20_3(final double radius, final double lambda0, final double lat,
            final double lon) {
        return radius * Math.cos(lat) * Math.sin(lon - lambda0);
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "20-4")
    public static double y_20_4(final double radius, final double cosPhi1, final double sinPhi1,
            final double lambda0, final double lat, final double lon) {
        return radius * (cosPhi1 * Math.sin(lat) - sinPhi1 * Math.cos(lat) * Math.cos(lon - lambda0));
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "20-4")
    public static double y_20_4(final double radius, final double phi1, final double centralLongitude,
            final double lat, final double lon) {
        return y_20_4(radius, Math.cos(phi1), Math.sin(phi1), centralLongitude, lat, lon);
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "20-14")
    public static double phi_20_14(final double cosPhi1, final double sinPhi1, final double rho,
            final double cosC, final double sinC, final double y) {
        return Math.asin(cosC * sinPhi1 + (y * sinC * cosPhi1 / rho));
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "20-14")
    public static double phi_20_14(final double phi1, final double rho, final double c, final double y) {
        return phi_20_14(Math.cos(phi1), Math.sin(phi1), rho, Math.cos(c), Math.sin(c), y);
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "20-15")
    public static double lambda_20_15(final double cosPhi1, final double sinPhi1, final double centralLongitude,
            final double rho, final double cosC, final double sinC, final double x, final double y) {
        return centralLongitude + Math.atan2(x * sinC, rho * cosPhi1 * cosC - y * sinPhi1 * sinC);
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "20-15")
    public static double lambda_20_15(final double phi1, final double centralLongitude, final double rho,
            final double c, final double x, final double y) {
        return lambda_20_15(Math.cos(phi1), Math.sin(phi1), centralLongitude, rho, Math.cos(c), Math.sin(c), x, y);
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "20-16")
    public static double lambda_20_16(final double centralLongitude, final double x, final double y) {
        return centralLongitude + Math.atan2(x, y);
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "20-17")
    public static double lambda_20_17(final double centralLongitude, final double x, final double y) {
        return centralLongitude + Math.atan2(x, -y);
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "20-18")
    public static double rho_20_18(final double x, final double y) {
        return Math.sqrt(x * x + y * y);
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "20-19")
    public static double c_20_19(final double radius, final double rho) {
        return Math.asin(rho / radius);
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "21-2")
    public static double x_21_2(final double radius, final double centralLongitude, final double lat, final double lon,
            final double scaleFactor) {
        return radius * scaleFactor * Math.cos(lat) * Math.sin(lon - centralLongitude);
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "21-3")
    public static double y_21_3(final double radius, final double cosPhi1, final double sinPhi1,
            final double centralLongitude, final double lat, final double lon, final double scaleFactor) {
        return radius * scaleFactor
                * (cosPhi1 * Math.sin(lat) - sinPhi1 * Math.cos(lat) * Math.cos(lon - centralLongitude));
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "21-4")
    public static double k_21_4(final double cosPhi1, final double sinPhi1, final double centralLongitude,
            final double centralScaleFactor, final double lat, final double lon) {
        return 2 * centralScaleFactor
                / (1. + sinPhi1 * Math.sin(lat) + cosPhi1 * Math.cos(lat) * Math.cos(lon - centralLongitude));
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "21-15")
    public static double c_21_15(final double radius, final double centralScaleFactor, final double rho) {
        return 2 * Math.atan(rho / (2 * radius * centralScaleFactor));
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "22-4")
    public static double x_22_4(final double radius, final double centralLongitude, final double scaleFactor,
            final double lat, final double lon) {
        return radius * scaleFactor * Math.cos(lat) * Math.sin(lon - centralLongitude);
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "22-5")
    public static double y_22_5(final double radius, final double cosPhi1, final double sinPhi1,
            final double centralLongitude, final double scaleFactor, final double lat, final double lon) {
        return radius * scaleFactor
                * (cosPhi1 * Math.sin(lat) - sinPhi1 * Math.cos(lat) * Math.cos(lon - centralLongitude));
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "22-16")
    public static double c_22_16(final double radius, final double rho) {
        return Math.atan(rho / radius);
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "22-14")
    public static double phi_22_14(final double cosPhi1, final double sinPhi1, final double rho, final double cosC,
            final double sinC, final double y) {
        return Math.asin(cosC * sinPhi1 + (y * sinC * cosPhi1 / rho));
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = "22-15")
    public static double lambda_22_15(final double cosPhi1, final double sinPhi1, final double centralLongitude,
            final double rho, final double cosC, final double sinC, final double x, final double y) {
        return centralLongitude + Math.atan2(x * sinC, rho * cosPhi1 * cosC - y * sinPhi1 * sinC);
    }

    /**
     * <div class="fr"></div>
     *
     * @param phi1 <span class="fr">Latitude du point à transformer</span><span class="en">Latitude to transform</span>
     * @param lambda0 <span class="fr">Longitude du point à transformer</span>
     * <span class="en">Longitude to transform</span>
     * @param c <span class="fr">Distance d'arc : Déplacement en latitude</span><span class="en">Arc distance</span>
     * @param az <span class="fr">Azimuth : déplacement en longitude</span><span class="en">Azimuth</span>
     * @return <span class="fr">Un tableau de deux final doubles indiquant la latitude et la longitude transformées
     * </span>
     */
    @SectionReference(type = SectionReferenceType.FORMULA, id = {"5-5", "5-6"})
    public static double[] shift(final double phi1, final double lambda0, final double c, final double az) {
        final double sinPhi1 = Math.sin(phi1);
        final double cosC = Math.cos(c);
        final double cosPhi1 = Math.cos(phi1);
        final double sinC = Math.sin(c);
        final double cosAz = Math.cos(az);
        return new double[]{
            Math.asin(sinPhi1 * cosC + cosPhi1 * sinC * cosAz),
            lambda0 + Math.atan(sinC * Math.sin(az) / (cosPhi1 * cosC - sinPhi1 * sinC * cosAz))};
    }

    @SectionReference(type = SectionReferenceType.FORMULA, id = {"5-7", "5-8b"})
    public static double[] shiftTransform(final double phi, final double lambda, final double a, final double b,
            final double lambda0) {

        final double sina = Math.sin(a);
        final double sinfi = Math.sin(phi);
        final double cosa = Math.cos(a);
        final double cosfi = Math.cos(phi);
        final double cosl = Math.cos(lambda - lambda0);

        return new double[]{Math.asin(sina * sinfi - cosa * cosfi * cosl),
            (Math.atan2(cosfi * Math.sin(lambda - lambda0), (sina * cosfi * cosl + cosa * sinfi)) + b)};
    }
}
