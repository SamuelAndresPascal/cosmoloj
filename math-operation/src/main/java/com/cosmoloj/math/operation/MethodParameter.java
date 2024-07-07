package com.cosmoloj.math.operation;

import com.cosmoloj.math.operation.parameter.EpsgParameter;
import com.cosmoloj.math.util.complex.Complex;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Samuel Andrés
 */
public enum MethodParameter {

    /**
     * EPSG::1040
     */
    RATE_OF_CHANGE_OF_X_AXIS_TRANSLATION,

    /**
     * EPSG::1041
     */
    RATE_OF_CHANGE_OF_Y_AXIS_TRANSLATION,

    /**
     * EPSG::1042
     */
    RATE_OF_CHANGE_OF_Z_AXIS_TRANSLATION,

    /**
     * EPSG::1043
     */
    RATE_OF_CHANGE_OF_X_AXIS_ROTATION,

    /**
     * EPSG::1044
     */
    RATE_OF_CHANGE_OF_Y_AXIS_ROTATION,

    /**
     * EPSG::1045
     */
    RATE_OF_CHANGE_OF_Z_AXIS_ROTATION,

    /**
     * EPSG::1046
     */
    RATE_OF_CHANGE_OF_SCALE_DIFFERENCE,

    /**
     * EPSG::1047
     */
    PARAMETER_REFERENCE_EPOCH,

    /**
     * EPSG::1049
     */
    TRANSFORMATION_REFERENCE_EPOCH,

    /**
     * EPSG::1051
     */
    UNIT_CONVERSION_SCALAR,

    /**
     * x0
     * EPSG::8837
     */
    GEOCENTRIC_X_OF_TOPOCENTRIC_ORIGIN,

    /**
     * y0
     * EPSG::8838
     */
    GEOCENTRIC_Y_OF_TOPOCENTRIC_ORIGIN,

    /**
     * z0
     * EPSG::8839
     */
    GEOCENTRIC_Z_OF_TOPOCENTRIC_ORIGIN,

    /**
     * phif
     * EPSG::8821
     */
    LATITUDE_OF_FALSE_ORIGIN,

    /**
     * lambdaf
     * EPSG::8822
     */
    LONGITUDE_OF_FALSE_ORIGIN,

    /**
     * phi0
     * EPSG::8801
     */
    LATITUDE_OF_NATURAL_ORIGIN,

    /**
     * phic
     * EPSG::8811
     */
    LATITUDE_OF_PROJECTION_CENTRE,

    /**
     * lambdac
     * EPSG::8812
     */
    LONGITUDE_OF_PROJECTION_CENTRE,

    /**
     * alphac
     * EPSG::8813
     */
    AZIMUTH_OF_THE_INITIAL_LINE,

    /**
     * gammac
     * EPSG::8814
     */
    ANGLE_FROM_THE_RECTIFIED_GRID_TO_THE_SKEW_GRID,

    /**
     * kc
     * EPSG::8815
     */
    SCALE_FACTOR_ON_THE_INITIAL_LINE,

    /**
     * phip
     * EPSG::8818
     */
    LATITUDE_OF_PSEUDO_STANDARD_PARALLEL,

    /**
     * alphac
     * EPSG::1036
     */
    COLATITUDE_OF_THE_CONE_AXIS,

    /**
     * lambda0
     * EPSG::8802
     */
    LONGITUDE_OF_NATURAL_ORIGIN,

    /**
     * lambdai
     * EPSG::8830
     */
    INITIAL_LONGITUDE,

    /**
     * w
     * EPSG::8831
     */
    ZONE_WIDTH,

    /**
     * phif
     * EPSG::8832
     */
    LATITUDE_OF_STANDARD_PARALLEL,

    /**
     * lambda0
     * EPSG::8833
     */
    LONGITUDE_OF_ORIGIN,

    /**
     * phi1
     * EPSG::8823
     */
    LATITUDE_OF_1ST_STANDARD_PARALLEL,

    /**
     * phi2
     * EPSG::8824
     */
    LATITUDE_OF_2ND_STANDARD_PARALLEL,

    /**
     * ef
     * EPSG::8826
     */
    EASTING_AT_FALSE_ORIGIN,

    /**
     * nf
     * EPSG::8827
     */
    NORTHING_AT_FALSE_ORIGIN,

    /**
     * ec
     * EPSG::8816
     */
    EASTING_AT_THE_PROJECTION_CENTRE,

    /**
     * nc
     * EPSG::8817
     */
    NORTHING_AT_THE_PROJECTION_CENTRE,

    /**
     * fe
     * EPSG::8806
     */
    FALSE_EASTING,

    /**
     * fn
     * EPSG::8807
     */
    FALSE_NORTHING,

    /**
     * h0
     * EPSG::1039
     */
    PROJECTION_PLANE_ORIGIN_HEIGHT,

    /**
     * k0
     * EPSG::8805
     */
    SCALE_FACTOR_AT_NATURAL_ORIGIN,

    /**
     * h0
     * Snyder 10…
     */
    SCALE_FACTOR,

    /**
     * kp
     * EPSG::8819
     */
    SCALE_FACTOR_ON_PSEUDO_STANDARD_PARALLEL,

    /**
     * phio
     * EPSG::8601
     */
    LATITUDE_OFFSET,

    /**
     * lambdao
     * EPSG::8602
     */
    LONGITUDE_OFFSET,

    /**
     * lambdao
     * EPSG::8603
     */
    VERTICAL_OFFSET,

    /**
     * tX
     * EPSG::8605
     */
    X_AXIS_TRANSLATION,

    /**
     * tY
     * EPSG::8606
     */
    Y_AXIS_TRANSLATION,

    /**
     * tZ
     * EPSG::8607
     */
    Z_AXIS_TRANSLATION,

    /**
     * rX
     * EPSG::8608
     */
    X_AXIS_ROTATION,

    /**
     * rY
     * EPSG::8609
     */
    Y_AXIS_ROTATION,

    /**
     * rZ
     * EPSG::8610
     */
    Z_AXIS_ROTATION,

    /**
     * dS
     * EPSG::8611
     */
    SCALE_DIFFERENCE,

    /**
     * EPSG::8612
     */
    SCALE_FACTOR_FOR_SOURCE_CRS_FIRST_AXIS,

    /**
     * EPSG::8613
     */
    SCALE_FACTOR_FOR_SOURCE_CRS_SECOND_AXIS,

    /**
     * EPSG::8614
     */
    ROTATION_ANGLE_OF_SOURCE_CRS_AXES,

    /**
     * EPSG::8663
     */
    POINT_SCALE_FACTOR,

    /**
     * EPSG::8615
     */
    ROTATION_ANGLE_OF_SOURCE_CRS_FIRST_AXIS,

    /**
     * EPSG::8616
     */
    ROTATION_ANGLE_OF_SOURCE_CRS_SECOND_AXIS,

    /**
     * EPSG::1038
     */
    ELLIPSOID_SCALING_FACTOR, // k
    @Deprecated STANDARD_PARALLEL, // = phi0 ?
    @Deprecated CENTRAL_LONGITUDE, // = lambda0 ?

    /**
     * x0
     * EPSG::8617
     */
    ORDINATE_1_OF_EVALUATION_POINT,

    /**
     * y0
     * EPSG::8618
     */
    ORDINATE_2_OF_EVALUATION_POINT,

    /**
     * z0
     * EPSG::8667
     */
    ORDINATE_3_OF_EVALUATION_POINT,

    /**
     * phi0
     * EPSG::8834
     */
    LATITUDE_OF_TOPOCENTRIC_ORIGIN,

    /**
     * lambda0
     * EPSG::8835
     */
    LONGITUDE_OF_TOPOCENTRIC_ORIGIN,

    /**
     * h0
     * EPSG::8836
     */
    ELLIPSOIDAL_HEIGHT_OF_TOPOCENTRIC_ORIGIN,

    /**
     * hnu
     * EPSG::8840
     */
    VIEWPOINT_HEIGHT,

    /**
     * c1
     * EPSG::1026
     */
    C1,

    /**
     * c2
     * EPSG::1027
     */
    C2,

    /**
     * c3
     * EPSG::1028
     */
    C3,

    /**
     * c4
     * EPSG::1029
     */
    C4,

    /**
     * c5
     * EPSG::1030
     */
    C5,

    /**
     * c6
     * EPSG::1031
     */
    C6,

    /**
     * c7
     * EPSG::1032
     */
    C7,

    /**
     * c8
     * EPSG::1033
     */
    C8,

    /**
     * c9
     * EPSG::1034
     */
    C9,

    /**
     * c10
     * EPSG::1035
     */
    C10,

    // (\d\d\d\d) \| ([^|]*)\s*\|.*
    // /**\n     * $2\n     * EPSG::$1\n     */\n    $2,\n\n
    /**
     * Ordinate 1 of evaluation point in source CRS
     * EPSG::8619
     */
    ORDINATE_1_OF_EVALUATION_POINT_IN_SOURCE_CRS,

    /**
     * Ordinate 2 of evaluation point in source CRS
     * EPSG::8620
     */
    ORDINATE_2_OF_EVALUATION_POINT_IN_SOURCE_CRS,

    /**
     * Ordinate 1 of evaluation point in target CRS
     * EPSG::8621
     */
    ORDINATE_1_OF_EVALUATION_POINT_IN_TARGET_CRS,

    /**
     * Ordinate 2 of evaluation point in target CRS
     * EPSG::8622
     */
    ORDINATE_2_OF_EVALUATION_POINT_IN_TARGET_CRS,

    /**
     * Scaling factor for source CRS coord differences
     * EPSG::8694
     */
    SCALING_FACTOR_FOR_SOURCE_CRS_COORD_DIFFERENCES,

    /**
     * Scaling factor for target CRS coord differences
     * EPSG::8695
     */
    SCALING_FACTOR_FOR_TARGET_CRS_COORD_DIFFERENCES,

    /**
     * Scaling factor for coord differences
     * EPSG::8696
     */
    SCALING_FACTOR_FOR_COORD_DIFFERENCES,

    /**
     * A0
     * EPSG::8623
     */
    A0,

    /**
     * A1
     * EPSG::8624
     */
    A1,

    /**
     * A2
     * EPSG::8625
     */
    A2,

    /**
     * A3
     * EPSG::8626
     */
    A3,

    /**
     * A4
     * EPSG::8627
     */
    A4,

    /**
     * A5
     * EPSG::8628
     */
    A5,

    /**
     * A6
     * EPSG::8629
     */
    A6,

    /**
     * A7
     * EPSG::8630
     */
    A7,

    /**
     * A8
     * EPSG::8631
     */
    A8,

    /**
     * Au1v0
     * EPSG::8716
     */
    A_U1V0,

    /**
     * Au0v1
     * EPSG::8717
     */
    A_U0V1,

    /**
     * Au2v0
     * EPSG::8718
     */
    A_U2V0,

    /**
     * Au1v1
     * EPSG::8719
     */
    A_U1V1,

    /**
     * Au0v2
     * EPSG::8720
     */
    A_U0V2,


    /**
     * Au3v0
     * EPSG::8721
     */
    A_U3V0,

    /**
     * Au2v1
     * EPSG::8722
     */
    A_U2V1,

    /**
     * Au1v2
     * EPSG::8723
     */
    A_U1V2,

    /**
     * EPSG::8727
     */
    GEOCENTRIC_TRANSLATION_FILE,

    /**
     * EPSG::8730
     */
    INCLINATION_IN_LATITUDE,

    /**
     * EPSG::8731
     */
    INCLINATION_IN_LONGITUDE,

    /**
     * EPSG::8733
     */
    BIN_GRID_ORIGIN_I,

    /**
     * EPSG::8734
     */
    BIN_GRID_ORIGIN_J,

    /**
     * EPSG::8735
     */
    BIN_GRID_ORIGIN_EASTING,

    /**
     * EPSG::8736
     */
    BIN_GRID_ORIGIN_NORTHING,

    /**
     * EPSG::8737
     */
    SCALE_FACTOR_OF_BIN_GRID,

    /**
     * EPSG::8738
     */
    BIN_WIDTH_ON_I_AXIS,

    /**
     * EPSG::8739
     */
    BIN_WIDTH_ON_J_AXIS,

    /**
     * EPSG::8740
     */
    MAP_GRID_BEARING_OF_BIN_GRID_J_AXIS,

    /**
     * EPSG::8741
     */
    BIN_NODE_INCREMENT_ON_I_AXIS,

    /**
     * EPSG::8742
     */
    BIN_NODE_INCREMENT_ON_J_AXIS,

    /**
     * EPSG::1037
     */
    HORIZONTAL_CRS_CODE,

    /**
     * Au0v3
     * EPSG::8632
     */
    A_U0V3,

    /**
     * Au4v0
     * EPSG::8633
     */
    A_U4V0,

    /**
     * Au3v1
     * EPSG::8634
     */
    A_U3V1,

    /**
     * Au2v2
     * EPSG::8635
     */
    A_U2V2,

    /**
     * Au1v3
     * EPSG::8636
     */
    A_U1V3,

    /**
     * Au0v4
     * EPSG::8637
     */
    A_U0V4,

    /**
     * Au5v0
     * EPSG::8668
     */
    A_U5V0,

    /**
     * Au4v1
     * EPSG::8669
     */
    A_U4V1,

    /**
     * Au3v2
     * EPSG::8670
     */
    A_U3V2,

    /**
     * Au2v3
     * EPSG::8671
     */
    A_U2V3,

    /**
     * Au1v4
     * EPSG::8672
     */
    A_U1V4,

    /**
     * Au0v5
     * EPSG::8673
     */
    A_U0V5,

    /**
     * Au6v0
     * EPSG::8674
     */
    A_U6V0,

    /**
     * Au5v1
     * EPSG::8675
     */
    A_U5V1,

    /**
     * Au4v2
     * EPSG::8676
     */
    A_U4V2,

    /**
     * Au3v3
     * EPSG::8677
     */
    A_U3V3,

    /**
     * Au2v4
     * EPSG::8678
     */
    A_U2V4,

    /**
     * Au1v5
     * EPSG::8679
     */
    A_U1V5,

    /**
     * Au0v6
     * EPSG::8680
     */
    A_U0V6,

    /**
     * B00
     * EPSG::8638
     */
    B00,

    /**
     * B0
     * EPSG::8639
     */
    B0,

    /**
     * B1
     * EPSG::8640
     */
    B1,

    /**
     * B2
     * EPSG::8641
     */
    B2,

    /**
     * B3
     * EPSG::8642
     */
    B3,

    /**
     * Bu1v0
     * EPSG::8724
     */
    B_U1V0,

    /**
     * Bu0v1
     * EPSG::8725
     */
    B_U0V1,

    /**
     * Bu2v0
     * EPSG::8726
     */
    B_U2V0,

    /**
     * Bu1v1
     * EPSG::8643
     */
    B_U1V1,

    /**
     * Bu0v2
     * EPSG::8644
     */
    B_U0V2,

    /**
     * Bu3v0
     * EPSG::8645
     */
    B_U3V0,

    /**
     * Bu2v1
     * EPSG::8646
     */
    B_U2V1,

    /**
     * Bu1v2
     * EPSG::8647
     */
    B_U1V2,

    /**
     * Bu0v3
     * EPSG::8648
     */
    B_U0V3,

    /**
     * Bu4v0
     * EPSG::8649
     */
    B_U4V0,

    /**
     * Bu3v1
     * EPSG::8650
     */
    B_U3V1,

    /**
     * Bu2v2
     * EPSG::8651
     */
    B_U2V2,

    /**
     * Bu1v3
     * EPSG::8652
     */
    B_U1V3,

    /**
     * Bu0v4
     * EPSG::8653
     */
    B_U0V4,

    /**
     * EPSG::8654
     */
    SEMI_MAJOR_AXIS_LENGTH_DIFFERENCE,

    /**
     * EPSG::8655
     */
    FLATTENING_DIFFERENCE,

    /**
     * Bu5v0
     * EPSG::8681
     */
    B_U5V0,

    /**
     * Bu4v1
     * EPSG::8682
     */
    B_U4V1,

    /**
     * Bu3v2
     * EPSG::8683
     */
    B_U3V2,

    /**
     * Bu2v3
     * EPSG::8684
     */
    B_U2V3,

    /**
     * Bu1v4
     * EPSG::8685
     */
    B_U1V4,

    /**
     * Bu0v5
     * EPSG::8686
     */
    B_U0V5,

    /**
     * Bu6v0
     * EPSG::8687
     */
    B_U6V0,

    /**
     * Bu5v1
     * EPSG::8688
     */
    B_U5V1,

    /**
     * Bu4v2
     * EPSG::8689
     */
    B_U4V2,

    /**
     * Bu3v3
     * EPSG::8690
     */
    B_U3V3,

    /**
     * Bu2v4
     * EPSG::8691
     */
    B_U2V4,

    /**
     * Bu1v5
     * EPSG::8692
     */
    B_U1V5,

    /**
     * Bu0v6
     * EPSG::8693
     */
    B_U0V6;

//    /**
//     * h0
//     * Snyder 10…
//     */
//    SCALE_FACTOR,

//    ELLIPSOID_SCALING_FACTOR, // k
//    @Deprecated STANDARD_PARALLEL, // = phi0 ?
//    @Deprecated CENTRAL_LONGITUDE, // = lambda0 ?

    public static MethodParameter of(final String authority, final String version, final String code) {
        if ("EPSG".equals(authority) || "OGP".equals(authority) || "IOGP".equals(authority)) {
            return EpsgParameter.of(Integer.parseInt(code));
        }
        throw new UnsupportedOperationException();
    }

    private static final Pattern N_PATTERN = Pattern.compile("([\\w])(\\d)");
    private static final Pattern NM_PATTERN = Pattern.compile("([\\w])_U(\\d)V(\\d)");

    /**
     * <div class="fr">Extrait la valeur correspondant au paramètre-coefficient des matrices A ou B.</div>
     *
     * @param m
     * @param selector
     * @return
     * @throws UnsupportedOperationException <span class="fr">Si l'instance d'appel de l'énumération n'est pas l'un
     * des coefficients matriciels A/B.</span>
     */
    public double extractCoef(final double[][] m, final String selector) {
        final String name = name();
        final Matcher m1 = NM_PATTERN.matcher(name);
        if (m1.matches()) {
            if (selector.equals(m1.group(1))) {
                return m[Integer.parseInt(m1.group(2))][Integer.parseInt(m1.group(3))];
            }
            throw new UnsupportedOperationException("this method only works for A/B matricial coefficients");
        }

        final Matcher m2 = N_PATTERN.matcher(name);
        if (m2.matches() && selector.equals(m2.group(1))) {
            return m[Integer.parseInt(m2.group(2))][Integer.parseInt(m2.group(2))];
        }
        throw new UnsupportedOperationException("this method only works for A/B matricial coefficients");
    }

    public void fillCoef(final double[][] m, final String selector, final double value) {
        final String name = name();
        final Matcher m1 = NM_PATTERN.matcher(name);
        if (m1.matches()) {
            if (selector.equals(m1.group(1))) {
                m[Integer.parseInt(m1.group(2))][Integer.parseInt(m1.group(3))] = value;
                return;
            }
            throw new UnsupportedOperationException("this method only works for A/B matricial coefficients");
        }

        final Matcher m2 = N_PATTERN.matcher(name);
        if (m2.matches() && selector.equals(m2.group(1))) {
            m[Integer.parseInt(m2.group(2))][Integer.parseInt(m2.group(2))] = value;
            return;
        }
        throw new UnsupportedOperationException("this method only works for A/B matricial coefficients");
    }

    public Complex extractCoef(final Complex[] m, final String selector) {
        final String name = name();

        final Matcher m2 = N_PATTERN.matcher(name);
        if (m2.matches() && selector.equals(m2.group(1))) {
            return m[Integer.parseInt(m2.group(2))];
        }
        throw new UnsupportedOperationException("this method only works for A/B matricial coefficients");
    }

    public void fillCoef(final double[] m, final String selector, final double value) {
        final String name = name();

        final Matcher m2 = N_PATTERN.matcher(name);
        if (m2.matches() && selector.equals(m2.group(1))) {
            m[Integer.parseInt(m2.group(2))] = value;
            return;
        }
        throw new UnsupportedOperationException("this method only works for A/B matricial coefficients");
    }
}
