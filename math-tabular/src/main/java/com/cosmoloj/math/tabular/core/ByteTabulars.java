package com.cosmoloj.math.tabular.core;

import com.cosmoloj.math.tabular.line.ByteVector;
import com.cosmoloj.math.tabular.matrix.ByteMatrix;
import com.cosmoloj.math.tabular.matrix.Matrix.Dimension;
import java.util.Arrays;
import java.util.function.Supplier;

/**
 *
 * @author Samuel Andrés
 */
public final class ByteTabulars {

    private ByteTabulars() {
    }

    public static byte[][] external(final byte l, final byte[][] m) {
        final byte[][] s = new byte[m.length][m[0].length];
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++) {
                s[i][j] = (byte) (l & m[i][j]);
            }
        }
        return s;
    }

    public static byte[][] external(final byte l, final ByteMatrix m) {
        return external(l, m.getMatrix());
    }

    //************************************************************************ ==

    public static byte[] external(final byte l, final byte[] v) {
        final byte[] s = new byte[v.length];
        for (int i = 0; i < s.length; i++) {
            s[i] = (byte) (l & v[i]);
        }
        return s;
    }

    public static byte[] external(final byte l, final ByteVector v) {
        return external(l, v.getMatrix());
    }

    //************************************************************************ ==

    public static byte[][] add(final byte[][] a, final byte[][] b) {
        final byte[][] s = new byte[a.length][a[0].length];
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++) {
                s[i][j] = (byte) (a[i][j] | b[i][j]);
            }
        }
        return s;
    }

    public static byte[][] add(final ByteMatrix a, final ByteMatrix b) {
        return add(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    public static byte[] add(final byte[] a, final byte[] b) {
        final byte[] s = new byte[a.length];
        for (int i = 0; i < s.length; i++) {
            s[i] = (byte) (a[i] | b[i]);
        }
        return s;
    }

    public static byte[] add(final ByteVector a, final ByteVector b) {
        return add(a.getMatrix(), b.getMatrix());
    }

    public static ByteVector add(final ByteVector a, final ByteVector b, final ByteVector s) {
        s.resetMatrix(add(a.getMatrix(), b.getMatrix()));
        return s;
    }

    public static ByteVector add(final ByteVector a, final ByteVector b, final Supplier<ByteVector> s) {
        return add(a, b, s.get());
    }

    //************************************************************************ ==

    public static byte[][] copy(final byte[][] m) {
        final byte[][] t = new byte[m.length][m[0].length];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                t[i][j] = m[i][j];
            }
        }
        return t;
    }

    public static byte[][] copy(final ByteMatrix m) {
        return copy(m.getMatrix());
    }

    //************************************************************************ ==

    public static byte[] copy(final byte[] v) {
        final byte[] t = new byte[v.length];
        System.arraycopy(v, 0, t, 0, v.length);
        return t;
    }

    public static byte[] copy(final ByteVector v) {
        return copy(v.getMatrix());
    }

    public static ByteVector copy(final ByteVector v, final ByteVector t) {
        t.resetMatrix(copy(v.getMatrix()));
        return t;
    }

    public static ByteVector copy(final ByteVector v, final Supplier<ByteVector> s) {
        return copy(v, s.get());
    }

    //************************************************************************ ==

    public static byte[][] transpose(final byte[][] m) {
        final byte[][] t = new byte[m[0].length][m.length];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[0].length; j++) {
                t[i][j] = m[j][i];
            }
        }
        return t;
    }

    public static byte[][] transpose(final ByteMatrix m) {
        return transpose(m.getMatrix());
    }

    //************************************************************************ ==

    public static byte[] transpose(final byte[] v) {
        return copy(v);
    }

    public static byte[] transpose(final ByteVector v) {
        return transpose(v.getMatrix());
    }

    public static ByteVector transpose(final ByteVector v, final ByteVector t) {
        t.resetMatrix(transpose(v.getMatrix()));
        return t;
    }

    public static ByteVector transpose(final ByteVector v, final Supplier<ByteVector> s) {
        return transpose(v, s.get());
    }

    //************************************************************************ ==

    public static byte[][] mult(final byte[][] a, final byte[][] b) {

        // mA doit avoir exactement autant de colonnes que mB a de lignes.
        final int cDim = a[0].length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }

        final byte[][] p = new byte[a.length][b[0].length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                byte s = 0;
                for (int k = 0; k < cDim; k++) {
                    s |= (byte) (a[i][k] & b[k][j]);
                }
                p[i][j] = s;
            }
        }

        return p;
    }

    public static byte[][] mult(final ByteMatrix mA, final ByteMatrix mB) {

        return mult(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************

    public static byte[] mult(final byte[] a, final byte[][] b) {
        final int cDim = a.length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }
        final byte[] p = new byte[b[0].length];

        for (int i = 0; i < p.length; i++) {
            byte s = 0;
            for (int j = 0; j < cDim; j++) {
                s |= (byte) (a[j] & b[j][i]);
            }
            p[i] = s;
        }
        return p;
    }

    public static byte[] mult(final ByteVector mA, final ByteMatrix mB) {
        return mult(mA.getMatrix(), mB.getMatrix());
    }

    public static ByteVector mult(final ByteVector mA, final ByteMatrix mB, final ByteVector mP) {
        mP.resetMatrix(mult(mA.getMatrix(), mB.getMatrix()));
        return mP;
    }

    public static ByteVector mult(final ByteVector mA, final ByteMatrix mB, final Supplier<ByteVector> s) {
        return mult(mA, mB, s.get());
    }

    //************************************************************************ ==

    public static byte[] mult(final byte[][] a, final byte[] b) {
        final int cDim = a[0].length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }
        final byte[] p = new byte[a.length];

        for (int i = 0; i < p.length; i++) {
            byte s = 0;
            for (int j = 0; j < cDim; j++) {
                s |= (byte) (a[i][j] & b[j]);
            }
            p[i] = s;
        }
        return p;
    }

    public static byte[] mult(final ByteMatrix mA, final ByteVector mB) {
        return mult(mA.getMatrix(), mB.getMatrix());
    }

    public static ByteVector mult(final ByteMatrix mA, final ByteVector mB, final ByteVector mP) {
        mP.resetMatrix(mult(mA.getMatrix(), mB.getMatrix()));
        return mP;
    }

    public static ByteVector mult(final ByteMatrix mA, final ByteVector mB, final Supplier<ByteVector> s) {
        return mult(mA, mB, s.get());
    }

    //************************************************************************ ==

    public static byte mult_1nn1(final byte[] a, final byte[] b) {
        final int cDim = a.length;
        if (b.length != cDim) {
            throw new IllegalArgumentException();
        }
        byte s = 0;
        for (int i = 0; i < cDim; i++) {
            s |= (byte) (a[i] & b[i]);
        }
        return s;
    }

    public static byte mult(final ByteVector mA, final ByteVector mB) {
        return mult_1nn1(mA.getMatrix(), mB.getMatrix());
    }

    //************************************************************************ ==

    public static byte[][] mult_n11m(final byte[] a, final byte[] b) {
        final byte[][] p = new byte[a.length][b.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                p[i][j] = (byte) (a[i] & b[j]);
            }
        }
        return p;
    }

    //************************************************************************ ==

    public static byte value(final byte[][] m, final int i, final int j) {
        return m[i][j];
    }

    public static byte value(final ByteMatrix m, final int i, final int j) {
        return value(m.getMatrix(), i, j);
    }

    //************************************************************************ ==

    public static byte value(final byte[] v, final int i) {
        return v[i];
    }

    public static byte value(final ByteVector v, final int i) {
        return value(v.getMatrix(), i);
    }

    //************************************************************************ ==

    public static byte[] line(final byte[][] m, final int i) {
        final byte[] r = new byte[m[0].length];
        for (int j = 0; j < r.length; j++) {
            r[j] = m[i][j];
        }
        return r;
    }

    public static byte[] line(final ByteMatrix m, final int i) {
        return line(m.getMatrix(), i);
    }

    public static ByteVector line(final ByteMatrix m, final int i, final ByteVector r) {
        r.resetMatrix(line(m.getMatrix(), i));
        return r;
    }

    public static ByteVector line(final ByteMatrix m, final int i, final Supplier<ByteVector> s) {
        return line(m, i, s.get());
    }

    //************************************************************************ ==

    public static byte[] column(final byte[][] m, final int j) {
        final byte[] r = new byte[m.length];
        for (int i = 0; i < r.length; i++) {
            r[i] = m[i][j];
        }
        return r;
    }

    public static byte[] column(final ByteMatrix m, final int j) {
        return column(m.getMatrix(), j);
    }

    //************************************************************************ ==

    public static byte[] vector(final byte[][] m, final int i, final Dimension d) {
        if (d == Dimension.ROW) {
            return line(m, i);
        } else if (d == Dimension.COLUMN) {
            return column(m, i);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static byte[] vector(final ByteMatrix m, final int i, final Dimension d) {
        return vector(m.getMatrix(), i, d);
    }

    //************************************************************************ ==

    public static byte[][] matrix(final byte[] v, final Dimension d) {
        final byte[][] r;
        if (d == Dimension.ROW) {
            r = new byte[1][v.length];
            for (int j = 0; j < v.length; j++) {
                r[0][j] = v[j];
            }
        } else if (d == Dimension.COLUMN) {
            r = new byte[v.length][1];
            for (int i = 0; i < v.length; i++) {
                r[i][0] = v[i];
            }
        } else {
            throw new UnsupportedOperationException();
        }
        return r;
    }

    public static byte[][] matrix(final ByteVector v, final Dimension d) {
        return matrix(v.getMatrix(), d);
    }

    //************************************************************************ ==

    public static byte[][] hadamard(final byte[][] a, final byte[][] b) {
        final byte[][] r = new byte[a.length][a[0].length];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                r[i][j] = (byte) (a[i][j] & b[i][j]);
            }
        }
        return r;
    }

    public static byte[][] hadamard(final ByteMatrix a, final ByteMatrix b) {
        return hadamard(a.getMatrix(), b.getMatrix());

    }

    //************************************************************************ ==

    public static byte[][] kronecker(final byte[][] a, final byte[][] b) {
        final int la = a.length;
        final int ca = a[0].length;
        final int lb = b.length;
        final int cb = b[0].length;
        final int l = a.length * b.length;
        final int c = a[0].length * b[0].length;
        final byte[][] r = new byte[l][c];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                r[i][j] = (byte) (a[i / lb][j / cb] & b[i % la][j % ca]);
            }
        }
        return r;
    }

    public static byte[][] kronecker(final ByteMatrix a, final ByteMatrix b) {
        return kronecker(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    // ALGORITHMES DE CRÉATION DE TABLEAUX PARTICULIERS.

    //************************************************************************ ==

    public static byte[][] diagonal(final byte[] v) {
        final byte[][] r = new byte[v.length][v.length];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r.length; j++) {
                r[i][j] = (i == j) ? v[i] : 0;
            }
        }
        return r;
    }

    public static byte[][] diagonal(final ByteVector v) {
        return diagonal(v.getMatrix());
    }

    //************************************************************************ ==

    public static byte[][] diagonal(final byte f, final int d) {
        return diagonal(f, d, d);
    }

    //************************************************************************ ==

    public static byte[][] diagonal(final byte d, final byte f, final int l, final int c) {
        final byte[][] r = new byte[l][c];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                r[i][j] = (i == j) ? d : 0;
            }
        }
        return r;
    }

    public static byte[][] diagonal(final byte d, final int l, final int c) {
        return diagonal(d, (byte) 0, l, c);
    }

    //************************************************************************ ==

    public static byte[][] fill(final byte f, final int l, final int c) {
        final byte[][] r = new byte[l][c];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                r[i][j] = f;
            }
        }
        return r;
    }

    //************************************************************************ ==

    public static byte[] fill(final byte f, final int n) {
        final byte[] r = new byte[n];
        Arrays.fill(r, f);
        return r;
    }

    public static ByteVector fill(final byte f, final int n, final ByteVector v) {
        v.resetMatrix(fill(f, n));
        return v;
    }

    public static ByteVector fill(final byte f, final int n, final Supplier<ByteVector> s) {
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
    public static byte[] linearizeLine(final byte[][] square) {
        final int width = square[0].length;
        final int height = square.length;
        final byte[] line = new byte[height * width];
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
     * @return <span class="fr">matrice ligne constituée de la concaténation des lignes de la matrice carrée</span>
     */
    public static byte[] linearizeColumn(final byte[][] square) {
        final int width = square[0].length;
        final int height = square.length;
        final byte[] line = new byte[height * width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                line[i + j * width] = square[i][j];
            }
        }
        return line;
    }
}
