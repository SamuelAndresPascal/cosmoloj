package com.cosmoloj.math.tabular.core;

import com.cosmoloj.math.tabular.line.IntVector;
import com.cosmoloj.math.tabular.matrix.IntMatrix;
import com.cosmoloj.math.tabular.matrix.Matrix.Dimension;
import java.util.Arrays;

/**
 *
 * @author Samuel Andrés
 */
public final class IntTabulars {

    private IntTabulars() {
    }

    public static boolean equal(final int[] a, final int[] b) {
        return Arrays.equals(a, b);
    }

    public static boolean equal(final IntVector a, final IntVector b) {
        return equal(a.getMatrix(), b.getMatrix());
    }

    public static boolean equal(final int[][] a, final int[][] b) {
        return Arrays.deepEquals(a, b);
    }

    public static boolean equal(final IntMatrix a, final IntMatrix b) {
        return equal(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    public static int[][] external(final int l, final int[][] m) {
        final int[][] s = new int[m.length][m[0].length];
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++) {
                s[i][j] = l * m[i][j];
            }
        }
        return s;
    }

    public static int[][] external(final int l, final IntMatrix m) {
        return external(l, m.getMatrix());
    }

    //************************************************************************ ==

    public static int[] external(final int l, final int[] v) {
        final int[] s = new int[v.length];
        for (int i = 0; i < s.length; i++) {
            s[i] = l * v[i];
        }
        return s;
    }

    public static int[] external(final int l, final IntVector v) {
        return external(l, v.getMatrix());
    }

    //************************************************************************ ==

    public static int[][] add(final int[][] a, final int[][] b) {
        final int[][] s = new int[a.length][a[0].length];
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++) {
                s[i][j] = a[i][j] + b[i][j];
            }
        }
        return s;
    }

    public static int[][] add(final IntMatrix a, final IntMatrix b) {
        return add(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    public static int[][] minus(final int[][] a, final int[][] b) {
        return add(a, external(-1, b));
    }

    public static int[][] minus(final IntMatrix a, final IntMatrix b) {
        return minus(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    public static int[] add(final int[] a, final int[] b) {
        final int[] s = new int[a.length];
        for (int i = 0; i < s.length; i++) {
            s[i] = a[i] + b[i];
        }
        return s;
    }

    public static int[] add(final IntVector a, final IntVector b) {
        return add(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    public static int[] minus(final int[] a, final int[] b) {
        return add(a, external(-1, b));
    }

    public static int[] minus(final IntVector a, final IntVector b) {
        return minus(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    public static int[][] copy(final int[][] m) {
        final int[][] t = new int[m.length][m[0].length];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                t[i][j] = m[i][j];
            }
        }
        return t;
    }

    public static int[][] copy(final IntMatrix m) {
        return copy(m.getMatrix());
    }

    //************************************************************************ ==

    public static int[] copy(final int[] v) {
        final int[] t = new int[v.length];
        System.arraycopy(v, 0, t, 0, v.length);
        return t;
    }

    public static int[] copy(final IntVector v) {
        return copy(v.getMatrix());
    }

    //************************************************************************ ==

    public static int[][] transpose(final int[][] m) {
        final int[][] t = new int[m[0].length][m.length];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[0].length; j++) {
                t[i][j] = m[j][i];
            }
        }
        return t;
    }

    public static int[][] transpose(final IntMatrix m) {
        return transpose(m.getMatrix());
    }

    //************************************************************************ ==

    public static int[] transpose(final int[] v) {
        return copy(v);
    }

    public static int[] transpose(final IntVector v) {
        return transpose(v.getMatrix());
    }

    //************************************************************************ ==

    public static int[][] mult(final int[][] a, final int[][] b) {

        // mA doit avoir exactement autant de colonnes que mB a de lignes.
        final int cDim = a[0].length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }

        final int[][] p = new int[a.length][b[0].length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                int s = 0;
                for (int k = 0; k < cDim; k++) {
                    s += a[i][k] * b[k][j];
                }
                p[i][j] = s;
            }
        }

        return p;
    }

    public static int[][] mult(final IntMatrix mA, final IntMatrix mB) {

        return mult(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static int[] mult(final int[] a, final int[][] b) {
        final int cDim = a.length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }
        final int[] p = new int[b[0].length];

        for (int i = 0; i < p.length; i++) {
            int s = 0;
            for (int j = 0; j < cDim; j++) {
                s += a[j] * b[j][i];
            }
            p[i] = s;
        }
        return p;
    }

    public static int[] mult(final IntVector mA, final IntMatrix mB) {
        return mult(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static int[] mult(final int[][] a, final int[] b) {
        final int cDim = a[0].length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }
        final int[] p = new int[a.length];

        for (int i = 0; i < p.length; i++) {
            int s = 0;
            for (int j = 0; j < cDim; j++) {
                s += a[i][j] * b[j];
            }
            p[i] = s;
        }
        return p;
    }

    public static int[] mult(final IntMatrix mA, final IntVector mB) {
        return mult(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static int mult_1nn1(final int[] a, final int[] b) {
        final int cDim = a.length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }
        int s = 0;
        for (int i = 0; i < cDim; i++) {
            s += a[i] * b[i];
        }
        return s;
    }

    public static int mult(final IntVector mA, final IntVector mB) {
        return mult_1nn1(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static int[][] mult_n11m(final int[] a, final int[] b) {
        final int[][] p = new int[a.length][b.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                p[i][j] = a[i] * b[j];
            }
        }
        return p;
    }

    public static int[][] multmn(final IntVector mA, final IntVector mB) {
        return mult_n11m(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static int value(final int[][] m, final int i, final int j) {
        return m[i][j];
    }

    public static int value(final IntMatrix m, final int i, final int j) {
        return value(m.getMatrix(), i, j);
    }

    //************************************************************************ ==

    public static int value(final int[] v, final int i) {
        return v[i];
    }

    public static int value(final IntVector v, final int i) {
        return value(v.getMatrix(), i);
    }

    //************************************************************************ ==

    public static int[] line(final int[][] m, final int i) {
        final int[] r = new int[m[0].length];
        for (int j = 0; j < r.length; j++) {
            r[j] = m[i][j];
        }
        return r;
    }

    public static int[] line(final IntMatrix m, final int i) {
        return line(m.getMatrix(), i);
    }

    //************************************************************************ ==

    public static int[] column(final int[][] m, final int j) {
        final int[] r = new int[m.length];
        for (int i = 0; i < r.length; i++) {
            r[i] = m[i][j];
        }
        return r;
    }

    public static int[] column(final IntMatrix m, final int j) {
        return column(m.getMatrix(), j);
    }

    //************************************************************************ ==

    public static int[] vector(final int[][] m, final int i, final Dimension d) {
        if (d == Dimension.ROW) {
            return line(m, i);
        } else if (d == Dimension.COLUMN) {
            return column(m, i);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static int[] vector(final IntMatrix m, final int i, final Dimension d) {
        return vector(m.getMatrix(), i, d);
    }

    //************************************************************************ ==

    public static int[][] matrix(final int[] v, final Dimension d) {
        final int[][] r;
        if (d == Dimension.ROW) {
            r = new int[1][v.length];
            for (int j = 0; j < v.length; j++) {
                r[0][j] = v[j];
            }
        } else if (d == Dimension.COLUMN) {
            r = new int[v.length][1];
            for (int i = 0; i < v.length; i++) {
                r[i][0] = v[i];
            }
        } else {
            throw new UnsupportedOperationException();
        }
        return r;
    }

    public static int[][] matrix(final IntVector v, final Dimension d) {
        return matrix(v.getMatrix(), d);
    }

    //************************************************************************ ==

    public static int[][] hadamard(final int[][] a, final int[][] b) {
        final int[][] r = new int[a.length][a[0].length];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                r[i][j] = a[i][j] * b[i][j];
            }
        }
        return r;
    }

    public static int[][] hadamard(final IntMatrix a, final IntMatrix b) {
        return hadamard(a.getMatrix(), b.getMatrix());

    }

    //************************************************************************ ==

    public static int[][] kronecker(final int[][] a, final int[][] b) {
        final int la = a.length;
        final int ca = a[0].length;
        final int lb = b.length;
        final int cb = b[0].length;
        final int l = a.length * b.length;
        final int c = a[0].length * b[0].length;
        final int[][] r = new int[l][c];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                r[i][j] = a[i / lb][j / cb] * b[i % la][j % ca];
            }
        }
        return r;
    }

    public static int[][] kronecker(final IntMatrix a, final IntMatrix b) {
        return kronecker(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    // ALGORITHMES DE CRÉATION DE TABLEAUX PARTICULIERS.

    //************************************************************************ ==

    public static int[][] diagonal(final int[] v) {
        final int[][] r = new int[v.length][v.length];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r.length; j++) {
                r[i][j] = (i == j) ? v[i] : 0;
            }
        }
        return r;
    }

    public static int[][] diagonal(final IntVector v) {
        return diagonal(v.getMatrix());
    }

    //************************************************************************ ==

    public static int[][] diagonal(final int f, final int d) {
        return diagonal(f, d, d);
    }

    //************************************************************************ ==

    public static int[][] diagonal(final int d, final int f, final int l, final int c) {
        final int[][] r = new int[l][c];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                r[i][j] = (i == j) ? d : f;
            }
        }
        return r;
    }

    public static int[][] diagonal(final int f, final int l, final int c) {
        return diagonal(f, 0, l, c);
    }

    //************************************************************************ ==

    public static int[][] fill(final int f, final int l, final int c) {
        final int[][] r = new int[l][c];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                r[i][j] = f;
            }
        }
        return r;
    }

    //************************************************************************ ==

    public static int[] fill(final int f, final int n) {
        final int[] r = new int[n];
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
    public static int[] linearizeLine(final int[][] square) {
        final int width = square[0].length;
        final int height = square.length;
        final int[] line = new int[height * width];
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
    public static int[] linearizeColumn(final int[][] square) {
        final int width = square[0].length;
        final int height = square.length;
        final int[] line = new int[height * width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                line[i + j * width] = square[i][j];
            }
        }
        return line;
    }
}
