package com.cosmoloj.math.tabular.core;

import com.cosmoloj.math.tabular.line.BooleanVector;
import com.cosmoloj.math.tabular.matrix.BooleanMatrix;
import com.cosmoloj.math.tabular.matrix.Matrix.Dimension;
import java.util.Arrays;
import java.util.function.Supplier;

/**
 *
 * @author Samuel Andrés
 */
public final class BooleanTabulars {

    private BooleanTabulars() {
    }

    public static boolean equal(final boolean[] a, final boolean[] b) {
        return Arrays.equals(a, b);
    }

    public static boolean equal(final BooleanVector a, final BooleanVector b) {
        return equal(a.getMatrix(), b.getMatrix());
    }

    public static boolean equal(final boolean[][] a, final boolean[][] b) {
        return Arrays.deepEquals(a, b);
    }

    public static boolean equal(final BooleanMatrix a, final BooleanMatrix b) {
        return equal(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    public static boolean[][] external(final boolean l, final boolean[][] m) {
        final boolean[][] s = new boolean[m.length][m[0].length];
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++) {
                s[i][j] = l && m[i][j];
            }
        }
        return s;
    }

    public static boolean[][] external(final boolean l, final BooleanMatrix m) {
        return external(l, m.getMatrix());
    }

    //************************************************************************ ==

    public static boolean[] external(final boolean l, final boolean[] v) {
        final boolean[] s = new boolean[v.length];
        for (int i = 0; i < s.length; i++) {
            s[i] = l && v[i];
        }
        return s;
    }

    public static boolean[] external(final boolean l, final BooleanVector v) {
        return external(l, v.getMatrix());
    }

    //************************************************************************ ==

    public static boolean[][] add(final boolean[][] a, final boolean[][] b) {
        final boolean[][] s = new boolean[a.length][a[0].length];
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++) {
                s[i][j] = a[i][j] || b[i][j];
            }
        }
        return s;
    }

    public static boolean[][] add(final BooleanMatrix a, final BooleanMatrix b) {
        return add(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    public static boolean[] add(final boolean[] a, final boolean[] b) {
        final boolean[] s = new boolean[a.length];
        for (int i = 0; i < s.length; i++) {
            s[i] = a[i] || b[i];
        }
        return s;
    }

    public static boolean[] add(final BooleanVector a, final BooleanVector b) {
        return add(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    public static boolean[][] copy(final boolean[][] m) {
        final boolean[][] t = new boolean[m.length][m[0].length];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                t[i][j] = m[i][j];
            }
        }
        return t;
    }

    public static boolean[][] copy(final BooleanMatrix m) {
        return copy(m.getMatrix());
    }

    //************************************************************************ ==

    public static boolean[] copy(final boolean[] v) {
        final boolean[] t = new boolean[v.length];
        System.arraycopy(v, 0, t, 0, v.length);
        return t;
    }

    public static boolean[] copy(final BooleanVector v) {
        return copy(v.getMatrix());
    }

    //************************************************************************ ==

    public static boolean[][] transpose(final boolean[][] m) {
        final boolean[][] t = new boolean[m[0].length][m.length];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[0].length; j++) {
                t[i][j] = m[j][i];
            }
        }
        return t;
    }

    public static boolean[][] transpose(final BooleanMatrix m) {
        return transpose(m.getMatrix());
    }

    //************************************************************************ ==

    public static boolean[] transpose(final boolean[] v) {
        return copy(v);
    }

    public static boolean[] transpose(final BooleanVector v) {
        return transpose(v.getMatrix());
    }

    //************************************************************************ ==

    public static boolean[][] mult(final boolean[][] a, final boolean[][] b) {

        // mA doit avoir exactement autant de colonnes que mB a de lignes.
        final int cDim = a[0].length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }

        final boolean[][] p = new boolean[a.length][b[0].length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                boolean s = false;
                for (int k = 0; k < cDim; k++) {
                    s |= a[i][k] && b[k][j];
                }
                p[i][j] = s;
            }
        }

        return p;
    }

    public static boolean[][] mult(final BooleanMatrix mA, final BooleanMatrix mB) {

        return mult(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static boolean[] mult(final boolean[] a, final boolean[][] b) {
        final int cDim = a.length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }
        final boolean[] p = new boolean[b[0].length];

        for (int i = 0; i < p.length; i++) {
            boolean s = false;
            for (int j = 0; j < cDim; j++) {
                s |= a[j] && b[j][i];
            }
            p[i] = s;
        }
        return p;
    }

    public static boolean[] mult(final BooleanVector mA, final BooleanMatrix mB) {
        return mult(mA.getMatrix(), mB.getMatrix());
    }

    public static BooleanVector mult(final BooleanVector mA, final BooleanMatrix mB, final BooleanVector mP) {
        mP.resetMatrix(mult(mA.getMatrix(), mB.getMatrix()));
        return mP;
    }

    public static BooleanVector mult(final BooleanVector mA, final BooleanMatrix mB, final Supplier<BooleanVector> s) {
        return mult(mA, mB, s.get());
    }

    //************************************************************************ ==

    public static boolean[] mult(final boolean[][] a, final boolean[] b) {
        final int cDim = a[0].length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }
        final boolean[] p = new boolean[a.length];

        for (int i = 0; i < p.length; i++) {
            boolean s = false;
            for (int j = 0; j < cDim; j++) {
                s |= a[i][j] && b[j];
            }
            p[i] = s;
        }
        return p;
    }

    public static boolean[] mult(final BooleanMatrix mA, final BooleanVector mB) {
        return mult(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static boolean mult_1nn1(final boolean[] a, final boolean[] b) {
        final int cDim = a.length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }
        boolean s = false;
        for (int i = 0; i < cDim; i++) {
            s |= a[i] && b[i];
        }
        return s;
    }

    public static boolean mult(final BooleanVector mA, final BooleanVector mB) {
        return mult_1nn1(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static boolean[][] mult_n11m(final boolean[] a, final boolean[] b) {
        final boolean[][] p = new boolean[a.length][b.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                p[i][j] = a[i] && b[j];
            }
        }
        return p;
    }

    public static boolean[][] multmn(final BooleanVector mA, final BooleanVector mB) {
        return mult_n11m(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static boolean value(final boolean[][] m, final int i, final int j) {
        return m[i][j];
    }

    public static boolean value(final BooleanMatrix m, final int i, final int j) {
        return value(m.getMatrix(), i, j);
    }

    //************************************************************************ ==

    public static boolean value(final boolean[] v, final int i) {
        return v[i];
    }

    public static boolean value(final BooleanVector v, final int i) {
        return value(v.getMatrix(), i);
    }

    //************************************************************************ ==

    public static boolean[] line(final boolean[][] m, final int i) {
        final boolean[] r = new boolean[m[0].length];
        for (int j = 0; j < r.length; j++) {
            r[j] = m[i][j];
        }
        return r;
    }

    public static boolean[] line(final BooleanMatrix m, final int i) {
        return line(m.getMatrix(), i);
    }

    public static BooleanVector line(final BooleanMatrix m, final int i, final BooleanVector r) {
        r.resetMatrix(line(m.getMatrix(), i));
        return r;
    }

    public static BooleanVector line(final BooleanMatrix m, final int i, final Supplier<BooleanVector> s) {
        return line(m, i, s.get());
    }

    //************************************************************************ ==

    public static boolean[] column(final boolean[][] m, final int j) {
        final boolean[] r = new boolean[m.length];
        for (int i = 0; i < r.length; i++) {
            r[i] = m[i][j];
        }
        return r;
    }

    public static boolean[] column(final BooleanMatrix m, final int j) {
        return column(m.getMatrix(), j);
    }

    public static BooleanVector column(final BooleanMatrix m, final int j, final BooleanVector r) {
        r.resetMatrix(column(m.getMatrix(), j));
        return r;
    }

    public static BooleanVector column(final BooleanMatrix m, final int i, final Supplier<BooleanVector> s) {
        return column(m, i, s.get());
    }

    //************************************************************************ ==

    public static boolean[] vector(final boolean[][] m, final int i, final Dimension d) {
        if (d == Dimension.ROW) {
            return line(m, i);
        } else if (d == Dimension.COLUMN) {
            return column(m, i);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static boolean[] vector(final BooleanMatrix m, final int i, final Dimension d) {
        return vector(m.getMatrix(), i, d);
    }

    public static BooleanVector vector(final BooleanMatrix m, final int i, final Dimension d, final BooleanVector r) {
        r.resetMatrix(vector(m.getMatrix(), i, d));
        return r;
    }

    public static BooleanVector vector(final BooleanMatrix m, final int i, final Dimension d,
            final Supplier<BooleanVector> s) {
        return vector(m, i, d, s.get());
    }

    //************************************************************************ ==

    public static boolean[][] matrix(final boolean[] v, final Dimension d) {
        final boolean[][] r;
        if (d == Dimension.ROW) {
            r = new boolean[1][v.length];
            for (int j = 0; j < v.length; j++) {
                r[0][j] = v[j];
            }
        } else if (d == Dimension.COLUMN) {
            r = new boolean[v.length][1];
            for (int i = 0; i < v.length; i++) {
                r[i][0] = v[i];
            }
        } else {
            throw new UnsupportedOperationException();
        }
        return r;
    }

    public static boolean[][] matrix(final BooleanVector v, final Dimension d) {
        return matrix(v.getMatrix(), d);
    }

    //************************************************************************ ==

    public static boolean[][] hadamard(final boolean[][] a, final boolean[][] b) {
        final boolean[][] r = new boolean[a.length][a[0].length];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                r[i][j] = a[i][j] && b[i][j];
            }
        }
        return r;
    }

    public static boolean[][] hadamard(final BooleanMatrix a, final BooleanMatrix b) {
        return hadamard(a.getMatrix(), b.getMatrix());

    }

    //************************************************************************ ==

    public static boolean[][] kronecker(final boolean[][] a, final boolean[][] b) {
        final int la = a.length;
        final int ca = a[0].length;
        final int lb = b.length;
        final int cb = b[0].length;
        final int l = a.length * b.length;
        final int c = a[0].length * b[0].length;
        final boolean[][] r = new boolean[l][c];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                r[i][j] = a[i / lb][j / cb] && b[i % la][j % ca];
            }
        }
        return r;
    }

    public static boolean[][] kronecker(final BooleanMatrix a, final BooleanMatrix b) {
        return kronecker(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    // ALGORITHMES DE CRÉATION DE TABLEAUX PARTICULIERS.

    //************************************************************************ ==

    public static boolean[][] diagonal(final boolean[] v) {
        final boolean[][] r = new boolean[v.length][v.length];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r.length; j++) {
                r[i][j] = (i == j) ? v[i] : false;
            }
        }
        return r;
    }

    public static boolean[][] diagonal(final BooleanVector v) {
        return diagonal(v.getMatrix());
    }

    //************************************************************************ ==

    public static boolean[][] diagonal(final boolean f, final int d) {
        return diagonal(f, d, d);
    }

    //************************************************************************ ==

    public static boolean[][] diagonal(final boolean d, final boolean f, final int l, final int c) {
        final boolean[][] r = new boolean[l][c];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                r[i][j] = (i == j) ? d : false;
            }
        }
        return r;
    }

    public static boolean[][] diagonal(final boolean d, final int l, final int c) {
        return diagonal(d, false, l, c);
    }

    //************************************************************************ ==

    public static boolean[][] fill(final boolean f, final int l, final int c) {
        final boolean[][] r = new boolean[l][c];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                r[i][j] = f;
            }
        }
        return r;
    }

    //************************************************************************ ==

    public static boolean[] fill(final boolean f, final int n) {
        final boolean[] r = new boolean[n];
        Arrays.fill(r, f);
        return r;
    }

    public static BooleanVector fill(final boolean f, final int n, final BooleanVector v) {
        v.resetMatrix(fill(f, n));
        return v;
    }

    public static BooleanVector fill(final boolean f, final int n, final Supplier<BooleanVector> s) {
        return fill(f, n, s.get());
    }

    //****************************************************************************************************************

    // LINEARISATION D'UN TABLEAU 2D EN TABLEAU 1D

    //****************************************************************************************************************

    /**
     *
     * @param square <span class="fr">matrice carrée</span>
     * @return <span class="fr">matrice ligne constituée de la concaténation des lignes de la matrice carrée</span>
     */
    public static boolean[] linearizeLine(final boolean[][] square) {
        final int width = square[0].length;
        final int height = square.length;
        final boolean[] line = new boolean[height * width];
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
    public static boolean[] linearizeColumn(final boolean[][] square) {
        final int width = square[0].length;
        final int height = square.length;
        final boolean[] line = new boolean[height * width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                line[i + j * width] = square[i][j];
            }
        }
        return line;
    }
}
