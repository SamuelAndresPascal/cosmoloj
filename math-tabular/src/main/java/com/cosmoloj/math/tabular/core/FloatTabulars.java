package com.cosmoloj.math.tabular.core;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.tabular.line.FloatVector;
import com.cosmoloj.math.tabular.matrix.FloatMatrix;
import com.cosmoloj.math.tabular.matrix.Matrix.Dimension;
import com.cosmoloj.util.bib.Page;
import com.cosmoloj.util.bib.Cite;
import java.util.Arrays;

/**
 *
 * @author Samuel Andrés
 */
public final class FloatTabulars {

    @FunctionalInterface
    public interface IntToFloatFunction {
        float applyAsFloat(int value);
    }

    private FloatTabulars() {
    }

    public static boolean equal(final float[] a, final float[] b) {
        return Arrays.equals(a, b);
    }

    public static boolean equal(final FloatVector a, final FloatVector b) {
        return equal(a.getMatrix(), b.getMatrix());
    }

    public static boolean equal(final float[][] a, final float[][] b) {
        return Arrays.deepEquals(a, b);
    }

    public static boolean equal(final FloatMatrix a, final FloatMatrix b) {
        return equal(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    /**
     * <span class="fr">Produit externe d'une matrice par un scalaire.</span>
     * @param l <span class="fr">scalaire</span>
     * @param m <span class="fr">matrice</span>
     * @return <span class="fr">résultat de la multiplication de la matrice par le scalaire</span>
     */
    public static float[][] external(final float l, final float[][] m) {
        float[][] s = new float[m.length][m[0].length];
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
    public static float[][] external(final float l, final FloatMatrix m) {
        return external(l, m.getMatrix());
    }

    //************************************************************************ ==

    /**
     * <span class="fr">Produit externe d'un vecteur par un scalaire.</span>
     * @param l <span class="fr">scalaire</span>
     * @param v <span class="fr">vecteur</span>
     * @return <span class="fr">résultat de la multiplication du vecteur par le scalaire</span>
     */
    public static float[] external(final float l, final float[] v) {
        final float[] s = new float[v.length];
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
    public static float[] external(final float l, final FloatVector v) {
        return external(l, v.getMatrix());
    }

    //************************************************************************ ==

    public static float[][] add(final float[][] a, final float[][] b) {
        final float[][] s = new float[a.length][a[0].length];
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++) {
                s[i][j] = a[i][j] + b[i][j];
            }
        }
        return s;
    }

    public static float[][] add(final FloatMatrix a, final FloatMatrix b) {
        return add(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    public static float[][] minus(final float[][] a, final float[][] b) {
        return add(a, external(-1, b));
    }

    public static float[][] minus(final FloatMatrix a, final FloatMatrix b) {
        return minus(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    public static float[] add(final float[] a, final float[] b, final int length) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("both vectors are expected to have the same dimension");
        }
        if (length > a.length) {
            throw new IllegalArgumentException("");
        }
        final float[] s = new float[length];
        for (int i = 0; i < length; i++) {
            s[i] = a[i] + b[i];
        }
        return s;
    }

    public static float[] add(final float[] a, final float[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("both vectors are expected to have the same dimension");
        }
        final float[] s = new float[a.length];
        for (int i = 0; i < s.length; i++) {
            s[i] = a[i] + b[i];
        }
        return s;
    }

    public static float[] add(final FloatVector a, final FloatVector b) {
        return add(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    public static float[] minus(final float[] a, final float[] b, final int length) {
        return add(a, external(-1.f, b), length);
    }

    public static float[] minus(final float[] a, final float[] b) {
        return add(a, external(-1.f, b));
    }

    public static float[] minus(final FloatVector a, final FloatVector b) {
        return minus(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    public static float max(final float[] a) {
        float max = a[0];
        for (final float c : a) {
            if (c > max) {
                max = c;
            }
        }
        return max;
    }

    public static float max(final FloatVector a) {
        return max(a.getMatrix());
    }

    //************************************************************************ ==

    public static float min(final float[] a) {
        float min = a[0];
        for (final float c : a) {
            if (c < min) {
                min = c;
            }
        }
        return min;
    }

    public static float min(final FloatVector a) {
        return min(a.getMatrix());
    }

    //************************************************************************ ==

    public static float sum(final float[] a) {
        float sum = 0.f;
        for (final float c : a) {
                sum += c;
        }
        return sum;
    }

    public static float sum(final FloatVector a) {
        return sum(a.getMatrix());
    }

    //************************************************************************ ==

    public static float avg(final float[] a) {
        return sum(a) / a.length;
    }

    public static float avg(final FloatVector a) {
        return avg(a.getMatrix());
    }

    //************************************************************************ ==

    public static float[][] copy(final float[][] m) {
        final float[][] t = new float[m.length][];
        for (int i = 0; i < t.length; i++) {
            t[i] = new float[m[i].length];
            System.arraycopy(m[i], 0, t[i], 0, t[i].length);
        }
        return t;
    }

    public static float[][] copy(final FloatMatrix m) {
        return copy(m.getMatrix());
    }

    //************************************************************************ ==

    public static float[] copy(final float[] v) {
        final float[] t = new float[v.length];
        System.arraycopy(v, 0, t, 0, v.length);
        return t;
    }

    public static float[] copy(final FloatVector v) {
        return copy(v.getMatrix());
    }

    //************************************************************************ ==

    public static float[][] transpose(final float[][] m) {
        final float[][] t = new float[m[0].length][m.length];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                t[i][j] = m[j][i];
            }
        }
        return t;
    }

    public static float[][] transpose(final FloatMatrix m) {
        return transpose(m.getMatrix());
    }

    //************************************************************************ ==

    public static float[] transpose(final float[] v) {
        return copy(v);
    }

    public static float[] transpose(final FloatVector v) {
        return transpose(v.getMatrix());
    }

    //************************************************************************ ==

    public static float[][] mult(final float[][] a, final float[][] b) {

        // mA doit avoir exactement autant de colonnes que mB a de lignes.
        final int cDim = a[0].length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }

        final float[][] p = new float[a.length][b[0].length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                float s = 0.f;
                for (int k = 0; k < cDim; k++) {
                    s += a[i][k] * b[k][j];
                }
                p[i][j] = s;
            }
        }

        return p;
    }

    public static float[][] mult(final FloatMatrix mA, final FloatMatrix mB) {

        return mult(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static float[] mult(final float[] a, final float[][] b) {
        final int cDim = a.length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }
        final float[] p = new float[b[0].length];

        for (int i = 0; i < p.length; i++) {
            float s = 0.f;
            for (int j = 0; j < cDim; j++) {
                s += a[j] * b[j][i];
            }
            p[i] = s;
        }
        return p;
    }

    public static float[] mult(final FloatVector mA, final FloatMatrix mB) {
        return mult(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static float[] mult(final float[][] a, final float[] b) {
        final int cDim = a[0].length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }
        final float[] p = new float[a.length];

        for (int i = 0; i < p.length; i++) {
            float s = 0.f;
            for (int j = 0; j < cDim; j++) {
                s += a[i][j] * b[j];
            }
            p[i] = s;
        }
        return p;
    }

    public static float[] mult(final FloatMatrix mA, final FloatVector mB) {
        return mult(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static float mult1nn1(final float[] a, final float[] b) {
        final int cDim = a.length;
        if (b.length != cDim) {
            throw new IllegalArgumentException("both vector are expected to have the same dimension");
        }
        float s = 0.f;
        for (int i = 0; i < cDim; i++) {
            s += a[i] * b[i];
        }
        return s;
    }

    public static float mult(final FloatVector mA, final FloatVector mB) {
        return mult1nn1(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static float[][] mult_n11m(final float[] a, final float[] b) {
        final float[][] p = new float[a.length][b.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                p[i][j] = a[i] * b[j];
            }
        }
        return p;
    }

    public static float[][] multmn(final FloatVector mA, final FloatVector mB) {
        return mult_n11m(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static float value(final float[][] m, final int i, final int j) {
        return m[i][j];
    }

    public static float value(final FloatMatrix m, final int i, final int j) {
        return value(m.getMatrix(), i, j);
    }

    //************************************************************************ ==

    public static float value(final float[] v, final int i) {
        return v[i];
    }

    public static float value(final FloatVector v, final int i) {
        return value(v.getMatrix(), i);
    }

    //************************************************************************ ==

    public static float[] line(final float[][] m, final int i) {
        final float[] r = new float[m[0].length];
        System.arraycopy(m[i], 0, r, 0, r.length);
        return r;
    }

    public static float[] line(final FloatMatrix m, final int i) {
        return line(m.getMatrix(), i);
    }

    //************************************************************************ ==

    public static float[] column(final float[][] m, final int j) {
        final float[] r = new float[m.length];
        for (int i = 0; i < r.length; i++) {
            r[i] = m[i][j];
        }
        return r;
    }

    public static float[] column(final FloatMatrix m, final int j) {
        return column(m.getMatrix(), j);
    }

    //************************************************************************ ==

    public static float[] vector(final float[][] m, final int i, final Dimension d) {
        if (d == Dimension.ROW) {
            return line(m, i);
        } else if (d == Dimension.COLUMN) {
            return column(m, i);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static float[] vector(final FloatMatrix m, final int i, final Dimension d) {
        return vector(m.getMatrix(), i, d);
    }

    //************************************************************************ ==

    public static float[][] matrix(final float[] v, final Dimension d) {
        final float[][] r;
        if (d == Dimension.ROW) {
            r = new float[1][v.length];
            System.arraycopy(v, 0, r[0], 0, v.length);
        } else if (d == Dimension.COLUMN) {
            r = new float[v.length][1];
            for (int i = 0; i < v.length; i++) {
                r[i][0] = v[i];
            }
        } else {
            throw new UnsupportedOperationException();
        }
        return r;
    }

    public static float[][] matrix(final FloatVector v, final Dimension d) {
        return matrix(v.getMatrix(), d);
    }

    //************************************************************************ ==

    public static float[][] hadamard(final float[][] a, final float[][] b) {
        final float[][] r = new float[a.length][a[0].length];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                r[i][j] = a[i][j] * b[i][j];
            }
        }
        return r;
    }

    public static float[][] hadamard(final FloatMatrix a, final FloatMatrix b) {
        return hadamard(a.getMatrix(), b.getMatrix());

    }

    //************************************************************************ ==

    public static float[][] kronecker(final float[][] a, final float[][] b) {
        final int la = a.length;
        final int ca = a[0].length;
        final int lb = b.length;
        final int cb = b[0].length;
        final int l = a.length * b.length;
        final int c = a[0].length * b[0].length;
        final float[][] r = new float[l][c];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                r[i][j] = a[i / lb][j / cb] * b[i % la][j % ca];
            }
        }
        return r;
    }

    public static float[][] kronecker(final FloatMatrix a, final FloatMatrix b) {
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
    public static float[][] diagonal(final float[] v) {
        final float[][] r = new float[v.length][v.length];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r.length; j++) {
                r[i][j] = (i == j) ? v[i] : 0;
            }
        }
        return r;
    }

    public static float[][] diagonal(final FloatVector v) {
        return diagonal(v.getMatrix());
    }

    //************************************************************************ ==

    public static float[][] identity(final int d) {
        return diagonal(1.f, d);
    }

    //************************************************************************ == =

    public static float[][] diagonal(final float f, final int d) {
        return diagonal(f, d, d);
    }

    //************************************************************************ ==

    public static float[][] diagonal(final float d, final float f, final int l, final int c) {
        final float[][] r = new float[l][c];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[0].length; j++) {
                r[i][j] = (i == j) ? d : f;
            }
        }
        return r;
    }

    public static float[][] diagonal(final float f, final int l, final int c) {
        return diagonal(f, 0.f, l, c);
    }

    //************************************************************************ ==

    public static float[][] fill(final float f, final int l, final int c) {
        final float[][] r = new float[l][c];
        for (final float[] r1 : r) {
            for (int j = 0; j < r1.length; j++) {
                r1[j] = f;
            }
        }
        return r;
    }

    public static float[][] fill(final Fill f, final int l, final int c) {
        final float[][] r = new float[l][c];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                r[i][j] = f.fill(i, j);
            }
        }
        return r;
    }

    @FunctionalInterface
    public interface Fill {
        float fill(int i, int j);
    }

    //************************************************************************ ==

    /**
     * <div class="fr">Produit un nouveau tableau de la longueur indiquée initialisé avec la valeur donnée.</div>
     * @param f <span class="fr">valeur des composantes du tableau</span>
     * @param n <span class="fr">longueur du tableau</span>
     * @return <span class="fr">un tableau de la longueur indiquée initialisé avec la valeur donnée</span>
     */
    public static float[] fill(final float f, final int n) {
        final float[] r = new float[n];
        Arrays.fill(r, f);
        return r;
    }

    public static float[] fill(final IntToFloatFunction f, final int n) {
        final float[] r = new float[n];
        for (int i = 0; i < n; i++) {
            r[i] = f.applyAsFloat(i);
        }
        return r;
    }

    //************************************************************************ ==

    public static float[][] rotation2D(final float theta) {
        final float cost = (float) Math.cos(theta);
        final float sint = (float) Math.sin(theta);
        return new float[][]{
            {cost, -sint},
            {sint, cost}};
    }

    //************************************************************************ ==

    public static float[][] rotation3DX(final float theta) {
        final float cost = (float) Math.cos(theta);
        final float sint = (float) Math.sin(theta);
        return new float[][]{
            {1.f,   0.f,    0.f},
            {0.f,  cost,  -sint},
            {0.f,  sint,   cost}};
    }

    //************************************************************************ ==

    public static float[][] rotation3DY(final float theta) {
        final float cost = (float) Math.cos(theta);
        final float sint = (float) Math.sin(theta);
        return new float[][]{
            {cost,   0.f,  sint},
            {0.f,    1.f,   0.f},
            {-sint,  0.f,  cost}};
    }

    //************************************************************************ ==

    public static float[][] rotation3DZ(final float theta) {
        final float cost = (float) Math.cos(theta);
        final float sint = (float) Math.sin(theta);
        return new float[][]{
            {cost, -sint, 0.f},
            {sint,  cost, 0.f},
            {0.f,    0.f, 1.f}};
    }

    //************************************************************************ ==

    public static float[][] rotation3DXYZ(final float x, final float y, final float z) {
        final float sinx = (float) Math.sin(x);
        final float cosx = (float) Math.cos(x);
        final float siny = (float) Math.sin(y);
        final float cosy = (float) Math.cos(y);
        final float sinz = (float) Math.sin(z);
        final float cosz = (float) Math.cos(z);

        return new float[][]{
            {cosy * cosz,   -cosx * sinz + sinx * siny * cosz,   sinx * sinz + cosx * siny * cosz},
            {cosy * sinz,   cosx * cosz + sinx * siny * sinz,    -sinx * cosz + cosx * siny * sinz},
            {-siny,         sinx * cosy,                         cosx * cosy}
        };
    }

    //************************************************************************ ==

    public static float[][] rotation3DZYX(final float x, final float y, final float z) {
        final float sinz = (float) Math.sin(z);
        final float cosz = (float) Math.cos(z);
        final float siny = (float) Math.sin(y);
        final float cosy = (float) Math.cos(y);
        final float sinx = (float) Math.sin(x);
        final float cosx = (float) Math.cos(x);

        return new float[][]{
            {cosy * cosz,   -cosy * sinz,   siny},
            {sinz * cosx + siny * cosz * sinx,   cosz * cosx - siny * sinz * sinx, -cosy * sinx},
            {sinz * sinx - siny * cosz * cosx,   cosz * sinx + siny * sinz * cosx, cosy * cosx}
        };
    }

    //************************************************************************ ==

    public static float[][] rotation3DXYZApprox(final float x, final float y, final float z) {

        return new float[][]{
            {1.f,  -z,   y},
            {z,   1.f,  -x},
            {-y,    x, 1.f}
        };
    }

    //************************************************************************ ==

    public static float[][] rotation3DZYXApprox(final float x, final float y, final float z) {
        return rotation3DXYZApprox(x, y, z);
    }

    // MATRICES JACOBIENNES
    //--------------------------------------------------------------------------

    public static float[][] jacobianCartesian2D() {
        return diagonal(1.f, 2);
    }

    public static float[][] jacobianCartesian3D() {
        return diagonal(1.f, 3);
    }

    public static float[][] jacobianPolar(final float r, final float theta) {
        final float cost = (float) Math.cos(theta);
        final float sint = (float) Math.sin(theta);
        return new float[][]{
            {cost, -r * sint},
            {sint, r * cost}};
    }

    public static float[][] jacobianCylindrical(final float r, final float theta) {
        return new float[][]{
            {1.f, 0.f, 0.f},
            {1.f, 0.f, 0.f},
            {0.f, 0.f, r}};
    }

    public static float[][] jacobianSpherical(final float r, final float theta, final float phi) {
        final float cost = (float) Math.cos(theta);
        final float sint = (float) Math.sin(theta);
        final float cosf = (float) Math.cos(phi);
        final float sinf = (float) Math.sin(phi);
        return new float[][]{
            {sint * cosf, r * cost * cosf, -r * sint * sinf},
            {sint * sinf, r * cost * sinf,  r * sint * cosf},
            {cost,              -r * sint,             0.f}};
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
    public static float determinant11(final float[][] m) {
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
    public static float determinant11(final FloatMatrix m) {
        return determinant11(m.getMatrix());
    }

    /**
     * <span class="en">Compute the 2x2 matrix determinant.</span>
     * @param m <span class="en">2x2 matrix</span>
     * @return <span class="en">the matrix determinant</span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    @Page(27)
    public static float determinant22(final float[][] m) {
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
    public static float determinant22(final FloatMatrix m) {
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
    public static float[][] subMatrix(final float[][]m, final int i, final int j) {
        if (m == null || m.length != m[0].length) {
            throw new IllegalArgumentException("square matrix expected");
        }
        if (!(i < m.length && j < m.length)) {
            throw new IllegalArgumentException("indices must be lower than matrix dimension");
        }

        final float[][] c = new float[m.length - 1][m.length - 1];
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
    public static float[][] subMatrix(final FloatMatrix m, final int i, final int j) {
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
    public static float cofactor(final float[][]m, final int i, final int j, final Dimension d) {
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
    public static float cofactor(final float[][]m, final int i, final int j) {
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
    public static float determinantNN(final float[][] m, final Dimension d) {
        if (m == null || m.length != m[0].length) {
            throw new IllegalArgumentException("square matrix expected");
        }
        if (d == null) {
            throw new IllegalArgumentException("matrix dimension expected");
        }

        if (m.length == 2) {
            return determinant22(m);
        }

        float det = 0.f;
        switch (d) {
            case ROW:
                final int row = 0;
                for (int j = 0; j < m.length; j++) {
                    det += m[row][j] * cofactor(m, row, j, d);
                }   break;
            case COLUMN:
                final int col = 0;
                for (int i = 0; i < m.length; i++) {
                    det += m[i][col] * cofactor(m, i, col, d);
                }   break;
            default:
                throw new IllegalArgumentException();
        }
        return det;
    }

    /**
     * <span class="en">Compute the NxN matrix determinant using the expansion by cofactors method and the default
     * determinant dimension.</span>
     * @param m <span class="en">NxN matrix</span>
     * @return <span class="en">the matrix determinant</span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    @Page(29)
    public static float determinantNN(final float[][] m) {
        return determinantNN(m, DEFAULT_DETERMINANT_DIMENSION);
    }

    /**
     * <span class="en">Compute the NxN matrix determinant.</span>
     * @param m <span class="en">NxN matrix</span>
     * @param d <span class="en">the dimension used to compute the determinant</span>
     * @return <span class="en">the matrix determinant</span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    public static float determinantNN(final FloatMatrix m, final Dimension d) {
        return determinantNN(m.getMatrix(), d);
    }

// MATRICES INVERSES

//--------------------------------------------------------------------------
    public static float determinantNN(final FloatMatrix m) {
        return determinantNN(m, DEFAULT_DETERMINANT_DIMENSION);
    }


    public static float[][] adjointMatrix22(final float[][] m) {
        return new float[][]{{m[1][1], -m[0][1]},
                              {-m[1][0], m[0][0]}};
    }

    public static float[][] inverse22(final float[][] m) {
        return external(1.f / determinant22(m), adjointMatrix22(m));
    }

    public static float[][] adjointMatrix33(final float[][] m) {
        return new float[][]{
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

    public static float[][] inverse33(final float[][] m, final Dimension d) {
        final float det = determinantNN(m, d);
        if (det != 0.) {
            final float invDet = 1.f / det;
            if (Float.isFinite(invDet)) {
                return external(invDet, adjointMatrix33(m));
            }
        }
        throw new IllegalArgumentException("matrix not inversible");
    }

    public static float[][] invserse33(final float[][] m) {
        return inverse33(m, DEFAULT_DETERMINANT_DIMENSION);
    }

    // NORMES
    //--------------------------------------------------------------------------

    public static float norm(final float[] v) {
        return (float) Math.sqrt(mult1nn1(v, v));
    }

    public static float[] normalize(final float[] v) {
        return external(1.f / norm(v), v);
    }

    // MATRICES DE TENSEURS MÉTRIQUES
    //--------------------------------------------------------------------------

    public static float[][] metricTensorCartesian2D() {
        return diagonal(1.f, 2);
    }

    public static float[][] metricTensorCartesian3D() {
        return diagonal(1.f, 3);
    }

//    public static float[][] metricTensorPolar(final float r, final float theta) {
//        final float cost = Math.cos(theta);
//        final float sint = Math.sin(theta);
//        return new float[][]{
//            {1., 0.},
//            {0., r*r}};
//    }
//
//    public static float[][] metricTensorCylindrical(final float r, final float theta) {
//        return new float[][]{
//            {1., 0., 0.},
//            {0., 1., 0.},
//            {0., 0., r*r}};
//    }
//
//    public static float[][] metricTensorSpherical(final float r, final float theta, final float phi) {
//        final float cost = Math.cos(theta);
//        final float sint = Math.sin(theta);
//        final float cosf = Math.cos(phi);
//        final float sinf = Math.sin(phi);
//        return new float[][]{
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
    public static float[] crossProduct(final float[] u, final float[] v) {
        return new float[]{u[1] * v[2] - u[2] * v[1],
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
    public static float scalarTripleProduct(final float[] u, final float[] v, final float[] w) {
        return mult1nn1(crossProduct(u, v), w);
    }

    /**
     * <div class="fr">Moyenne d'un ensemble de valeurs.</div>
     * @param set <span class="fr">ensemble de valeurs</span>
     * @return <span class="fr">moyenne des valeurs</span>
     */
    @Cite(Cosmoloj.REAL_TIME_COLLISION_DETECTION)
    @Page(92)
    public static float mean1D(final float[] set) {
        float u = 0.f;
        for (final float c : set) {
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
    public static float mean1D(final float[][] set) {
        float u = 0.f;
        for (final float[] c : set) {
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
    public static float variance1D(final float[] set) {
        final float u = mean1D(set);

        float s2 = 0.f;
        for (final float c : set) {
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
    public static float variance1D(final float[][] set) {
        final float u = mean1D(set);

        float s2 = 0.f;
        for (final float[] c : set) {
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
    public static float[][] covarianceMatrix(final float[][] set) {
        final float oon = 1.f / set.length;
        final int dim = set[0].length;
        float[] c = fill(0.f, dim);

        for (final float[] pt1 : set) {
            c = FloatTabulars.add(c, pt1);
        }
        c = FloatTabulars.external(oon, c);

        final float[][] cov = new float[dim][dim];

        for (final float[] pt1 : set) {
            final float[] p = FloatTabulars.minus(pt1, c);
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
    public static float[] symSchur2(final float[][] a, final int p, final int q) {
        if (Math.abs(a[p][q]) > 0.0001) {
            final float r = (a[q][q] - a[p][p]) / (2.f * a[p][q]);
            final float t;
            if (r >= 0.) {
                t = 1.f / (r + (float) Math.sqrt(1. + r * r));
            } else {
                t = -1.f / (-r + (float) Math.sqrt(1. + r * r));
            }
            final float c = 1.f / (float) Math.sqrt(1. + t * t);
            return new float[]{c, t * c};
        } else {
            return new float[]{1.f, 0.f};
        }
    }

    public static float[][] jacobi(final float[][] aa) {
        float[][] a = aa;
        final int dim = aa.length;
        int j, k, i, p, q;
        float prevoff = 0;
        final float[][] jj = new float[dim][dim];

        float[][] v = identity(dim);

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

            final float[] symSchur2 = symSchur2(a, p, q);
            final float c = symSchur2[0];
            final float s = symSchur2[1];
            for (j = 0; j < dim; j++) {
                for (k = 0; k < j; k++) {
                    jj[k][j] = 0.f;
                    jj[j][k] = jj[k][j];
                }
                jj[j][j] = 1.f;
            }
            jj[q][q] = c;
            jj[p][p] = jj[q][q];
            jj[p][q] = s;
            jj[q][p] = -s;

            v = mult(v, jj);

            a = mult(mult(transpose(jj), a), jj);

            float off = 0.f;
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

