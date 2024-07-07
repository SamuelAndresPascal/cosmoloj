package com.cosmoloj.math.operation;

import com.cosmoloj.math.operation.conversion.Epsg9602;
import com.cosmoloj.math.operation.conversion.Epsg9659;
import com.cosmoloj.math.operation.conversion.Epsg9836;
import com.cosmoloj.math.operation.conversion.Epsg9837;
import com.cosmoloj.math.operation.conversion.Epsg9843;
import com.cosmoloj.math.operation.conversion.Epsg9844;
import com.cosmoloj.math.operation.perspective.Epsg9838;
import com.cosmoloj.math.operation.perspective.Epsg9839;
import com.cosmoloj.math.operation.projection.Epsg1024;
import com.cosmoloj.math.operation.projection.Epsg1026;
import com.cosmoloj.math.operation.projection.Epsg1027;
import com.cosmoloj.math.operation.projection.Epsg1028;
import com.cosmoloj.math.operation.projection.Epsg1029;
import com.cosmoloj.math.operation.projection.Epsg1041a;
import com.cosmoloj.math.operation.projection.Epsg1042a;
import com.cosmoloj.math.operation.projection.Epsg1043a;
import com.cosmoloj.math.operation.projection.Epsg1044;
import com.cosmoloj.math.operation.projection.Epsg1051;
import com.cosmoloj.math.operation.projection.Epsg1052;
import com.cosmoloj.math.operation.projection.Epsg9801;
import com.cosmoloj.math.operation.projection.Epsg9802;
import com.cosmoloj.math.operation.projection.Epsg9803;
import com.cosmoloj.math.operation.projection.Epsg9804;
import com.cosmoloj.math.operation.projection.Epsg9805;
import com.cosmoloj.math.operation.projection.Epsg9806;
import com.cosmoloj.math.operation.projection.Epsg9807jhs;
import com.cosmoloj.math.operation.projection.Epsg9808;
import com.cosmoloj.math.operation.projection.Epsg9809;
import com.cosmoloj.math.operation.projection.Epsg9810;
import com.cosmoloj.math.operation.projection.Epsg9811;
import com.cosmoloj.math.operation.projection.Epsg9812;
import com.cosmoloj.math.operation.projection.Epsg9813;
import com.cosmoloj.math.operation.projection.Epsg9815;
import com.cosmoloj.math.operation.projection.Epsg9817a;
import com.cosmoloj.math.operation.projection.Epsg9818;
import com.cosmoloj.math.operation.projection.Epsg9819a;
import com.cosmoloj.math.operation.projection.Epsg9820;
import com.cosmoloj.math.operation.projection.Epsg9822;
import com.cosmoloj.math.operation.projection.Epsg9824;
import com.cosmoloj.math.operation.projection.Epsg9825;
import com.cosmoloj.math.operation.projection.Epsg9826;
import com.cosmoloj.math.operation.projection.Epsg9827;
import com.cosmoloj.math.operation.projection.Epsg9828;
import com.cosmoloj.math.operation.projection.Epsg9829;
import com.cosmoloj.math.operation.projection.Epsg9830;
import com.cosmoloj.math.operation.projection.Epsg9831;
import com.cosmoloj.math.operation.projection.Epsg9832;
import com.cosmoloj.math.operation.projection.Epsg9833;
import com.cosmoloj.math.operation.projection.Epsg9834;
import com.cosmoloj.math.operation.projection.Epsg9835;
import com.cosmoloj.math.operation.projection.Epsg9840;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.operation.surface.Spheroid;
import com.cosmoloj.math.operation.surface.Surface;
import com.cosmoloj.math.operation.transformation.Epsg1031;
import com.cosmoloj.math.operation.transformation.Epsg1032;
import com.cosmoloj.math.operation.transformation.Epsg1033;
import com.cosmoloj.math.operation.transformation.Epsg1034;
import com.cosmoloj.math.operation.transformation.Epsg1046;
import com.cosmoloj.math.operation.transformation.Epsg1049;
import com.cosmoloj.math.operation.transformation.Epsg1053;
import com.cosmoloj.math.operation.transformation.Epsg1056;
import com.cosmoloj.math.operation.transformation.Epsg1061;
import com.cosmoloj.math.operation.transformation.Epsg1065;
import com.cosmoloj.math.operation.transformation.Epsg1066;
import com.cosmoloj.math.operation.transformation.Epsg1068;
import com.cosmoloj.math.operation.transformation.Epsg1069;
import com.cosmoloj.math.operation.transformation.Epsg9605;
import com.cosmoloj.math.operation.transformation.Epsg9616;
import com.cosmoloj.math.operation.transformation.Epsg9617;
import com.cosmoloj.math.operation.transformation.Epsg9619;
import com.cosmoloj.math.operation.transformation.Epsg9621;
import com.cosmoloj.math.operation.transformation.Epsg9622;
import com.cosmoloj.math.operation.transformation.Epsg9623;
import com.cosmoloj.math.operation.transformation.Epsg9624;
import com.cosmoloj.math.operation.transformation.Epsg9648;
import com.cosmoloj.math.operation.transformation.Epsg9651;
import com.cosmoloj.math.operation.transformation.Epsg9652;
import com.cosmoloj.math.operation.transformation.Epsg9653;
import com.cosmoloj.math.operation.transformation.Epsg9660;
import com.cosmoloj.math.operation.transformation.Epsg9666;
import com.cosmoloj.math.operation.transformation.concat.Epsg1035;
import com.cosmoloj.math.operation.transformation.concat.Epsg1037;
import com.cosmoloj.math.operation.transformation.concat.Epsg1038;
import com.cosmoloj.math.operation.transformation.concat.Epsg1039;
import com.cosmoloj.math.operation.transformation.concat.Epsg1054;
import com.cosmoloj.math.operation.transformation.concat.Epsg1055;
import com.cosmoloj.math.operation.transformation.concat.Epsg1057;
import com.cosmoloj.math.operation.transformation.concat.Epsg1058;
import com.cosmoloj.math.operation.transformation.concat.Epsg1062;
import com.cosmoloj.math.operation.transformation.concat.Epsg1063;
import com.cosmoloj.math.operation.transformation.concat.Epsg9603;
import com.cosmoloj.math.operation.transformation.concat.Epsg9606;
import com.cosmoloj.math.operation.transformation.concat.Epsg9607;
import com.cosmoloj.math.operation.transformation.concat.Epsg9636;
import com.cosmoloj.math.operation.transformation.concat.Epsg9655;
import com.cosmoloj.math.tabular.core.DoubleTabulars;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Samuel Andrés
 */
public final class Operations {

    private static final String AUTHORITY_EPSG = "EPSG";
    private static final String AUTHORITY_OGP = "OGP";
    private static final String AUTHORITY_IOGP = "IOGP";
    private static final String AUTHORITY_SNYDER = "SNYDER";

    private Operations() {
    }

    public static Operation<?, ?> authority(final String authority, final String version, final String code,
            final Surface sourceSurface,
            final Surface targetSurface,
            final Map<MethodParameter, ?> params,
            final List<?> inputUnits,
            final List<?> outputUnits) {

        if (AUTHORITY_EPSG.equals(authority) || AUTHORITY_OGP.equals(authority) || AUTHORITY_IOGP.equals(authority)) {
            return switch (Integer.parseInt(code)) {
                // PROJECTIONS
                case 1024 -> Epsg1024.ofParams((Ellipsoid) sourceSurface, params);
                case 1026 -> Epsg1026.ofParams((Spheroid) sourceSurface, params);
                case 1027 -> Epsg1027.ofParams((Spheroid) sourceSurface, params);
                // plusieurs implémentations disponibles pour 1028 (il faudrait pouvoir en choisir une autre que Series)
                case 1028 -> Epsg1028.Series.ofParams((Ellipsoid) sourceSurface, params);
                case 1029 -> Epsg1029.ofParams((Ellipsoid) sourceSurface, params);
                case 1041 -> Epsg1041a.ofParams((Ellipsoid) sourceSurface, params); // deux impleméntations
                case 1042 -> Epsg1042a.ofParams((Ellipsoid) sourceSurface, params); // deux impleméntations
                case 1043 -> Epsg1043a.ofParams((Ellipsoid) sourceSurface, params); // deux impleméntations
                case 1044 -> Epsg1044.ofParams((Ellipsoid) sourceSurface, params);
                case 1051 -> Epsg1051.ofParams((Ellipsoid) sourceSurface, params);
                case 1052 -> Epsg1052.ofParams((Ellipsoid) sourceSurface, params);
                case 9801 -> Epsg9801.ofParams((Ellipsoid) sourceSurface, params);
                case 9802 -> Epsg9802.ofParams((Ellipsoid) sourceSurface, params);
                case 9803 -> Epsg9803.ofParams((Ellipsoid) sourceSurface, params);
                case 9804 -> Epsg9804.ofParams((Ellipsoid) sourceSurface, params);
                case 9805 -> Epsg9805.ofParams((Ellipsoid) sourceSurface, params);
                case 9806 -> Epsg9806.ofParams((Ellipsoid) sourceSurface, params);
                case 9807 -> Epsg9807jhs.ofParams((Ellipsoid) sourceSurface, params); // deux impleméntations
                case 9808 -> Epsg9808.ofParams((Ellipsoid) sourceSurface, params);
                case 9809 -> Epsg9809.ofParams((Ellipsoid) sourceSurface, params);
                case 9810 -> Epsg9810.ofParams((Ellipsoid) sourceSurface, params);
                case 9811 -> Epsg9811.ofParams((Ellipsoid) sourceSurface, params);
                case 9812 -> Epsg9812.ofParams((Ellipsoid) sourceSurface, params);
                case 9813 -> Epsg9813.ofParams((Ellipsoid) sourceSurface, params);
                case 9815 -> Epsg9815.ofParams((Ellipsoid) sourceSurface, params);
                case 9817 -> Epsg9817a.ofParams((Ellipsoid) sourceSurface, params); // deux impleméntations
                case 9818 -> Epsg9818.ofParams((Ellipsoid) sourceSurface, params);
                case 9819 -> Epsg9819a.ofParams((Ellipsoid) sourceSurface, params); // deux impleméntations
                case 9820 -> Epsg9820.ofParams((Ellipsoid) sourceSurface, params);
                case 9822 -> Epsg9822.ofParams((Ellipsoid) sourceSurface, params);
                case 9824 -> Epsg9824.ofParams((Ellipsoid) sourceSurface, params);
                case 9825 -> Epsg9825.ofParams((Ellipsoid) sourceSurface);
                case 9826 -> Epsg9826.ofParams((Ellipsoid) sourceSurface, params);
                case 9827 -> Epsg9827.ofParams((Ellipsoid) sourceSurface, params);
                case 9828 -> Epsg9828.ofParams((Ellipsoid) sourceSurface, params);
                case 9829 -> Epsg9829.ofParams((Ellipsoid) sourceSurface, params);
                case 9830 -> Epsg9830.ofParams((Ellipsoid) sourceSurface, params);
                case 9831 -> Epsg9831.ofParams((Ellipsoid) sourceSurface, params);
                case 9832 -> Epsg9832.ofParams((Ellipsoid) sourceSurface, params);
                case 9833 -> Epsg9833.ofParams((Ellipsoid) sourceSurface, params);
                case 9834 -> Epsg9834.ofParams((Spheroid) sourceSurface, params);
                case 9835 -> Epsg9835.ofParams((Ellipsoid) sourceSurface, params);
                case 9840 -> Epsg9840.ofParams((Ellipsoid) sourceSurface, params);
                // TRANSFORMATIONS
                case 1031 -> Epsg1031.ofParams(params);
                case 1032 -> Epsg1032.ofParams(params);
                case 1033 -> Epsg1033.ofParams(params);
                case 1034 -> Epsg1034.ofParams(params);
                case 1046 -> Epsg1046.ofParams((Ellipsoid) sourceSurface, params);
                case 1049 -> Epsg1049.ofParams(params);
                case 1053 -> Epsg1053.ofParams(params);
                case 1056 -> Epsg1056.ofParams(params);
                case 1061 -> Epsg1061.ofParams(params);
                case 1065 -> Epsg1065.ofParams(params);
                case 1066 -> Epsg1066.ofParams(params);
                case 1068 -> Epsg1068.instance();
                case 1069 -> Epsg1069.ofParams(params);
                case 9605 -> Epsg9605.ofParams((Ellipsoid) sourceSurface, (Ellipsoid) targetSurface, params);
                case 9616 -> Epsg9616.ofParams(params);
                case 9617 -> Epsg9617.ofParams(params);
                case 9619 -> Epsg9619.ofParams(params);
                case 9621 -> Epsg9621.ofParams(params);
                case 9622 -> Epsg9622.ofParams(params);
                case 9623 -> Epsg9623.ofParams(params);
                case 9624 -> Epsg9624.ofParams(params);
                case 9648 -> Epsg9648.ofParams(params);
                case 9651 -> Epsg9651.ofParams(params);
                case 9652 -> Epsg9652.ofParams(params);
                case 9653 -> Epsg9653.ofParams(params);
                case 9660 -> Epsg9660.ofParams(params);
                case 9666 -> Epsg9666.ofParams(params);
                // CONCAT TRANSFORMATIONS
                case 1035 -> Epsg1035.ofParams((Ellipsoid) sourceSurface, (Ellipsoid) targetSurface, params);
                case 1037 -> Epsg1037.ofParams((Ellipsoid) sourceSurface, (Ellipsoid) targetSurface, params);
                case 1038 -> Epsg1038.ofParams((Ellipsoid) sourceSurface, (Ellipsoid) targetSurface, params);
                case 1039 -> Epsg1039.ofParams((Ellipsoid) sourceSurface, (Ellipsoid) targetSurface, params);
                case 1054 -> Epsg1054.ofParams((Ellipsoid) sourceSurface, (Ellipsoid) targetSurface, params);
                case 1055 -> Epsg1055.ofParams((Ellipsoid) sourceSurface, (Ellipsoid) targetSurface, params);
                case 1057 -> Epsg1057.ofParams((Ellipsoid) sourceSurface, (Ellipsoid) targetSurface, params);
                case 1058 -> Epsg1058.ofParams((Ellipsoid) sourceSurface, (Ellipsoid) targetSurface, params);
                case 1062 -> Epsg1062.ofParams((Ellipsoid) sourceSurface, (Ellipsoid) targetSurface, params);
                case 1063 -> Epsg1063.ofParams((Ellipsoid) sourceSurface, (Ellipsoid) targetSurface, params);
                case 9603 -> Epsg9603.ofParams((Ellipsoid) sourceSurface, (Ellipsoid) targetSurface, params);
                case 9606 -> Epsg9606.ofParams((Ellipsoid) sourceSurface, (Ellipsoid) targetSurface, params);
                case 9607 -> Epsg9607.ofParams((Ellipsoid) sourceSurface, (Ellipsoid) targetSurface, params);
                case 9636 -> Epsg9636.ofParams((Ellipsoid) sourceSurface, (Ellipsoid) targetSurface, params);
                case 9655 -> Epsg9655.ofParams((Ellipsoid) sourceSurface, (Ellipsoid) targetSurface, params);
                // CONVERSIONS
                case 9602 -> Epsg9602.ofParams((Ellipsoid) sourceSurface);
                case 9659 -> Epsg9659.ofParams((Ellipsoid) sourceSurface);
                case 9836 -> Epsg9836.ofParams((Ellipsoid) sourceSurface, params);
                case 9837 -> Epsg9837.ofParams((Ellipsoid) sourceSurface, params);
                case 9843 -> Epsg9843.ofParams((Ellipsoid) sourceSurface);
                case 9844 -> Epsg9844.ofParams((Ellipsoid) sourceSurface);
                // PERSPECTIVES
                case 9838 -> Epsg9838.ofParams((Ellipsoid) sourceSurface, params);
                case 9839 -> Epsg9839.ofParams((Ellipsoid) sourceSurface, params);
                default -> throw new UnsupportedOperationException();
            };
//        } else if ("SNYDER".equals(authority)) {
//            return switch (code) {
//                default -> throw new UnsupportedOperationException();
//            };
        }
        throw new UnsupportedOperationException();
    }

    public static CoordinateOperation matrix(final double[][] matrix) {

        return new CoordinateOperation() {

            @Override
            public double[] compute(final double[] input) {
                return DoubleTabulars.mult(matrix, input);
            }

            @Override
            public List<MethodParameter> getParameters() {
                return List.of();
            }

            @Override
            public Object getParameter(final MethodParameter parameter) {
                return null;
            }
        };
    }

    public static CoordinateOperation concat(final List<Operation<double[], double[]>> operations) {
        return new CoordinateConcat(operations);
    }

    public static CoordinateOperation identity() {
        return IDENTITY;
    }

    private static final class CoordinateConcat implements CoordinateOperation {

        private final List<Operation<double[], double[]>> operations;

        CoordinateConcat(final List<Operation<double[], double[]>> operations) {
            this.operations = operations;
        }

        @Override
        public double[] compute(final double[] input) {
            double[] result = input;
            for (final Operation<double[], double[]> op : operations) {
                result = op.compute(result);
            }
            return result;
        }

        @Override
        public List<MethodParameter> getParameters() {
            throw new UnsupportedOperationException("no parameters for computed operation");
        }

        @Override
        public Object getParameter(final MethodParameter parameter) {
            throw new UnsupportedOperationException("no parameters for computed operation");
        }

    }

    private static final CoordinateOperation IDENTITY = new CoordinateOperation() {

        @Override
        public double[] compute(final double[] input) {
            return Arrays.copyOf(input, input.length);
        }

        @Override
        public List<MethodParameter> getParameters() {
            return List.of();
        }

        @Override
        public Object getParameter(final MethodParameter parameter) {
            return null;
        }
    };
}
