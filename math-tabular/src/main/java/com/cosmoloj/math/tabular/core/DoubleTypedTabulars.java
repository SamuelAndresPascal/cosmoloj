package com.cosmoloj.math.tabular.core;

import com.cosmoloj.math.tabular.line.TypedVector;
import com.cosmoloj.math.tabular.matrix.Matrix.Dimension;
import com.cosmoloj.math.tabular.matrix.TypedMatrix;
import java.util.Arrays;

/**
 *
 * @author Samuel Andrés
 */
public final class DoubleTypedTabulars {

    private DoubleTypedTabulars() {
    }

    public static double[][] toPrimitive(final Double[][] m) {
        final double[][] s = new double[m.length][m[0].length];
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++) {
                s[i][j] = m[i][j];
            }
        }
        return s;
    }

    public static Double[][] fromPrimitive(final double[][] m) {
        final Double[][] s = new Double[m.length][m[0].length];
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++) {
                s[i][j] = m[i][j];
            }
        }
        return s;
    }

    public static double[] toPrimitive(final Double[] m) {
        final double[] s = new double[m.length];
        for (int i = 0; i < s.length; i++) {
            s[i] = m[i];
        }
        return s;
    }

    public static Double[] fromPrimitive(final double[] m) {
        final Double[] s = new Double[m.length];
        for (int i = 0; i < s.length; i++) {
            s[i] = m[i];
        }
        return s;
    }

    //************************************************************************ ==

    // ALGORITHMES D'OPÉRATIONS SUR DES TABLEAUX

    //************************************************************************ ==

    public static Double[][] external(final double l, final Double[][] m) {
        final Double[][] s = new Double[m.length][m[0].length];
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++) {
                s[i][j] = l * m[i][j];
            }
        }
        return s;
    }

    public static Double[][] external(final double l, final TypedMatrix<Double> m) {
        return external(l, m.getMatrix());
    }

    //************************************************************************ ==

    public static Double[] external(final double l, final Double[] v) {
        final Double[] s = new Double[v.length];
        for (int i = 0; i < s.length; i++) {
            s[i] = l * v[i];
        }
        return s;
    }

    public static Double[] external(final double l, final TypedVector<Double> v) {
        return external(l, v.getMatrix());
    }

    //************************************************************************ ==

    public static Double[][] add(final Double[][] a, final Double[][] b) {
        final Double[][] s = new Double[a.length][a[0].length];
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++) {
                s[i][j] = a[i][j] + b[i][j];
            }
        }
        return s;
    }

    public static Double[][] add(final TypedMatrix<Double> a, final TypedMatrix<Double> b) {
        return add(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    public static Double[][] minus(final Double[][] a, final Double[][] b) {
        return add(a, external(-1, b));
    }

    public static Double[][] minus(final TypedMatrix<Double> a, final TypedMatrix<Double> b) {
        return minus(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    public static Double[] add(final Double[] a, final Double[] b) {
        final Double[] s = new Double[a.length];
        for (int i = 0; i < s.length; i++) {
            s[i] = a[i] + b[i];
        }
        return s;
    }

    public static Double[] add(final TypedVector<Double> a, final TypedVector<Double> b) {
        return add(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    public static Double[] minus(final Double[] a, final Double[] b) {
        return add(a, external(-1, b));
    }

    public static Double[] minus(final TypedVector<Double> a, final TypedVector<Double> b) {
        return minus(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    public static Double[][] copy(final Double[][] m) {
        final Double[][] t = new Double[m.length][m[0].length];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                t[i][j] = m[i][j];
            }
        }
        return t;
    }

    public static Double[][] copy(final TypedMatrix<Double> m) {
        return copy(m.getMatrix());
    }

    //************************************************************************

    public static Double[] copy(final Double[] v) {
        final Double[] t = new Double[v.length];
        System.arraycopy(v, 0, t, 0, v.length);
        return t;
    }

    public static Double[] copy(final TypedVector<Double> v) {
        return copy(v.getMatrix());
    }

    //************************************************************************

    public static Double[][] transpose(final Double[][] m) {
        final Double[][] t = new Double[m[0].length][m.length];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                t[i][j] = m[j][i];
            }
        }
        return t;
    }

    public static Double[][] transpose(final TypedMatrix<Double> m) {
        return transpose(m.getMatrix());
    }

    //************************************************************************ ==

    public static Double[] transpose(final Double[] v) {
        return copy(v);
    }

    public static Double[] transpose(final TypedVector<Double> v) {
        return transpose(v.getMatrix());
    }

    //************************************************************************ ==

    public static Double[][] mult(final Double[][] a, final Double[][] b) {

        // mA doit avoir exactement autant de colonnes que mB a de lignes.
        final int cDim = a[0].length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }

        final Double[][] p = new Double[a.length][b[0].length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                double s = 0.;
                for (int k = 0; k < cDim; k++) {
                    s += a[i][k] * b[k][j];
                }
                p[i][j] = s;
            }
        }

        return p;
    }

    public static Double[][] mult(final TypedMatrix<Double> mA, final TypedMatrix<Double> mB) {

        return mult(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static Double[] mult(final Double[] a, final Double[][] b) {
        final int cDim = a.length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }
        final Double[] p = new Double[b[0].length];

        for (int i = 0; i < p.length; i++) {
            double s = 0.;
            for (int j = 0; j < cDim; j++) {
                s += a[j] * b[j][i];
            }
            p[i] = s;
        }
        return p;
    }

    public static Double[] mult(final TypedVector<Double> mA, final TypedMatrix<Double> mB) {
        return mult(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static Double[] mult(final Double[][] a, final Double[] b) {
        final int cDim = a[0].length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }
        final Double[] p = new Double[a.length];

        for (int i = 0; i < p.length; i++) {
            double s = 0.;
            for (int j = 0; j < cDim; j++) {
                s += a[i][j] * b[j];
            }
            p[i] = s;
        }
        return p;
    }

    public static Double[] mult(final TypedMatrix<Double> mA, final TypedVector<Double> mB) {
        return mult(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static double mult_1nn1(final Double[] a, final Double[] b) {
        final int cDim = a.length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }
        double s = 0.;
        for (int i = 0; i < cDim; i++) {
            s += a[i] * b[i];
        }
        return s;
    }

    public static double mult(final TypedVector<Double> mA, final TypedVector<Double> mB) {
        return mult_1nn1(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static Double[][] mult_n11m(final Double[] a, final Double[] b) {
        final Double[][] p = new Double[a.length][b.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                p[i][j] = a[i] * b[j];
            }
        }
        return p;
    }

    public static Double[][] multmn(final TypedVector<Double> mA, final TypedVector<Double> mB) {
        return mult_n11m(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static double value(final Double[][] m, final int i, final int j) {
        return m[i][j];
    }

    public static double value(final TypedMatrix<Double> m, final int i, final int j) {
        return value(m.getMatrix(), i, j);
    }

    //************************************************************************ ==

    public static double value(final Double[] v, final int i) {
        return v[i];
    }

    public static double value(final TypedVector<Double> v, final int i) {
        return value(v.getMatrix(), i);
    }

    //************************************************************************ ==

    public static Double[] line(final Double[][] m, final int i) {
        final Double[] r = new Double[m[0].length];
        for (int j = 0; j < r.length; j++) {
            r[j] = m[i][j];
        }
        return r;
    }

    public static Double[] line(final TypedMatrix<Double> m, final int i) {
        return line(m.getMatrix(), i);
    }

    //************************************************************************ ==

    public static Double[] column(final Double[][] m, final int j) {
        final Double[] r = new Double[m.length];
        for (int i = 0; i < r.length; i++) {
            r[i] = m[i][j];
        }
        return r;
    }

    public static Double[] column(final TypedMatrix<Double> m, final int j) {
        return column(m.getMatrix(), j);
    }

    //************************************************************************ ==

    public static Double[] vector(final Double[][] m, final int i, final Dimension d) {
        if (d == Dimension.ROW) {
            return line(m, i);
        } else if (d == Dimension.COLUMN) {
            return column(m, i);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static Double[] vector(final TypedMatrix<Double> m, final int i, final Dimension d) {
        return vector(m.getMatrix(), i, d);
    }

    //************************************************************************ ==

    public static Double[][] matrix(final Double[] v, final Dimension d) {
        final Double[][] r;
        if (d == Dimension.ROW) {
            r = new Double[1][v.length];
            for (int j = 0; j < v.length; j++) {
                r[0][j] = v[j];
            }
        } else if (d == Dimension.COLUMN) {
            r = new Double[v.length][1];
            for (int i = 0; i < v.length; i++) {
                r[i][0] = v[i];
            }
        } else {
            throw new UnsupportedOperationException();
        }
        return r;
    }

    public static Double[][] matrix(final TypedVector<Double> v, final Dimension d) {
        return matrix(v.getMatrix(), d);
    }

    //************************************************************************ ==

    public static Double[][] hadamard(final Double[][] a, final Double[][] b) {
        final Double[][] r = new Double[a.length][a[0].length];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                r[i][j] = a[i][j] * b[i][j];
            }
        }
        return r;
    }

    public static Double[][] hadamard(final TypedMatrix<Double> a, final TypedMatrix<Double> b) {
        return hadamard(a.getMatrix(), b.getMatrix());

    }

    //************************************************************************ ==

    public static Double[][] kronecker(final Double[][] a, final Double[][] b) {
        final int la = a.length;
        final int ca = a[0].length;
        final int lb = b.length;
        final int cb = b[0].length;
        final int l = a.length * b.length;
        final int c = a[0].length * b[0].length;
        final Double[][] r = new Double[l][c];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                r[i][j] = a[i / lb][j / cb] * b[i % la][j % ca];
            }
        }
        return r;
    }

    public static Double[][] kronecker(final TypedMatrix<Double> a, final TypedMatrix<Double> b) {
        return kronecker(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    // ALGORITHMES DE CRÉATION DE TABLEAUX PARTICULIERS.

    //************************************************************************ ==

    public static Double[][] diagonal(final Double[] v) {
        final Double[][] r = new Double[v.length][v.length];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r.length; j++) {
                r[i][j] = (i == j) ? v[i] : 0;
            }
        }
        return r;
    }

    public static Double[][] diagonal(final TypedVector<Double> v) {
        return diagonal(v.getMatrix());
    }

    //************************************************************************ ==

    public static Double[][] diagonal(final double f, final int d) {
        return diagonal(f, d, d);
    }

    //************************************************************************ ==

    public static Double[][] diagonal(final double d, final double f, final int l, final int c) {
        final Double[][] r = new Double[l][c];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[0].length; j++) {
                r[i][j] = (i == j) ? d : f;
            }
        }
        return r;
    }

    public static Double[][] diagonal(final double f, final int l, final int c) {
        return diagonal(f, 0., l, c);
    }

    //************************************************************************ ==

    public static Double[][] fill(final double f, final int l, final int c) {
        final Double[][] r = new Double[l][c];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                r[i][j] = f;
            }
        }
        return r;
    }

    //************************************************************************ ==

    public static Double[] fill(final double f, final int n) {
        final Double[] r = new Double[n];
        Arrays.fill(r, f);
        return r;
    }

    //****************************************************************************************************************

    // LINEARISATION D'UN TABLEAU 2D EN TABLEAU 1D

    //****************************************************************************************************************

    /**
     *
     * @param square <span class="fr">matrice carrée</span>
     * @return <span class="fr">matrice ligne constituée de la concaténation des lignes de la matrice carrée</span>
     */
    public static Double[] linearizeLine(final Double[][] square) {
        final int width = square[0].length;
        final int height = square.length;
        final Double[] line = new Double[height * width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                line[i * height + j] = square[i][j];
            }
        }
        return line;
    }
    /**
     *
     * @param square <span class="fr">matrice carrée</span>
     * @return <span class="fr">matrice ligne constituée de la concaténation des colonnes de la matrice carrée</span>
     */
    public static Double[] linearizeColumn(final Double[][] square) {
        final int width = square[0].length;
        final int height = square.length;
        final Double[] line = new Double[height * width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                line[i + j * width] = square[i][j];
            }
        }
        return line;
    }

    //************************************************************************ ==

    public static Double[][] rotation2D(final double theta) {
        final double cost = Math.cos(theta);
        final double sint = Math.sin(theta);
        return new Double[][]{
            {cost, -sint},
            {sint, cost}};
    }

    //************************************************************************ ==

    public static Double[][] rotation3DX(final double theta) {
        final double cost = Math.cos(theta);
        final double sint = Math.sin(theta);
        return new Double[][]{
            {1.,   0.,    0.},
            {0., cost, -sint},
            {0., sint,  cost}};
    }

    //************************************************************************ ==

    public static Double[][] rotation3DY(final double theta) {
        final double cost = Math.cos(theta);
        final double sint = Math.sin(theta);
        return new Double[][]{
            {cost,  0., sint},
            {0.,    1.,   0.},
            {-sint, 0., cost}};
    }

    //************************************************************************ ==

    public static Double[][] rotation3DZ(final double theta) {
        final double cost = Math.cos(theta);
        final double sint = Math.sin(theta);
        return new Double[][]{
            {cost, -sint, 0.},
            {sint,  cost, 0.},
            {0.,      0., 1.}};
    }

    // MATRICES JACOBIENNES
    //--------------------------------------------------------------------------

    public static Double[][] jacobianCartesian2D() {
        return diagonal(1., 2);
    }

    public static Double[][] jacobianCartesian3D() {
        return diagonal(1., 3);
    }

    public static Double[][] jacobianPolar(final double r, final double theta) {
        final double cost = Math.cos(theta);
        final double sint = Math.sin(theta);
        return new Double[][]{
            {cost, -r * sint},
            {sint, r * cost}};
    }

    public static Double[][] jacobianCylindrical(final double r, final double theta) {
        return new Double[][]{
            {1., 0., 0.},
            {1., 0., 0.},
            {0., 0., r}};
    }

    public static Double[][] jacobianSpherical(final double r, final double theta, final double phi) {
        final double cost = Math.cos(theta);
        final double sint = Math.sin(theta);
        final double cosf = Math.cos(phi);
        final double sinf = Math.sin(phi);
        return new Double[][]{
            {sint * cosf, r * cost * cosf, -r * sint * sinf},
            {sint * sinf, r * cost * sinf,  r * sint * cosf},
            {cost,              -r * sint,               0.}};
    }

//    public static  static Double[][] metricTensorCartesian2D() {
//
//    }
}
