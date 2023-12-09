package com.cosmoloj.math.tabular.core;

import com.cosmoloj.math.tabular.line.TypedVector;
import com.cosmoloj.math.tabular.matrix.TypedMatrix;
import java.util.Arrays;

/**
 *
 * @author Samuel Andrés
 */
public final class TypedTabulars {

    private TypedTabulars() {
    }

    public static boolean equal(final Object[] a, final Object[] b) {
        return Arrays.equals(a, b);
    }

    public static <K> boolean equal(final TypedVector<K> a, final TypedVector b) {
        return equal(a.getMatrix(), b.getMatrix());
    }

    public static boolean equal(final Object[][] a, final Object[][] b) {
        return Arrays.deepEquals(a, b);
    }

    public static <K> boolean equal(final TypedMatrix<K> a, final TypedMatrix<K> b) {
        return equal(a.getMatrix(), b.getMatrix());
    }

    //************************************************************************ ==

    /**
     *
     * @param square <span class="fr">matrice carrée</span>
     * @return <span class="fr">matrice ligne constituée de la concaténation des lignes de la matrice carrée</span>
     * @param <T>
     */
    public static <T> T[] linearizeLine(final T[][] square) {
        final int width = square[0].length;
        final int height = square.length;
        final T[] line = (T[]) new Object[height * width];
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
     * @param <T>
     */
    public static <T> T[] linearizeColumn(final T[][] square) {
        final int width = square[0].length;
        final int height = square.length;
        final T[] line = (T[]) new Object[height * width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                line[i + j * width] = square[i][j];
            }
        }
        return line;
    }
}
