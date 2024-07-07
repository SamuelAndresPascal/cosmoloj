package com.cosmoloj.math.operation.parameter;

import com.cosmoloj.math.operation.MethodParameter;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 *
 * @author Samuel Andrés
 */
public final class EpsgParameter {

    private EpsgParameter() {
    }

    private static final MethodParameter[] EPSG = new MethodParameter[8841];

    static {
        EPSG[1026] = MethodParameter.C1;
        EPSG[1027] = MethodParameter.C2;
        EPSG[1028] = MethodParameter.C3;
        EPSG[1029] = MethodParameter.C4;
        EPSG[1030] = MethodParameter.C5;
        EPSG[1031] = MethodParameter.C6;
        EPSG[1032] = MethodParameter.C7;
        EPSG[1033] = MethodParameter.C8;
        EPSG[1034] = MethodParameter.C9;
        EPSG[1035] = MethodParameter.C10;
        EPSG[1036] = MethodParameter.COLATITUDE_OF_THE_CONE_AXIS;
        EPSG[1037] = MethodParameter.HORIZONTAL_CRS_CODE;
        EPSG[1038] = MethodParameter.ELLIPSOID_SCALING_FACTOR;
        EPSG[1039] = MethodParameter.PROJECTION_PLANE_ORIGIN_HEIGHT;
        EPSG[1040] = MethodParameter.RATE_OF_CHANGE_OF_X_AXIS_TRANSLATION;
        EPSG[1041] = MethodParameter.RATE_OF_CHANGE_OF_Y_AXIS_TRANSLATION;
        EPSG[1042] = MethodParameter.RATE_OF_CHANGE_OF_Z_AXIS_TRANSLATION;
        EPSG[1043] = MethodParameter.RATE_OF_CHANGE_OF_X_AXIS_ROTATION;
        EPSG[1044] = MethodParameter.RATE_OF_CHANGE_OF_Y_AXIS_ROTATION;
        EPSG[1045] = MethodParameter.RATE_OF_CHANGE_OF_Z_AXIS_ROTATION;
        EPSG[1046] = MethodParameter.RATE_OF_CHANGE_OF_SCALE_DIFFERENCE;
        EPSG[1047] = MethodParameter.PARAMETER_REFERENCE_EPOCH;
        EPSG[1049] = MethodParameter.TRANSFORMATION_REFERENCE_EPOCH;
        EPSG[1051] = MethodParameter.UNIT_CONVERSION_SCALAR;
        EPSG[8601] = MethodParameter.LATITUDE_OFFSET;
        EPSG[8602] = MethodParameter.LONGITUDE_OFFSET;
        EPSG[8603] = MethodParameter.VERTICAL_OFFSET;
        EPSG[8605] = MethodParameter.X_AXIS_TRANSLATION;
        EPSG[8606] = MethodParameter.Y_AXIS_TRANSLATION;
        EPSG[8607] = MethodParameter.Z_AXIS_TRANSLATION;
        EPSG[8608] = MethodParameter.X_AXIS_ROTATION;
        EPSG[8609] = MethodParameter.Y_AXIS_ROTATION;
        EPSG[8610] = MethodParameter.Z_AXIS_ROTATION;
        EPSG[8611] = MethodParameter.SCALE_DIFFERENCE;
        EPSG[8612] = MethodParameter.SCALE_FACTOR_FOR_SOURCE_CRS_FIRST_AXIS;
        EPSG[8613] = MethodParameter.SCALE_FACTOR_FOR_SOURCE_CRS_SECOND_AXIS;
        EPSG[8614] = MethodParameter.ROTATION_ANGLE_OF_SOURCE_CRS_AXES;
        EPSG[8615] = MethodParameter.ROTATION_ANGLE_OF_SOURCE_CRS_FIRST_AXIS;
        EPSG[8616] = MethodParameter.ROTATION_ANGLE_OF_SOURCE_CRS_SECOND_AXIS;
        EPSG[8617] = MethodParameter.ORDINATE_1_OF_EVALUATION_POINT;
        EPSG[8618] = MethodParameter.ORDINATE_2_OF_EVALUATION_POINT;
        EPSG[8619] = MethodParameter.ORDINATE_1_OF_EVALUATION_POINT_IN_SOURCE_CRS;
        EPSG[8620] = MethodParameter.ORDINATE_2_OF_EVALUATION_POINT_IN_SOURCE_CRS;
        EPSG[8621] = MethodParameter.ORDINATE_1_OF_EVALUATION_POINT_IN_TARGET_CRS;
        EPSG[8622] = MethodParameter.ORDINATE_2_OF_EVALUATION_POINT_IN_TARGET_CRS;
        EPSG[8623] = MethodParameter.A0;
        EPSG[8624] = MethodParameter.A1;
        EPSG[8625] = MethodParameter.A2;
        EPSG[8626] = MethodParameter.A3;
        EPSG[8627] = MethodParameter.A4;
        EPSG[8628] = MethodParameter.A5;
        EPSG[8629] = MethodParameter.A6;
        EPSG[8630] = MethodParameter.A7;
        EPSG[8631] = MethodParameter.A8;
        EPSG[8632] = MethodParameter.A_U0V3;
        EPSG[8633] = MethodParameter.A_U4V0;
        EPSG[8634] = MethodParameter.A_U3V1;
        EPSG[8635] = MethodParameter.A_U2V2;
        EPSG[8636] = MethodParameter.A_U1V3;
        EPSG[8637] = MethodParameter.A_U0V4;
        EPSG[8638] = MethodParameter.B00;
        EPSG[8639] = MethodParameter.B0;
        EPSG[8640] = MethodParameter.B1;
        EPSG[8641] = MethodParameter.B2;
        EPSG[8642] = MethodParameter.B2;
        EPSG[8643] = MethodParameter.B_U1V1;
        EPSG[8644] = MethodParameter.B_U0V2;
        EPSG[8645] = MethodParameter.B_U3V0;
        EPSG[8646] = MethodParameter.B_U2V1;
        EPSG[8647] = MethodParameter.B_U1V2;
        EPSG[8648] = MethodParameter.B_U0V3;
        EPSG[8649] = MethodParameter.B_U4V0;
        EPSG[8650] = MethodParameter.B_U3V1;
        EPSG[8651] = MethodParameter.B_U2V2;
        EPSG[8652] = MethodParameter.B_U1V3;
        EPSG[8653] = MethodParameter.B_U0V4;
        EPSG[8654] = MethodParameter.SEMI_MAJOR_AXIS_LENGTH_DIFFERENCE;
        EPSG[8655] = MethodParameter.FLATTENING_DIFFERENCE;
        EPSG[8663] = MethodParameter.POINT_SCALE_FACTOR;
        EPSG[8667] = MethodParameter.ORDINATE_3_OF_EVALUATION_POINT;
        EPSG[8668] = MethodParameter.A_U5V0;
        EPSG[8669] = MethodParameter.A_U4V1;
        EPSG[8670] = MethodParameter.A_U3V2;
        EPSG[8671] = MethodParameter.A_U2V3;
        EPSG[8672] = MethodParameter.A_U1V4;
        EPSG[8673] = MethodParameter.A_U0V5;
        EPSG[8674] = MethodParameter.A_U6V0;
        EPSG[8675] = MethodParameter.A_U5V1;
        EPSG[8676] = MethodParameter.A_U4V2;
        EPSG[8677] = MethodParameter.A_U3V3;
        EPSG[8678] = MethodParameter.A_U2V4;
        EPSG[8679] = MethodParameter.A_U1V5;
        EPSG[8680] = MethodParameter.A_U0V6;
        EPSG[8681] = MethodParameter.B_U5V0;
        EPSG[8682] = MethodParameter.B_U4V1;
        EPSG[8683] = MethodParameter.B_U3V2;
        EPSG[8684] = MethodParameter.B_U2V3;
        EPSG[8685] = MethodParameter.B_U1V4;
        EPSG[8686] = MethodParameter.B_U0V5;
        EPSG[8687] = MethodParameter.B_U6V0;
        EPSG[8688] = MethodParameter.B_U5V1;
        EPSG[8689] = MethodParameter.B_U4V2;
        EPSG[8690] = MethodParameter.B_U3V3;
        EPSG[8691] = MethodParameter.B_U2V4;
        EPSG[8692] = MethodParameter.B_U1V5;
        EPSG[8693] = MethodParameter.B_U0V6;
        EPSG[8694] = MethodParameter.SCALING_FACTOR_FOR_SOURCE_CRS_COORD_DIFFERENCES;
        EPSG[8695] = MethodParameter.SCALING_FACTOR_FOR_TARGET_CRS_COORD_DIFFERENCES;
        EPSG[8696] = MethodParameter.SCALING_FACTOR_FOR_COORD_DIFFERENCES;
        EPSG[8716] = MethodParameter.A_U1V0;
        EPSG[8717] = MethodParameter.A_U0V1;
        EPSG[8718] = MethodParameter.A_U2V0;
        EPSG[8719] = MethodParameter.A_U1V1;
        EPSG[8720] = MethodParameter.A_U0V2;
        EPSG[8721] = MethodParameter.A_U3V0;
        EPSG[8722] = MethodParameter.A_U2V1;
        EPSG[8723] = MethodParameter.A_U1V2;
        EPSG[8724] = MethodParameter.B_U1V0;
        EPSG[8725] = MethodParameter.B_U0V1;
        EPSG[8726] = MethodParameter.B_U2V0;
        EPSG[8727] = MethodParameter.GEOCENTRIC_TRANSLATION_FILE;
        EPSG[8730] = MethodParameter.INCLINATION_IN_LATITUDE;
        EPSG[8731] = MethodParameter.INCLINATION_IN_LONGITUDE;
        EPSG[8733] = MethodParameter.BIN_GRID_ORIGIN_I;
        EPSG[8734] = MethodParameter.BIN_GRID_ORIGIN_J;
        EPSG[8735] = MethodParameter.BIN_GRID_ORIGIN_EASTING;
        EPSG[8736] = MethodParameter.BIN_GRID_ORIGIN_NORTHING;
        EPSG[8737] = MethodParameter.SCALE_FACTOR_OF_BIN_GRID;
        EPSG[8738] = MethodParameter.BIN_WIDTH_ON_I_AXIS;
        EPSG[8739] = MethodParameter.BIN_WIDTH_ON_J_AXIS;
        EPSG[8740] = MethodParameter.MAP_GRID_BEARING_OF_BIN_GRID_J_AXIS;
        EPSG[8741] = MethodParameter.BIN_NODE_INCREMENT_ON_I_AXIS;
        EPSG[8742] = MethodParameter.BIN_NODE_INCREMENT_ON_J_AXIS;
        EPSG[8801] = MethodParameter.LATITUDE_OF_NATURAL_ORIGIN;
        EPSG[8802] = MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN;
        EPSG[8805] = MethodParameter.SCALE_FACTOR_AT_NATURAL_ORIGIN;
        EPSG[8806] = MethodParameter.FALSE_EASTING;
        EPSG[8807] = MethodParameter.FALSE_NORTHING;
        EPSG[8811] = MethodParameter.LATITUDE_OF_PROJECTION_CENTRE;
        EPSG[8812] = MethodParameter.LONGITUDE_OF_PROJECTION_CENTRE;
        EPSG[8813] = MethodParameter.AZIMUTH_OF_THE_INITIAL_LINE;
        EPSG[8814] = MethodParameter.ANGLE_FROM_THE_RECTIFIED_GRID_TO_THE_SKEW_GRID;
        EPSG[8815] = MethodParameter.SCALE_FACTOR_ON_THE_INITIAL_LINE;
        EPSG[8816] = MethodParameter.EASTING_AT_THE_PROJECTION_CENTRE;
        EPSG[8817] = MethodParameter.NORTHING_AT_THE_PROJECTION_CENTRE;
        EPSG[8818] = MethodParameter.LATITUDE_OF_PSEUDO_STANDARD_PARALLEL;
        EPSG[8819] = MethodParameter.SCALE_FACTOR_ON_PSEUDO_STANDARD_PARALLEL;
        EPSG[8821] = MethodParameter.LATITUDE_OF_FALSE_ORIGIN;
        EPSG[8822] = MethodParameter.LONGITUDE_OF_FALSE_ORIGIN;
        EPSG[8823] = MethodParameter.LATITUDE_OF_1ST_STANDARD_PARALLEL;
        EPSG[8824] = MethodParameter.LATITUDE_OF_2ND_STANDARD_PARALLEL;
        EPSG[8826] = MethodParameter.EASTING_AT_FALSE_ORIGIN;
        EPSG[8827] = MethodParameter.NORTHING_AT_FALSE_ORIGIN;
        EPSG[8830] = MethodParameter.INITIAL_LONGITUDE;
        EPSG[8831] = MethodParameter.ZONE_WIDTH;
        EPSG[8832] = MethodParameter.LATITUDE_OF_STANDARD_PARALLEL;
        EPSG[8833] = MethodParameter.LONGITUDE_OF_ORIGIN;
        EPSG[8834] = MethodParameter.LATITUDE_OF_TOPOCENTRIC_ORIGIN;
        EPSG[8835] = MethodParameter.LONGITUDE_OF_TOPOCENTRIC_ORIGIN;
        EPSG[8836] = MethodParameter.ELLIPSOIDAL_HEIGHT_OF_TOPOCENTRIC_ORIGIN;
        EPSG[8837] = MethodParameter.GEOCENTRIC_X_OF_TOPOCENTRIC_ORIGIN;
        EPSG[8838] = MethodParameter.GEOCENTRIC_Y_OF_TOPOCENTRIC_ORIGIN;
        EPSG[8839] = MethodParameter.GEOCENTRIC_Z_OF_TOPOCENTRIC_ORIGIN;
        EPSG[8840] = MethodParameter.VIEWPOINT_HEIGHT;
    }

    private static final int[] CODES = IntStream.range(0, EPSG.length)
            .filter(i -> EPSG[i] != null)
            .toArray();

    public static MethodParameter of(final int code) {
        return code < EPSG.length ? EPSG[code] : null;
    }

    public static int[] codes() {
        return Arrays.copyOf(CODES, CODES.length);
    }
}
