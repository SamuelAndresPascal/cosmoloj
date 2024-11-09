package com.cosmoloj.math.tabular.core;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.tabular.line.DoubleVector;
import com.cosmoloj.math.tabular.matrix.DoubleMatrix;
import com.cosmoloj.math.tabular.matrix.Matrix.Dimension;
import com.cosmoloj.math.tabular.line.FloatVector;
import com.cosmoloj.math.tabular.matrix.FloatMatrix;
import com.cosmoloj.util.bib.Page;
import com.cosmoloj.util.bib.Cite;
import java.util.Arrays;
import java.util.function.IntToDoubleFunction;

/**
 *
 * @author Samuel Andrés
 */
public final class DoubleTabulars {

    private DoubleTabulars() {
    }

    public static boolean equal(final double[] a, final double[] b) {
        return Arrays.equals(a, b);
    }

    public static boolean equal(final DoubleVector a, final DoubleVector b) {
        return equal(a.getMatrix(), b.getMatrix());
    }

    public static boolean equal(final double[][] a, final double[][] b) {
        return Arrays.deepEquals(a, b);
    }

    public static boolean equal(final DoubleMatrix a, final DoubleMatrix b) {
        return equal(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    /**
     * <span class="fr">Produit externe d'une matrice par un scalaire.</span>
     * @param l <span class="fr">scalaire</span>
     * @param m <span class="fr">matrice</span>
     * @return <span class="fr">résultat de la multiplication de la matrice par le scalaire</span>
     */
    public static double[][] external(final double l, final double[][] m) {
        double[][] s = new double[m.length][m[0].length];
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++) {
                s[i][j] = l * m[i][j];
            }
        }
        return s;
    }

    /**
     * <span class="fr">Produit externe d'une matrice par un scalaire.</span>
     * @param l <span class="fr">scalaire</span>
     * @param m <span class="fr">matrice</span>
     * @return <span class="fr">résultat de la multiplication de la matrice par le scalaire</span>
     */
    public static double[][] external(final double l, final DoubleMatrix m) {
        return external(l, m.getMatrix());
    }

    //************************************************************************ ==

    /**
     * <span class="fr">Produit externe d'un vecteur par un scalaire.</span>
     * @param l <span class="fr">scalaire</span>
     * @param v <span class="fr">vecteur</span>
     * @return <span class="fr">résultat de la multiplication du vecteur par le scalaire</span>
     */
    public static double[] external(final double l, final double[] v) {
        final double[] s = new double[v.length];
        for (int i = 0; i < s.length; i++) {
            s[i] = l * v[i];
        }
        return s;
    }

    /**
     * <span class="fr">Produit externe d'un vecteur par un scalaire.</span>
     * @param l <span class="fr">scalaire</span>
     * @param v <span class="fr">vecteur</span>
     * @return <span class="fr">résultat de la multiplication du vecteur par le scalaire</span>
     */
    public static double[] external(final double l, final DoubleVector v) {
        return external(l, v.getMatrix());
    }

    //************************************************************************ ==

    public static double[][] add(final double[][] a, final double[][] b) {
        final double[][] s = new double[a.length][a[0].length];
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++) {
                s[i][j] = a[i][j] + b[i][j];
            }
        }
        return s;
    }

    public static double[][] add(final DoubleMatrix a, final DoubleMatrix b) {
        return add(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    public static double[][] minus(final double[][] a, final double[][] b) {
        return add(a, external(-1, b));
    }

    public static double[][] minus(final DoubleMatrix a, final DoubleMatrix b) {
        return minus(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    public static double[] add(final double[] a, final double[] b, final int length) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("both vectors are expected to have the same dimension");
        }
        if (length > a.length) {
            throw new IllegalArgumentException("");
        }
        final double[] s = new double[length];
        for (int i = 0; i < length; i++) {
            s[i] = a[i] + b[i];
        }
        return s;
    }

    public static double[] add(final double[] a, final double[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("both vectors are expected to have the same dimension");
        }
        final double[] s = new double[a.length];
        for (int i = 0; i < s.length; i++) {
            s[i] = a[i] + b[i];
        }
        return s;
    }

    public static double[] add(final DoubleVector a, final DoubleVector b) {
        return add(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    public static double[] minus(final double[] a, final double[] b, final int length) {
        return add(a, external(-1., b), length);
    }

    public static double[] minus(final double[] a, final double[] b) {
        return add(a, external(-1., b));
    }

    public static double[] minus(final DoubleVector a, final DoubleVector b) {
        return minus(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    public static double max(final double[] a) {
        double max = a[0];
        for (final double c : a) {
            if (c > max) {
                max = c;
            }
        }
        return max;
    }

    public static double max(final DoubleVector a) {
        return max(a.getMatrix());
    }

    //************************************************************************ ==

    public static double min(final double[] a) {
        double min = a[0];
        for (final double c : a) {
            if (c < min) {
                min = c;
            }
        }
        return min;
    }

    public static double min(final DoubleVector a) {
        return min(a.getMatrix());
    }

    //************************************************************************ ==

    public static double sum(final double[] a) {
        double sum = 0.;
        for (final double c : a) {
                sum += c;
        }
        return sum;
    }

    public static double sum(final DoubleVector a) {
        return sum(a.getMatrix());
    }

    //************************************************************************ ==

    public static double avg(final double[] a) {
        return sum(a) / a.length;
    }

    public static double avg(final DoubleVector a) {
        return avg(a.getMatrix());
    }

    //************************************************************************ ==

    public static double[][] copy(final double[][] m) {
        final double[][] t = new double[m.length][];
        for (int i = 0; i < t.length; i++) {
            t[i] = new double[m[i].length];
            System.arraycopy(m[i], 0, t[i], 0, t[i].length);
        }
        return t;
    }

    public static double[][] copy(final DoubleMatrix m) {
        return copy(m.getMatrix());
    }

    //************************************************************************ ==

    public static double[] copy(final double[] v) {
        final double[] t = new double[v.length];
        System.arraycopy(v, 0, t, 0, v.length);
        return t;
    }

    public static double[] copy(final DoubleVector v) {
        return copy(v.getMatrix());
    }

    //************************************************************************ ==

    public static double[][] copy(final float[][] m) {
        final double[][] t = new double[m.length][];
        for (int i = 0; i < t.length; i++) {
            t[i] = new double[m[i].length];
            for (int j = 0; j < t[i].length; j++) {
                t[i][j] = (double) m[i][j];
            }
        }
        return t;
    }

    public static double[][] copy(final FloatMatrix m) {
        return copy(m.getMatrix());
    }

    //************************************************************************ ==

    public static double[] copy(final float[] v) {
        final double[] t = new double[v.length];
        for (int i = 0; i < t.length; i++) {
            t[i] = (double) v[i];
        }
        return t;
    }

    public static double[] copy(final FloatVector v) {
        return copy(v.getMatrix());
    }

    //************************************************************************ ==

    public static double[][] transpose(final double[][] m) {
        final double[][] t = new double[m[0].length][m.length];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                t[i][j] = m[j][i];
            }
        }
        return t;
    }

    public static double[][] transpose(final DoubleMatrix m) {
        return transpose(m.getMatrix());
    }

    //************************************************************************ ==

    public static double[] transpose(final double[] v) {
        return copy(v);
    }

    public static double[] transpose(final DoubleVector v) {
        return transpose(v.getMatrix());
    }

    //************************************************************************ ==

    public static double[][] mult(final double[][] a, final double[][] b) {

        // mA doit avoir exactement autant de colonnes que mB a de lignes.
        final int cDim = a[0].length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }

        final double[][] p = new double[a.length][b[0].length];

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

    public static double[][] mult(final DoubleMatrix mA, final DoubleMatrix mB) {

        return mult(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static double[] mult(final double[] a, final double[][] b) {
        final int cDim = a.length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }
        final double[] p = new double[b[0].length];

        for (int i = 0; i < p.length; i++) {
            double s = 0.;
            for (int j = 0; j < cDim; j++) {
                s += a[j] * b[j][i];
            }
            p[i] = s;
        }
        return p;
    }

    public static double[] mult(final DoubleVector mA, final DoubleMatrix mB) {
        return mult(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    // pratique pour les opérations de transformation
    public static double[] mult(final double[][] a, final double[] b) {
        final int cDim = a[0].length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }
        final double[] p = new double[a.length];

        for (int i = 0; i < p.length; i++) {
            double s = 0.;
            for (int j = 0; j < cDim; j++) {
                s += a[i][j] * b[j];
            }
            p[i] = s;
        }
        return p;
    }

    public static double[] mult(final DoubleMatrix mA, final DoubleVector mB) {
        return mult(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static double mult1nn1(final double[] a, final double[] b) {
        final int cDim = a.length;
        if (b.length != cDim) {
            throw new IllegalArgumentException("both vector are expected to have the same dimension");
        }
        double s = 0.;
        for (int i = 0; i < cDim; i++) {
            s += a[i] * b[i];
        }
        return s;
    }

    public static double mult(final DoubleVector mA, final DoubleVector mB) {
        return mult1nn1(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static double[][] mult_n11m(final double[] a, final double[] b) {
        final double[][] p = new double[a.length][b.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                p[i][j] = a[i] * b[j];
            }
        }
        return p;
    }

    public static double[][] multmn(final DoubleVector mA, final DoubleVector mB) {
        return mult_n11m(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static double value(final double[][] m, final int i, final int j) {
        return m[i][j];
    }

    public static double value(final DoubleMatrix m, final int i, final int j) {
        return value(m.getMatrix(), i, j);
    }

    //************************************************************************ ==

    public static double value(final double[] v, final int i) {
        return v[i];
    }

    public static double value(final DoubleVector v, final int i) {
        return value(v.getMatrix(), i);
    }

    //************************************************************************ ==

    public static double[] line(final double[][] m, final int i) {
        final double[] r = new double[m[0].length];
        System.arraycopy(m[i], 0, r, 0, r.length);
        return r;
    }

    public static double[] line(final DoubleMatrix m, final int i) {
        return line(m.getMatrix(), i);
    }

    //************************************************************************ ==

    public static double[] column(final double[][] m, final int j) {
        final double[] r = new double[m.length];
        for (int i = 0; i < r.length; i++) {
            r[i] = m[i][j];
        }
        return r;
    }

    public static double[] column(final DoubleMatrix m, final int j) {
        return column(m.getMatrix(), j);
    }

    //************************************************************************ ==

    public static double[] vector(final double[][] m, final int i, final Dimension d) {
        if (d == Dimension.ROW) {
            return line(m, i);
        } else if (d == Dimension.COLUMN) {
            return column(m, i);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static double[] vector(final DoubleMatrix m, final int i, final Dimension d) {
        return vector(m.getMatrix(), i, d);
    }

    //************************************************************************ ==

    public static double[][] matrix(final double[] v, final Dimension d) {
        final double[][] r;
        if (d == Dimension.ROW) {
            r = new double[1][v.length];
            System.arraycopy(v, 0, r[0], 0, v.length);
        } else if (d == Dimension.COLUMN) {
            r = new double[v.length][1];
            for (int i = 0; i < v.length; i++) {
                r[i][0] = v[i];
            }
        } else {
            throw new UnsupportedOperationException();
        }
        return r;
    }

    public static double[][] matrix(final DoubleVector v, final Dimension d) {
        return matrix(v.getMatrix(), d);
    }

    //************************************************************************ ==

    public static double[][] hadamard(final double[][] a, final double[][] b) {
        final double[][] r = new double[a.length][a[0].length];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                r[i][j] = a[i][j] * b[i][j];
            }
        }
        return r;
    }

    public static double[][] hadamard(final DoubleMatrix a, final DoubleMatrix b) {
        return hadamard(a.getMatrix(), b.getMatrix());

    }

    //************************************************************************ ==

    public static double[][] kronecker(final double[][] a, final double[][] b) {
        final int la = a.length;
        final int ca = a[0].length;
        final int lb = b.length;
        final int cb = b[0].length;
        final int l = a.length * b.length;
        final int c = a[0].length * b[0].length;
        final double[][] r = new double[l][c];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                r[i][j] = a[i / lb][j / cb] * b[i % la][j % ca];
            }
        }
        return r;
    }

    public static double[][] kronecker(final DoubleMatrix a, final DoubleMatrix b) {
        return kronecker(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    // ALGORITHMES DE CRÉATION DE TABLEAUX PARTICULIERS.

    //************************************************************************ ==

    /**
     * <div class="fr">Transformation d'un vecteur de valeurs en une matrice diagonale. La matrice est une matrice
     * carrée dont le nombre de lignes et le nombre de colonnes sont égaux au nombre de composanttes du vecteur de
     * valeurs. Les valeurs du vecteurs sont affectées à la diagonale de la matrice.</div>
     * @param v
     * @return
     */
    public static double[][] diagonal(final double[] v) {
        final double[][] r = new double[v.length][v.length];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r.length; j++) {
                r[i][j] = (i == j) ? v[i] : 0.;
            }
        }
        return r;
    }

    public static double[][] diagonal(final DoubleVector v) {
        return diagonal(v.getMatrix());
    }

    //************************************************************************ ==

    public static double[][] identity(final int d) {
        return diagonal(1., d);
    }

    //************************************************************************ == =

    public static double[][] diagonal(final double f, final int d) {
        return diagonal(f, d, d);
    }

    //************************************************************************ ==

    public static double[][] diagonal(final double d, final double f, final int l, final int c) {
        final double[][] r = new double[l][c];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[0].length; j++) {
                r[i][j] = (i == j) ? d : f;
            }
        }
        return r;
    }

    public static double[][] diagonal(final double f, final int l, final int c) {
        return diagonal(f, 0., l, c);
    }

    //************************************************************************ ==

    public static double[][] fill(final double f, final int l, final int c) {
        final double[][] r = new double[l][c];
        for (final double[] r1 : r) {
            for (int j = 0; j < r1.length; j++) {
                r1[j] = f;
            }
        }
        return r;
    }

    public static double[][] fill(final Fill f, final int l, final int c) {
        final double[][] r = new double[l][c];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                r[i][j] = f.fill(i, j);
            }
        }
        return r;
    }

    @FunctionalInterface
    public interface Fill {
        double fill(int i, int j);
    }

    //************************************************************************ ==

    /**
     * <div class="fr">Produit un nouveau tableau de la longueur indiquée initialisé avec la valeur donnée.</div>
     * @param f <span class="fr">valeur des composantes du tableau</span>
     * @param n <span class="fr">longueur du tableau</span>
     * @return <span class="fr">un tableau de la longueur indiquée initialisé avec la valeur donnée</span>
     */
    public static double[] fill(final double f, final int n) {
        final double[] r = new double[n];
        Arrays.fill(r, f);
        return r;
    }

    public static double[] fill(final IntToDoubleFunction f, final int n) {
        final double[] r = new double[n];
        for (int i = 0; i < n; i++) {
            r[i] = f.applyAsDouble(i);
        }
        return r;
    }

    //************************************************************************ ==

    public static double[][] rotation2D(final double theta) {
        final double cost = Math.cos(theta);
        final double sint = Math.sin(theta);
        return new double[][]{
            {cost, -sint},
            {sint, cost}};
    }

    //************************************************************************ ==

    public static double[][] rotation3DX(final double theta) {
        final double cost = Math.cos(theta);
        final double sint = Math.sin(theta);
        return new double[][]{
            {1.,   0.,    0.},
            {0., cost, -sint},
            {0., sint,  cost}};
    }

    //************************************************************************ ==

    public static double[][] rotation3DY(final double theta) {
        final double cost = Math.cos(theta);
        final double sint = Math.sin(theta);
        return new double[][]{
            {cost,  0., sint},
            {0.,    1.,   0.},
            {-sint, 0., cost}};
    }

    //************************************************************************ ==

    public static double[][] rotation3DZ(final double theta) {
        final double cost = Math.cos(theta);
        final double sint = Math.sin(theta);
        return new double[][]{
            {cost, -sint, 0.},
            {sint,  cost, 0.},
            {0.,      0., 1.}};
    }

    //************************************************************************ ==

    public static double[][] rotation3DXYZ(final double x, final double y, final double z) {
        final double sinx = Math.sin(x);
        final double cosx = Math.cos(x);
        final double siny = Math.sin(y);
        final double cosy = Math.cos(y);
        final double sinz = Math.sin(z);
        final double cosz = Math.cos(z);

        return new double[][]{
            {cosy * cosz,   -cosx * sinz + sinx * siny * cosz,   sinx * sinz + cosx * siny * cosz},
            {cosy * sinz,   cosx * cosz + sinx * siny * sinz,    -sinx * cosz + cosx * siny * sinz},
            {-siny,         sinx * cosy,                         cosx * cosy}
        };
    }

    //************************************************************************ ==

    public static double[][] rotation3DZYX(final double x, final double y, final double z) {
        final double sinz = Math.sin(z);
        final double cosz = Math.cos(z);
        final double siny = Math.sin(y);
        final double cosy = Math.cos(y);
        final double sinx = Math.sin(x);
        final double cosx = Math.cos(x);

        return new double[][]{
            {cosy * cosz,   -cosy * sinz,   siny},
            {sinz * cosx + siny * cosz * sinx,   cosz * cosx - siny * sinz * sinx, -cosy * sinx},
            {sinz * sinx - siny * cosz * cosx,   cosz * sinx + siny * sinz * cosx, cosy * cosx}
        };
    }

    //************************************************************************ ==

    public static double[][] rotation3DXYZApprox(final double x, final double y, final double z) {

        return new double[][]{
            {1.,  -z,  y},
            {z,   1.,  -x},
            {-y,  x,   1.}
        };
    }

    //************************************************************************ ==

    public static double[][] rotation3DZYXApprox(final double x, final double y, final double z) {
        return rotation3DXYZApprox(x, y, z);
    }

    // MATRICES JACOBIENNES
    //--------------------------------------------------------------------------

    public static double[][] jacobianCartesian2D() {
        return diagonal(1., 2);
    }

    public static double[][] jacobianCartesian3D() {
        return diagonal(1., 3);
    }

    public static double[][] jacobianPolar(final double r, final double theta) {
        final double cost = Math.cos(theta);
        final double sint = Math.sin(theta);
        return new double[][]{
            {cost, -r * sint},
            {sint, r * cost}};
    }

    public static double[][] jacobianCylindrical(final double r, final double theta) {
        return new double[][]{
            {1., 0., 0.},
            {1., 0., 0.},
            {0., 0., r}};
    }

    public static double[][] jacobianSpherical(final double r, final double theta, final double phi) {
        final double cost = Math.cos(theta);
        final double sint = Math.sin(theta);
        final double cosf = Math.cos(phi);
        final double sinf = Math.sin(phi);
        return new double[][]{
            {sint * cosf, r * cost * cosf, -r * sint * sinf},
            {sint * sinf, r * cost * sinf, r * sint * cosf},
            {cost,        -r * sint,       0.}};
    }

    // MATRICES DE DÉTERMINANTS
    //--------------------------------------------------------------------------

    /**
     * <span class="en">Compute the 2x2 matrix determinant.</span>
     * @param m <span class="en">2x2 matrix</span>
     * @return <span class="en">the matrix determinant</span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    @Page(27)
    public static double determinant11(final double[][] m) {
        if (m.length != 1 && m[0].length != 1) {
            throw new IllegalArgumentException("matrix 1x1 expected");
        }
        return m[0][0];
    }

    /**
     * <span class="en">Compute the 2x2 matrix determinant.</span>
     * @param m <span class="en">2x2 matrix</span>
     * @return <span class="en">the matrix determinant</span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    @Page(27)
    public static double determinant11(final DoubleMatrix m) {
        return determinant11(m.getMatrix());
    }

    /**
     * <span class="en">Compute the 2x2 matrix determinant.</span>
     * @param m <span class="en">2x2 matrix</span>
     * @return <span class="en">the matrix determinant</span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    @Page(27)
    public static double determinant22(final double[][] m) {
        if (m.length != 2 && m[0].length != 2) {
            throw new IllegalArgumentException("matrix 2x2 expected");
        }
        return m[0][0] * m[1][1] - m[0][1] * m[1][0];
    }

    /**
     * <span class="en">Compute the 2x2 matrix determinant.</span>
     * @param m <span class="en">2x2 matrix</span>
     * @return <span class="en">the matrix determinant</span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    @Page(27)
    public static double determinant22(final DoubleMatrix m) {
        return determinant22(m.getMatrix());
    }


    /**
     * <span class="fr">Sous-matrice associée au cofacteur i,j.</span>
     * @param m <span class="fr">matrice principale</span>
     * @param i <span class="fr">index de ligne du cofacteur associé</span>
     * @param j <span class="fr">index de colonne du cofacteur associé</span>
     * @return <span class="fr">la sous-matrice associée au cofacteur indiqué par ses index de ligne et de colonne
     * </span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    @Page(29)
    public static double[][] subMatrix(final double[][]m, final int i, final int j) {
        if (m == null || m.length != m[0].length) {
            throw new IllegalArgumentException("square matrix expected");
        }
        if (!(i < m.length && j < m.length)) {
            throw new IllegalArgumentException("indices must be lower than matrix dimension");
        }

        final double[][] c = new double[m.length - 1][m.length - 1];
        for (int x = 0; x < m.length; x++) {
            if (x != i) {
                for (int y = 0; y < m.length; y++) {
                    if (y != j) {
                        c[(x < i) ? x : x - 1][(y < j) ? y : y - 1] = m[x][y];
                    }
                }
            }
        }
        return c;
    }

    /**
     * <span class="fr">Sous-matrice associée au cofacteur i,j.</span>
     * @param m <span class="fr">matrice principale</span>
     * @param i <span class="fr">index de ligne du cofacteur associé</span>
     * @param j <span class="fr">index de colonne du cofacteur associé</span>
     * @return <span class="fr">la sous-matrice associée au cofacteur indiqué par ses index de ligne et de colonne
     * </span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    @Page(29)
    public static double[][] subMatrix(final DoubleMatrix m, final int i, final int j) {
        return subMatrix(m.getMatrix(), i, j);
    }

    /**
     * <span class="fr">final Dimension utilisée par défaut pour le calcul des déterminants.</span>
     */
    public static final Dimension DEFAULT_DETERMINANT_DIMENSION = Dimension.ROW;

    /**
     * <span class="en">Compute the coffacor of a matrix based on the row i and the column j and computing the
     * determinant using the indicated dimension.</span>
     * @param m <span class="en">NxN matrix</span>
     * @param i <span class="en">the row index</span>
     * @param j <span class="en">the column index</span>
     * @param d <span class="en">the dimension used to compute determinant</span>
     * @return <span class="en">the matrix determinant</span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    @Page(29)
    public static double cofactor(final double[][]m, final int i, final int j, final Dimension d) {
        return (((i + j) % 2 == 0) ? 1 : -1) * determinantNN(subMatrix(m, i, j), d);
    }

    /**
     * <span class="en">Compute the coffacor of a matrix based on the row i and the column j and computing the
     * determinant using the default dimension.</span>
     * @param m <span class="en">NxN matrix</span>
     * @param i <span class="en">the row index</span>
     * @param j <span class="en">the column index</span>
     * @return <span class="en">the matrix determinant</span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    @Page(29)
    public static double cofactor(final double[][]m, final int i, final int j) {
        return cofactor(m, i, j, DEFAULT_DETERMINANT_DIMENSION);
    }

    /**
     * <span class="en">Compute the NxN matrix determinant using the expansion by cofactors method.</span>
     * @param m <span class="en">NxN matrix</span>
     * @param d <span class="en">the dimension used to compute the determinant</span>
     * @return <span class="en">the matrix determinant</span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    @Page(29)
    public static double determinantNN(final double[][] m, final Dimension d) {
        if (m == null || m.length != m[0].length) {
            throw new IllegalArgumentException("square matrix expected");
        }
        if (d == null) {
            throw new IllegalArgumentException("matrix dimension expected");
        }

        if (m.length == 2) {
            return determinant22(m);
        }

        return switch (d) {
            case ROW -> {
                double det = 0.;
                for (int j = 0; j < m.length; j++) {
                    det += m[0][j] * cofactor(m, 0, j, d);
                }
                yield det;
            }
            case COLUMN -> {
                double det = 0.;
                for (int i = 0; i < m.length; i++) {
                    det += m[i][0] * cofactor(m, i, 0, d);
                }
                yield det;
            }
            default -> throw new IllegalArgumentException();
        };
    }

    /**
     * <span class="en">Compute the NxN matrix determinant using the expansion by cofactors method and the default
     * determinant dimension.</span>
     * @param m <span class="en">NxN matrix</span>
     * @return <span class="en">the matrix determinant</span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    @Page(29)
    public static double determinantNN(final double[][] m) {
        return determinantNN(m, DEFAULT_DETERMINANT_DIMENSION);
    }

    /**
     * <span class="en">Compute the NxN matrix determinant.</span>
     * @param m <span class="en">NxN matrix</span>
     * @param d <span class="en">the dimension used to compute the determinant</span>
     * @return <span class="en">the matrix determinant</span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    public static double determinantNN(final DoubleMatrix m, final Dimension d) {
        return determinantNN(m.getMatrix(), d);
    }

// MATRICES INVERSES

//--------------------------------------------------------------------------
    public static double determinantNN(final DoubleMatrix m) {
        return determinantNN(m, DEFAULT_DETERMINANT_DIMENSION);
    }


    public static double[][] adjointMatrix22(final double[][] m) {
        return new double[][]{{m[1][1], -m[0][1]},
                              {-m[1][0], m[0][0]}};
    }

    public static double[][] inverse22(final double[][] m) {
        return external(1. / determinant22(m), adjointMatrix22(m));
    }

    public static double[][] adjointMatrix33(final double[][] m) {
        return new double[][]{
            {m[1][1] * m[2][2] - m[1][2] * m[2][1],
                m[0][2] * m[2][1] - m[0][1] * m[2][2],
                m[0][1] * m[1][2] - m[0][2] * m[1][1]},
            {m[1][2] * m[2][0] - m[1][0] * m[2][2],
                m[0][0] * m[2][2] - m[0][2] * m[2][0],
                m[0][2] * m[1][0] - m[0][0] * m[1][2]},
            {m[1][0] * m[2][1] - m[1][1] * m[2][0],
                m[0][1] * m[2][0] - m[0][0] * m[2][1],
                m[0][0] * m[1][1] - m[0][1] * m[1][0]}};
    }

    public static double[][] inverse33(final double[][] m, final Dimension d) {
        final double det = determinantNN(m, d);
        if (det != 0.) {
            final double invDet = 1. / det;
            if (Double.isFinite(invDet)) {
                return external(invDet, adjointMatrix33(m));
            }
        }
        throw new IllegalArgumentException("matrix not inversible");
    }

    public static double[][] invserse33(final double[][] m) {
        return inverse33(m, DEFAULT_DETERMINANT_DIMENSION);
    }

    // NORMES
    //--------------------------------------------------------------------------

    public static double norm(final double[] v) {
        return Math.sqrt(mult1nn1(v, v));
    }

    public static double[] normalize(final double[] v) {
        return external(1. / norm(v), v);
    }

    // MATRICES DE TENSEURS MÉTRIQUES
    //--------------------------------------------------------------------------

    public static double[][] metricTensorCartesian2D() {
        return diagonal(1., 2);
    }

    public static double[][] metricTensorCartesian3D() {
        return diagonal(1., 3);
    }

//    public static double[][] metricTensorPolar(final double r, final double theta) {
//        final double cost = Math.cos(theta);
//        final double sint = Math.sin(theta);
//        return new double[][]{
//            {1., 0.},
//            {0., r*r}};
//    }
//
//    public static double[][] metricTensorCylindrical(final double r, final double theta) {
//        return new double[][]{
//            {1., 0., 0.},
//            {0., 1., 0.},
//            {0., 0., r*r}};
//    }
//
//    public static double[][] metricTensorSpherical(final double r, final double theta, final double phi) {
//        final double cost = Math.cos(theta);
//        final double sint = Math.sin(theta);
//        final double cosf = Math.cos(phi);
//        final double sinf = Math.sin(phi);
//        return new double[][]{
//            {sint*cosf, r*cost*cosf, -r*sint*sinf},
//            {sint*sinf, r*cost*sinf, r*sint*cosf},
//            {cost,      -r*sint,     0.}};
//    }

    /**
     * <span class="fr">Produit vectoriel de deux vecteurs</span>
     * @param u <span class="fr">premier vecteur</span>
     * @param v <span class="fr">second vecteur</span>
     * @return <span class="fr">vecteur résultant du produit vectoriel</span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    @Page(41)
    public static double[] crossProduct(final double[] u, final double[] v) {
        return new double[]{u[1] * v[2] - u[2] * v[1],
                            u[2] * v[0] - u[0] * v[2],
                            u[0] * v[1] - u[1] * v[0]};
    }

    /**
     * <span class="fr">Produit triple de trois vecteurs</span>
     * @param u <span class="fr">premier vecteur</span>
     * @param v <span class="fr">deuxième vecteur</span>
     * @param w <span class="fr">troisième vecteur</span>
     * @return <span class="fr">scalaire résultant du produit triple</span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    @Page(44)
    public static double scalarTripleProduct(final double[] u, final double[] v, final double[] w) {
        return mult1nn1(crossProduct(u, v), w);
    }

    /**
     * <div class="fr">Moyenne d'un ensemble de valeurs.</div>
     * @param set <span class="fr">ensemble de valeurs</span>
     * @return <span class="fr">moyenne des valeurs</span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    @Page(92)
    public static double mean1D(final double[] set) {
        double u = 0.;
        for (final double c : set) {
            u += c;
        }
        return u / set.length;
    }


    /**
     * <div class="fr">Moyenne d'un ensemble de points de dimension 1.</div>
     * @param set <span class="fr">ensemble de points de dimension 1</span>
     * @return <span class="fr">moyenne des valeurs</span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    @Page(92)
    public static double mean1D(final double[][] set) {
        double u = 0.;
        for (final double[] c : set) {
            u += c[0];
        }
        return u / set.length;
    }

    /**
     * <div class="fr">Variance d'un ensemble de valeurs</div>
     * @param set <span class="fr">ensemble de valeurs</span>
     * @return <span class="fr">variance des valeurs</span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    @Page(92)
    public static double variance1D(final double[] set) {
        final double u = mean1D(set);

        double s2 = 0.;
        for (final double c : set) {
            s2 += Math.pow(c - u, 2);
        }
        return s2 / set.length;
    }


    /**
     * <div class="fr">Variance d'un ensemble de points de dimension 1.</div>
     * @param set <span class="fr">ensemble de points de dimension 1</span>
     * @return <span class="fr">variance des valeurs</span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    @Page(92)
    public static double variance1D(final double[][] set) {
        final double u = mean1D(set);

        double s2 = 0.;
        for (final double[] c : set) {
            s2 += Math.pow(c[0] - u, 2);
        }
        return s2 / set.length;
    }

    /**
     * <div class="fr">Matrice de covariance d'un ensemble de points.</div>
     * @param set <span class="fr">ensemble de points</span>
     * @return <span class="fr">matrice de covariance des points</span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    @Page(93)
    public static double[][] covarianceMatrix(final double[][] set) {
        final double oon = 1. / set.length;
        final int dim = set[0].length;
        double[] c = fill(0., dim);

        for (final double[] pt1 : set) {
            c = DoubleTabulars.add(c, pt1);
        }
        c = DoubleTabulars.external(oon, c);

        final double[][] cov = new double[dim][dim];

        for (final double[] pt1 : set) {
            final double[] p = DoubleTabulars.minus(pt1, c);
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < i; j++) {
                    cov[j][i] = p[i] * p[j] * oon;
                    cov[i][j] = cov[j][i];
                }
                cov[i][i] = p[i] * p[i] * oon;
            }
        }
        return cov;
    }

    // Donne le cosinus et le sinus de l'angle a[p][q] ???
    public static double[] symSchur2(final double[][] a, final int p, final int q) {
        if (Math.abs(a[p][q]) > 0.0001) {
            final double r = (a[q][q] - a[p][p]) / (2. * a[p][q]);
            final double t;
            if (r >= 0.) {
                t = 1. / (r + Math.sqrt(1. + r * r));
            } else {
                t = -1. / (-r + Math.sqrt(1. + r * r));
            }
            final double c = 1. / Math.sqrt(1. + t * t);
            return new double[]{c, t * c};
        } else {
            return new double[]{1., 0.};
        }
    }

    public static double[][] jacobi(final double[][] aa) {
        double[][] a = aa;
        final int dim = aa.length;
        int j, k, i, p, q;
        double prevoff = 0;
        final double[][] jj = new double[dim][dim];

        double[][] v = identity(dim);

        final int max = 50;

        for (i = 0; i < max; i++) {
            p = 0; q = 1;
            for (j = 0; j < dim; j++) {
                for (k = 0; k < dim; k++) {
                    if (j == k) {
                        continue;
                    }
                    if (Math.abs(a[j][k]) > Math.abs(a[p][q])) {
                        p = j;
                        q = k;
                    }
                }
            }

            final double[] symSchur2 = symSchur2(a, p, q);
            final double c = symSchur2[0];
            final double s = symSchur2[1];
            for (j = 0; j < dim; j++) {
                for (k = 0; k < j; k++) {
                    jj[k][j] = 0.;
                    jj[j][k] = jj[k][j];
                }
                jj[j][j] = 1.;
            }
            jj[q][q] = c;
            jj[p][p] = jj[q][q];
            jj[p][q] = s;
            jj[q][p] = -s;

            v = mult(v, jj);

            a = mult(mult(transpose(jj), a), jj);

            double off = 0.;
            for (j = 0; j < dim; j++) {
                for (k = 0; k < dim; k++) {
                    if (j == k) {
                        continue;
                    }
                    off += a[j][k] * a[j][k];
                }
            }

            if (i > 2 && off >= prevoff) {
                break;
            }

            prevoff = off;
        }
        return v;
    }
}

